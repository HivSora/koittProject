<%@page import="article.model.ArticleContent"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${articleData.article.writer.name}님의 게시글</title>
</head>
<body>
	<table border=1>
		<tr>
			<th colspan="6">${articleData.article.title }</th>
		</tr>
		<tr>
			<th>번호</th>
			<th>작성자</th>
			<th>게시일</th>
			<th>수정일</th>
			<th>조회수</th>
		</tr>
		<tr>
			<td>${articleData.article.articleId}</td>
			<td>${articleData.article.writer.name}</td>
			<td>${articleData.article.wdate }</td>
			<td>${articleData.article.udate }</td>
			<td>${articleData.article.readCnt}</td>
		</tr>
		<tr>
			<td colspan="6">
				<u:pre value="${articleData.content }"></u:pre>
			</td>
		</tr>
		<c:if test="${authUser.userId == articleData.article.writer.writerId }">
		<tr>
			<td colspan="5"><a href="modify?no=${articleData.article.articleId }">[게시글 수정]</a></td>
		</tr>
		<tr>	
			<td colspan="5"><a href="delete?no=${articleData.article.articleId }">[게시글 삭제]</a></td>
		</tr>
		</c:if>
		<tr>
			<c:set var="pageNo" value="${empty param.pageNo ? '1' : param.pageNo }"></c:set>
			<td colspan="5"><a href="list?pageNo=${pageNo } ">[게시글 목록]</a></td>
		</tr>
		<tr>
			<td colspan="5"><a href="../index.jsp">[메인으로]</a></td>
		</tr>
	</table>
</body>
</html>