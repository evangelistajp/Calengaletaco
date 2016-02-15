package br.edu.ifpb.pweb.calendario.dao;

import java.util.Date;

import br.edu.ifpb.pweb.calendario.model.Feriado;

public class DAOFeriado extends DAO<Feriado>{

	public DAOFeriado() {
		super();
	}
	
	public Feriado findById(long id){
		return (Feriado) super.findByQuery("select f from Feriado f where f.id = " + id);
	}
	
	public Feriado findByNome(String nome){
		return (Feriado) super.findByQuery("select f from Feriado f where f.nome = " + nome);
	}
	
	public Feriado findByData(Date data){
		return (Feriado) super.findByQuery("select f from Feriado f where f.data = " + data);
	}
	
	public Feriado findByTipo(String tipo){
		return (Feriado) super.findByQuery("select f from Feriado f where f.tipo = " + tipo);
	}
	
}
