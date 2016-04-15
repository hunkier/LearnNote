<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/13
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>分页数据查询</title>
</head>
<body>
<table border="1" width="80%" align="center" cellpadding="5" cellspacing="0">
    <tr>
        <td>序号</td>
        <td>员工编号</td>
        <td>员工姓名</td>
    </tr>
    <!-- 迭代数据 -->

    <c:choose>
        <c:when test="${not empty requestScope.pageBean.pageData}">
            <c:forEach var="emp" items="${requestScope.pageBean.pageData}" varStatus="vs">
                <tr>
                    <td>${vs.count}</td>
                    <td>${emp.empId}</td>
                    <td>${emp.empName}</td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="3">对不起，没有你要找的数据</td>
            </tr>
        </c:otherwise>
    </c:choose>

    <tr>
        <td colspan="3" align="center">
            当前${requestScope.pageBean.currentPage}/${requestScope.pageBean.totalPage}页   &nbsp; &nbsp;
            <a href="page?currentPage=1">首页</a>
            <a href="page?currentPage=${requestScope.pageBean.currentPage-1}">上一页</a>
            <a href="page?currentPage=${requestScope.pageBean.currentPage+1}">下一页</a>
            <a href="page?currentPage=${requestScope.pageBean.totalPage}">尾页</a>

        </td>
    </tr>
</table>
</body>
</html>
