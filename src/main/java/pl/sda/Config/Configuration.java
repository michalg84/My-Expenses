package pl.sda.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.sda.service.user.UserDetailsServiceImpl;

/**
 * Created by Michał Gałka on 2017-04-29.
 */
@org.springframework.context.annotation.Configuration
@EnableWebSecurity

public class Configuration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/register", "/login/error", "/table/transaction").permitAll()

                //                .antMatchers("/save").hasAnyAuthority("ADMIN")
                //                .antMatchers("/user/**").hasRole("USER")

                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and().headers().frameOptions().sameOrigin()
                .and()
                .csrf()
                .disable()       //wyłącznie tokena
                //                .and()
                .formLogin()
                .loginPage("/login")
                //                .loginProcessingUrl("/transaction/list")
                .failureForwardUrl("/login/error")
                .successForwardUrl("/user/account")
                .passwordParameter("password")
                .usernameParameter("username")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("remember-me")
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .rememberMe();


    }

    //    @Override
    //    public void init(WebSecurity web) throws Exception {
    //        web
    //                .ignoring().antMatchers("h2/console/**");
    //    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(bCryptPasswordEncoder());
    }
}
