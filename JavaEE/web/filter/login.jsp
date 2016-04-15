<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/13
  Time: 16:21
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
    <title>login</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
</head>
<body>
<form name="frmLogin" action="login" method="POST">
    用户名：<input type="text" name="userName"/><br/>
    <input type="submit" value="POST提交"/>
</form>
<form name="frmLogin" action="login" method="GET">
    用户名：<input type="text" name="userName"/><br/>
    <input type="submit" value="GET提交"/>
</form>
</body>
</html>
