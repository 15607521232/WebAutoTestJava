package day19.BaseServletLearn;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public  class Day19Aservlet extends Day19BaseServlet {

    public void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        System.out.println("addUser()...");
        throw new IOException("测试一下");
    }

    public void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        System.out.println("editUser()...");

    }


    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        System.out.println("deleteUser()...");

    }


    public void loadUser(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        System.out.println("loadUser()...");

    }

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        System.out.println("findAll()...");

    }






}
