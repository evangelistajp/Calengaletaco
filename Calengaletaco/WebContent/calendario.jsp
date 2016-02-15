<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Calendario WEB</title>

<link href="css/fullcalendar.print.css" rel='stylesheet' media='print'/>
<link href="css/fullcalendar.css" rel='stylesheet'/>

<script src='js/jquery.min.js'></script>
<script src='js/jquery-ui.custom.min.js'></script>
<script src='js/fullcalendar.min.js'></script>
<script src='js/fullcalendar.js'></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-datepicker.min.js"></script>
<script src='js/pt-br.js'></script>
<script src='js/lang-all.js'></script>

<script type="text/javascript">

$(document).ready(function() {
	$('#calendar').fullCalendar({
		header: {
			left: 'title',
			right: 'prev,next today'
		},
		editable : false,
		selectable: true,
		selectHelper: true,
		lang: 'pt-br',
		eventSources: [
					<c:if test="${!empty anotacoes}">
						{
							events: [
								${anotacoes}
							],
							color: 'blue',
							textColor: 'white'
			             },
			        </c:if>
			             
			        <c:if test="${!empty feriados}">
						{
							events: [
								${feriados}
							],
							color: 'red',
							textColor: 'white'
				         },
				     </c:if>
			           ],
	 	monthNames:['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho',
                    'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
	});
});
</script>
<style>
	

	body {
		margin: 40px 10px;
		padding: 0;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		font-size: 14px;
	}

	#calendar {
		max-width: 900px;
		margin: 0 auto;
	}

	
</style>




</head>
<body>
	<div id='wrap'>
		<div id='calendar' class="fc fc-ltr fc-unthemed">
			
		
		</div>
		<div style='clear:both'></div>
	</div>
</body>
</html>

