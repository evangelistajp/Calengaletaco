package br.edu.ifpb.pweb.calendario.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Anotacao {
	@Id		
	@GeneratedValue
	private long id;
	private String descricao;
	@Temporal(TemporalType.DATE)
	private Date data;
	@ManyToOne(cascade = CascadeType.DETACH)
	private Usuario usuario;
	
	public Anotacao(){
		super();
	}
	
	public Anotacao(String descricao, Date data, Usuario usuario){
		this.descricao = descricao;
		this.data = data;
		this.usuario = usuario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String convertData(Date date){
		String txdata = new SimpleDateFormat("yyyy-MM-dd").format(date);
		return txdata ;
	}

	@Override
	public String toString() {
		String txdata = convertData(getData());
		return  "{ title : '" + descricao + "',start : '"
				+ txdata +  "'}";
	}

	
	
}
