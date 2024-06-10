package com.upce.raven.util;

import jakarta.servlet.http.*;
import org.springframework.security.access.*;
import org.springframework.security.web.access.*;

import java.io.*;

public class RavenAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException {
        System.out.println("Access Denied Error!");
    }

}
