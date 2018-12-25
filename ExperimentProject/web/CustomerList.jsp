<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.sql.*" %>
<%@ page import="goods.util.ConnectionPool" %><%--
  Created by IntelliJ IDEA.
  User: 54234
  Date: 2018-10-12
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>客户列表</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    用户管理系统 <small>用户列表</small>
                </h1>
            </div>
            <div class="jumbotron well">
                <h4>
                    <div name="Use of cookies" >
                        <h2>您好，${sessionScope.id}</h2>
                        <p>
                        <c:if test = "${not empty cookie[\"last_login_time\"]}"> 最近一周登录时间：${cookie["last_login_time"].value} </c:if>
                        </p>
                        <p>
                        <c:if test = "${not empty cookie[\"login_times\"]}"> 您已登录了 ${cookie["login_times"].value} 次 </c:if>


                            <%
                            ConnectionPool pool = (ConnectionPool) request.getServletContext().getAttribute("connectionPool");
                            Connection conn = pool.getConnection();
                            ResultSet rs = null;
                            Statement stmt = null;
                            if(request.getAttribute("searchDB") != null && (boolean)request.getAttribute("searchDB")){
                                rs =(ResultSet)request.getAttribute("result");
                            }else{
                                stmt = conn.createStatement();
                                String sql = "SELECT * FROM customer_info";
                                rs = stmt.executeQuery(sql);
                            }
                        %>

                            <a class="btn btn-default " href="logout.do" style="margin:auto">注销</a>
                        </p>
                    </div>
                </h4>
            </div>
        </div>
    </div>
    <nav class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button> <a class="navbar-brand" href="#">输入查询条件</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <form class="navbar-form navbar-left" role="search" method="post" action="customer.do">
                <div class="form-group">
                    <input hidden name="mode" value="search">
                    <input type="text" name="id" class="form-control" placeholder="请输入用户ID"/>
                    <input type="text" name="name" class="form-control" placeholder="请输入用户姓名"/>
                </div> <button type="submit" class="btn btn-info">查询</button>
            </form>
            <ul class="nav navbar-right navbar-btn">
                <div>
                    <li>
                        <a class="btn btn-primary navbar-left" href="CustomerAdd.jsp" >
                            添加新用户
                        </a>
                    </li>
                </div>
            </ul>
        </div>
    </nav>
<div>

<table id="customers" title="用户信息" class="table table-striped table-hover table-condensed"   rownumbers="true" fitcolumns="true" singleselect="true" style="text-align:center;vertical-align: center;margin:auto">
    <thead style="text-align:center">
    <tr>
        <th class="text-center" field ="1" >客户ID</th>
        <th class="text-center" field ="2" >客户姓名</th>
        <th class="text-center" field ="3" >性别</th>
        <th class="text-center" field ="4" >文化程度</th>
        <th class="text-center" field ="5" >职业</th>
        <th class="text-center" field ="6" >住址</th>
        <th class="text-center" field ="7" >操作</th>
    </tr>
    </thead>

    <tbody>
        <% while(rs.next()){ %>
        <tr>
            <td><%= rs.getString("id")%></td>
            <td><%= rs.getString("name")%></td>
            <td><%= rs.getString("gender")%></td>
            <td><%= rs.getString("education")%></td>
            <td><%= rs.getString("job") %></td>
            <td><%= rs.getString("home")%></td>
            <td>
                <table align='center'>
                <tr>
                    <td>
                        <form method='post' action="customer.do">
                            <input hidden name ="mode" value="delete">
                            <button class="btn btn-danger " type="submit" name="id" value= <%= rs.getString("id")%> >删除</button>
                        </form>
                    </td>
                    <td>
                        <form method=post action="CustomerModify.jsp">
                            <input hidden name ="mode" value="modify">
                            <button class="btn btn-warning " type="submit" name="id" value= <%= rs.getString("id")%> >修改</button>
                        </form>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
    </tbody>
    <% }//善后
        if (stmt != null) stmt.close();
        if (conn != null) pool.returnConnection(conn);
    %>
</table>
</div>
</div>
</body>
</html>
