<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%-- /jspstudy2/src/main/webapp/jstl/jstlcoreEx3.jsp --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jstl core 태그 : 반복문</title></head>
</head>
<body>
<h3>반복 관련 태그 : forEach</h3>
<c:forEach var="i" begin="1" end="10">
  ${i}&nbsp;&nbsp;
</c:forEach><br>
<c:forEach var="i" begin="1" end="10" step="2">
  ${i}&nbsp;&nbsp;
</c:forEach><br>
<c:forEach var="i" begin="2" end="10" step="2">
  ${i}&nbsp;&nbsp;
</c:forEach>
<h4>1에서 100까지의 합 구하기</h4>
<c:forEach var="i" begin="1" end="100">
  <c:set var="sum" value="${sum+i }"/>
</c:forEach>
1부터 100까지의 합:${sum}<br>
<c:set var="sum" value="${0}"/>
<c:forEach var="i" begin="2" end="100" step="2">
  <c:set var="sum" value="${sum+i }"/>
</c:forEach>
1부터 100까지의 짝수합:${sum}<br>
<c:set var="sum" value="${0}"/>
<c:forEach var="i" begin="" end="100">
  <c:if test="${i%2==0}">
     <c:set var="sum" value="${sum+i }"/>
  </c:if>   
</c:forEach>
1부터 100까지의 짝수합:${sum}<br>

<c:set var="sum" value="${0}"/>
<c:forEach var="i" begin="1" end="100" step="2">
  <c:set var="sum" value="${sum+i }"/>
</c:forEach>
1부터 100까지의 홀수합:${sum}<br>
<c:set var="sum" value="${0}"/>
<c:forEach var="i" begin="1" end="100">
  <c:if test="${i%2==1}">
     <c:set var="sum" value="${sum+i }"/>
  </c:if>   
</c:forEach>
1부터 100까지의 홀수합:${sum}<br><br>
<h3>forEach 태그를 이용하여 구구단 출력하기</h3>
<c:forEach var="i" begin="2" end="9">
  <h4>${i}단</h4>
  <c:forEach var="j" begin="2" end="9">
     ${i } * ${j} = ${i*j}<br>
  </c:forEach>
</c:forEach>
<h3>forEach 태그를 이용하여 List 객체 출력하기</h3>
<%
    List<Integer> list = new ArrayList<>();
    for(int i=1;i<=10;i++) list.add(i*10);
    pageContext.setAttribute("list", list);
    for(Integer i : list) {%>
      <%=i %><br>    	
<% } %>

<c:forEach var="i" items="${list}"><%-- i: list의 요소값 --%>
  ${i }&nbsp;&nbsp;
</c:forEach>
<br>
<%-- i:인덱스값.0부터시작. end="9" 까지만 실행 --%>
<c:forEach var="i" begin="0" end="20">
  ${list[i]}&nbsp;&nbsp;
</c:forEach>
<%--
  varStatus="st" : 반복문의 상태를 st 변수로 설정
    st.index : 0부터 시작. 인덱스(첨자)
    st.count : 1부터 시작. 순서
 --%>
<c:forEach var="i" items="${list}" varStatus="st">
<%--  <c:if test="${st.index==0 }"> --%> <%-- 첫번째 요소? --%>
 <c:if test="${st.count==1 }">
   <h4>forEach 태그의 varStatus 속성 연습</h4>
 </c:if>
  ${st.index }:${i },${list[st.index]} &nbsp;&nbsp;&nbsp;
  ${st.count }:${i },${list[st.count-1]}
  <br>
</c:forEach>

</body>
</html>