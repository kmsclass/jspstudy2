<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /jspstudy2/src/main/webapp/view/member/picture.jsp
   opener 화면에 결과 전달 =>javascript
   현재 화면 close() =>javascript
--%>
<script type="text/javascript">
   img = opener.document.getElementById("pic");
   img.src = "/jspstudy2/picture/${fname}"
   opener.document.f.picture.value="${fname}"; 
   self.close();  //현재창 닫기
</script>