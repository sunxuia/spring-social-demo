package net.sunxu.study.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.*;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.weibo.connect.WeiboConnectionFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {
    // 注册链接工厂
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
                                       Environment environment) {
        connectionFactoryConfigurer.addConnectionFactory(new WeiboConnectionFactory(
                environment.getProperty("weibo.client.clientId"),
                environment.getProperty("weibo.client.clientSecret")));
        connectionFactoryConfigurer.addConnectionFactory(new GitHubConnectionFactory(
                environment.getProperty("github.client.clientId"),
                environment.getProperty("github.client.clientSecret")));
        connectionFactoryConfigurer.addConnectionFactory(new FacebookConnectionFactory(
                environment.getProperty("facebook.client.clientId"),
                environment.getProperty("facebook.client.clientSecret")));
    }

    // 持久化连接的仓库. 其中添加隐式注册设置, 不然会被引导到/signup 上去注册
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        repository.setConnectionSignUp(new CustomConnectionSignUp());
        return repository;
    }

    /**
     * 隐式注册接口
     */
    private class CustomConnectionSignUp implements ConnectionSignUp {
        private Map<String, UserProfile> store = new HashMap<>();

        @Override
        public String execute(Connection<?> connection) {
            UserProfile profile = connection.fetchUserProfile();
            store.put(profile.getId(), profile);
            return "custom_" + profile.getName();
        }
    }

    // 通过 UserIDSource 获得每个用户的唯一性标识. 来识别每个用户和对应的连接. 这个多个配置中有一个就够了
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    /**
     * 添加查看连接的bean
     *
     * @param connectionFactoryLocator
     * @param connectionRepository
     * @return
     */
    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,
                                               ConnectionRepository connectionRepository) {
        ConnectController controller = new ConnectController(connectionFactoryLocator, connectionRepository);
        controller.setApplicationUrl("http://127.0.0.1:8080/login/");
        return controller;
    }
}
