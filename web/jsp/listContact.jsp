<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/4
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.hunk.learn.web.contact.entity.Contact" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>所有联系人信息</title>
</head>
<body>
<center><h3>查询所以联系人（jsp版本）</h3></center>
<table align="center" border="1" width="700px">
    <tr>
        <th>编号</th>
        <th>姓名</th>
        <th>性别</th>
        <th>年龄</th>
        <th>电话</th>
        <th>邮箱</th>
        <th>QQ</th>
        <th>操作</th>
    </tr>
    <%
        // 从request域中接收数据
        List<Contact> list = (List<Contact>)request.getAttribute("contacts");
        for (Contact con : list) {

    %>
    <tr>
        <td><%=con.getId()%></td>
        <td><%=con.getName()%></td>
        <td><%=con.getGender()%></td>
        <td><%=con.getAge()%></td>
        <td><%=con.getPhone()%></td>
        <td><%=con.getEmail()%></td>
        <td><%=con.getQq()%></td>
        <td>
            <a href="QueryContactServlet?id=<%=con.getId()%>">修改</a>&nbsp;
            <a href="DeleteContactServlet?id=<%=con.getId()%>">删除</a>
        </td>
    </tr>
    <%
        }
    %>
    <tr>
        <td colspan="8" align="center">
            <a href="jsp/addContact.jsp">[添加联系人]</a>
        </td>
    </tr>
</table>
</body>
</html>
