package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.authentication.ApiKeyAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service interface for authentication.
 */
@Service
public interface AuthenticationService {

    static final String AUTH_TOKEN_HEADER_NAME = "X-ADMIN-KEY";
    static final String AUTH_TOKEN = "ADMIN";

    /**
     * Retrieves authentication based on the API key provided in the request header.
     *
     * @param request The HTTP servlet request.
     * @return An optional authentication object. If authentication fails, an empty optional is returned.
     */

    public static Optional<Authentication> getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
            return Optional.empty(); // Return empty optional when authentication fails
        }
        return Optional.of(new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES));
    }
}