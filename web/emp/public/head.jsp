<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/14
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath" ,basePath);
%>
<h3 align="center">
    欢迎你，${sessionScope.loginInfo.userName};
    <a href="AdminServlet?method=out">退出</a>
    <a href="emp/online.jsp">在线列表展示</a>${basePath}
</h3>
