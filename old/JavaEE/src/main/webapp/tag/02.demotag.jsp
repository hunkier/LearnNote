<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/5
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="hunk" uri="http://hunk.com.cn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>第二个自定义标签</title>
</head>
<body>
This is my first jsp page.<br/>
<hunk:demoTag num="2">xxxxxabcdefg${5+3}</hunk:demoTag>
标签余下内容
</body>
</html>
