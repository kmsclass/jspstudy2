<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /jspstudy2/src/main/webapp/view/board/deleteForm.jsp--%>   
<!DOCTYPE html><html><head>
<meta charset="UTF-8">
<title>게시글삭제</title></head>
<body>
 <h2>게시물 삭제</h2>
 <form action="delete" method="post">
		<input type="hidden"  name="num"  value="${param.num}">
		<label >Password:</label>
		<input type="password" class="w3-input" name="pass">
	<div class="w3-center" style="padding: 3px;">
		<button type="submit">게시물삭제</button>
	</div>
 </form>
 </body>
 </html>