<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /jspstudy1/src/main/webapp/model1/member/pwForm.jsp--%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호찾기</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head><body>
<div class="container">
<h3>비밀번호찾기</h3>
<form action="pw" method="post">
  <table class="table">
     <tr><th>아이디</th><td><input type="text" name="id"></td></tr>
     <tr><th>이메일</th><td><input type="text" name="email"></td></tr>
     <tr><th>전화번호</th><td><input type="text" name="tel"></td></tr>
     <tr><td colspan="2"><input type="submit" value="비밀번호찾기"></td></tr>
  </table>	
</form></div></body></html>