<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /jspstudy2/src/main/webapp/jstl/jstlfmtEx1.jsp --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL 형식화 태그</title>
</head>
<body>
<h3>숫자 관련 형식 지정하기</h3>
<%-- type="currency" : 해당 지역의 화폐 표시 --%>
<fmt:formatNumber value="500000000" type="currency"
    currencySymbol="￦" /><br>
<%-- 지역 설정 --%>
<fmt:setLocale value="en_US"/>
<fmt:formatNumber value="500000000" type="currency" /><br>
<fmt:setLocale value="ko_KR"/>    
<fmt:formatNumber value="500000000" type="currency" 
     currencySymbol=""/><br>
<fmt:formatNumber value="500000.345" pattern="###,###.00"/><br>
<fmt:formatNumber value="0.15" type="percent"/><br>
<h3>날짜 관련 형식 지정하기</h3>
<c:set var="today" value="<%=new java.util.Date() %>" />
년월일:<fmt:formatDate value="${today}" type="date" /><br>
시분초:<fmt:formatDate value="${today}" type="time" /><br>
년월일시분초1:<fmt:formatDate value="${today}" type="both" /><br>
년월일시분초2:<fmt:formatDate value="${today}" type="both" 
          timeStyle="short" dateStyle="short"/><br>
년월일시분초3:<fmt:formatDate value="${today}" type="both" 
          timeStyle="long" dateStyle="long"/><br>
년월일시분초4:<fmt:formatDate value="${today}" type="both" 
          timeStyle="full" dateStyle="full"/><br>
형식지정 : <fmt:formatDate value="${today}"
          pattern="yyyy년 MM월 dd일 HH:mm:ss a E" /><br>          
</body>
</html>