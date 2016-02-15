package br.edu.ifpb.pweb.calendario.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.jasper.tagplugins.jstl.core.If;

import br.edu.ifpb.pweb.calendario.dao.DAO;
import br.edu.ifpb.pweb.calendario.dao.DAOAnotacao;
import br.edu.ifpb.pweb.calendario.dao.DAOFeriado;
import br.edu.ifpb.pweb.calendario.dao.DAOFeriadoSubstituto;
import br.edu.ifpb.pweb.calendario.dao.DAOUsuario;
import br.edu.ifpb.pweb.calendario.model.Anotacao;
import br.edu.ifpb.pweb.calendario.model.Feriado;
import br.edu.ifpb.pweb.calendario.model.FeriadoSubstituto;
import br.edu.ifpb.pweb.calendario.model.Usuario;
import br.edu.ifpb.pweb.exception.AddAnotacaoExeption;
import br.edu.ifpb.pweb.exception.AddFeriadoException;
import br.edu.ifpb.pweb.exception.CadastroException;



public class ControllerFacade {
	
	private static DAOUsuario daoUsuario = new DAOUsuario();
	private static DAOAnotacao daoAnotacao = new DAOAnotacao();
	private static DAOFeriado daoFeriado = new DAOFeriado();
	private static DAOFeriadoSubstituto daoSubstituto = new DAOFeriadoSubstituto();
	private static Feriado fe;
	
	public static void open() {
		daoUsuario = new DAOUsuario();
	}
	
	//Cadastrar Usuário Comum
	public static Usuario cadastrarUsuarioComum(String nome, String email,
			String senha) throws CadastroException {

		DAO.begin();

		
		Usuario us = daoUsuario.readByEmail(email);

		if (us != null) {
			throw new CadastroException("Usuario ja cadastrado: " + email
					+ "\n");
		}
		us = new Usuario(nome, email, senha);

		daoUsuario.create(us);

		DAO.commit();

		return us;
	}
	
	//Cadastrar Usuário Admin
		public static Usuario cadastrarUsuarioAdmin(String nome, String email,
				String senha) throws CadastroException {

			DAO.begin();

			
			Usuario us = daoUsuario.readByEmail(email);

			if (us != null) {
				throw new CadastroException("Usuario ja cadastrado: " + email
						+ "\n");
			}
			us = new Usuario(nome, email, senha,true);

			daoUsuario.create(us);

			DAO.commit();

			return us;
		}
	
	// Realiza o login 
	public static Usuario realizaLogin(String email, String senha) {

		DAO.begin();

		Usuario us = daoUsuario.readLogin(email, senha);

		return us;

	}
	
	
	// Muda a senha do administrador
	public static Usuario mudarsenha(String email, String senha) {

		DAO.begin();

		Usuario usuario = daoUsuario.readByEmail(email);
		
		if (usuario.isIsadmin()) {

			usuario.setSenha(senha);
			daoUsuario.update(usuario);

			DAO.commit();
		}

		return usuario;
	}
	
	//Excluir Usuario
	public static Usuario excluirusuario(String email) {
		
		DAO.begin();

		Usuario usuario = daoUsuario.readByEmail(email);
		List<Anotacao> anotacoes = usuario.getAnotacoes();
		
		if (usuario.isIsadmin()==false) {
			for (Anotacao anotacao : anotacoes) {
				daoAnotacao.delete(anotacao);
			}
			daoUsuario.delete(usuario);
						
			DAO.commit();
			

		}
		return null;

	}
	//add Anotação
	public static Usuario addAnotacao(Usuario us, String descricao, String data)
			throws AddAnotacaoExeption, ParseException{
		
		DAO.begin();
		
		if (us == null) {
			throw new AddAnotacaoExeption("Usuario não esta logado ");
		}
		
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(data);
		Anotacao an = new Anotacao(descricao, date,us);
		daoAnotacao.create(an);
		us.addAnotacao(an);
		daoUsuario.update(us);
		
		
		DAO.commit();
		
		return us;
	}
	
	
	//Add Feriado
	public static Feriado addFeriado(Usuario us, String nome, String data, Boolean fixo)
			throws AddFeriadoException, ParseException{
		
		DAO.begin();
		
		if (!us.isIsadmin()) {
			throw new AddFeriadoException("Usuario nao é admin");
		}
		
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(data);
		fe = new Feriado(nome,date,fixo);
		
		daoFeriado.create(fe);
		
		DAO.commit();
		
		return fe;
	}
	
