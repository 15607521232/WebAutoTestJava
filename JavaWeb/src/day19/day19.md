### BaseServlet

    1. 我们希望在一个Servlet中可以有多个请求处理方法！
    2. 客户端发送请求时，必须多给出一个参数，用来说明要调用的方法
      请求处理方法的签名必须与service相同，即返回值和参数，以及声明的异常都相同！
    3. 客户端必须传递名为method的参数！
    
    domain: User
    dao: UserDao
    service: UserService
    servlet: UserServlet
    
    void init(ServletConfig config)
    void destory()
    void service(ServletRequest, ServletResponse)
      throws IOException, ServletException {
        在这里让它去调用其他方法！
        要求：用户发出请求时，必须给出一个参数，来说明要调用哪一个方法
        // 获取参数，通过参数名称来确定要调用的方法  
    }
    
    public void addUser() {
    }
    
    http://lcoalhost:8080/xxx/AServlet?m=addUser
