<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>    
<%-- /jspstudy2/src/main/webapp/jstl/jstlfmtEx2.jsp  --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>형식 관련 JSTL 2</title>
</head>
<body>
<h3>Format 된 숫자형 문자열을 숫자로 변경하기</h3>
<fmt:parseNumber value="20,000" var="num1" pattern="##,###" />
<fmt:parseNumber value="10,000" var="num2" pattern="##,###" />
합:${num1} + ${num2} = ${num1+num2}<br>
문제 : 합:20,000 + 10,000 = 30,000 출력하기<br>
<fmt:formatNumber value="${num1}" var="snum1" pattern="##,###" /> 
<fmt:formatNumber value="${num2}" var="snum2" pattern="##,###" />  
<fmt:formatNumber value="${num1+num2}" var="snum3" pattern="##,###" />
${snum1}+${snum2} = ${snum3} <br>

<h3>Format 된 문자열형 날짜를 날짜형으로 변경</h3>
<fmt:parseDate value="2023-12-25 12:00:00" 
   pattern="yyyy-MM-dd HH:mm:ss" var="day" />
${day} <br>  
<%-- 2023-12-25의 요일만 출력하기 --%>
<fmt:formatDate value="${day}" pattern="E요일" var="week"/>
${week}
</body>
</html>