	//Add Feriado Substituto
	public static FeriadoSubstituto addFeriadoSubstituto(long feriadoid, String nome, String data) throws ParseException{
		
		DAO.begin();
		
		Feriado fe = daoFeriado.findById(feriadoid);
			
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(data);
		FeriadoSubstituto sub = new FeriadoSubstituto(feriadoid,nome,date, fe);
			
		daoSubstituto.create(sub);
		fe.addSubstituto(sub);
		daoFeriado.update(fe);
		
		DAO.commit();
		
		return sub;
	}
	

	//Excluir Anotação de um Usuario
	public static Usuario excluiranotacao(long id){
		DAO.begin();
		
		Anotacao anotacao = daoAnotacao.findById(id);
		
		Usuario us = anotacao.getUsuario();
		
		daoAnotacao.delete(anotacao);
		us.removerAnotacao(anotacao);
		daoUsuario.update(us);
	
		DAO.commit();
		
		return us;	
		
	}
	
	//Excluir Feriado
	public static Feriado excluirFeriados(long id) {
		DAO.begin();
		
		Feriado feriado = daoFeriado.findById(id);
		List<FeriadoSubstituto> substitutos = feriado.getSubstitutos();
		
		for (FeriadoSubstituto substituto : substitutos) {
			daoSubstituto.delete(substituto);
		}
		daoFeriado.delete(feriado);
		
		DAO.commit();
		
		return feriado;
	}
	//Excluir Feriado Substituto
		public static FeriadoSubstituto excluirFeriadoSubstituto(long id) {
			DAO.begin();
			
			FeriadoSubstituto substituto = daoSubstituto.findById(id);
			
			daoSubstituto.delete(substituto);
			
			DAO.commit();
			
			return substituto;
		}
	
	//listar Feriado FullCalendar
	public static StringBuilder listarFeriadosFullCalendar(){
		
		Date[] datas = new Date[25];
		Calendar calferiado = Calendar.getInstance();
		Calendar calSubstituto = Calendar.getInstance();
		String data, data2;
		int ano;
		
		DAOFeriado feriadoDAO = new DAOFeriado();
		List<Feriado> feriados = feriadoDAO.readAll();
		DAOFeriadoSubstituto substitutoDAO = new DAOFeriadoSubstituto();
		List<FeriadoSubstituto> susbstitutos = substitutoDAO.readAll();
		
		StringBuilder listaFeriados = new StringBuilder();		
		for (Feriado feriado : feriados) {
			data = new SimpleDateFormat("yyyy-MM-dd").format(feriado.getData());
			if (feriado.getFixo()) {
				calferiado.setTime(feriado.getData());
				int anoferiado = calferiado.get(Calendar.YEAR);
				if (feriado.getSubstitutos().size() >=1) {
					for(FeriadoSubstituto substituto : susbstitutos){
						calSubstituto.setTime(substituto.getData());
						int anoSubstituto = calSubstituto.get(Calendar.YEAR);
						if (feriado.getId() == substituto.getId()) {
							if (anoferiado == anoSubstituto) {
								data2 = new SimpleDateFormat("yyyy-MM-dd").format(substituto.getData());
								listaFeriados.append("{");
								listaFeriados.append("title: '"+feriado.getNome()+"',");
								listaFeriados.append("start: '"+data2+"',");
								listaFeriados.append("},");
							}
						}
					}
				}else{
					listaFeriados.append("{");
					listaFeriados.append("title: '"+feriado.getNome()+"',");
					listaFeriados.append("start: '"+data+"',");
					listaFeriados.append("},");
				}
			}else{
				listaFeriados.append("{");
				listaFeriados.append("title: '"+feriado.getNome()+"',");
				listaFeriados.append("start: '"+data+"',");
				listaFeriados.append("},");
			}
	
		}	
		data2 = null;
		for (Feriado feriado : feriados) {
			if (feriado.getFixo()) {
				calferiado.setTime(feriado.getData());
				ano = calferiado.get(Calendar.YEAR);
				int mes = calferiado.get(Calendar.MONTH)+1;
				int dia = calferiado.get(Calendar.DAY_OF_MONTH);
				
				for (int i = 1; i <= datas.length; i++) {
					ano ++;
					
					if (mes <= 9) {
						if (dia <= 9) {
							data2 = ano+"-0"+mes+"-0"+dia;
						}else{
							data2 = ano+"-0"+mes+"-"+dia;
						}
					}else{
						if (dia <= 9) {
							data2 = ano+"-"+mes+"-0"+dia;
						}else{
							data2 = ano+"-"+mes+"-"+dia;
						}
					}
 					 listaFeriados.append("{");
					 listaFeriados.append("title: '"+feriado.getNome()+"',");
					 listaFeriados.append("start: '"+data2 +"',");
					 listaFeriados.append("},");
				}
			}
		}

		return listaFeriados;
	}	
	
