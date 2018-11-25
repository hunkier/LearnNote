<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/14
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@page import="cn.hunk.learn.listener.session.Admin" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>listener_test</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
</head>
<body>
This is my first jsp page.<br/>
<%
   // session.setAttribute("userName","jack");
   // session.setAttribute("userName","hunk");
    //  session.removeAttribute("userName");
    // session.invalidate();

    session.setAttribute("userInfo",new Admin());
    session.removeAttribute("userInfo");
%>
</body>
</html>
