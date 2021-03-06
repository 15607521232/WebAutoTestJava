package day14.cn.itcast.user.web.servlet;

import day12.JavaBeanLearn.domain.CommonUtils;
import day14.cn.itcast.user.domain.User;
import day14.cn.itcast.user.service.UserException;
import day14.cn.itcast.user.service.UserService14;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * UserServlet层
 */

public class LoginServlet14 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        req.setCharacterEncoding("utf-8");//请求编码
        resp.setContentType("text/html;charset=utf-8");//响应编码

        //依赖UserService
        UserService14 userService = new UserService14() ;

        User form = CommonUtils.toBean(req.getParameterMap(),User.class);

        try {
            User user = userService.login(form);
            req.getSession().setAttribute("sessionUser",user);
            resp.sendRedirect(req.getContextPath() + "/day14/usermng/welcome.jsp");
        } catch (UserException e) {
            req.setAttribute("msg",e.getMessage());
            req.setAttribute("usermng",form);
            req.getRequestDispatcher("/day14/usermng/login.jsp").forward(req,resp);
        }


    }
}
