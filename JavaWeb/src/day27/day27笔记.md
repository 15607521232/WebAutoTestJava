### 泛型
    1. 泛型类：具有一个或多个类型变量的类，称之为泛型类！
    
    class A<T> {
    }
    
    2. 在创建泛型类实例时，需要为其类型变量赋值
    
    A<String> a = new A<String>();
      * 如果创建实例时，不给类型变量赋值，那么会有一个警告！
    
    3. 泛型方法：具有一个或多个类型变量的方法，称之为泛型方法！
    
    class A<T> {
      public T fun(T t1) {}
    }
    
    fun()方法不是泛型方法！它是泛型类中的一个方法！
    
    public <T> T fun(T t1) {} --> 它是泛型方法
    
    * 泛型方法与泛型类没什么关系，泛型方法不一定非要在泛型类中！
    
    4. 泛型在类中或方法中的使用
      * 泛型类中使用泛型：
        > 成员类型
        > 返回值和参数类型
        > 局部变量的引用上
    
    class A<T> {
      private T bean;//泛型可在成员变量上使用
      public T fun(T t) {}//泛型可以在类中的方法上（返回值和参数类型）使用！
    
      public void fun2() {//泛型还可以在局部变量的引用类型上使用
        T b = ...
        new T();//不行的！
      }
    }
    
    =========================================
    
    5. 泛型的继承和实现
    
    class A<T> {}
    
    class AA extends A<String> {} //不是泛型类，只是它爸爸是泛型类！
    
    5.1 继承泛型类
    
      * 子类不是泛型类：需要给父类传递类型常量
        > 当给父类传递的类型常量为String时，那么在父类中所有T都会被String替换！
      * 子类是泛型类：可以给父类传递类型常量，也可以传递类型变量
    
    class AA1 extends A<Integer> {}
    
    class AA3<E> extends A<E> {}
    
    =========================================
    =========================================
    =========================================
    
    泛型的通配符
    
    1. 通配符使用的场景
    
      方法的形参！
    
    2. 通配符的优点
      使方法更加通用！
    
    3. 通配符分类
      无界通配：?
      子类限定：? extends Object
      父类限定：? super Integer
    
    4. 通配符缺点
      使变量使用上不再方便
      无界：参数和返回值为泛型的方法，不能使用！
      子类：参数为泛型的方法不能使用
      父类：返回值为泛型的方法不能使用
    
    5. 比较通配符
    boolean addAll(Collection<E> c)
    
    List<Number> numList = new ArrayList<Number>();
    List<Integer> intList = new ArrayList<Integer>();
    numList.addAll(intList);//addAll(Collection<Number> c), 传递的是List<Integer>
    
    
    boolean addAll(Collection<? extends E> c)
    
    List<Number> numList = new ArrayList<Number>();
    List<Integer> intList = new ArrayList<Integer>();
    numList.addAll(intList);//addAll(Collection<? extends Number> c), 传递的是List<Integer>
  
###  注解
    语法：@注解名称
      注解的作用：替代xml配置文件！
        servlet3.0中，就可以不再使用web.xml文件，而是所有配置都使用注解！
      注解是由框架来读取使用的！
    
    2. 注解的使用
      * 定义注解类：框架的工作
      * 使用注解：我们的工作
      * 读取注解（反射）：框架的工作
    
    3. 定义注解类
      class A {}
      interface A{}
      enum A{}
      @interface A{}//天下所有的注解都是Annotation的子类！
    
    4. 使用注解
      注解的作用目标：
        * 类
        * 方法
        * 构造器
        * 参数
        * 局部变量
        * 包
    
    5. 注解的属性
      * 定义属性：
        > 格式：
        @interface MyAnno1 {
    	int age();
    	String name();
        }
      * 使用注解时给属性赋值
        > @MyAnno1(age=100, name="zhangSan")
      * 注解属性的默认值：在定义注解时，可以给注解指定默认值！
        > int age() default 100;
        > 在使用注解时，可以不给带有默认值的属性赋值！
      * 名为value的属性的特权
        > 当使用注解时，如果只给名为value的属性赋值时，可以省略“value=”，例如： @MyAnno1(value="hello")，可以书写成 @MyAnno1("hello")
      * 注解属性的类型
        > 8种基本类型
        > String
        > Enum
        > Class
        > 注解类型
        > 以上类型的一维数组类型
    
        当给数组类型的属性赋值时，若数组元素的个数为1时，可以省略大括号
    @MyAnno1(
    	a=100,
    	b="hello",
    	c=MyEnum1.A,
    	d=String.class,
    	e=@MyAnno2(aa=200, bb="world"),
    	f=100
    )
    public class Demo3 {
    
    }
    
    @interface MyAnno1 {
    	int a();
    	String b();
    	MyEnum1 c();
    	Class d();
    	MyAnno2 e();
    	int[] f();
    }
    
    
    6. 注解的作用目标限定以及保存策略限定
      6.1 让一个注解，它的作用目标只能在类上，不能在方法上，这就叫作用目标的限定！
      * 在定义注解时，给注解添加注解，这个注解是@Target
    
    @Target(value={ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
    @interface MyAnno1 {
    	
    }
    
      6.2 保留策略
      * 源代码文件（SOURCE）：注解只在源代码中存在，当编译时就被忽略了
      * 字节码文件（CLASS）：注解在源代码中存在，然后编译时会把注解信息放到了class文件，但JVM在加载类时，会忽略注解！
      * JVM中（RUNTIME）：注解在源代码、字节码文件中存在，并且在JVM加载类时，会把注解加载到JVM内存中（它是唯一可反射注解！）
    
      限定注解的保留策略
      
    @Retention(RetentionPolicy.RUNTIME)
    @interface MyAnno1 {
    	
    }
      
    
    7. 读取注解（反射）
    
    
    ========================
    
    模拟注解的使用场景
###  反射注解
    1. 要求
      * 注解的保留策略必须是RUNTIME
    
    2. 反射注解需要从作用目标上返回
      * 类上的注解，需要使用Class来获取
      * 方法上的注解，需要Method来获取
      * 构造器上的注解，需要Construcator来获取
      * 成员上的，需要使用Field来获取
    
      Class
      Method、Constructor、Field：AccessibleObject
    
      它们都有一个方法：
      * Annotation getAnnotation(Class)，返回目标上指定类型的注解！
      * Annotation[] getAnnotations()，返回目标上所有注解！

### 反射泛型信息

    Class --> Type getGenericSupperclass()
    Type --> ParameterizedType，把Type强转成ParameterizedType类型！！！
    ParameterizedType --> 参数化类型 = A<String>
    ParameterizedType：Type[] getActualTypeArguments()，A<String>中的String
    Type[]就是Class[]，我们就得到了类型参数了！
    
    
    abstract class A<T> {
        public A() {
            /*
             * 在这里获取子类传递的泛型信息，要得到一个Class！
             */
    //		Class clazz = this.getClass();//得到子类的类型
    //		Type type = clazz.getGenericSuperclass();//获取传递给父类参数化类型
    //		ParameterizedType pType = (ParameterizedType) type;//它就是A<String>
    //		Type[] types = pType.getActualTypeArguments();//它就是一个Class数组
    //		Class c = (Class)types[0];//它就是String
    //		
            Class c = (Class)((ParameterizedType)this.getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
            
            System.out.println(c.getName());
        }
    }
    
    class B extends A<String> {
        
    }
    
    class C extends A<Integer> {
        
    }
      