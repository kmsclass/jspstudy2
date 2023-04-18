<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /jspstudy2/src/main/webapp/view/board/replyForm.jsp --%>
<!DOCTYPE html><html><head><meta charset="UTF-8">
<title>게시판 답글 쓰기</title></head>
<body><form action="reply" method="post" name="f">
<input type="hidden" name="num" value="${board.num}">
<input type="hidden" name="grp" value="${board.grp}" >
<input type="hidden" name="grplevel" value="${board.grplevel}" >
<input type="hidden" name="grpstep" value="${board.grpstep}" >
<input type="hidden" name="boardid" value="${board.boardid}" >
<div class="w3-container w3-center">
<h2>${(board.boardid=='1')?"공지사항":(board.boardid=='2')?"자유게시판":"QNA"}</h2>
<table class="w3-table-all">
<tr><th>글쓴이</th><td><input type="text" name="writer" class="w3-input"></td></tr>
<tr><th>비밀번호</th><td><input type="password" name="pass" class="w3-input"></td></tr>
<tr><th>제목</th><td><input type="text" name="title"  class="w3-input" 
      value="RE:${board.title}"></td></tr>
  <tr><th>내용</th>
  <td><textarea name="content" rows="15"  class="w3-input" id="content"></textarea></td></tr>
<script>CKEDITOR.replace("content",{
	filebrowserImageUploadUrl : "imgupload"	 
 })</script>
  
  <tr><td colspan="2" class="w3-center">
  <a href="javascript:document.f.submit()">[답변글등록]</a></td></tr>    
  </table></div></form></body></html>