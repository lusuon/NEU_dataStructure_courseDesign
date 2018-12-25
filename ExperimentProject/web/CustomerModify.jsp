<%@ page import="java.sql.*" %>
<%@ page import="goods.util.JDBCUtil" %><%--
  Created by IntelliJ IDEA.
  User: 54234
  Date: 2018-10-12
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Connection conn = JDBCUtil.getConn();
    Statement stmt = conn.createStatement();
    String sql = "SELECT * FROM customer_info WHERE id = "+request.getParameter("id");
    ResultSet rs = stmt.executeQuery(sql);
    rs.next();
    pool.returnConnection(conn);
    String gen = rs.getString("gender");
    String edu = rs.getString("education") ;
%>
<!DOCTYPE html>
<html>
<head>
    <title>修改客户信息</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    客户管理系统 <small>修改客户 <small><a href="CustomerList.jsp">返回主系统</a></small> </small>
                </h1>
            </div>
        </div>
    </div>
    <div style="text-align:center;vertical-align: center;margin:auto">
        <form method='post' action='customer.do' style="text-align:center;vertical-align: center;margin:auto">
            <input type = "hidden" name = "mode" value="modify">
            <table class="table table-striped table-hover table-condensed" rownumbers="true" fitcolumns="true" singleselect="true"  >
                <tr class="text-left">
                    <td class="text-right">客户ID：</td>
                    <td><fieldset disabled><input type='text' name='id' class="form-control" value=<%= request.getParameter("id") %> ></fieldset></td>"
                </tr>
                <tr class="text-left">
                    <td class="text-right">客户姓名：</td>
                    <td><input type='text' name='name' class="form-control" value='<%= rs.getString("name")%>'></td>
                </tr>
                <tr class="text-left">
                    <td class="text-right">性别：</td>
                    <td>
                        <input type="radio" name="gender" value="男" <%= gen.equals("男")?"Checked":"" %>>男
                        <input type="radio" name="gender" value="女" <%= gen.equals("女")?"Checked":"" %>>女
                    </td>
                </tr>
                <tr class="text-left">
                    <td class="text-right">职业：</td>
                    <td><input type='text' name='job' class="form-control" value='<%= rs.getString("job")%>'></td>
                </tr>
                <tr class="text-left">
                    <td class="text-right">文化程度：</td>
                    <td>
                        <select name="education">
                            <option value="">请选择</option>
                            <option value="小学以下" <%= edu.equals("小学以下")?"selected":"" %>>小学以下</option>
                            <option value="小学" <%= edu.equals("小学")?"selected":"" %>>小学</option>
                            <option value="初中" <%= edu.equals("初中")?"selected":"" %>>初中</option>
                            <option value="高中" <%= edu.equals("高中")?"selected":"" %>>高中</option>
                            <option value="本科" <%= edu.equals("本科")?"selected":"" %>>本科</option>
                            <option value="硕士" <%= edu.equals("硕士")?"selected":"" %>>硕士</option>
                            <option value="博士" <%= edu.equals("博士")?"selected":"" %>>博士</option>
                        </select>
                    </td>
                </tr>
                <tr class="text-left">
                    <td class="text-right">住址：</td>
                    <td><input type='text' name='home' value='<%= rs.getString("home") %>'></td>
                </tr>
                <tr>
                    <input type = "hidden" name = "id" value="<%= rs.getString("id") %>">
                    <td colspan='2' align='center'><input type='submit' value='保存'></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>


