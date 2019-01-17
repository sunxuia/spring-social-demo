package net.sunxu.study;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.github.api.GitHub;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {
    @Autowired
    private ConnectionFactoryLocator factoryLocator;

    /**
     * 发起认证请求
     *
     * @return
     */
    @GetMapping("/login/github")
    public String githubLogin() {
        GitHubConnectionFactory githubFactory =
                (GitHubConnectionFactory) factoryLocator.getConnectionFactory(GitHub.class);
        OAuth2Operations oauth2Operations = githubFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri("http://127.0.0.1:8080/login/github");
        String authorizeUrl = oauth2Operations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
        return "redirect:" + authorizeUrl;
    }

    /**
     * 成功获得code, 请求access_token 和用户信息
     *
     * @param code
     * @return
     */
    @GetMapping(value = "/login/github", params = {"code"})
    @ResponseBody
    public String githubLoginCallback(@RequestParam("code") String code) {
        GitHubConnectionFactory githubFactory =
                (GitHubConnectionFactory) factoryLocator.getConnectionFactory(GitHub.class);
        // 获取access_token
        OAuth2Operations oauth2Operations = githubFactory.getOAuthOperations();
        AccessGrant accessGrant = oauth2Operations.exchangeForAccess(code, "http://127.0.0.1:8080/login/github", null);

        // 获取用户的连接信息
        Connection<GitHub> connection = githubFactory.createConnection(accessGrant);
        return connection.getDisplayName();
    }

    /**
     * 错误
     *
     * @param error
     * @param errorDescription
     * @return
     */
    @GetMapping(value = "/login/github", params = {"error", "error_description"})
    @ResponseBody
    public String githubLoginError(@RequestParam("error") String error,
                                   @RequestParam("error_description") String errorDescription) {
        return error + "\n" + errorDescription;
    }
}
