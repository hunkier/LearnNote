<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/18
  Time: 14:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>原型</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
</head>
<body>
<script type="text/javascript">
    // 相当于在原来的String源码上添加了一个trim()方法
    String.prototype.trim=function(){
        return this.replace(/(^\s*)|(\s*$)/g,"");
    }

    var s1="         abc              ";
    alert("|" + s1 +"|");
    alert("|" + s1.trim() +"|");
</script>
This is my first jsp page.<br/>
</body>
</html>
