package com.hearing.springsecuritygradledemo.handle;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by hearing on 18-9-2
 */

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        System.out.println("onAuthenticationSuccess...");
//        System.out.println(((UserDetails)(authentication.getPrincipal())).getUsername());
//        getRedirectStrategy().sendRedirect(request, response, "loginSuccess");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
