package net.sunxu.study.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.antMatcher("/**").authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().apply(springSocialConfigurer())
                .and().csrf().disable();
        // @formatter:on
    }

    private SpringSocialConfigurer springSocialConfigurer() {
        // 默认的url 是"/auth/{ProviderId}" 这里修改为"/login/{ProviderId}"
        SpringSocialConfigurer configurer = new SpringSocialConfigurer() {
            @Override
            protected <T> T postProcess(T object) {
                if (object instanceof SocialAuthenticationFilter) {
                    ((SocialAuthenticationFilter) object).setFilterProcessesUrl("/login");
                }
                return super.postProcess(object);
            }
        };
        return configurer;
    }
}
