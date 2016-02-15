<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Alterar Senha</title>
<link href="css/bootstrap.min.css" rel='stylesheet'/>
</head>
<body>
	<div class="container" style="width: 400px">
		<c:if test="${!sessionScope.usuario.isadmin}">
			<c:redirect url="index.jsp"></c:redirect>
		</c:if>
		<h3>Admin : ${usuario.nome}</h3>
		${erro}
		${ok }
		<form action="executa?op=alterarsenha" method="post">
	
		    Email: <input type="text" name="email" class="form-control" required/>
		    Nova Senha: <input type="password"  name="novasenha" class="form-control" required/>
		    Repita a Senha: <input type="password"  name="novasenha2" class="form-control" required/>
		    <br />
		    <input type="submit" value="Mudar Senha" class="btn btn-primary btn-sm" />
	        <a href="painel.jsp" class="btn btn-primary btn-sm"> Voltar </a>
		</form>
		
	</div>	
</body>
</html>