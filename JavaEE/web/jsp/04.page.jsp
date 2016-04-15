<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/3
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title></title>
</head>
<body>
<%
    new Date();
    new SimpleDateFormat();

%>
This is my first jsp page.<br/>
</body>
</html>
