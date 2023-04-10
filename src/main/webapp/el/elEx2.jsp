<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /jspstudy2/src/main/webapp/el/elEx2.jsp --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL 에서 연산자 사용하기</title>
</head>
<body>
<h3>\${5+7}=${5+7}</h3>
<h3>\${8-3}=${8-3}</h3>
<h3>\${8/3}=${8/3}</h3>
<h3>\${8 div 3}=${8 div 3}</h3>
<h3>\${8 % 3}=${8 % 3}</h3>
<h3>\${8 mod 3}=${8 mod 3}</h3>
<h3>\${10 == 9}=${10 == 9}</h3>
<h3>\${10 eq 9}=${10 eq 9}</h3>
<h3>\${10 != 9}=${10 != 9}</h3>
<h3>\${10 ne 9}=${10 ne 9}</h3>
<h3>\${10 >= 9}=${10 >= 9}</h3>
<h3>\${10 ge 9}=${10 ge 9}</h3>
<h3>\${10 < 9}=${10 < 9}</h3>
<h3>\${10 lt 9}=${10 lt 9}</h3>
<h3>\${10 > 9}=${10 > 9}</h3>
<h3>\${10 gt 9}=${10 gt 9}</h3>
<h3>\${5+4==8?8:10}=${5+4==8?8:10}</h3>
<h3>\${5+3==8?8:10}=${5+3==8?8:10}</h3>
<h3>EL에서 상수 표현</h3>
<h4>\${10}=${10 }</h4>
<h4>\${'test'}=${'test'}</h4>
<h4>\${"test"}=${"test"}</h4>
</body>
</html>
<%-- 1. http://mvnrepository.com
     2. jstl 검색
     3. jstl>jstl 선택
     4. 1.2 선택
     5. jar 파일 다운 받기 => jstl-1.2.jar 
     6. WEB-INF/lib 폴더에 jstl-1.2.jar 파일 붙여넣기 
     --%>