package br.edu.ifpb.pweb.calendario.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpSession;

import br.edu.ifpb.pweb.calendario.controller.ControllerFacade;
import br.edu.ifpb.pweb.calendario.model.Feriado;

/**
 * Servlet Filter implementation class FilterCalendario
 */
@WebFilter(filterName = "FilterCalendario", urlPatterns = {"/index.jsp"})
public class FilterCalendario implements Filter {
	
	HttpSession session;
	   
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		request.setAttribute("feriados", ControllerFacade.listarFeriadosFullCalendar());
		chain.doFilter(request, response);
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
