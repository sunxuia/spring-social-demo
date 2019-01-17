package net.sunxu.study.weibo;

import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

import java.util.Map;

/**
 * 因为微博获取用户信息需要access_token 和uid, 而默认的实现只包含access_token 的情况, 所以要重写获取access_token 的代码,
 * 在access_token 中加入uid 然后在通过这个access_token 获取用户数据的时候再拆分字符串分别获得access_token 和uid 然后传递给
 * 服务器获得用户数据.
 */
public class WeiboOAuth2Template extends OAuth2Template {
    public WeiboOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        Map<String, Object> result = (Map<String, Object>)
                getRestTemplate().postForObject(accessTokenUrl, parameters, Map.class, new Object[0]);
        if (result == null) {
            throw new RestClientException("access token endpoint returned empty result");
        } else {
            return new AccessGrant(
                    result.get("access_token") + ";" + result.get("uid"),
                    null,
                    null,
                    Long.parseLong(result.get("expires_in").toString()));
        }
    }
}
