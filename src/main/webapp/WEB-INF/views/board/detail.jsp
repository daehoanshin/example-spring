<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Example</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<div class="card">
			<div class="card-header">Quote</div>
			<div class="card-body">
				<blockquote class="blockquote mb-0">
					<p>${board.contents}</p>
					<footer class="blockquote-footer">
						<fmt:formatDate value="${board.regDate}" pattern="yyyy.MM.dd HH:mm" />
					</footer>
				</blockquote>
			</div>
		</div>
		<div class="d-grid gap-2 d-md-flex justify-content-md-end mt-3">
			<a href="/${menuType}" class="btn btn-secondary" type="button"><spring:message code="button.list" /></a>
			<a href="/${menuType}/edit/${board.boardSeq}" class="btn btn-primary" type="button"><spring:message code="button.edit" /></a>
		</div>
	</div>

</body>
</html>