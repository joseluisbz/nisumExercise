package org.bz.nisum.ms.app.usuarios;

import org.bz.nisum.ms.app.usuarios.security.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment environment;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.addFilterAfter(new JWTAuthorizationFilter(environment), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/auth").permitAll()
			.antMatchers("/open-api/**").permitAll()
			.anyRequest().authenticated();
	}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("someUser")
                .password(passwordEncoder().encode("somePassword"))
                .authorities("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
