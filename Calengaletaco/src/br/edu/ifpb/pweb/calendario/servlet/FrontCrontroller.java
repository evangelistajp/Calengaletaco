package br.edu.ifpb.pweb.calendario.servlet;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.ifpb.pweb.calendario.controller.ControllerFacade;
import br.edu.ifpb.pweb.calendario.dao.DAO;
import br.edu.ifpb.pweb.calendario.model.Anotacao;
import br.edu.ifpb.pweb.calendario.model.Feriado;
import br.edu.ifpb.pweb.calendario.model.FeriadoSubstituto;
import br.edu.ifpb.pweb.calendario.model.Usuario;
import br.edu.ifpb.pweb.exception.AddAnotacaoExeption;
import br.edu.ifpb.pweb.exception.AddFeriadoException;
import br.edu.ifpb.pweb.exception.CadastroException;


/**
 * Servlet implementation class FrontCrontroller
 */
@WebServlet("/executa")
public class FrontCrontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Usuario us;
	HttpSession session;
	List<Anotacao> anotacoes = new ArrayList<Anotacao>();
	List<Feriado> feriados = new ArrayList<Feriado>();
	List<FeriadoSubstituto> substitutos = new ArrayList<FeriadoSubstituto>();
	Anotacao an;
	Feriado fe;
	FeriadoSubstituto sub;
	private String id;
	private long longid;
	private boolean fixo;
	private String nome;
	private String email;
	private String senha;
	private String data;
	
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = request.getParameter("op");
		
		if (op == null) {
			throw new IllegalArgumentException("Ação inválida");
		}
		
		switch (op) {
		
			case "login":
				login(request, response);
					
				break;
			case "cadastrar":
				cadastraUsuario(request, response);
					
				break;
				
			case"alterarsenha":
				alteraSenha(request, response);
				
				break;
			case "excluirUsuario": 
				excluirUsuario(request, response);

				break;
			case "logout":
				logout(request, response);
				
				break;
			case "addNota":
				try {
					addAnotacao(request, response);
				} catch (ParseException e) {
						e.printStackTrace();
				}
				
				break;
			case "addFeriado":
				try {
					addFeriado(request, response);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}				

				break;	
			
			case "addFeriadoSubstituto":
				try {
					addFeriadoSubstituto(request, response);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				break;
			case "excluirNotas":
				try {
					excluirNota(request, response);
				} catch (ParseException e) {
					e.printStackTrace();
				}
		
				break;
			case "excluirFeriado":
				try {
					excluirFeriado(request, response);
				} catch (ParseException e) {
					e.printStackTrace();
				}					
				
				break; 
			case "excluirFeriadoSubstituto":
				try {
					excluirFeriadoSubstituto(request, response);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			
				
				break;
			case "editarNotas":
				try {
					editarNota(request, response);
				} catch (ParseException e) {
					e.printStackTrace();
				}				
				
				break;
			case "editarFeriado":
				try {
					editarFeriado(request, response);
				} catch (ParseException e) {
					e.printStackTrace();
				}				
			
				break;
			case "buscaNotas":
				try {
					buscaNota(request, response);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				break;
			case "buscaFeriado":
				try {
					buscaFeriado(request, response);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				break;
			case "buscaFeriadoSubstituto":
				try {
					buscaFeriadoSubstituto(request, response);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				break;
			case "painelNotas":
				try {
					painelNotas(request, response);
				} catch (ParseException e) {
					e.printStackTrace();
				}
		
				break;
			case "painelFeriados":
				try {
					painelFeriados(request, response);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				break;	
				
			default:
				request.getRequestDispatcher("index.jsp").forward(request, response);
				break;
		}	
	
	}
	
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doRequest(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doRequest(request, response);
	}

	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		this.email = request.getParameter("email");
		this.senha = request.getParameter("senha");
			
		try {
			us = ControllerFacade.realizaLogin(email, senha);
				
			if (us != null) {						
				if (us.isIsadmin()) {				
						HttpSession session = request.getSession(true);
						session.setAttribute("usuario", us);
									
						session.setAttribute("feriados", ControllerFacade.listarFeriadosFullCalendar());			
						request.getRequestDispatcher("painel.jsp").forward(request, response);
					}else{
									
						HttpSession session = request.getSession(true);
						session.setAttribute("usuario", us);

						request.setAttribute("anotacoes", ControllerFacade.listarAnotacoesFullCalendar(us));
						session.setAttribute("feriados", ControllerFacade.listarFeriadosFullCalendar());
						request.getRequestDispatcher("painel.jsp").forward(request, response);
					}
				}else{
					request.setAttribute("erro", "Usuário ou senha incorretos ");
					request.setAttribute("feriados", ControllerFacade.listarFeriadosFullCalendar());
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
			} catch (Exception e) {
				request.setAttribute("erro", "Erro: No login");
				request.setAttribute("feriados", ControllerFacade.listarFeriadosFullCalendar());
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
	}
	
	public void cadastraUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		this.nome = request.getParameter("nome");
		this.email = request.getParameter("email");
		this.senha = request.getParameter("senha");
		try {
			us = ControllerFacade.cadastrarUsuarioComum(nome, email, senha);
			request.setAttribute("ok", "cadastrado com sucesso");
			request.setAttribute("feriados", ControllerFacade.listarFeriadosFullCalendar());
			request.getRequestDispatcher("index.jsp").forward(request, response);
			
		} catch (CadastroException e) {
			request.setAttribute("erro", "Usuario já cadastrado");
			request.getRequestDispatcher("cadastrarUsuario.jsp").forward(request, response);
			System.out.println("-->" + e.getMessage());
		}
	}
	
	public void alteraSenha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		email = request.getParameter("email");
		String novasenha = request.getParameter("novasenha");
		String novasenha2 = request.getParameter("novasenha2");
		
		if(novasenha.equals(novasenha2)){
			try {
				us= ControllerFacade.mudarsenha(email,novasenha);	
				request.setAttribute("ok", "Senhas Alterada com sucesso");
				request.getRequestDispatcher("alterarSenha.jsp").forward(request, response);
			}catch(Exception e)	{
				request.setAttribute("erro", "Erro: Não foi possivel alterar a senha");
				request.getRequestDispatcher("alterarSenha.jsp").forward(request, response);
			}
		}else{
			request.setAttribute("erro", "Senhas diferentes");
			request.getRequestDispatcher("alterarSenha.jsp").forward(request, response);
		}
		
	}
	
	public void excluirUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		session.invalidate();
		us = ControllerFacade.excluirusuario(us.getEmail());
		
		request.setAttribute("feriados", ControllerFacade.listarFeriadosFullCalendar());
		request.setAttribute("ok", "Usuário excluido com sucesso");
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		session = request.getSession();
		session.invalidate();
		us = null;
		request.setAttribute("feriados", ControllerFacade.listarFeriadosFullCalendar());
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	
	public void addAnotacao(HttpServletRequest request, HttpServletResponse response) throws ParseException, ServletException, IOException {
		String descricao = request.getParameter("descricao");
		data = request.getParameter("data");
		
			try {
				us = ControllerFacade.addAnotacao(us, descricao, data);
								
				request.setAttribute("ok", "Anotação Adicionada com Sucesso");
				session = request.getSession();
				session.setAttribute("usuario", us);
				anotacoes = us.getAnotacoes();
				session.setAttribute("listaNotas", anotacoes);
				session.setAttribute("anotacoes", ControllerFacade.listarAnotacoesFullCalendar(us));
						
				request.getRequestDispatcher("painelNotas.jsp").forward(request, response);
			} catch (AddAnotacaoExeption e) {
				request.setAttribute("erro", "Erro: Na adição de Nota");
				request.getRequestDispatcher("painelNotas.jsp").forward(request, response);
			} catch (ParseException e) {
				request.setAttribute("erro", "Erro: Na Conversao de Data");
				request.getRequestDispatcher("painelNotas.jsp").forward(request, response);
			}
	}
	public void addFeriado(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException, ServletException {
		nome = request.getParameter("nome");
		data = request.getParameter("data");
		String strfixo = request.getParameter("fixo");
		
		try {
		if (strfixo == null) {
			this.fixo = false;
		}else{
			this.fixo = true;
		}
		fe = ControllerFacade.addFeriado(us,nome, data, this.fixo);
								
		request.setAttribute("ok", "Feriado Adicionada com Sucesso");
		feriados = ControllerFacade.buscaTodosFeriadosPainel();
		
		session = request.getSession();
		session.setAttribute("listaferiados", feriados);
		session.setAttribute("feriados", ControllerFacade.listarFeriadosFullCalendar());
			
		request.getRequestDispatcher("painelFeriados.jsp").forward(request, response);
		} catch (AddFeriadoException e) {
		request.setAttribute("erro", "Erro: Na adição de feriado");
		request.getRequestDispatcher("painelFeriados.jsp").forward(request, response);
		} catch (ParseException e) {
		request.setAttribute("erro", "Erro: Na Conversao de Data");
		request.getRequestDispatcher("painelFeriados.jsp").forward(request, response);
		}
	}
	
	public void addFeriadoSubstituto(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException, ServletException {
		nome = request.getParameter("nome");
		data = request.getParameter("data");
		id = request.getParameter("id");
		longid = Long.parseLong(id);
		
		try {
			sub = ControllerFacade.addFeriadoSubstituto(longid,nome,data);
			request.setAttribute("ok", "Feriado Substituto Adicionada com Sucesso");
			
			substitutos = ControllerFacade.buscaTodosFeriadosSubstitutosPainel();
			session = request.getSession();
			session.setAttribute("listaferiadossubstitutos",substitutos);
			session.setAttribute("feriados", ControllerFacade.listarFeriadosFullCalendar());
			request.getRequestDispatcher("painelFeriados.jsp").forward(request, response);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void excluirFeriado(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException, ServletException {
		id = request.getParameter("id");
		longid = Long.parseLong(id);
		
		fe = ControllerFacade.excluirFeriados(longid);
		request.setAttribute("ok", "Feriado excluido");
		
		feriados = ControllerFacade.buscaTodosFeriadosPainel();
		substitutos = ControllerFacade.buscaTodosFeriadosSubstitutosPainel();
		session = request.getSession();
		session.setAttribute("listaferiados", feriados);
		session.setAttribute("listaferiadossubstitutos", substitutos);
		session.setAttribute("feriados", ControllerFacade.listarFeriadosFullCalendar());
		request.getRequestDispatcher("painelFeriados.jsp").forward(request, response);
	}
	
	public void excluirNota(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException, ServletException {
		id = request.getParameter("id");
		longid = Long.parseLong(id);

		us = ControllerFacade.excluiranotacao(longid);
		request.setAttribute("ok", "Anotação excluida");
		anotacoes = us.getAnotacoes();
							
		session = request.getSession();
		session.setAttribute("listaNotas", anotacoes);
		session.setAttribute("anotacoes", ControllerFacade.listarAnotacoesFullCalendar(us));
    	request.getRequestDispatcher("painelNotas.jsp").forward(request, response);
	}
	
	public void excluirFeriadoSubstituto(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException, ServletException {
		id = request.getParameter("id");
		longid = Long.parseLong(id);	
		
		sub =ControllerFacade.excluirFeriadoSubstituto(longid);
		request.setAttribute("ok", "Substituto excluido");
		
		substitutos = ControllerFacade.buscaTodosFeriadosSubstitutosPainel();
		
		session = request.getSession();
		session.setAttribute("listaferiadossubstitutos", substitutos);
		session.setAttribute("feriados", ControllerFacade.listarFeriadosFullCalendar());
		request.getRequestDispatcher("painelFeriados.jsp").forward(request, response);
	}
	
	public void editarNota(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException, ServletException {
		data = request.getParameter("data");
		id = request.getParameter("id");
		longid = Long.parseLong(id);
		
		try {
			us = ControllerFacade.editarAnotacao(longid, data);
			anotacoes = us.getAnotacoes();
			session = request.getSession();
			session.setAttribute("listaNotas", anotacoes);
			session.setAttribute("anotacoes", ControllerFacade.listarAnotacoesFullCalendar(us));
			request.getRequestDispatcher("painelNotas.jsp").forward(request, response);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void editarFeriado(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException, ServletException {
		data = request.getParameter("data");
		id = request.getParameter("id");
		longid = Long.parseLong(id);	
		
		try {
			fe = ControllerFacade.editarFeriado(longid, data);
		
			session = request.getSession();
			session.setAttribute("listaferiados", feriados);
			session.setAttribute("feriados", ControllerFacade.listarFeriadosFullCalendar());
			
			request.getRequestDispatcher("painelFeriados.jsp").forward(request, response);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void buscaNota(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException, ServletException {
		id = request.getParameter("id");
		longid = Long.parseLong(id);
		
		an = ControllerFacade.buscaNota(longid);
		request.setAttribute("anotacao", an);
		request.getRequestDispatcher("editarNotas.jsp").forward(request, response);
	}
	
	public void buscaFeriado(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException, ServletException {
		id = request.getParameter("id");
		longid = Long.parseLong(id);	
		
		fe = ControllerFacade.buscaFeriado(longid);
		request.setAttribute("feriado", fe);
		request.getRequestDispatcher("editarFeriados.jsp").forward(request, response);
	}
	
	public void buscaFeriadoSubstituto(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException, ServletException {
		id = request.getParameter("id");
		longid = Long.parseLong(id);	
		
		fe = ControllerFacade.buscaFeriado(longid);
		request.setAttribute("feriado", fe);
		request.getRequestDispatcher("addFeriadoSubstituto.jsp").forward(request, response);
	}
	
	public void painelNotas(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException, ServletException {
		anotacoes = us.getAnotacoes();
		session = request.getSession();
		session.setAttribute("listaNotas",anotacoes);
		session.setAttribute("anotacoes", ControllerFacade.listarAnotacoesFullCalendar(us));
		request.getRequestDispatcher("painelNotas.jsp").forward(request, response);
		
	}
	
	public void painelFeriados(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException, ServletException {
		feriados = ControllerFacade.buscaTodosFeriadosPainel();
		substitutos = ControllerFacade.buscaTodosFeriadosSubstitutosPainel();
		session = request.getSession();
		session.setAttribute("listaferiados", feriados);
		session.setAttribute("listaferiadossubstitutos", substitutos);
		request.getRequestDispatcher("painelFeriados.jsp").forward(request, response);
	}

}