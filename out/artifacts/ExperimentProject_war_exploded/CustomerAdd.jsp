<%--
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
    <title>添加客户</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>客户管理系统 <small>添加客户
                    <small><a href="CustomerList.jsp">返回主系统</a></small></small>
                </h1>
            </div>
        </div>
    </div>
  <div style="text-align:center;vertical-align: center;margin:auto">
    <form method='post' action='customer.do' style="text-align:center;vertical-align: center;margin:auto">
        <input type = "hidden" name = "mode" value="add">
        <table class="table table-striped table-hover table-condensed" rownumbers="true" fitcolumns="true" singleselect="true"  >
            <tr class="text-left">
                <td class="text-right">客户ID：</td>
                <td><input type='text' name='id' class="form-control"></td>
            </tr>
            <tr class="text-left">
                <td class="text-right">客户姓名：</td>
                <td><input type='text' name='name' class="form-control"></td>
            </tr>
            <tr class="text-left">
                <td class="text-right">性别：</td>
                <td>
                    <input type="radio" name="gender" value="男">男
                    <input type="radio" name="gender" value="女">女
                </td>
            </tr>
            <tr class="text-left">
                <td class="text-right">职业：</td>
                <td><input type='text' name='job' class="form-control"></td>
            </tr>
            <tr class="text-left">
                <td class="text-right">文化程度：</td>
                <td>
                    <select name="education">
                        <option value="">请选择</option>
                        <option value="小学以下">小学以下</option>
                        <option value="小学">小学</option>
                        <option value="初中">初中</option>
                        <option value="高中">高中</option>
                        <option value="本科">本科</option>
                        <option value="硕士">硕士</option>
                        <option value="博士">博士</option>
                    </select>
                </td>
            </tr>
            <tr class="text-left">
                <td class="text-right">住址：</td>
                <td><input type='te"text-right"xt' name='home' class="form-control"></td>
            </tr>
            <tr>
                <td colspan='2' align='center'><input type='submit'></td>
            </tr>
          </table>
        </form>
        </div>
</div>
</body>
</html>
