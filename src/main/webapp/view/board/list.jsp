<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>    
<%-- /jspstudy2/src/main/webapp/view/board/list.jsp--%>
<!DOCTYPE html><html><head><meta charset="UTF-8">
<title>게시물 목록 보기</title>
<script type="text/javascript">
    function listsubmit(page) {
    	f = document.sf;  //검색 form 태그
    	f.pageNum.value=page;
    	f.submit();
    }
</script>    
</head><body>
<div class="w3-container">
<h2 class="w3-center">${boardName}</h2>

<div  class="w3-container w3-center">
<form  action="list?boardid=${boardid}"  method="post" name="sf">
   <input type="hidden" name="pageNum" value="1" >
   <select  class="w3-select" name="column" >
     <option value="" >선택하시오</option>
     <option value="writer" >글쓴이</option>
	 <option value="title">제목</option>
	 <option value="content">내용</option>
     <option value="title,writer">제목+작성자</option>
     <option value="title,content">제목+내용</option>
     <option value="writer,content">작성자+내용</option>
     <option value="title,writer,content">제목+작성자+내용</option></select>
     <script type="text/javascript">
         document.sf.column.value='${param.column}'
     </script>
	 <input class="w3-input" type="text"
	   placeholder="Search" name="find" value="${param.find}">
	<button class="btn btn-border" type="submit">Search</button>
</form></div><p>
<table class="w3-table-all">
 <c:if test="${boardcount == 0}"> 
 <tr><td colspan="5">등록된 게시글이 없습니다.</td></tr>
 </c:if>
 <c:if test="${boardcount > 0}"> 
<tr><td colspan="5" style="text-align:right">글개수:${boardcount}</td></tr>
<tr><th width="8%">번호</th><th width="50%">제목</th>
    <th width="14%">작성자</th><th width="17%">등록일</th>
    <th width="11%">조회수</th></tr>
<c:forEach var="b" items="${list}">
 <tr><td>${boardnum}</td>
 <c:set var="boardnum" value="${boardnum - 1}" />
 <td style="text-align: left">
 <%-- 첨부파일 @로 표시하기 --%>
  <c:if test="${!empty b.file1}">
     <a href="../upload/board/${b.file1}">@</a></c:if>  
  <c:if test="${empty b.file1}">&nbsp;&nbsp;&nbsp;</c:if>
<%-- 답글의 level 만큼 들여쓰기 --%>
   <c:if test="${b.grplevel > 0}">
      <c:forEach var="i" begin="1" end="${b.grplevel}">   
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      </c:forEach>└ <%-- ㅂ한자 --%>
   </c:if>   
  <a href="info?num=${b.num}">${b.title}</a>
  <%-- 댓글 갯수 badge로 표현 시작 --%>
    <c:if test="${b.commcnt > 0}">
    <a href="info?num=${b.num}#comment">
    <span class="w3-badge w3-blue w3-tiny">${b.commcnt}</span></a>
    </c:if>
  <%-- 댓글 갯수 badge로 표현 끝 --%>
  </td>
 <td>${b.writer}</td>
<%-- 오늘 등록된 게시물 날짜 format대로 출력하기 --%>
 <fmt:formatDate value="${today}" pattern="yyyy-MM-dd" var="t" /> 
 <fmt:formatDate value="${b.regdate}" pattern="yyyy-MM-dd" var="r" /> 
 <td><c:if test="${t==r}">
     <fmt:formatDate value="${b.regdate}" pattern="HH:mm:ss" />
	 </c:if>
	 <c:if test="${t!=r}">
     <fmt:formatDate value="${b.regdate}" pattern="yyyy-MM-dd HH:mm" />
   </c:if></td>
 <td>${b.readcnt}</td></tr>
</c:forEach>
<%-- 페이지 처리하기 --%>
 <tr><td colspan="5" class="w3-center">
      <c:if test="${pageNum <= 1}">[이전]</c:if>
      <c:if test="${pageNum > 1}">
      <a href="javascript:listsubmit(${pageNum-1})">[이전]</a>
      </c:if>
      <c:forEach var="a" begin="${startpage}" end="${endpage}">
        <c:if test="${a == pageNum}">[${a}]</c:if>
        <c:if test="${a != pageNum}">
          <a href="javascript:listsubmit(${a})">[${a}]</a>
        </c:if>
      </c:forEach>
      <c:if test="${pageNum >= maxpage}">[다음]</c:if>
      <c:if test="${pageNum < maxpage}">
      <a href="javascript:listsubmit(${pageNum+1})">[다음]</a>
      </c:if>
 </td></tr>  
</c:if>
  <tr><td colspan="5" style="text-align:right">
<c:if  test="${boardid != '1' || sessionScope.login == 'admin'}">
	<p align="right"><a href="writeForm">[글쓰기]</a></p>
</c:if>		
</td></tr></table></div></body></html>