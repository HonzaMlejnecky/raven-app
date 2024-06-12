package com.upce.raven.util;

import jakarta.servlet.http.*;
import org.springframework.context.annotation.*;
import org.springframework.security.access.*;
import org.springframework.security.web.access.*;
import org.springframework.stereotype.*;

import java.io.*;

@Component
public class RavenAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException {
        System.out.println("Access Denied Error!");
    }

}
