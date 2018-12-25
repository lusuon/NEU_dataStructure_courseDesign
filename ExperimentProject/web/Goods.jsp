<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="java.sql.*" %>
<%@ page import="goods.DAO.impl.GoodsDAOImpl" %>
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
    <title>商品列表</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    商品管理系统 <small>商品列表</small>
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
                            <a class="btn btn-default " href="logout.do" style="margin:auto">注销</a>
                        </p>
                    </div>
                </h4>
            </div>
        </div>
    </div>
    <h1>以下为JSTL的sql标签实现</h1>
    <div>
        <table id="goods" title="商品信息" class="table table-striped table-hover table-condensed"   rownumbers="true" fitcolumns="true" singleselect="true" style="text-align:center;vertical-align: center;margin:auto">
            <thead style="text-align:center">
            <tr>
                <th class="text-center" field ="1" >图片</th>
                <th class="text-center" field ="2" >商品ID</th>
                <th class="text-center" field ="3" >商品名</th>
                <th class="text-center" field ="4" >生产厂商</th>
                <th class="text-center" field ="5" >类别</th>
                <th class="text-center" field ="6" >型号</th>
                <th class="text-center" field ="7" >产地</th>
                <th class="text-center" field ="8" >商品描述</th>
                <th class="text-center" field ="9" >上传</th>
            </tr>
            </thead>
            <tbody>
            <sql:setDataSource var="db" driver="com.mysql.jdbc.Driver"
                               url="jdbc:mysql://localhost/neu_javaweb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false"
                               user="root"  password="qpalzm"
            />
            <sql:query var="result" dataSource="${db}">SELECT * from goods;</sql:query>


            <c:forEach var="row" items="${result.rows}">
            <tr>
                <td><img alt="图片未上传" src="${row.path}" width="100"> </td>
                <td><c:out value="${row.id}"/></td>
                <td><c:out value="${row.name}"/></td>
                <td><c:out value="${row.factory}"/></td>
                <td><c:out value="${row.category}"/></td>
                <td><c:out value="${row.type}"/></td>
                <td><c:out value="${row.origin}"/></td>
                <td><c:out value="${row.description}"/></td>
                <td>
                    <form role="form" action="/upload.do" method="post" enctype="multipart/form-data" >
                            <table>
                                <tr>
                                    <input hidden name="fileName" value="${row.id}"/>
                                    <td><input type="file" name="file" accept="image/x-png,image/gif,image/jpeg" /></td>
                                    <td><button type="submit" class="btn btn-default">上传</button></td>
                                </tr>
                            </table>
                    </form>
                </td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <h5>当前登录人数：${applicationScope.counter}</h5>

    <h1>以下为使用Bean实现</h1>
    <table title="商品信息" class="table table-striped table-hover table-condensed"   rownumbers="true" fitcolumns="true" singleselect="true" style="text-align:center;vertical-align: center;margin:auto">
        <thead style="text-align:center">
        <tr>
            <th class="text-center" field ="1" >图片</th>
            <th class="text-center" field ="2" >商品ID</th>
            <th class="text-center" field ="3" >商品名</th>
            <th class="text-center" field ="4" >生产厂商</th>
            <th class="text-center" field ="5" >类别</th>
            <th class="text-center" field ="6" >型号</th>
            <th class="text-center" field ="7" >产地</th>
            <th class="text-center" field ="8" >商品描述</th>
            <th class="text-center" field ="9" >操作</th>
        </tr>
        <%
            GoodsDAOImpl goodsDAO = new GoodsDAOImpl();
            request.setAttribute("goods",goodsDAO.list());
        %>
        <a href="/edit.jsp" class="btn btn-default">添加商品</a>
        <c:if test="${empty goods}">
            <tr align="center">沒有数据</tr>
        </c:if>
        <c:forEach items="${goods}" var="good" varStatus="vs">
            <tr>
                <td><img alt="图片未上传" src="${good.path}" width="100"> </td>
                <td>${good.id}</td>
                <td>${good.name}</td>
                <td>${good.factory}</td>
                <td>${good.category}</td>
                <td>${good.type}</td>
                <td>${good.origin}</td>
                <td>${good.description}</td>
                <td>
                    <form role="form" action="/upload.do" method="post" enctype="multipart/form-data" >
                        <table>
                            <tr>
                                <input hidden name="fileName" value="${good.id}"/>
                                <td><input type="file" name="file" accept="image/x-png,image/gif,image/jpeg" /></td>
                                <td><button type="submit" class="btn btn-default">上传图片</button></td>
                                <td><a href="/goods.do?cmd=delete&id=${good.id}" class="btn btn-default">删除</a></td>
                                <td><a href="/edit.jsp?cmd=edit&id=${good.id}" class="btn btn-default">编辑</a></td>
                            </tr>
                        </table>
                    </form>

                </td>
            </tr>
        </c:forEach>
    </table>



</div>
</body>
</html>
