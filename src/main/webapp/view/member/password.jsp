<%@page import="model.Member"%>
<%@page import="model.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /jspstudy2/src/main/webapp/view/member/password.jsp--%>
<script>
   alert("${msg}")
   if(${opener}) {
	   opener.location.href="${url}"
	   self.close()
   } else {
	   location.href="${url}"
   }
</script>