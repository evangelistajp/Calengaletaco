<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
	
	<link href="css/fullcalendar.css" rel="stylesheet"/>
	<link href="css/fullcalendar.print.css" rel="stylesheet" media="print"/>
	<link href="css/bootstrap.min.css" rel='stylesheet'/>
	
	<script src="js/jquery.min.js"></script>
	<script src="js/moment.min.js"></script>
	<script src="js/fullcalendar.min.js"></script>
		
	<script src="js/bootstrap.min.js"></script>
	
	
	

</head>
<body>

	<div class="panel panel-primary" align="center"   >
      <div class="panel-heading">Calendário WEB</div>
      
		${erro}
		${ok }
		<form action="executa?op=login" method="POST">

		
			<input type="text" name="email" class="form-control" style="width: 200px"  placeholder="Email address" required/>
			<input type="password" name="senha" class="form-control" style="width: 200px" placeholder="Password" required/>
			<input type="submit" value="Login" class="btn btn-primary btn-sm"/>
		    
		     
		<a href="cadastrarUsuario.jsp" class="btn btn-primary btn-sm">Cadastrar</a>
		</form>

		
	
	</div><!-- /container -->
	</div>

	
	
	
	<div class="container">
		<div id='calendar'><%@ include file ="calendario.jsp" %></div>
	</div>
	
</body>
</html>