package br.edu.ifpb.pweb.calendario.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FeriadoSubstituto{

	@Id
	private long id;
	private String nome;
	private Date data;
	@ManyToOne(cascade = CascadeType.DETACH)
	private Feriado fixo;
	
	
	public FeriadoSubstituto(){
		super();
	}

	public FeriadoSubstituto(Long id,String nome, Date data, Feriado fe){
		this.id = id;
		this.nome = nome;
		this.data = data;	
		this.fixo = fe;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Date getData() {
		return data;
	}


	public void setData(Date datasbs) {
		this.data = datasbs;
	}

	public Feriado getFixo() {
		return fixo;
	}

	public void setFixo(Feriado fixo) {
		this.fixo = fixo;
	}

	@Override
	public String toString() {
		return "FeriadoSubstituto [id=" + id + ", nome=" + nome + ", data="
				+ data + ", fixo=" + fixo + "]";
	}
	
	

	
	
}
