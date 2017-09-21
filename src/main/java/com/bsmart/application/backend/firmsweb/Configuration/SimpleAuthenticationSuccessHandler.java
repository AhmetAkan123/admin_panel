package com.bsmart.application.backend.firmsweb.Configuration;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        authorities.forEach(authority -> {

            // Success Handler //
            if (authority.getAuthority().equals(Role.ROLE_SUPER_ADMIN)) {
                try {
                    redirectStrategy.sendRedirect(request, response, "/admin/home");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    redirectStrategy.sendRedirect(request, response, "/");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

    }

}
