package br.edu.ifpb.pweb.calendario.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

public abstract class DAO<T> implements DAOInterface<T>{
	protected static EntityManager manager;
	
	public DAO(){

		}
	
	protected static EntityManager getManager(){
		if(manager==null){
			EntityManagerFactory factory = 
				Persistence.createEntityManagerFactory("calendario");
			manager = factory.createEntityManager();
		}
		return manager;
	}
	public static void open(){
		getManager();
	}
	
	public static void close(){
		if(manager!=null) {
			manager.close();
			manager=null;
		}
	}
	
	public void create(T obj) {
		manager.persist(obj);
	}

	public T update(T obj) {
		return manager.merge(obj);
	}

	public void delete(T obj) {
		manager.remove(obj);
	}

	public void refresh(T obj) {
		manager.refresh(obj);;
	}
	
	//--------transa��o---------------
		public static void begin(){
			if(!manager.getTransaction().isActive())
				manager.getTransaction().begin();
		}
		
		public static void commit(){
			if(manager.getTransaction().isActive()){
				manager.getTransaction().commit();
				manager.clear();		// esvaziar o cache de objetos
			}
		}
		
		public static void flush(){	//commit intermediario
			commit();
		}
		
		public static void rollback(){
			if(manager.getTransaction().isActive())
				manager.getTransaction().rollback();
		}
		
		@SuppressWarnings("unchecked")
		public T read(Object chave){
			Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
	                .getGenericSuperclass()).getActualTypeArguments()[0];
			return manager.find(type, chave);
		}
		
		@SuppressWarnings("unchecked")
		public List<T> readAll(){
			Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
			Query query = manager.createQuery("select x from " + type.getSimpleName() + " x");
			return (List<T>) query.getResultList();

		}

		//----------------------------------------------------------
		//----------------------- USO DE JPQL ----------------------
		//----------------------------------------------------------
		public  Object findByQuery(String consultaJPQL){		
			try{
				Query q = manager.createQuery(consultaJPQL);
				return (Object) q.getSingleResult();
			}
			catch(NoResultException e){
				return null;
			}
			catch(NonUniqueResultException e){
				return null;
			}
		}

		@SuppressWarnings("unchecked")
		public  List<T> findAllByQuery(String consulta){		
			try{
				Query q = manager.createQuery(consulta);
				return q.getResultList();
			}
			catch(NoResultException e){
				return null;
			}
			catch(NonUniqueResultException e){
				return null;
			}
		}

		@SuppressWarnings("unchecked")
		public  List<T> findAgregateByQuery(String consulta){
			Query q = manager.createQuery(consulta);
			return q.getResultList();
		}
			
		public int executeUpdate(String consulta) {
			Query q = manager.createQuery(consulta);
			int linhas = q.executeUpdate();
			return linhas;
		}
		
	}		