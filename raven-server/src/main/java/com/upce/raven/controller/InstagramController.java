package com.upce.raven.controller;

import com.upce.raven.dto.*;
import com.upce.raven.service.*;
import com.upce.raven.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.file.*;
import java.util.concurrent.*;

@RestController
@RequestMapping("ig")
public class InstagramController {

    @Autowired
    private InstagramService instagramService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity getPostDetail(@RequestHeader(name = "Authorization") String token, @RequestParam(value = "ig-url") String igUrl, @RequestParam(name = "username") String username) {
        try {
            instagramService.getPostDetail(igUrl, username);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body("Saved details to DB!");
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity getCombinedDetailsOfPost(@RequestHeader(name = "Authorization") String token, @RequestParam(value = "ig-shortcode") String igShortcode) {
        try {
            CombinedPostDetailsDTO data = instagramService.getCombinedSavedPostDetail(igShortcode);

            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            System.out.printf("Error! %s", e);
        }
        return ResponseEntity.status(500).body(null);
    }

    @RequestMapping(value = "/get-tracked-posts", method = RequestMethod.GET)
    ResponseEntity getUniquePostsForUser(@RequestHeader(name = "Authorization") String token, @RequestParam(value = "username") String username) {
        try {
            UserTrackedPostsDTO dto = instagramService.getUserTrackedPosts(username);

            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            System.out.printf("Error! %s", e);
        }
        return ResponseEntity.status(500).body(null);
    }

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    ResponseEntity getIgHealth() {
        return ResponseEntity.status(201).body("Healthy!");
    }

}
