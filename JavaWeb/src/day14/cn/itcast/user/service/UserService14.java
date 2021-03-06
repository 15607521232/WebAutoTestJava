package day14.cn.itcast.user.service;

import day14.cn.itcast.user.dao.UserDao;
import day14.cn.itcast.user.domain.User;

/**
 * User的业务层
 */

public class UserService14 {
    private UserDao userDao = new UserDao();

    public void register(User user) throws UserException{
        /**
         * 使用用户名去查询，如果返回null,完成添加
         * 如果返回的不是null,抛出异常
         */

        User _user  = userDao.findByUsername(user.getUsername());
        if(_user != null) throw new UserException("用户名" + user.getUsername() + ", 已被注册过了");

        userDao.addUser(user);
    }

    /**
     * 登录功能
     *
     */
    public User login(User form) throws UserException{
        User user = userDao.findByUsername(form.getUsername());

        if(user == null) throw new UserException("用户名不存在");

        if(!form.getPassword().equals(user.getPassword())){
            throw new UserException("密码错误！");
        }

        return user;

    }

}
