package com.example.librarymanagementsystem.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

/**
 * Custom authentication token for API key authentication.
 * This class extends AbstractAuthenticationToken, which provides a base implementation of the Authentication interface.
 */
public class ApiKeyAuthentication extends AbstractAuthenticationToken {

    private final String apiKey;

    /**
     * Constructs a new instance of ApiKeyAuthentication with the provided API key and authorities.
     *
     * @param apiKey       the API key used for authentication
     * @param authorities  the collection of authorities granted to the authenticated user
     */
    public ApiKeyAuthentication(String apiKey, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.apiKey = apiKey;
        setAuthenticated(true); // Mark the authentication as authenticated
    }

    /**
     * Returns null as no credentials are required for API key authentication.
     *
     * @return null
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * Returns the API key used for authentication as the principal.
     *
     * @return the API key
     */
    @Override
    public Object getPrincipal() {
        return apiKey;
    }
}
