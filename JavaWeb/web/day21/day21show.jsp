<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 94010
  Date: 2021/5/25
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1 align="center">显示结果</h1>
<table align="center" width="60%" border="1">
    <tr>
        <th>IP</th>
        <th>次数</th>
    </tr>
<c:forEach items="${applicationScope.map}" var="entry">
    <tr>
        <td>${entry.key}</td>
        <td>${entry.value}</td>
    </tr>

</c:forEach>


</table>

</body>
</html>
