package net.sunxu.study.weibo;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * 获得微博的连接的工厂
 */
public class WeiboConnectionFactory extends OAuth2ConnectionFactory<Weibo> {

    public WeiboConnectionFactory(String clientId, String clientSecret) {
        super("weibo", new WeiboServiceProvider(clientId, clientSecret), new WeiboAdapter());
    }
}
