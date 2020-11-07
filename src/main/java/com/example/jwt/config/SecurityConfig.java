package com.example.jwt.config;

import com.example.jwt.filter.JwtFilter;
import com.example.jwt.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // what is the use of EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService userDetailService;
    @Autowired
    private JwtFilter jwtFilter;
    //step 1
    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        System.out.println("******************************* SecurityConfig configure START");
        // here we are telling Authentication manager to what type authentication we are using
        // eg. database authentication or inmemory authentication
        auth.userDetailsService(userDetailService);
        System.out.println("******************************* SecurityConfig configure END");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        System.out.println("******************************* SecurityConfig passwordEncoder START");
        return new BCryptPasswordEncoder();
    }


    @Bean(name= BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

   // here we are providing route, it mean which all url can use security and which url can not
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").and().antMatchers("/user/**").hasRole("USER").antMatchers("/**").permitAll()
        http.cors().and().csrf().disable().authorizeRequests().antMatchers("/authenticate").permitAll()
                  .anyRequest().authenticated()
                  .and().exceptionHandling().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
    // we are not
}
