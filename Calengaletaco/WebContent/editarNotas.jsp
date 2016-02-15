<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Editar Anotações</title>
<link href="css/bootstrap.min.css" rel='stylesheet'/>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</head>
<body>
	<c:if test="${sessionScope.usuario == null}">
		<c:redirect url="index.jsp"></c:redirect>
	</c:if>
	<div class="container" style="width: 400px">
		<h1>Editar Anotacao</h1>
		<form action="executa?op=editarNotas" method="POST">
			<h3>Nome: ${anotacao.descricao}</h3>
			<input type="hidden" name="id" value="${anotacao.id}" required>
			Data <input type="date" name="data" value="${anotacao.data}" class="form-control" required />
			<br/><br/>
			<input type="submit" value="Editar Anotação" class="btn btn-primary btn-sm">
			<a href="painelNotas.jsp" class="btn btn-primary btn-sm"> Voltar </a>
		</form>
		
		
	</div>
</body>
</html>