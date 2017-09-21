package com.bsmart.application.backend.firmsweb.Configuration;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Role;
import com.bsmart.application.backend.firmsweb.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties security;

    @Autowired
    private UserService userService;

    @Value("${app.secret}")
    private String applicationSecret;

    @Autowired
    private SimpleAuthenticationSuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/register").permitAll()
                .antMatchers("/user/activate").permitAll()
                .antMatchers("/user/activation-send").permitAll()
                .antMatchers("/user/reset-password").permitAll()
                .antMatchers("/user/reset-password-change").permitAll()
                .antMatchers("/admin/**").hasAnyAuthority(Role.ROLE_SUPER_ADMIN)
                .antMatchers("/user/autologin").hasAnyAuthority(Role.ROLE_SUPER_ADMIN)
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successHandler).loginPage("/login").failureUrl("/login?error").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .and()
                .rememberMe().key(applicationSecret)
                .tokenValiditySeconds(31536000)
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**",
                "/js/**", "/images/**", "/libs/**", "/img/**", "/fonts/**");
    }

}
