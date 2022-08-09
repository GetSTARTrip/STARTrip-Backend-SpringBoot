package com.startrip.core.entity.auth;

import com.startrip.core.jwt.TokenProvider;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException, IOException {
        String token = tokenProvider.createToken((OAuth2AuthenticationToken) authentication);

        String uri =  UriComponentsBuilder.fromUriString("/api/user/auth/success")
                .queryParam("token", token)
                .build().toString();

        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, uri);
    }
}
