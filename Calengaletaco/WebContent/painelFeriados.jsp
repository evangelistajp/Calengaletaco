<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Adicionar Feriados</title>
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
		<h1>Adicionar Feriados</h1>
		${erro}
		${ok}
		<form action="executa?op=addFeriado" method="POST">
			Nome: <input type="text" name="nome" class="form-control" required/>
			Data <input type="date" name="data" class="form-control" required/>
			<h4><input type="checkbox" name="fixo" value="FIXO" > FIXO</h4>
			<input type="submit" value="Adicionar Feriado" class="btn btn-primary btn-sm">
			<a href="painel.jsp" class="btn btn-primary btn-sm"> voltar </a>
		</form>
		
		<br/>
		<c:if test="${not empty feriados}">
				
			<table class="table" style="width: 500px" align="center">
				<h3>Feriados</h3>
				<c:forEach var="feriado" items="${listaferiados}">
					
					<tr>
						<td>${feriado.nome}</td>
						<td>${feriado.data}</td>
						<td>
							<c:if test="${feriado.fixo}"> Fixo</c:if>
							<c:if test="${!feriado.fixo}"> Movel</c:if>	
						</td>
						<td><a href="executa?op=buscaFeriado&id=${feriado.id}" class="btn btn-primary btn-sm">Editar</a></td>
						<td><a href="executa?op=excluirFeriado&id=${feriado.id}" class="btn btn-primary btn-sm">Excluir</a></td>
						<td><c:if test="${feriado.fixo}">
								<a href="executa?op=buscaFeriadoSubstituto&id=${feriado.id}" class="btn btn-primary btn-sm">Substituir 
							</c:if></td>
					</tr>
				</c:forEach>	
			</table>
			<table class="table" style="width: 500px" align="center">
				<h3>Feriados Substitutos</h3>
				<c:forEach var="substituto" items="${listaferiadossubstitutos}">
					
					<tr>
						<td>${substituto.nome}</td>
						<td>${substituto.data}</td>
						<td><a href="executa?op=excluirFeriadoSubstituto&id=${substituto.id}" class="btn btn-primary btn-sm" >Excluir</a></td>
					</tr>
					
				</c:forEach>
			</table>
		</c:if>
	</div>
</body>
</html>