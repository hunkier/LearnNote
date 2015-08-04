<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/4
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"
         language="java"
         import="java.util.*"
         pageEncoding="UTF-8"
         isErrorPage="true"
         session="true"
        %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>pageContext对象</title>
</head>
<body>
This is my first jsp page.<br/>
</body>
</html>
