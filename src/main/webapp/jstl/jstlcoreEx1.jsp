<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- taglib : 접두사가 c인 태그의 상세 정보는 
   uri="http://java.sun.com/jsp/jstl/core" 인 파일에 존재 --%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%-- /jspstudy2/src/main/webapp/jstl/jstlcoreEx1.jsp --%>    
<!DOCTYPE html>
<html><head><meta charset="UTF-8">
<title>JSTL core 태그 1</title></head>
<body>
<h3>속성 관련 태그 : set, remove, out 태그</h3>
<%-- set 태그 : 속성 등록
     remove 태그 : 속성 제거
     out 태그 : 속성 조회. excape xml 처리됨.
 --%>
<%-- 
<c:set var="test" value="${'Hello JSTL'}" scope="session" />
--%>
<% session.setAttribute("test","<div style='color:blue'>Hello JSTL</div>"); %>
test 속성 : ${sessionScope.test }<br>
test 속성 : <c:out value="${test}" /><br>
test 속성 : ${test}<br>
<c:remove var="test" />
test 속성 : ${test}<br>
</body>
</html>