package com.gaoxi.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gaoxi.controller.redis.RedisServiceTemp;
import com.gaoxi.entity.user.*;
import com.gaoxi.facade.redis.RedisService;
import com.gaoxi.facade.user.UserService;
import com.gaoxi.req.BatchReq;
import com.gaoxi.req.user.*;
import com.gaoxi.rsp.Result;
import com.gaoxi.utils.KeyGenerator;
import com.gaoxi.utils.RedisPrefixUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: lizhaowen
 * @Date: 2020/9/10 09:45
 * @Description: TODO
 */
@RestController
public class UserControllerImpl implements UserController{

    @Reference(version = "1.0.0")
    private UserService userService;

    @Reference(version = "1.0.0")
    private RedisService redisService;

    /** HTTP Response中Session ID 的名字 */
    @Value("${session.SessionIdName}")
    private String sessionIdName;

    @Override
    public Result login(LoginReq loginReq, HttpServletResponse httpRsp) {
        // 登录鉴权
        UserEntity userEntity = userService.login(loginReq);

        // 登录成功
        doLoginSuccess(userEntity, httpRsp);
        return Result.newSuccessResult(userEntity);
    }

    /**
     * 处理登录成功
     * @param userEntity 用户信息
     * @param httpRsp HTTP响应参数
     */
    private void doLoginSuccess(UserEntity userEntity, HttpServletResponse httpRsp) {
        // 生成SessionID
        String sessionID = RedisPrefixUtil.SessionID_Prefix + KeyGenerator.getKey();

        // 将 SessionID-UserEntity 存入Redis
        // TODO 暂时存储本地
//        redisService.set(sessionID, userEntity, sessionExpireTime);
        RedisServiceTemp.userMap.put(sessionID, userEntity);

        // 将SessionID存入HTTP响应头
        Cookie cookie = new Cookie(sessionIdName, sessionID);
        httpRsp.addCookie(cookie);
    }
    @Override
    public Result logout(HttpServletRequest httpReq, HttpServletResponse httpRsp) {
        return null;
    }

    @Override
    public Result register(RegisterReq registerReq, HttpServletResponse httpRsp) {
        return null;
    }

    @Override
    public Result isLogin(HttpServletRequest request) {
        return null;
    }

    @Override
    public Result<List<UserEntity>> findUsers(UserQueryReq userQueryReq) {
        return null;
    }

    @Override
    public Result batchUpdateUserState(BatchReq<UserStateReq> userStateReqs) {
        return null;
    }

    @Override
    public Result createAdminUser(AdminCreateReq adminCreateReq) {
        return null;
    }

    @Override
    public Result<List<RoleEntity>> findRoles() {
        return null;
    }

    @Override
    public Result deleteRole(String roleId) {
        return null;
    }

    @Override
    public Result updateMenuOfRole(RoleMenuReq roleMenuReq) {
        return null;
    }

    @Override
    public Result updatePermissionOfRole(RolePermissionReq rolePermissionReq) {
        return null;
    }

    @Override
    public Result<List<PermissionEntity>> findPermissions() {
        return null;
    }

    @Override
    public Result<List<MenuEntity>> findMenus() {
        return null;
    }

    @Override
    public Result<List<LocationEntity>> findLocations(HttpServletRequest httpReq) {
        return null;
    }

    @Override
    public Result<String> createLocation(LocationCreateReq locationCreateReq, HttpServletRequest httpReq) {
        return null;
    }

    @Override
    public Result deleteLocation(String locationId, HttpServletRequest httpReq) {
        return null;
    }

    @Override
    public Result modifyLocation(LocationUpdateReq locationUpdateReq, HttpServletRequest httpReq) {
        return null;
    }
}
