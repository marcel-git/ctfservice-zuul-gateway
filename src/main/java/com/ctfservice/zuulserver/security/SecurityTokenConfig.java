package com.ctfservice.zuulserver.security;

import com.ctfservice.zuulserver.Filter.JwtConfig;
import com.ctfservice.zuulserver.Filter.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                    //stateless session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    //handle unauthorized attempts
                    .exceptionHandling().authenticationEntryPoint((req,rsp,e)->rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                    //add a filter to validate the tokens with every request
                    .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    // allow all who are accessing "auth" service
                    .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
                    // must be an admin
                    .antMatchers(HttpMethod.POST,"/content").hasAuthority("admin")
                    .antMatchers(HttpMethod.PUT,"/content/**").hasAuthority("admin")
                    .antMatchers(HttpMethod.DELETE,"/content/**").hasAuthority("admin")
                    .antMatchers(HttpMethod.GET, "/user/**").hasAuthority("admin")
                    //accessible by user
                    //accessible by public
                    .antMatchers(HttpMethod.GET,"/content/**").permitAll()
                    .antMatchers(HttpMethod.OPTIONS,"**").permitAll()

                    // Any other request must be authenticated
                    .anyRequest().authenticated();

    }

    @Bean
    public JwtConfig jwtConfig(){
        return new JwtConfig();
    }
}
