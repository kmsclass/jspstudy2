<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
    
<!DOCTYPE html>
<html>
<head>
<title><sitemesh:write property="title"/></title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
</style>
<sitemesh:write property="head"/>
</head>
<body class="w3-light-grey">

<!-- Top container -->
<div class="w3-bar w3-top w3-black w3-large" style="z-index:4">
  <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey" onclick="w3_open();"><i class="fa fa-bars"></i> &nbsp;Menu</button>
  <span class="w3-bar-item w3-right">
  <c:if test="${empty sessionScope.login}">
     <a href="${path}/member/loginForm">로그인</a>
     <a href="${path}/member/joinForm">회원가입</a>
  </c:if>
  <c:if test="${!empty sessionScope.login}">
     ${sessionScope.login }님이 로그인 하셨습니다.
     <a href="${path}/member/logout">로그아웃</a>
  </c:if>
  </span>
</div>

<!-- Sidebar/menu -->
<nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>
  <div class="w3-container w3-center">
    <div class="w3-row w3-margin-top">
      <img src="${path }/image/logo.gif" 
      class="w3-circle w3-margin-right" style="width:100px">
  </div>
  <div class="w3-row w3-margin-top">
     <c:if test="${!empty sessionScope.login }">
      <span>반갑습니다.<strong>${sessionScope.login}님</strong></span>
     </c:if>
     <c:if test="${empty sessionScope.login }">
      <span></span><strong>로그인하세요</strong></span>
     </c:if>
  </div>
  </div>
  <hr>
  <div class="w3-bar-block">
    <a href="#" class="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black" onclick="w3_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>&nbsp; Close Menu</a>
    <a href="${path}/member/main" class="w3-bar-item w3-button w3-padding w3-blue">
    <i class="fa fa-users fa-fw"></i>&nbsp; 회원관리</a>
    <a href="${path}/board/list?boardid=1" 
      class="w3-bar-item w3-button w3-padding">
      <i class="fa fa-eye fa-fw"></i>&nbsp; 공지사항</a>
      
    <a href="${path}/board/list?boardid=2" 
      class="w3-bar-item w3-button w3-padding">
    <i class="fa fa-users fa-fw"></i>&nbsp; 자유게시판</a>
    
    <a href="${path}/board/list?boardid=3" 
    class="w3-bar-item w3-button w3-padding">
    <i class="fa fa-bullseye fa-fw"></i>&nbsp;QNA</a>
  </div>
</nav>

<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="w3-main" style="margin-left:300px;margin-top:43px;">

  <!-- Header -->
  <header class="w3-container" style="padding-top:22px">
    <h5><b>공공데이터 융합 자바/스프링 개발자 양성과정(GDJ62)</b></h5>
  </header>
  <div class="w3-panel">
    <sitemesh:write property="body"/>
  </div>
  <hr>
  <!-- Footer -->
  <footer class="w3-container w3-padding-16 w3-light-grey">
    <h4>구디아카데미</h4>
    <p>Powered by <a href="https://www.gdu.co.kr" target="_blank">구디</a></p>
  </footer>
  <!-- End page content -->
</div>
<script>
// Get the Sidebar
var mySidebar = document.getElementById("mySidebar");

// Get the DIV with overlay effect
var overlayBg = document.getElementById("myOverlay");

// Toggle between showing and hiding the sidebar, and add overlay effect
function w3_open() {
  if (mySidebar.style.display === 'block') {
    mySidebar.style.display = 'none';
    overlayBg.style.display = "none";
  } else {
    mySidebar.style.display = 'block';
    overlayBg.style.display = "block";
  }
}

// Close the sidebar with the close button
function w3_close() {
  mySidebar.style.display = "none";
  overlayBg.style.display = "none";
}
</script>

</body>
</html>