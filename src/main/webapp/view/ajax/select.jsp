<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /jspstudy2/src/main/webapp/view/ajax/select.jsp --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- list의 데이터를 JSON(배열) 형태의 문자열로 출력 --%>    
[
<c:forEach var="s" items="${list}" varStatus="stat">
  "${s}" <c:if test="${stat.count < len}">,</c:if>
</c:forEach>
]
