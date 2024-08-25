package com.hubo.gillajabi.global.common.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
//
//@Component
//@Slf4j
//public class GraphQLLoggingFilter extends OncePerRequestFilter {
//
//    private final ObjectMapper objectMapper;
//
//    public GraphQLLoggingFilter() {
//        this.objectMapper = new ObjectMapper();
//        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        if (isGraphQLRequest(request)) {
//            ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
//            filterChain.doFilter(wrappedRequest, response);
//
//            String body = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
//            try {
//                Object json = objectMapper.readValue(body, Object.class);
//                String prettyJson = objectMapper.writeValueAsString(json);
//                log.info("GraphQL Request Body:\n{}", prettyJson);
//            } catch (IOException e) {
//                log.error("Error parsing GraphQL request body", e);
//                log.info("GraphQL Request Body (unparsed): {}", body);
//            }
//        } else {
//            filterChain.doFilter(request, response);
//        }
//    }
//
//    private boolean isGraphQLRequest(HttpServletRequest request) {
//        return request.getRequestURI().endsWith("/graphql");
//    }
//}