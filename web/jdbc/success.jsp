<%--
  Created by IntelliJ IDEA.
<<<<<<< Updated upstream
  User: hunk
  Date: 2015/8/12
  Time: 10:34
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
    <title>注册成功</title>
</head>
<body>
恭喜，${userName}注册陈功！
</body>
</html>
