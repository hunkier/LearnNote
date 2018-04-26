<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/4
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"
         language="java"
         import="java.util.*"
         pageEncoding="UTF-8"
         isErrorPage="true"
         session="true"
        %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>pageContext对象</title>
</head>
<body>
This is my first jsp page.<br/>
<%
    // 1) 可以获取其他8个内置对象
    response.getWriter().write("是否相等？ " + (out==pageContext.getOut())+"<br/>");
    response.getWriter().write("是否相等？ " + (session==pageContext.getSession())+"<br/>");
%>
    <%--
    2)pageContext作为域对象使用
        2.1 可以往不同域对象中存取数据
    --%>
<%
    // 保存数据。默认情况下，保存在page域中
    pageContext.setAttribute("message", "page's message");
    pageContext.setAttribute("message", "request's message", PageContext.REQUEST_SCOPE);
    pageContext.setAttribute("message", "session's message", PageContext.SESSION_SCOPE);
    pageContext.setAttribute("message", "application's message", PageContext.APPLICATION_SCOPE);

%>

<%
    // 获取数据
//    String message = (String)pageContext.getAttribute("message");
//    out.write(message);
%>

<%--从request域中取出数据--%>
<%--
    原则：
    1）在哪个域保存数据，就必须从哪个域中取出数据
--%>
<%=pageContext.getAttribute("message",PageContext.PAGE_SCOPE)%><br/>
<%=pageContext.getAttribute("message",PageContext.REQUEST_SCOPE)%><br/>
<%=pageContext.getAttribute("message",PageContext.SESSION_SCOPE)%><br/>
<%=pageContext.getAttribute("message",PageContext.APPLICATION_SCOPE)%><br/>
<%--
    findAttribut()：在四个域中自动搜索数据
--%>
<%=pageContext.findAttribute("message")%><br/>
<%
    //转发
//    request.getRequestDispatcher("03.pageContext2.jsp").forward(request,response);
    //重定向
    response.sendRedirect("03.pageContext2.jsp");
%>

</body>
</html>
