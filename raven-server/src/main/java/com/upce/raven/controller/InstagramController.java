package com.upce.raven.controller;

import com.upce.raven.dto.*;
import com.upce.raven.service.*;
import com.upce.raven.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.*;

@RestController
@RequestMapping("ig")
public class InstagramController {

    @Autowired
    private InstagramService instagramService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity getPostDetail(@RequestHeader(name = "Authorization") String token, @RequestParam(name = "ig-url") String igUrl, @RequestParam(name = "username") String username) {
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
    ResponseEntity getCombinedDetailsOfPost(@RequestHeader(name = "Authorization") String token, @RequestParam(name = "ig-shortcode") String igShortcode) {
        CombinedPostDetailsDTO data = instagramService.getCombinedSavedPostDetail(igShortcode);

        return ResponseEntity.ok().body(data);
    }

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    ResponseEntity getIgHealth() {
        return ResponseEntity.status(201).body("Healthy!");
    }

}
