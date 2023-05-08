<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<c:set var="uri" value="${pageContext.request.servletPath}" />
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
<script type="text/javascript"
  src="http://cdn.ckeditor.com/4.5.7/full/ckeditor.js"></script>
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
    <a href="${path}/member/main" 
       class="w3-bar-item w3-button w3-padding ">
    <i class="fa fa-users fa-fw"></i>&nbsp; 회원관리</a>
    <a href="${path}/board/list?boardid=1" 
      class="w3-bar-item w3-button w3-padding <c:if test='${empty boardid || boardid==1}'>w3-blue</c:if>">
      <i class="fa fa-eye fa-fw"></i>&nbsp; 공지사항</a>
      
    <a href="${path}/board/list?boardid=2" 
      class="w3-bar-item w3-button w3-padding  <c:if test='${boardid==2}'>w3-blue</c:if>">
    <i class="fa fa-users fa-fw"></i>&nbsp; 자유게시판</a>
    
    <a href="${path}/board/list?boardid=3" 
    class="w3-bar-item w3-button w3-padding <c:if test='${boardid==3}'>w3-blue</c:if>">
    <i class="fa fa-bullseye fa-fw"></i>&nbsp;QNA</a>
  </div>
  <%-- ajax을 이용하여 환율 정보 출력 --%>
  <div class="w3-content">
     <div id="exchange"></div>
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
  <div class="w3-row-padding w3-margin-bottom">
     <div class="w3-half">
       <div class="w3-container w3-padding-16">
         <div id="piecontainer" 
                  style="width:80%; border:1px solid #ffffff">
            <canvas id="canvas1" style="width:100%"></canvas>      
         </div>
       </div>
     </div>
     <div class="w3-half">
       <div class="w3-container w3-padding-16">
         <div id="barontainer" 
                  style="width:80%; border:1px solid #ffffff">
            <canvas id="canvas2" style="width:100%"></canvas>      
         </div>
       </div>
     </div>
  </div>
  <div class="w3-panel">
    <sitemesh:write property="body"/>
  </div>
  <hr>
  <!-- Footer -->
  <footer class="w3-container w3-padding-16 w3-light-grey">
    <h4>구디아카데미</h4>
    <p>Powered by <a href="https://www.gdu.co.kr" target="_blank">구디</a></p>
    <hr>
    <div><span id="si">
       <select name="si" onchange="getText('si')">
          <option value="">시도를 선택하세요</option>
       </select></span>
      <span id="gu">
       <select name="gu" onchange="getText('gu')">
          <option value="">구군을 선택하세요</option>
       </select></span>
      <span id="dong">
       <select name="dong">
          <option value="">동리를 선택하세요</option>
       </select></span></div>
  </footer>
  <!-- End page content -->
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
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
<script type="text/javascript" 
src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js"></script>
<script type="text/javascript">
  let randomColorFactor = function() {
	  return Math.round(Math.random() * 255)
  }
  let randomColor = function(opa) {
	  return "rgba(" + randomColorFactor() + ","
			  + randomColorFactor() + ","
			  + randomColorFactor() + "," 
			  + (opa || '.3') + ")"
  }
  $(function(){ 
	  piegraph()  //작성자별 게시물 등록 건수
	  bargraph()  //작성일자별 게시물 등록 건수
	  //ajax을 이용하여 환율 데이터 조회하기
	  exchangeRate();
	  //ajax을 이용하여 시도 데이터를 조회하기	  
	  let divid;
	  let si;
	  $.ajax({
		  url : "${path}/ajax/select",
		  success : function(data) {
			  //data : ["서울특별시","부산광역시",....]
			  let arr = JSON.parse(data)
			  $.each(arr,function(i,item){
				  //<select name="si" 인 태그 선택 
				  $("select[name=si]").append(function(){
					  return "<option>"+item+"</option>"
				  })
			  })
		  },
		  error : function(e) {
			  alert("서버오류:"+e.status)
		  }
	  })
  })
  function  piegraph() {
	  //url : "${path}/ajax/graph1" => 서버요청 url
	    $.ajax("${path}/ajax/graph1",{
	    	success : function(data) {
	    		//data : 서버에서 응답한 데이터
	    		//[{"cnt":7,"writer":"홍길동"},...]
	    		pieGraphPrint(data);
	    	},
	    	error : function(e) {
	    		alert("서버오류:"+e.status)
	    	}
	    })
  }
  function bargraph() {
	    $.ajax("${path}/ajax/graph2",{
	    	success : function(data) {
	    		barGraphPrint(data);
	    	},
	    	error : function(e) {
	    		alert("서버오류:"+e.status)
	    	}
	    })
	  
  }
  function pieGraphPrint(data) {
	  console.log(data) //[{writer:'이름1',cnt:갯수},....]
	  //rows : JSON 객체. 배열객체
	  let rows = JSON.parse(data)
	  let writers = []  //x축의 내용
	  let datas = []
	  let colors = []
	  $.each(rows,function(i,item){
		  writers[i] = item.writer
		  datas[i] = item.cnt
		  colors[i] = randomColor(1);
	  })
	  let config = {
		  type : 'pie',
		  data : {
			  datasets : [{
				  data : datas,
				  backgroundColor : colors
			  }],
			  labels : writers
		  },
		  options : {
			  responsive : true,
			  legend : {position : 'top'},
			  title : {
				  display : true,
				  text : '게시물 작성자별 등록 건수',
				  position : "bottom"
			  }
		  }
	  }
	  let ctx = document.getElementById("canvas1").getContext("2d")
	  new Chart(ctx,config)
  }
  function barGraphPrint(data){
	  console.log(data)
	  //[{"regdate":"2023-04-07","cnt":8},
	  // {"regdate":"2023-04-06","cnt":10},...]
	  let rows = JSON.parse(data)
	  let regdates = []
	  let datas = []
	  let colors = []
	  $.each(rows,function(i,item){
		  regdates[i]=item.regdate
		  datas[i]=item.cnt
		  colors[i] = randomColor(1)
	  })
	  let chartData = {
		  labels : regdates,
		  datasets : [{
			  type : "line",
			  borderWidth : 2,
			  borderColor:colors,
			  label :'건수',
			  fill:false,
			  data : datas
		  },{
			  type : 'bar',
			  label : '건수',
			  backgroundColor : colors,
			  data : datas
		  }]
	  }
	  let config = {
		  type : 'bar',
		  data : chartData,
		  options : {
			  responsive : true,
			  title : {display : true,
				  text : '최근 7일 게시판 등록건수',
				  position : 'bottom'},
			  legend : {display : false},
			  scales : {
				  xAxes : [{display : true, stacked:true}],
				  yAxes : [{display : true, stacked:true}]
			  }
		  }
	  }
	  let ctx = document.getElementById("canvas2").getContext("2d")
	  new Chart(ctx,config)
  }
  function getText(name) { //si : 시도 선택, gu:구군 선택
	let city = $("select[name='si']").val()
	let gun = $("select[name='gu']").val()
	let disname;
    let toptext='구군을 선택하세요'
    let params = ''
    if(name=='si') {
    	params = "si=" + city.trim()
    	disname = "gu"
    } else if (name=='gu') {
    	params = "si=" + city.trim()+"&gu="+gun.trim()
    	disname = "dong"
    	toptext='동리를 선택하세요'
    } else {
    	return 
    }
    $.ajax({
    	url : "${path}/ajax/select",
    	type : "POST",
    	data:params,
    	success : function(data) {
    		console.log(data)
    		let arr = JSON.parse(data)
    		$("select[name="+disname+"] option").remove()
    		$("select[name="+disname+"]").append(function(){
    			return "<option value=''>"+toptext+"</option>"
    		})
    		$.each(arr,function(i,item){
        		$("select[name="+disname+"]").append(function(){
        			return "<option>"+item+"</option>"
    		    })
    		})
    	   },
    	error : function(e){
    		alert("서버오류:"+e.status)
    	}
    })
  }
  function exchangeRate() {
	  $.ajax("${path}/ajax/exchange",{
		  success : function(data) {
			  $("#exchange").html(data)
		  },
		  error : function(e) {
			  alert("환율조회시 서버 오류:" + e.status)
		  }
	  })
  }
</script>
</body>
</html>