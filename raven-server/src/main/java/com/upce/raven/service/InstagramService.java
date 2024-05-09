package com.upce.raven.service;

import com.github.instagram4j.instagram4j.exceptions.*;
import com.upce.raven.dto.*;

import java.util.concurrent.*;

public interface InstagramService {

    void getPostDetail(String url, String ravenUsername) throws IGLoginException, ExecutionException, InterruptedException;
    CombinedPostDetailsDTO getCombinedSavedPostDetail(String url);

}
