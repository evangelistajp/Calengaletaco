package br.edu.ifpb.pweb.calendario.dao;


import java.util.Date;

import br.edu.ifpb.pweb.calendario.model.FeriadoSubstituto;

public class DAOFeriadoSubstituto extends DAO<FeriadoSubstituto>{

	public DAOFeriadoSubstituto() {
		super();
	}
	
	public FeriadoSubstituto findById(long id){
		return  (FeriadoSubstituto) super.findByQuery("select f from FeriadoSubstituto f where f.id = " + id);
	}
	
	public FeriadoSubstituto findByData(Date data){
		return  (FeriadoSubstituto) super.findByQuery("select f from FeriadoSubstituto f where f.data = " + data);
	}
}
