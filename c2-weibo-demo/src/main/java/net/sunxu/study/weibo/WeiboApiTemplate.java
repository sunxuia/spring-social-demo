package net.sunxu.study.weibo;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

import java.util.Map;

/**
 * 微博接口的实现
 */
public class WeiboApiTemplate extends AbstractOAuth2ApiBinding implements WeiboApi {

    private String accessToken, uid;

    public WeiboApiTemplate(String accessToken, String uid) {
        super(accessToken);
        this.accessToken = accessToken;
        this.uid = uid;
    }

    /**
     * 获取用户基本信息
     *
     * @return
     */
    @Override
    public WeiboUserProfile getUserProfile() {
        String url = "https://api.weibo.com/2/users/show.json?access_token=" + accessToken + "&uid=" + uid;
        Map<String, Object> result = getRestTemplate().getForObject(url, Map.class);

        // 为了省事, 这里只写了一部分的设置的值
        WeiboUserProfile userInfo = new WeiboUserProfile();
        userInfo.setId(Long.parseLong((String) result.get("idstr")));
        userInfo.setName((String) result.get("name"));
        userInfo.setProfileImageUrl((String) result.get("profile_image_url"));
        userInfo.setUrl("https://weibo.com/" + result.get("profile_url"));
        userInfo.setScreenName((String) result.get("screen_name"));
        return userInfo;
    }
}
