package net.sunxu.study.weibo;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;

/**
 * 获得微博的连接的工厂
 */
public class WeiboConnectionFactory extends OAuth2ConnectionFactory<WeiboApi> {

    public WeiboConnectionFactory(String clientId, String clientSecret) {
        super("weibo", new WeiboServiceProvider(clientId, clientSecret), new WeiboAdapter());
    }
}
