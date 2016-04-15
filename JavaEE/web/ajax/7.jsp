<%--
  Created by IntelliJ IDEA.
  User: hunk
  Date: 2015/8/18
  Time: 12:41
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
    <title>显示所有的商品</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="${basePath}ajax/util.js" ></script>
    <style type="text/css">
        .odd{
            background-color: #c3f3c3;
        }
        .even{
            background-color: #f3c3f3;
        }
    </style>
    <script type="text/javascript">
        window.onload=function(){
            document.getElementById("bt1").onclick=function(){
                // 发出异步请求
                var xhr = getXHR();
                xhr.onreadystatechange=function(){
                    if(xhr.readyState==4){
                        if(xhr.status==200){
                            var text = xhr.responseText;
                            var products = eval("("+text+")");

                            document.getElementById("d1").innerHTML="<table id='t1' border='1' width='438'><tr><th>编号</th><th>名称</th><th>价格</th></tr></table>";
                            for(var i=0;i<products.length;i++){
                                var p = products[i];
                                var tableObj = document.getElementById("t1");
                                // 得到表格对象：HTML DOM
                                // 调用insertRow插入一行，返回TableRow
                                var trObj = tableObj.insertRow(tableObj.rows.length);
                                trObj.class=i%2 ==0 ? "odd" : "even";
                                // TableRow的insertCell，返回TableCell；
                                var idTdObj = trObj.insertCell(trObj.cells.length);
                                idTdObj.align="center";
                                idTdObj.innerHTML= p.id;
                                var nameTdObj = trObj.insertCell(trObj.cells.length);
                                nameTdObj.innerHTML= p.name;
                                var priceTdObje = trObj.insertCell(trObj.cells.length);
                                priceTdObje.innerHTML= p.price;
                            }
                        }
                    }
                }
                xhr.open("GET","${basePath}ajax/demo5?time="+new Date().getTime());
                xhr.send(null);
            }
        }
    </script>
</head>
<body>
<input type="button" id="bt1" value="显示商品"/>
<hr/>
<div id="d1"></div>
</body>
</html>
