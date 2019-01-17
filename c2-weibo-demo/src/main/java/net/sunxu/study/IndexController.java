package net.sunxu.study;

import net.sunxu.study.weibo.WeiboApi;
import net.sunxu.study.weibo.WeiboConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @Autowired
    private ConnectionFactoryLocator factoryLocator;

    /**
     * 发起认证请求
     *
     * @return
     */
    @GetMapping("/login/weibo")
    public String weiboLogin() {
        WeiboConnectionFactory weiboFactory =
                (WeiboConnectionFactory) factoryLocator.getConnectionFactory(WeiboApi.class);
        OAuth2Operations oauth2Operations = weiboFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri("http://127.0.0.1:8080/login/weibo");
        String authorizeUrl = oauth2Operations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
        return "redirect:" + authorizeUrl;
    }

    /**
     * 成功获得code, 请求access_token 和用户信息
     *
     * @param code
     * @return
     */
    @GetMapping(value = "/login/weibo", params = {"code"})
    @ResponseBody
    public String weiboLoginCallback(@RequestParam("code") String code) {
        WeiboConnectionFactory weiboFactory =
                (WeiboConnectionFactory) factoryLocator.getConnectionFactory(WeiboApi.class);
        // 获取access_token
        OAuth2Operations oauth2Operations = weiboFactory.getOAuthOperations();
        AccessGrant accessGrant = oauth2Operations.exchangeForAccess(code, "http://127.0.0.1:8080/login/weibo", null);

        // 获取用户的连接信息
        Connection<WeiboApi> connection = weiboFactory.createConnection(accessGrant);
        return connection.getDisplayName();
    }

    @GetMapping(value = "/login/weibo", params = {"error", "error_description"})
    @ResponseBody
    public String weiboLoginError(@RequestParam("error") String error,
                                  @RequestParam("error_description") String errorDescription) {
        return error + "\n" + errorDescription;
    }
}
