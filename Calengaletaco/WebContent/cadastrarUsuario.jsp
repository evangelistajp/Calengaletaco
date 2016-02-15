<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastrar Usuario</title>

<link href="css/bootstrap.min.css" rel='stylesheet'/>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container" style="width: 400px">
		<h1>Cadastrar Usuário</h1>
		${erro}
		<form action="executa?op=cadastrar" method="POST">
			Nome: <input type="text" name="nome" class="form-control" placeholder="Name" required/>
			Email: <input type="text" name="email" class="form-control" placeholder="Email address" required/>
			Senha: <input type="password" name="senha" class="form-control" placeholder="Password" required/>
			<br />
			<input type="submit" value="Cadastrar" class="btn btn-primary btn-sm"/>
			<a href="index.jsp" class="btn btn-primary btn-sm">Voltar</a>
		</form>
		
	</div>
</body>
</html>