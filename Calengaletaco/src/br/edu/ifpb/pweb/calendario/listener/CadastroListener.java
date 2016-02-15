package br.edu.ifpb.pweb.calendario.listener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;

import br.edu.ifpb.pweb.calendario.controller.ControllerFacade;
import br.edu.ifpb.pweb.calendario.dao.DAO;
import br.edu.ifpb.pweb.calendario.model.Usuario;
import br.edu.ifpb.pweb.exception.AddFeriadoException;
import br.edu.ifpb.pweb.exception.CadastroException;

/**
 * Application Lifecycle Listener implementation class CadastroListener
 *
 */
@WebListener
public class CadastroListener implements ServletContextListener {

	Usuario us;
    public CadastroListener() {
        // TODO Auto-generated constructor stub
    }


    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }


    public void contextInitialized(ServletContextEvent arg0)  { 
    	
    	DAO.open();
		try {
			us = ControllerFacade.cadastrarUsuarioAdmin("admin","admin","admin");
			ControllerFacade.addFeriado(us, "CARNAVAL","2016-02-09" , false);
			} catch (CadastroException e) {
				e.printStackTrace();
			} catch (AddFeriadoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
	
}
