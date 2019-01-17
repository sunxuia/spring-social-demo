package net.sunxu.study.weibo;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.web.client.HttpClientErrorException;

/**
 * 微博接口的adapter
 */
public class WeiboAdapter implements ApiAdapter<WeiboApi> {

    @Override
    public boolean test(WeiboApi weiboApi) {
        try {
            weiboApi.getUserProfile();
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    @Override
    public void setConnectionValues(WeiboApi weiboApi, ConnectionValues values) {
        WeiboUserProfile profile = weiboApi.getUserProfile();
        values.setProviderUserId(String.valueOf(profile.getId()));
        values.setDisplayName(profile.getScreenName());
        values.setProfileUrl(profile.getUrl());
        values.setImageUrl(profile.getProfileImageUrl());
    }

    @Override
    public UserProfile fetchUserProfile(WeiboApi weiboApi) {
        WeiboUserProfile profile = weiboApi.getUserProfile();
        return new UserProfileBuilder()
                .setName(profile.getScreenName())
                .setUsername(profile.getName())
                .build();
    }

    @Override
    public void updateStatus(WeiboApi weiboApi, String message) {
        // not supported
    }
}
