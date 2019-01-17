package net.sunxu.study;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;
import org.thymeleaf.expression.Lists;

import java.util.Arrays;
import java.util.Collection;

// SpringSocialConfigurer 的配置中要求生成的组件. 这个在第三方登录成功并确定本地用户id 之后会被调用用来获得本地用户数据.
@Component
public class CustomSocialUserDetailsService implements SocialUserDetailsService {
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return new CustomSocialUserDetails(userId);
    }
}

class CustomSocialUserDetails implements SocialUserDetails {
    private String userId;

    public CustomSocialUserDetails(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("NORMAL"));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

