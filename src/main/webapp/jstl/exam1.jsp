<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%-- /jspstudy2/src/main/webapp/jstl/exam1.jsp --%>    
<!DOCTYPE html><html>
<head>
<meta charset="UTF-8">
<title>forEach 문제</title>
</head>
<body>
<h3>문제 : 1부터 10까지를 3개씩 출력하기</h3>
<%-- 
1  2  3
4  5  6
7  8  9
10
--%>
<c:forEach var="i" begin="1"  end="10">
  ${i}&nbsp;&nbsp;&nbsp;
  <c:if test="${i%3==0}"><br></c:if>
</c:forEach><br>
<h3>문제 : list 10부터 100까지를 3개씩 출력하기</h3>
<%
   List<Integer> list = new ArrayList<>();
   for(int i=1;i<=10;i++) list.add(i*10);
   pageContext.setAttribute("list", list);
%>
<c:forEach var="i" items="${list}" varStatus="stat">
  ${i}&nbsp;&nbsp;&nbsp;
  <c:if test="${stat.index%3==2}"><br></c:if>
</c:forEach><br>

</body>
</html>