package net.sunxu.study.weibo;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * 微博连接的工厂
 */
public class WeiboServiceProvider extends AbstractOAuth2ServiceProvider<WeiboApi> {

    public WeiboServiceProvider(String clientId, String clientSecret) {
        super(createOAuth2Template(clientId, clientSecret));
    }

    private static OAuth2Template createOAuth2Template(String clientId, String clientSecret) {
        WeiboOAuth2Template oAuth2Template = new WeiboOAuth2Template(clientId, clientSecret,
                "https://api.weibo.com/oauth2/authorize",
                "https://api.weibo.com/oauth2/access_token");
        oAuth2Template.setUseParametersForClientAuthentication(true);
        return oAuth2Template;
    }

    /**
     * 获得微博接口
     * @param accessToken access_token, 这个access_token 在获取的时候加入了uid 的信息, 通过分号(;) 分隔
     * @return 获得的微博接口
     */
    @Override
    public WeiboApi getApi(String accessToken) {
        String realAccessToken = accessToken.split(";")[0];
        String uid = accessToken.split(";")[1];
        return new WeiboApiTemplate(realAccessToken, uid);
    }
}