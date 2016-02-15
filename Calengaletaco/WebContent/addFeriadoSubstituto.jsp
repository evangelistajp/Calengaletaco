<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Adicionar Feriado Substituto</title>
<link href="css/bootstrap.min.css" rel='stylesheet'/>
</head>
<body>
	<div class="container" style="width: 400px">
		<c:if test="${sessionScope.usuario == null}">
			<c:redirect url="index.jsp"></c:redirect>
		</c:if>
		<c:if test="${!sessionScope.usuario.isadmin}">
			<c:redirect url="index.jsp"></c:redirect>
		</c:if>
		<h1>Adicionar Feriado Substituto</h1>
		${erro}
		${ok}
		<form action="executa?op=addFeriadoSubstituto" method="POST">
			<h3>Nome : ${feriado.nome}</h3>
			<h3>Data : ${feriado.data}</h3>
			<input type="hidden" name="id" value="${feriado.id}" required>
			<input type="hidden" name="nome" value="${feriado.nome}" required>
			<input type="hidden" name="data2" value="${feriado.data}" required>
			Qual a nova Data: <input type="date" name="data" value="${feriado.data}" class="form-control" required />
			<br/><br/>
			<input type="submit" value="Adicionar Feriado Substituto" class="btn btn-primary btn-sm">
			<a href="painelFeriados.jsp" class="btn btn-primary btn-sm"> Voltar </a>
		</form>
		
	</div>
				
</body>
</html>