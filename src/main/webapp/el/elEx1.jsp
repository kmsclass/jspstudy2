<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /jspstudy2/src/main/webapp/el/elEx1.jsp --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>elForm.jsp의 결과 화면</title>
</head><body>
<%
   request.setCharacterEncoding("UTF-8");
   String tel = "010-1111-2222";
   String tel1 = "010-1111-3333";
   pageContext.setAttribute("tel", tel);
   pageContext.setAttribute("test", "pageContext 객체의 test 속성");
   request.setAttribute("test", "request 객체의 test 속성");
   application.setAttribute("test", "application 객체의 test 속성");
%>
<h3>JSP의 스크립트를 이용하여 파라미터와 속성값 출력</h3>
pageContext test 속성값 : <%=pageContext.getAttribute("test") %><br>
session test 속성값 : <%=session.getAttribute("test") %><br>
today 속성값 : <%=session.getAttribute("today") %><br>
name 파라미터값 : <%=request.getParameter("name") %><br>
tel 변수값 : <%=tel %><br>
tel 속성값 : <%=pageContext.getAttribute("tel") %><br>
noAttr 속성값 : <%=pageContext.getAttribute("noAttr") %><br><br>
noparam 파라미터값 : <%=request.getParameter("noparam") %><br>

<h3>JSP의 EL(표현언어)를 이용하여 파라미터와 속성값 출력</h3>
pageContext test 속성값 : ${pageScope.test}<br>
request test 속성값 : ${requestScope.test }<br>
session test 속성값 : ${sessionScope.test }<br>
application test 속성값 : ${applicationScope.test }<br>
today 속성값 : ${today }<br>
name 파라미터값 : ${param.name}<br>
tel1 변수값 : EL로 표현 못함,${tel1}<br>
tel 속성값 : ${pageScope.tel }<br>
noAttr 속성값 : ${pageScope.noAttr }<br><br>
noparam 파라미터값 : ${param.noparam }<br>
<br>
<h3>영역을 표시하여 test 속성값 출력하기</h3>
\${test} = ${test}<br>
\${pageScope.test} = ${pageScope.test}<br>
\${requestScope.test} = ${requestScope.test}<br>
\${sessionScope.test} = ${sessionScope.test}<br>
\${applicationScope.test} = ${applicationScope.test}<br>
\${today} = ${today}<br>
\${pageScope.today} = ${pageScope.today}<br>
\${requestScope.today} = ${requestScope.today}<br>
\${sessionScope.today} = ${sessionScope.today}<br>
\${applicationScope.today} = ${applicationScope.today}<br>

<%--
  == ${test} : 영역담당 객체에 저장된 속성 중 이름이 test인 속성값을 출력
   1. page 영역에 등록된 pageScope.test 속성 값 리턴 
   2. 1번의 내용 없으면, request 영역에 등록된 requestScope.test 속성 값 리턴 
   3. 1,2번의 내용 없으면, session 영역에 등록된 sessionScope.test 속성 값 리턴 
   4. 1,2,3번의 내용 없으면, application 영역에 등록된 applicationScope.test 속성 값 리턴
   5. 1,2,3,4번의 내용이 없으면 공란을 리턴
  == 직접 영역 담당 객체를 표현하면 영역에 해당하는 속성을 리턴  
   1. 해당 영역에 등록된 속성이 없으면 공란 리턴
--%>