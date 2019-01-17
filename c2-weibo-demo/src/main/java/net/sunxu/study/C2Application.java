package net.sunxu.study;

import net.sunxu.study.weibo.WeiboConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;

@Configuration
@SpringBootApplication
public class C2Application {
    public static void main(String[] args) {
        SpringApplication.run(C2Application.class, args);
    }

    @Value("${weibo.client.clientId}")
    private String weiboClientId;

    @Value("${weibo.client.clientSecret}")
    private String weiboClientSecret;

    /**
     * 用于创建和weibo 的连接的工厂
     *
     * @return
     */
    @Bean
    public ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(new WeiboConnectionFactory(weiboClientId, weiboClientSecret));
        return registry;
    }
}
