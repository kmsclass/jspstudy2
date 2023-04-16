<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--  /jspstudy2/src/main/webapp/view/test/test1.jsp-->    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록</title>
</head>
<body>
  <h2>방명록 등록 결과 </h2>
<table class="w3-table-all">
     <tr><td>이름</td><td>${book.writer}</td></tr>
     <tr><td>제목</td><td>${book.title}</td></tr>
     <tr><td>내용</td><td>${book.content}</td></tr></table>
</body></html>
