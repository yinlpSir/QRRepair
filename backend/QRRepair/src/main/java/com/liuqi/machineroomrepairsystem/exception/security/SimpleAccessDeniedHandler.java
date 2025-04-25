package com.liuqi.machineroomrepairsystem.exception.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 授权失败处理
 *
 * This access denied handler implementation returns 403 Forbidden whenever an
 * authenticated user accesses a secured REST end-point without the necessary
 * permission.
 */
@Component
public class SimpleAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * The 403 JSON response body.
     */
    private static final String ERROR_MESSAGE = "{ \"msg\": \"%s\" }";

    /**
     * Handle the access denied exception.
     *
     * @param request
     *          the request
     * @param response
     *          the response
     * @param accessDeniedException
     *          the access denied exception
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);//403
        response.getOutputStream().println(String.format(ERROR_MESSAGE, accessDeniedException.getMessage()));
    }
}
