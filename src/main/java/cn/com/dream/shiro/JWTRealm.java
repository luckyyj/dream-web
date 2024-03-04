package cn.com.dream.shiro;

import cn.com.dream.common.model.LoginUser;
import cn.com.dream.common.util.RedisUtil;
import cn.com.dream.user.service.UserService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 认证
 */
@Component
public class JWTRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 权限鉴权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LoginUser user = (LoginUser) principals.getPrimaryPrincipal();

        //用户权限列表
        // Set<String> permsSet = shiroService.getUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(null);
        return info;
    }

    /**
     * 登录状态鉴权
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();
        // 根据accessToken，查询用户信息
        String tokenVal = redisUtil.get("dream:login:" + accessToken);
        if (StrUtil.isBlank(tokenVal)) {
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        LoginUser loginUser = JSONUtil.toBean(tokenVal, LoginUser.class);
        // token失效
        if (loginUser.getExpireTime() < System.currentTimeMillis()) {
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(loginUser, accessToken, getName());
        return info;
    }
}
