<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/4
  Time: 16:51
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
    <title>从四个域中获取数据</title>
</head>
<body>
page域：<%=pageContext.getAttribute("message", PageContext.PAGE_SCOPE)%><br/>
request域：<%=pageContext.getAttribute("message", PageContext.REQUEST_SCOPE)%><br/>
session域：<%=pageContext.getAttribute("message", PageContext.SESSION_SCOPE)%><br/>
application域：<%=pageContext.getAttribute("message", PageContext.APPLICATION_SCOPE)%><br/>
</body>
</html>
