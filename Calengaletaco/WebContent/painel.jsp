<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Calendario Web</title>
	<link href="css/fullcalendar.css" rel="stylesheet"/>
	<link href="css/fullcalendar.print.css" rel="stylesheet" media="print"/>
	<link href="css/bootstrap.min.css" rel='stylesheet'/>
	
	
	<script src="js/jquery.min.js"></script>
	<script src="js/moment.min.js"></script>
	<script src="js/fullcalendar.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	

</head>

<body>
	
	<c:if test="${sessionScope.usuario == null}">
		<c:redirect url="index.jsp"></c:redirect>
	</c:if>
	

	<c:if test="${sessionScope.usuario.isadmin}">
		    <div class="panel panel-primary">
      <div class="panel-heading">Calendário WEB</div>
			<!-- <h1>Calendario Web</h1> -->
			<h3>Admin : ${usuario.nome}</h3>
			${erro}
			${ok}
			<a href="<c:url value="executa?op=painelFeriados"/>" class="btn btn-primary">Add Feriado</a>
			<a href="alterarSenha.jsp" class="btn btn-primary">Alterar Senha</a>
			
			<a href="<c:url value="executa?op=logout"/>" class="btn btn-primary">logout</a>
		</div><!-- /container -->
	
	</c:if>
	<c:if test="${!sessionScope.usuario.isadmin}">
	 <div class="panel panel-primary">
      <div class="panel-heading">Calendário WEB</div>
			<h3>Usuário : ${usuario.nome}</h3>
			
			<a href="<c:url value="executa?op=painelNotas"/>" class="btn btn-primary">Adicionar Anotação</a>
			<a href="<c:url value="executa?op=excluirUsuario"/>" class="btn btn-primary">Se excluir do Sistema</a>
			<a href="<c:url value="executa?op=logout"/>" class="btn btn-primary">logout</a>
		</div>
	
	</c:if>
	
		
			<div class ="fc fc-ltr ui-widget" id="calendar"><%@ include file ="calendario.jsp" %></div>
		
	
</body>
</html>