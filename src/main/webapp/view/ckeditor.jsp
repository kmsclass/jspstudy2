<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /jspstudy2/src/main/webapp/view/ckeditor.jsp --%>
<script>
   window.parent.CKEDITOR.tools.callFunction
(${param.CKEditorFuncNum},'../upload/imgfile/${fileName}','이미지업로드완료')
</script>