package com.example.demo.util;

import com.example.demo.model.ApiResponse;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUtil {

    public static String getCurrentUser() {
        return (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

}
