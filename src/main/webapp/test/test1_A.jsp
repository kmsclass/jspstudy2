<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- /jspstudy2/src/main/webapp/test/test1_A.jsp --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>두개의 파라미터값을 계산하기</title>
</head>
<body>
<form method="post" >
  x:<input type="text" name="x" value="${param.x}"><br>
  y:<input type="text" name="y" value="${param.y}">
   <input type="submit" value="더하기">  
</form>
합계 : <c:out value="${param.x + param.y}" />,${param.x + param.y}

<h3>if 태그를 이용하여 출력하기</h3>
<c:if test="${param.x + param.y > 0}">
${param.x + param.y}은 양수 입니다.<br></c:if>
<c:if test="${param.x + param.y < 0}">
${param.x + param.y}은 음수 입니다.<br></c:if>
<c:if test="${param.x + param.y == 0}">
${param.x + param.y}은 0 입니다.<br></c:if>

<h3>choose when 태그를 이용하여 출력하기</h3>
<c:choose>
  <c:when test="${param.x + param.y > 0}">
     ${param.x + param.y}은 양수 입니다.<br></c:when>
  <c:when test="${param.x + param.y < 0}">
     ${param.x + param.y}은 음수 입니다.<br></c:when>
  <c:otherwise>${param.x + param.y}은 0 입니다.<br></c:otherwise>  
</c:choose>
</body>
</html>