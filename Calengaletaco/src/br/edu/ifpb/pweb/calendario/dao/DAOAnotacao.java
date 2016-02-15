package br.edu.ifpb.pweb.calendario.dao;

import java.util.Date;

import br.edu.ifpb.pweb.calendario.model.Anotacao;
import br.edu.ifpb.pweb.calendario.model.Usuario;

public class DAOAnotacao extends DAO<Anotacao>{
	
	public DAOAnotacao(){
		super();
	}
	
	public Anotacao findById(long id){
		return (Anotacao) super.findByQuery("select a from Anotacao a where a.id = " + id);
	}
	
	public Anotacao findByDescricao(String descricao){
		return (Anotacao) super.findByQuery("select a from Anotacao a where a.descricao = " + descricao);
	}
	
	public Anotacao findByData(Date data){
		return (Anotacao) super.findByQuery("select a from Anotacao a where a.data = " + data);
	}
	
	public Anotacao findByAnotacoes(String email){
		return (Anotacao) super.findByQuery("select a from Anotacao a where a.usuario.email = " + email);
	}
	

}
