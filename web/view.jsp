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
    <!-- MSA VIEW -->
    <script src="//cdn.bio.sh/msa/latest/msa.min.gz.js"></script>

    <style type="text/css">
      body {
              padding-top: 80px;
            }
    </style>
  </head>
  <body>
	<!-- 引入导航栏 -->
    <jsp:include page="header.jsp" />

    <div class="container well">
        <div class="row">
          <p>
            1. <strong>Multiple Sequences Alignment visualization:</strong>
          <div id="msa">Loading Multiple Alignment...</div>
          </p>
          <p>
            2. <strong>Original Sequences visualization:</strong>
          <div id="msa2">Loading YOUR INPUT...</div>
          </p>
        </div>
    </div> <!-- /container -->

    <script type="text/javascript">
        var rootDiv = document.getElementById("msa");
        var opts = {
            el: rootDiv,
            importURL: "./data/${requestScope.time}.fasta",
        };
        var m = msa(opts);
        var rootDiv2 = document.getElementById("msa2");
        var opts2 = {
            el: rootDiv2,
            importURL: "./data/input.fasta",
        };
        var m2 = msa(opts2);
    </script>

  </body>
</html>