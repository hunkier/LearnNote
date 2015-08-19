<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/3
  Time: 16:11
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
    <title></title>
</head>
<body>
<%@include file="common/hearder.jsp"%>
<br/>
首页的内容XXXXXXXXXXXXXXX
</body>
</html>
