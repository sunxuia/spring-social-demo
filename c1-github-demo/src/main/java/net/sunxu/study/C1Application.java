package net.sunxu.study;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@SpringBootApplication
public class C1Application {
    public static void main(String[] args) {
        SpringApplication.run(C1Application.class, args);
    }

    @Value("${github.client.clientId}")
    private String githubClientId;

    @Value("${github.client.clientSecret}")
    private String githubClientSecret;

    /**
     * 用于创建和github 的连接的工厂
     *
     * @return
     */
    @Bean
    public ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(new GitHubConnectionFactory(githubClientId, githubClientSecret));
        return registry;
    }
}
