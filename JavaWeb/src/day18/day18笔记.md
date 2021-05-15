### 事务
    什么是事务?
    转账：
    1. 给张三账户减1000元
    2. 给李四账户加1000元
    
    当给张三账户减1000元后，抛出了异常！这会怎么样呢？我相信从此之后，张三再也不敢转账了。
    
    使用事务就可以处理这一问题：把多个对数据库的操作绑定成一个事务，要么都成功，要么都失败！
    
    ==============
    
    事物的特性：ACID
    
    * 原子性：事务中所有操作是不可再分割的原子单位。事务中所有操作要么全部执行成功，要么全部执行失败。
    * 一致性：事务执行后，数据库状态与其它业务规则保持一致。如转账业务，无论事务执行成功与否，参与转账的两个账号余额之和应该是不变的。
    * 隔离性：隔离性是指在并发操作中，不同事务之间应该隔离开来，使每个并发中的事务不会相互干扰。
    * 持久性：一旦事务提交成功，事务中所有的数据操作都必须被持久化到数据库中，即使提交事务后，数据库马上崩溃，在数据库重启时，也必须能保证通过某种机制恢复数据。
    
    ==============
    
    MySQL操作事务
    
    1. 开始事务：start transaction
    2. 结束事务：commit或rollback
    
    ==============
    
    JDBC事务
    
    1. 开始事务：con.setAutoCommit(false);
    2. 结束事务；con.commit()或con.rollback();
    
    ==============
    
    保存点
    
    保存点的是可以回滚到事务中的某个位置，而不是回滚整个事务。
    回滚到保存点不会结束事务。
    设置保存点：Savepoint sp = con.setSavepoint();
    回滚到保存点：con.rollback(sp);
    
    ==============
    
    事务隔离级别
    
    * 脏读：读到未提交
    * 不可重复读：两次读取不一致，读取到另一事务修改的记录
    * 幻读：两次读取不一致，读取到另一事务插入的记录
    
    --------------
    
    四大隔离级别
    * SERIALIZABLE（串行化）：对同一数据的访问是串行的，即非并发的，所以不会出现任何并发问题。易出现死锁，效率太低！不可用！
    * REPEATABLE READ（可重复读）：防止了脏读、不可重复读，但没有防止幻读
    * READ COMMITTED（读已提交）：防止了脏读，但没有防止不可重复读，以及幻读
    * READ UNCOMMITTED（读未提交）：可能出现所有并发问题，效率最高，但不可用！
    
    MySQL默认事务隔离级别为：REPEATABLE READ
    Oracle默认事务隔离级别为：READ COMMITTED
    
    --------------
    
    MySQL设置事务隔离级别
    * 查看：select @@tx_isolation
    * 设置：set transaction isolation level 四选一
    
    JDBC设置事务隔离级别
    con.setTransactionIsolation(四选一)
    
    ===============
    
    数据库连接池
    
    作用：使用池来管理连接的生命周期，节省资源，提高性能。
    java提供的连接池接口：javax.sql.DataSource，连接池厂商的连接池类需要实现这一接口。
    
    -------------
    
    DBCP
    
    jar：commons-pool.jar、commons-dbcp.jar
    
    BasicDataSource ds = new BasicDataSource();
    ds.setUsername("root");
    ds.setPassword("123");
    ds.setUrl("jdbc:mysql://localhost:3306/mydb1");
    ds.setDriverClassName("com.mysql.jdbc.Driver");
     		
    ds.setMaxActive(20); 
    ds.setMaxIdle(10); 
    ds.setInitialSize(10) ;
    ds.setMinIdle(2) ;
    ds.setMaxWait(1000) ;
    		
    Connection con = ds.getConnection();
    
    -------------
    
    C3P0
    
    jar：c3p0-0.9.2-pre1.jar、c3p0-oracle-thin-extras-0.9.2-pre1.jar、mchange-commons-0.2.jar
    
    ComboPooledDataSource ds = new ComboPooledDataSource();
    ds.setJdbcUrl("jdbc:mysql://localhost:3306/mydb1");
    ds.setUser("root");
    ds.setPassword("123");
    ds.setDriverClass("com.mysql.jdbc.Driver");
     		
    ds.setAcquireIncrement(5) ;
    ds.setInitialPoolSize(20) ;
    ds.setMinPoolSize(2) ;
    ds.setMaxPoolSize(50) ;
    		
    Connection con = ds.getConnection();
    
    -------------
    
    C3P0配置文件
    
    1. 通过默认配置初始化连接池
    ComboPooledDataSource ds = new ComboPooledDataSource();
    Connection con = ds.getConnection();
    
    <default-config>
      <property name="xxx">XXX</property>
    </defualt-config>
    
    2. 通过命名配置初始化连接池
    ComboPooledDataSource ds = new ComboPooledDataSource("oracle-config");
    Connection con = ds.getConnection();
    
    <named-config name="orcale-config">
      <property name="xxx">XXX</property>
    </named-config>
    
    ==================
    
    Tomcat配置连接池
    在server.xml中，或在conf/catalina/localhost/下创建xml文件
    
    <Context>  
      <Resource name="myc3p0" 
    			type="com.mchange.v2.c3p0.ComboPooledDataSource"
    			factory="org.apache.naming.factory.BeanFactory"
    			user="root" 
    			password="123" 
    			classDriver="com.mysql.jdbc.Driver"    
    			jdbcUrl="jdbc:mysql://127.0.0.1/mydb1"
    			maxPoolSize="20"
    			minPoolSize ="5"
    			initialPoolSize="10"
    			acquireIncrement="2"/>
    </Context>  
    
    -------------
    
    获取Tomcat资源
    Context cxt = new InitialContext(); 
    DataSource ds = (DataSource)cxt.lookup("java:/comp/env/myc3p0");
    Connection con = ds.getConnection();
    
    ==================
    
    修改JdbcUtils
    
    public class JdbcUtils {
    	private static DataSource dataSource = new ComboPooledDataSource();
    
    	public static DataSource getDataSource() {
    		return dataSource;
    	}
    
    	public static Connection getConnection() {
    		try {
    			return dataSource.getConnection();
    		} catch (Exception e) {
    			throw new RuntimeException(e);
    		}
    	}
    }
    
    ==================
    
    DBUtils
    
    jar：commons-dbutils.jar
    核心类：QueryRunner、ResultSetHandler
    
    QueryRunner方法：
    * update()：DDL、DML
    * query()：DQL
    * batch()：批处理
    
    -------------
    
    增、删、改
    
    public void fun1() throws SQLException {
    	QueryRunner qr = new QueryRunner();
    	String sql = "insert into user values(?,?,?)";
    	qr.update(JdbcUtils.getConnection(), sql, "u1", "zhangSan", "123");
    }
    
    -------------
    
    查
    
    DataSource ds = JdbcUtils.getDataSource();
    QueryRunner qr = new QueryRunner(ds);
    String sql = "select * from tab_student";
    
    // 把结果集转换成Bean
    Student stu = qr.query(sql, new BeanHandler<Student>(Student.class));
    
    // 把结果集转换成Bean的List
    List<Student> list = qr.query(sql, new BeanListHandler<Student>(Student.class));
    
    // 把结果集转换成Map
    Map<String,Object> map = qr.query(sql, new MapHandler());
    
    // 把结果集转换成List<Map>
    List<Map<String,Object>> list = qr.query(sql, new MapListHandler() );
    
    // 把结果集转换成一列的List
    List<Object> list = qr.query(sql, new ColumnListHandler("name")) ;
    
    // 把结果转换成单行单列的值
    Number number = (Number)qr.query(sql, new ScalarHandler());
    
    ================
    
    批处理
    
    	DataSource ds = JdbcUtils.getDataSource();
    	QueryRunner qr = new QueryRunner(ds);
    	String sql = "insert into tab_student values(?,?,?,?)";
    	Object[][] params = new Object[10][]; //表示 要插入10行记录
    	for(int i = 0; i < params.length; i++) {
    		params[i] = new Object[]{"S_300" + i, "name" + i, 30 + i, i%2==0?"男":"女"};
    	}
    	qr.batch (sql, params);
    	
    对象增强的手段
    * 继承
      被增强的对象固定的
      增强的内容也是固定的
    * 装饰者模式
      被增强的对象是可以切换的
      增强的内容是固定的
    * 动态代理（AOP）
      被增强的对象可以切换：Service
      增强的内容也可以切换：事务处理
    
    --------------------------
    
    继承：
    
    缺点：
    1. 增强的内容是死的，不能动！
    2. 被增强的对象也是死的！
    
    使用继承会使类增多！！！
    
    class 咖啡类 {}
    
    class 有糖咖啡 extends 咖啡类 {
    }
    
    class 加奶咖啡 extends 咖啡类 {
    }
    
    class 加盐咖啡 extends 咖啡类 {
    }
    
    class 加糖加奶 extends 加奶 {
    }
    
    ---------------------------
    
    装饰者模式
    
    1. 增强的内容是不能修改的！
    2. 被增强的对象可以是任意的！
    
    class 咖啡类 {}
    
    class 有糖咖啡 extends 咖啡类 {
    }
    
    class 加奶咖啡 extends 咖啡类 {
    }
    
    class 加盐咖啡 extends 咖啡类 {
    }
    
    咖啡 a = new 加糖();
    咖啡 b = new 加盐(a);//对a进行装饰，就是给a加盐
    咖啡 c = new 加奶(b);
    
    Java API
    IO流！
    
    四大家：
    1. 字节：InputStream、OutputStream
    2. 字符：Reader、Writer
    
    InputStream
    FileInputSteram：它是节点流！就是和一个资源绑定在一起的！文件！
    BufferedInputStream：它是装饰流！创建我是一定要给我一个底层对象，然后我不管你给我的是什么流，我都会给它添加缓冲区！
    
    new BufferedInputStream(任意的InputStream)
    
    FileInputStream in = new FileInputStream("F:/a.jpg");
    BufferedInputStream b = new BufferedInputStream(in);
    ObjectInputStream o = new ObjectInputStream(b);
    
    
    --------------------------
    
    Connection con = ...
    
    装饰：不知道被增强对象的具体类型时，可以使用！
    
    1. 是你还有你，一切拜托你！
    
    is a
    has a
    use a
    
    class MyConnection implements Connection {//是你
    	// 还有你
    	private Connection con;//底层对象，被增强对象
    
    	public MyConnection(Connection con){//通过构造器传递底层对象！
    	    this.con = con;
    	}
    
    	// 一切拜托你
    	public Statement createStatement() {
    		return con.createStatement();
    	}
    
    	// 增强点
    	public void close(){
    		把当前连接归还给池！
    	}
    }
    
    Connection con = 通过四大参数创建连接对象！是由mysql提供的！
    Connection con1 = new MyConnection(con);
    
    con1.createStatement()
    con.createStatement();
    
    con1.close();
    con.close();
    
    
 ###common-dbutils.jar
    
    QueryRunner
    
    update方法：
    * int update(String sql, Object... params) -->  可执行增、删、改语句
    * int update(Connection con, String sql, Object... parmas) --> 需要调用者提供Connection，这说明本方法不再管理Connection了。支持事务!
    
    query方法：
    * T query(String sql, ResultSetHandler rsh, Object... params) --> 可执行查询
    　> 它会先得到ResultSet，然后调用rsh的handle()把rs转换成需要的类型！
    * T query(Connection con, String sql, ResultSetHadler rsh, Object... params)，支持事务
    
    ResultSetHandler接口：
    * BeanHandler(单行) --> 构造器需要一个Class类型的参数，用来把一行结果转换成指定类型的javaBean对象
    * BeanListHandler(多行) --> 构造器也是需要一个Class类型的参数，用来把一行结果集转换成一个javabean，那么多行就是转换成List对象，一堆javabean
    * MapHandler(单行) --> 把一行结果集转换Map对象
      > 一行记录：
        sid  sname  age  gender
        1001 zs     99   male
      > 一个Map：
        {sid:1001, sname:zs, age:99, gender:male}
    * MapListHandler(多行) --> 把一行记录转换成一个Map，多行就是多个Map，即List<Map>！
    * ScalarHandler(单行单列) --> 通常用与select count(*) from t_stu语句！结果集是单行单列的！它返回一个Object
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 
    
