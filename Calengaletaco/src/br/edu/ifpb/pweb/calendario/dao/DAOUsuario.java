package br.edu.ifpb.pweb.calendario.dao;

import br.edu.ifpb.pweb.calendario.model.Usuario;



public class DAOUsuario extends DAO<Usuario>{
	
	public DAOUsuario(){
		super();
	}
	

	public Usuario findByNome(String nome){
		return (Usuario) super.findByQuery("select u from Usuario u where u.nome = " + nome);
	}
	
	public Usuario readByEmail(String email){
		return (Usuario) super.findByQuery("select u from Usuario u where u.email = '" + email+"'");
	}
	
	
	public Usuario findBySenha(String s){
		return (Usuario) super.findByQuery("select u from Usuario u where u.senha = " + s);
	}
	
	public Usuario readLogin(String email, String senha){
		return (Usuario) super.findByQuery("select p from  Usuario p where p.email= '"+email+"' and p.senha='"+senha+"'");
	}


}