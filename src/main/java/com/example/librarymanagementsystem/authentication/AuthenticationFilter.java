package com.example.librarymanagementsystem.authentication;

import com.example.librarymanagementsystem.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;
import java.util.Optional;

/**
 * Custom filter for API key authentication.
 * This filter intercepts incoming requests and attempts to authenticate them using an API key.
 */
public class AuthenticationFilter extends GenericFilterBean {

    /**
     * Performs authentication using an API key for incoming requests.
     * If authentication succeeds, the authenticated user is set in the security context.
     * If authentication fails, an unauthorized response is sent back.
     *
     * @param request     the incoming servlet request
     * @param response    the servlet response to send
     * @param filterChain the filter chain to continue processing the request
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        // Attempt to authenticate the request using an API key
        Optional<Authentication> authResult = AuthenticationService.getAuthentication((HttpServletRequest) request);

        // Check if authentication succeeded
        if (authResult.isEmpty()) {
            // Authentication failed, send an unauthorized response
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
            return;
        }

        // Authentication succeeded, set the authenticated user in the security context
        Authentication authentication = authResult.get();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue processing the request
        filterChain.doFilter(request, response);
    }
}