	//listar Anotações fullCalendar
	public static StringBuilder listarAnotacoesFullCalendar(Usuario us){	
			
			if(us != null && us.isIsadmin() == false) {
				
				List<Anotacao> anotacoes = new ArrayList<Anotacao>();
				
				anotacoes = us.getAnotacoes();

				StringBuilder listaAnotacoes = new StringBuilder();
				if (anotacoes.size() != 0) {
					for (Anotacao anotacao : anotacoes) {
						String data = new SimpleDateFormat("yyyy-MM-dd").format(anotacao.getData());

						listaAnotacoes.append("{");
						listaAnotacoes.append("title: '"+anotacao.getDescricao()+"',");
						listaAnotacoes.append("start: '"+data+"',");
						listaAnotacoes.append("},");
					}
					return listaAnotacoes;
				}
				
			}
			return null;
		}	
	
	//editar Notas, Altera a Data
	public static Usuario editarAnotacao(long id, String data) throws ParseException{
		
		DAO.begin();
	
		Anotacao anotacao = daoAnotacao.findById(id);
		
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(data);
		anotacao.setData(date);
		Usuario us = anotacao.getUsuario();
		daoAnotacao.update(anotacao);
				
		DAO.commit();
		
		return us;		
	}
	
	//editar Feriado, altera a Data
	public static Feriado editarFeriado(long id, String data) throws ParseException{
		DAO.begin();
		
		Feriado feriado = daoFeriado.findById(id);
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(data);
		feriado.setData(date);
		daoFeriado.update(feriado);
		
		DAO.commit();
		return feriado;
	}

	//Busca Anotação pelo ID
	public static Anotacao buscaNota(long id){
		DAO.begin();
		
		Anotacao anotacao = daoAnotacao.findById(id);
		
		return anotacao;
		
	}
	// busca feriado pelo ID
	public static Feriado buscaFeriado(long id){
		DAO.begin();
		
		Feriado feriado = daoFeriado.findById(id);
		
		return feriado;
		
	}
	
	// busca feriado substituto pelo ID
	public static FeriadoSubstituto buscaFeriadoSubstituto(long id){
		DAO.begin();
		
		FeriadoSubstituto substituto = daoSubstituto.findById(id);
		
		return substituto;
		
	}
	
	//busca Anotações de um Usuario
		public static List<Anotacao> buscaAnotacoes(Usuario usuario) {
			DAO.begin();
			return (List<Anotacao>) daoAnotacao.findByAnotacoes(usuario.getEmail());
		}
		
	//listar todos os feriados
	public static List<Feriado> buscaTodosFeriadosPainel(){
		DAO.begin();
		return daoFeriado.readAll();
	}
	
	//listar todos os feriados substitutos
		public static List<FeriadoSubstituto> buscaTodosFeriadosSubstitutosPainel(){
			DAO.begin();
			return daoSubstituto.readAll();
		}
	
		
}