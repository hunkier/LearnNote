<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/4
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page
        contentType="text/html;charset=UTF-8"
        language="java" import="java.util.*"
        pageEncoding="UTF-8"
        buffer="1kb"

        %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>查看缓冲区大小</title>

</head>
<body>
This is my first jsp page.<br/>
<%
    for (int i = 0; i < 1024; i++) {
        out.write('a');
    }
    // 查看缓冲区大小
    int bufferSize = out.getBufferSize();
    System.out.println("当前缓冲区大小：" + out.getBufferSize());
    //查看剩余缓冲区大小
    int remaining = out.getRemaining();
    System.out.println("缓冲区剩余大小：" + out.getRemaining());
    // 刷新缓冲区
//    out.flush();
    response.getWriter().println("<br/>");
    response.getWriter().println("当前缓冲区大小：" + bufferSize);
    response.getWriter().println("<br/>");
    response.getWriter().println("缓冲区剩余大小：" + remaining);
    response.getWriter().println("<br/>");
    response.getWriter().println("123");
%>
</body>
</html>
