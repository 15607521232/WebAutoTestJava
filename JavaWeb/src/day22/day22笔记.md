### 上传(上传不能使用BaseServlet)
    
    1. 上传对表单限制
      * method="post"
      * enctype="multipart/form-data"
      * 表单中需要添加文件表单项：<input type="file" name="xxx" />
    
    <form action="xxx" method="post" enctype="multipart/form-data">
      用户名；<input type="text" name="username"/><br/>
      照　片：<input type="file" name="zhaoPian"/><br/>
      <input type="submit" value="上传"/>
    </form>
    
    2. 上传对Servlet限制
      * request.getParametere("xxx");这个方法在表单为enctype="multipart/form-data"时，它作废了。它永远都返回null
      * ServletInputStream request.getInputStream();包含整个请求的体！
    
    -------------------------------
    
    多部件表单的体
    
    1. 每隔出多个部件，即一个表单项一个部件。
    2. 一个部件中自己包含请求头和空行，以及请求体。
    3. 普通表单项：
      > 1个头：Content-Disposition：包含name="xxxx"，即表单项名称。
      > 体就是表单项的值
    4. 文件表单项：
      > 2个头：
        * Content-Disposition：包含name="xxxx"，即表单项名称；还有一个filename="xxx"，表示上传文件的名称
        * Content-Type：它是上传文件的MIME类型，例如：image/pjpeg，表示上传的是图片，图上中jpg扩展名的图片。
      > 体就是上传文件的内容。
    
    ===========================================
    
    commons-fileupload
      * commons-fileupload.jar
      * commons-io.jar
    
    这个小组件，它会帮我们解析request中的上传数据，解析后的结果是一个表单项数据封装到一个FileItem对象中。我们只需要调用FileItem的方法即可！
    
    －－－－－－－－－－－－－－－
    
    1. 上传三步
      相关类：
      * 工厂：DiskFileItemFactory
      * 解析器：ServletFileUpload
      * 表单项：FileItem
    
      1). 创建工厂：DiskFileItemFactory factory = new DiskFileItemFactory();
      2). 创建解析器：ServletFileUpload sfu = new ServletFileUpload(factory);
      3). 使用解析器来解析request，得到FileItem集合：List<FileItem> fileItemList = sfu.parseRequest(request);
    
    2. FileItem
      * boolean isFormField()：是否为普通表单项！返回true为普通表单项，如果为false即文件表单项！
      * String getFieldName()：返回当前表单项的名称；
      * String getString(String charset)：返回表单项的值；
      * String getName()：返回上传的文件名称
      * long getSize()：返回上传文件的字节数
      * InputStream getInputStream()：返回上传文件对应的输入流
      * void write(File destFile)：把上传的文件内容保存到指定的文件中。
      * String getContentType();
    
    －－－－－－－－－－－－－－－
    
    上传的细节：
    
    1. 文件必须保存到WEB-INF下！
      * 目的是不让浏览器直接访问到！
      * 把文件保存到WEB-INF目录下！
    2. 文件名称相关问题
    　* 有的浏览器上传的文件名是绝对路径，这需要切割！C:\files\baibing.jpg
            String filename = fi2.getName();
    	int index = filename.lastIndexOf("\\");
    	if(index != -1) {
    	    filename = filename.substring(index+1);
    	}
      * 文件名乱码或者普通表单项乱码：request.setCharacterEncoding("utf-8");因为fileupload内部会调用request.getCharacterEncoding();
    	> request.setCharacterEncoding("utf-8");//优先级低
    	> servletFileUpload.setHeaderEncoding("utf-8");//优先级高
      * 文件同名问题；我们需要为每个文件添加名称前缀，这个前缀要保证不能重复。uuid
    	> filename = CommonUtils.uuid() + "_" + filename;
    3. 目录打散
      * 不能在一个目录下存放之多文件。
        > 首字符打散：使用文件的首字母做为目录名称，例如：abc.txt，那么我们把文件保存到a目录下。如果a目录这时不存在，那么创建之。
        > 时间打散：使用当前日期做为目录。
        > 哈希打散：
          * 通过文件名称得到int值，即调用hashCode()
          * 它int值转换成16进制0~9, A~F
          * 获取16进制的前两位用来生成目录，目录为二层！例如：1B2C3D4E5F，/1/B/保存文件。
    4. 上传文件的大小限制
      * 单个文件大小限制
        > sfu.setFileSizeMax(100*1024)：限制单个文件大小为100KB
        > 上面的方法调用，必须在解析开始之前调用！
        > 如果上传的文件超出限制，在parseRequest()方法执行时，会抛出异常！FileUploadBase.FileSizeLimitExceededException
      * 整个请求所有数据大小限制
        > sfu.setSizeMax(1024 * 1024);//限制整个表单大小为1M
        > 这个方法也是必须在parseRequest()方法之前调用
        > 如果上传的文件超出限制，在parseRequest()方法执行时，会抛出异常！FileUploadBase.SizeLimitExceededException
    5. 缓存大小与临时目录
      * 缓存大小：超出多大，才向硬盘保存！默认为10KB
      * 临时目录：向硬盘的什么目录保存
      设置缓存大小与临时目录：new DiskFileItemFactory(20*1024, new File("F:/temp"))
###  下载
    1. 下载就是向客户端响应字节数据！
      原来我们响应的都是html的字符数据！
      把一个文件变成字节数组，使用response.getOutputStream()来各应给浏览器！！！
    
    2. 下载的要求
      * 两个头一个流！
        > Content-Type：你传递给客户端的文件是什么MIME类型，例如：image/pjpeg
          * 通过文件名称调用ServletContext的getMimeType()方法，得到MIME类型！
        > Content-Disposition：它的默认值为inline，表示在浏览器窗口中打开！attachment;filename=xxx
          * 在filename=后面跟随的是显示在下载框中的文件名称！
        > 流：要下载的文件数据！
          * 自己new一个输入流即可！
    
    ---------------------------
    
    下载的细节
    
    1. 显示在下载框中的中文名称时，会出现乱码。
      * FireFox：Base64编码。
      * 其他大部分浏览器：URL编码。
    
      通用方案：filename = new String(filename.getBytes("GBK"), "ISO-8859-1");


	public static String filenameEncoding(String filename, HttpServletRequest request) throws IOException {
		String agent = request.getHeader("User-Agent"); //获取浏览器
		if (agent.contains("Firefox")) {
			BASE64Encoder base64Encoder = new BASE64Encoder();
			filename = "=?utf-8?B?"
					+ base64Encoder.encode(filename.getBytes("utf-8"))
					+ "?=";
		} else if(agent.contains("MSIE")) {
			filename = URLEncoder.encode(filename, "utf-8");
		} else {
			filename = URLEncoder.encode(filename, "utf-8");
		}
		return filename;
	}

---------------------------
---------------------------