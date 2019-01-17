package net.sunxu.study.weibo;

import org.springframework.social.ApiBinding;

/**
 * 微博的接口, 通过这个接口和服务平台进行交互, 可以在这里添加更多的和用户相关的交互操作.
 * 参考压缩包内的sdk 可以获得更多的相关数据.
 */
public interface WeiboApi extends ApiBinding {
    /**
     * 获得用户信息
     *
     * @return
     */
    WeiboUserProfile getUserProfile();
}
