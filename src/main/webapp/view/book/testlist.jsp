<%@page import="model.BookDao"%>
<%@page import="model.Book"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /jspstudy1/src/main/webapp/model1/test/testlist.jsp : 방명록 목록 조회하기
   1. db에서 방명록 목록 조회하기
   2. 조회된 내용 화면에 출력하기
--%>    
<%
    //list : book 테이블의 모든 정보를 저장
    List<Book> list = new BookDao().list();  
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록 목록</title>
<link rel="stylesheet" href="../../css/main.css">
</head>
<body>
<table><caption>회원목록</caption>
<tr><th>작성자</th><th>제목</th><th>내용</th></tr>
<% for(Book b : list) {%>
<tr><td><%=b.getWriter()%></td>
<td><%=b.getTitle()%></td>
<td><%=b.getContent()%></td>
</tr>
<% } %>
</body>
</html>