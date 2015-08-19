<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/18
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>listProducts</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <style type="text/css">
        .odd{
            background-color: #c3f3c3;
        }
        .even{
            background-color: #f3c3f3;
        }
    </style>
</head>
<body>
<table border="1" width="438" align="center">
    <tr>
        <th>编号</th>
        <th>单价</th>
        <th>名称</th>
    </tr>
    <c:forEach items="${products}" var="p" varStatus="vs">
        <tr class="${vs.index%2==0?'odd':'even'}" align="center">
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td>${p.price}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
