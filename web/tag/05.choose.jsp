<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/5
  Time: 16:35
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
    <title>choose标签</title>
</head>
<body>
This is my first jsp page.<br/>
<hunk:ChooseTag>
    <hunk:WhenTag test="${10<3}">
        条件成立
    </hunk:WhenTag>
    <hunk:OtherwiseTag>
        条件不成立
    </hunk:OtherwiseTag>
</hunk:ChooseTag>
</body>
</html>
