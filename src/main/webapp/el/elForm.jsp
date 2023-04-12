<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- /jspstudy2/src/main/webapp/el/elForm.jsp
   문제 : 스크립트의 내용을 jstl로 변경하기.    
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%	//session.setAttribute("test", "session test 속성");
	//session.setAttribute("today", new Date());
%>
 <c:set var="test" value="session test 속성"  scope="session"/>
 <c:set var="today" value="<%=new Date() %>" scope="session"/>
 
	<form action="elEx1.jsp" method="post">
		이름 <input type="text" name="name" value="홍길동"> 
		<input	type="submit" value="전송">
	</form>
</body>
</html>