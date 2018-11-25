<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/14
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <%
        ResourceBundle bundle = ResourceBundle.getBundle("cn.hunk.learn.listener.i18n.msg",request.getLocale());
        pageContext.setAttribute("bundle",bundle);
    %>
    <base href="<%=basePath%>">
    <title><%=bundle.getString("title")%></title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
</head>
<body>
<form name="frmLogin" action="admin?method=login" method="post">
    <table align="center" border="1">
        <tr>
            <td><%=bundle.getString("username") %></td>
            <td>
                <input type="text" name="userName">
            </td>
        </tr>
        <tr>
            <td><%=bundle.getString("pwd") %></td>
            <td>
                <input type="password" name="pwd">
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="<%=bundle.getString("submit") %>">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
