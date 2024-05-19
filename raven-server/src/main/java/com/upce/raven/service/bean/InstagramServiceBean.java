package com.upce.raven.service.bean;

import com.upce.raven.constant.*;
import com.upce.raven.dao.*;
import com.upce.raven.dto.*;
import com.upce.raven.entity.*;
import com.upce.raven.service.*;
import jakarta.annotation.*;
import okhttp3.*;
import org.json.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@Service
public class InstagramServiceBean implements InstagramService {

    final Logger LOG = LoggerFactory.getLogger(InstagramServiceBean.class);

    private static final String ENCODING_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
    protected Executor executor = Executors.newCachedThreadPool();
    protected static OkHttpClient client = new OkHttpClient().newBuilder().followRedirects(false).build();

    @Autowired
    private InstagramPostDAO instagramPostDAO;

    @Autowired
    private RavenUserDAO ravenUserDAO;

    @Override
    public void getPostDetail(String url, String ravenUsername) throws ExecutionException, InterruptedException {
        var shortcode = url.split("/p/")[1].split("/")[0];
        var vars = new JSONObject();
        vars.put("shortcode", shortcode);
        vars.put("fetch_comment_count",4);
        vars.put("fetch_tagged_user_count",0);

        var body = new FormBody.Builder()
                .addEncoded("doc_id", "10015901848480474")
                .addEncoded("variables", vars.toString())
                .addEncoded("lsd","raven")
                .build();
        var req = new Request.Builder()
                .url("https://www.instagram.com/api/graphql")
                .headers(Headers.of(Constants.BASE_HEADERS))
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("user-agent", Constants.WEB_USER_AGENT)
                .addHeader("x-fb-lsd", "raven")
                .method("POST",body)
                .build();

      try (var res = callIG(req, null)) {
          var json = new JSONObject(res.body().string());
          var data = json.getJSONObject("data");
          var shortcode_media = data.getJSONObject("xdt_shortcode_media");

          RavenUser ravenUser = ravenUserDAO.findByUsername(ravenUsername);

          InstagramPost instagramPost = new InstagramPost();
          instagramPost.setCreatedAt(Calendar.getInstance().getTime());
          if (shortcode_media.getJSONObject("edge_media_to_caption").getJSONArray("edges").getJSONObject(0).getJSONObject("node").getString("text").length() > 254) {
              instagramPost.setDescription(shortcode_media.getJSONObject("edge_media_to_caption").getJSONArray("edges").getJSONObject(0).getJSONObject("node").getString("text").substring(0, 254));
          } else {
              instagramPost.setDescription(shortcode_media.getJSONObject("edge_media_to_caption").getJSONArray("edges").getJSONObject(0).getJSONObject("node").getString("text"));
          }
          instagramPost.setPosterUsername(shortcode_media.getJSONObject("owner").getString("username"));
          instagramPost.setVideo(shortcode_media.getBoolean("is_video"));
          instagramPost.setNumOfLikes(shortcode_media.getJSONObject("edge_media_preview_like").getLong("count"));
          instagramPost.setNumOfComments((shortcode_media.getJSONObject("edge_media_to_comment").getLong("count")));
          instagramPost.setShortcode(shortcode);
          instagramPost.setRavenUser(ravenUser);
          instagramPost.setImageUrl(shortcode_media.getString("display_url"));

          instagramPostDAO.create(instagramPost);
          LOG.info("Saved post {} to DB", instagramPost);
      } catch (IOException e) {
          LOG.error("Error when saving IG post {}", e.getMessage());
          throw new RuntimeException(e);
      }

    }

    @Override
    public CombinedPostDetailsDTO getCombinedSavedPostDetail(String shortcode) {
        List<InstagramPost> instagramPosts = instagramPostDAO.getPostsTimeline(shortcode);
        InstagramPost instagramPost = instagramPostDAO.getPostDetails(shortcode);

        CombinedPostDetailsDTO dto = new CombinedPostDetailsDTO();
        dto.setCreatedAt(instagramPost.getCreatedAt());
        dto.setDescription(instagramPost.getDescription());
        dto.setVideo(instagramPost.isVideo());
        dto.setPosterUsername(instagramPost.getPosterUsername());
        dto.setNumberOfCommentsTotal(instagramPost.getNumOfComments());
        dto.setNumOfLikesTotal(instagramPost.getNumOfLikes());
        dto.setPostTimelineDTOS(new ArrayList<PostTimelineDTO>());
        dto.setPostTimelineCommentsDTOS(new ArrayList<PostTimelineCommentsDTO>());
        dto.setImageUrl(instagramPost.getImageUrl());

        for (InstagramPost ig : instagramPosts) {
            PostTimelineDTO timelineDTO = new PostTimelineDTO();
            timelineDTO.setDatetime(ig.getCreatedAt());
            timelineDTO.setNumOfLikes(ig.getNumOfLikes());
            dto.getPostTimelineDTOS().add(timelineDTO);
        }

        for (InstagramPost ig : instagramPosts) {
            PostTimelineCommentsDTO timelineDTO = new PostTimelineCommentsDTO();
            timelineDTO.setDatetime(ig.getCreatedAt());
            timelineDTO.setNumOfComments(ig.getNumOfComments());
            dto.getPostTimelineCommentsDTOS().add(timelineDTO);
        }

        return dto;
    }

    @Override
    public UserTrackedPostsDTO getUserTrackedPosts(String username) {
        List<String> instagramPostShortcodes = instagramPostDAO.getUserTrackedPosts(username);
        UserTrackedPostsDTO userTrackedPostsDTO = new UserTrackedPostsDTO();
        userTrackedPostsDTO.setUsername(username);
        userTrackedPostsDTO.setPostShortDTOs(new ArrayList<>());
        for (String instagramShortcode : instagramPostShortcodes) {
            InstagramPost instagramPost = instagramPostDAO.getPostDetails(instagramShortcode);

            PostShortDTO postShortDTO = new PostShortDTO();
            postShortDTO.setPosterUsername(instagramPost.getPosterUsername());
            postShortDTO.setDescription(instagramPost.getDescription());
            postShortDTO.setNumberOfLikes(instagramPost.getNumOfLikes());
            postShortDTO.setShortcode(instagramPost.getShortcode());

            userTrackedPostsDTO.getPostShortDTOs().add(postShortDTO);
        }

        return userTrackedPostsDTO;
    }

    private Response callIG(Request request, @Nullable Object info) throws IOException {
        var res = client.newCall(request).execute();
        if (res.isSuccessful()){
            return res;
        } else {
            var body = res.body().string();
            try {
                var json = new JSONObject(body);
                var message = json.getString("message");
                if (message.equals("checkpoint_required")) {
                    LOG.error("ERROR CANNOT CALL INSTAGRAM API! - Checkpoint required!");
                } else if (message.equals("Please wait a few minutes before you try again.")) {
                    LOG.error("ERROR CANNOT CALL INSTAGRAM API! - Limit exceeded");
                } else if (message.startsWith("The password you entered is incorrect")){
                    LOG.error("ERROR CANNOT CALL INSTAGRAM API! - Wrong Details");
                } else if (message.startsWith("The username you entered doesn't appear to belong to an account")) {
                    LOG.error("ERROR CANNOT CALL INSTAGRAM API! - Wrong Details");
                } else {
                    LOG.error("ERROR CANNOT CALL INSTAGRAM API! - Unrecognized error!");
                }
            } catch (JSONException e){
                LOG.error("Error parsing JSON {}", e);
                throw e;
            }
        }
        return res;
    }
}
