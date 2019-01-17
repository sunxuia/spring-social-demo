package net.sunxu.study.weibo;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.web.client.HttpClientErrorException;

/**
 * 微博接口的adapter
 */
public class WeiboAdapter implements ApiAdapter<Weibo> {

    @Override
    public boolean test(Weibo weibo) {
        try {
            weibo.getUserProfile();
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    @Override
    public void setConnectionValues(Weibo weibo, ConnectionValues values) {
        WeiboUserProfile profile = weibo.getUserProfile();
        values.setProviderUserId(String.valueOf(profile.getId()));
        values.setDisplayName(profile.getScreenName());
        values.setProfileUrl(profile.getUrl());
        values.setImageUrl(profile.getProfileImageUrl());
    }

    @Override
    public UserProfile fetchUserProfile(Weibo weibo) {
        WeiboUserProfile profile = weibo.getUserProfile();
        return new UserProfileBuilder()
                .setName(profile.getScreenName())
                .setUsername(profile.getName())
                .build();
    }

    @Override
    public void updateStatus(Weibo weibo, String message) {
        // not supported
    }
}
