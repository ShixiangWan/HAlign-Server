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
    <title>HAlign</title>
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
          <p><strong>Phylogenetic tree result: <a target="_blank" href="data/tree/tree.tre">tree.tre</a></strong></p>
            <p>Note: please refer to <a target="_blank" href="http://www.evolgenius.info/evolview/">EvolView</a> for better phylogenetic tree visualization.</p>
            <img src="./data/tree/${requestScope.name}.svg" class="img-responsive" alt="Responsive image">

        </div>
        <!-- 底部栏 -->
        <hr style="width:100%;margin:3px;"/>
        <p style="text-align:center;" class="text-muted">Bioinformatics Laboratory - Tianjin University @ <a target="_blank" href="http://lab.malab.cn/~shixiang/">Shixiang Wan</a></p>
    </div> <!-- /container -->
  </body>
</html>