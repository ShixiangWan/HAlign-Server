<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <base href="<%=basePath%>">
    <link rel="shortcut icon" href="icon.ico" type="image/x-icon" />
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>HAlign Server</title>
    <!-- bootstrap -->
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-2.1.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>

    <style type="text/css">
      body {
              padding-top: 80px;
            }
    </style>
  </head>
  <body>
	<!-- 引入导航栏 -->
    <jsp:include page="header.jsp" />

    <div class="container">
        <div class="row">
          <p><strong>Phylogenetic tree:</strong>
            <img src="./data/tree/tree.svg" class="img-responsive" alt="Responsive image">
          </p>
        </div>
    </div> <!-- /container -->
  </body>
</html>