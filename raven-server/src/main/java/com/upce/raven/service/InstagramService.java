package com.upce.raven.service;

import com.upce.raven.dto.*;

import java.util.concurrent.*;

public interface InstagramService {

    void getPostDetail(String url, String ravenUsername) throws ExecutionException, InterruptedException;
    CombinedPostDetailsDTO getCombinedSavedPostDetail(String url);

}
