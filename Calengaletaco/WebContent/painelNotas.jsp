<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Anotação</title>
<link href="css/bootstrap.min.css" rel='stylesheet'/>
</head>
<body>
	<div class="container" style="width: 400px">	
		<c:if test="${sessionScope.usuario == null}">
			<c:redirect url="index.jsp"></c:redirect>
		</c:if>
		
		<h1>Adicionar Anotação</h1>
		${erro}
		${ok}
		<form action="executa?op=addNota" method="POST">
			Descricao: <input type="text" name="descricao" class="form-control" required/>
			Data <input type="date" name="data" class="form-control" required/>
			<br/><br/>
			<input type="submit" value="Adicionar Anotação" class="btn btn-primary btn-sm">
			<c:if test ="${sessionScope.usuario.isadmin=='false'}">
			<a href="painel.jsp" class="btn btn-primary btn-sm"> Voltar </a>
		</c:if>
		</form>
	
		
			
			<br/>	
			<c:if test="${not empty anotacoes}">
				<h3>Anotações</h3>
				<table class="table" style="width: 435px">
					<c:forEach var="anotacao" items="${listaNotas}">
						<tr>
							<td>${anotacao.descricao}</td>
							<td>${anotacao.data}</td>
							<td><a href="executa?op=buscaNotas&id=${anotacao.id}" class="btn btn-primary btn-sm">Editar</a></td>
							<td><a href="executa?op=excluirNotas&id=${anotacao.id}" class="btn btn-primary btn-sm">Excluir</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:if>	
	</div>	
</body>
</html>