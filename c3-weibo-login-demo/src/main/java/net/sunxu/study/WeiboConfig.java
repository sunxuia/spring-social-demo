package net.sunxu.study;

import net.sunxu.study.weibo.WeiboConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.*;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableSocial
public class WeiboConfig extends SocialConfigurerAdapter {
    // 注册链接工厂
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
                                       Environment environment) {
        connectionFactoryConfigurer.addConnectionFactory(
                new WeiboConnectionFactory(environment.getProperty("weibo.client.clientId"),
                        environment.getProperty("weibo.client.clientSecret")));
    }

    // 持久化连接的仓库
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        repository.setConnectionSignUp(new CustomConnctionSignUp());
        return repository;
    }

    /**
     * 默认的第三方认证获得access token 之后, 如果在仓库内找不到连接对应的用户, 将会重定向到/signup 页面上注册.
     * 所以这里要实现这个接口来隐式的注册一个新的用户.
     */
    private class CustomConnctionSignUp implements ConnectionSignUp {
        private Map<String, UserProfile> store = new HashMap<>();

        @Override
        public String execute(Connection<?> connection) {
            UserProfile profile = connection.fetchUserProfile();
            store.put(profile.getId(), profile);
            return "weibo_" + profile.getId();
        }
    }

    // 通过 UserIDSource 获得每个用户的唯一性标识. 来识别每个用户和对应的连接.
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

}
