package bingege.movie.common.service;

import bingege.movie.common.model.User;

import java.util.Optional;

public interface UserService {
    /**
     * 获取用户
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 初始化一个管理员
     * @return
     */
    Optional<User> initAdmin();

    /**
     * 更新登录时间
     * @param username
     */
    void fresh(String username);
}
