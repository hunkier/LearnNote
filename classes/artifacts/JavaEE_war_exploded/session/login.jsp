<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/3
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>登录页面</title>
</head>
<body>
    <form action="LoginServlet" method="post">
        用户名：<input type="text" name="userName">
        <br/>
        密码：<input type="password" name="userPwd">
        <br/>
        <input type="submit" value="登录">
    </form>
</body>
</html>
