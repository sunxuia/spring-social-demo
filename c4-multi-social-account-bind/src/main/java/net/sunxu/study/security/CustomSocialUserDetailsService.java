package net.sunxu.study.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

// SpringSocialConfigurer 的配置中要求生成的组件. 这个在第三方登录成功并确定本地用户id 之后会被调用用来获得本地用户数据.
@Component
public class CustomSocialUserDetailsService implements SocialUserDetailsService {
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return new CustomSocialUserDetails(userId);
    }
}

