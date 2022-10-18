package taskScheduler.DAOLayer;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import taskScheduler.User;
import taskScheduler.UserTask;


public class MainDAO {
	public static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
	
	public static int num = 0;
    
    public static String create(Object obj){
    	EntityManager em = entityManagerFactory.createEntityManager();
    	em.getTransaction().begin();
    	em.persist(obj);
    	em.getTransaction().commit();
    	em.close();
    	return "success";
    }
    
    public static Object read(Class<?> classType, String id) {
    	EntityManager em = entityManagerFactory.createEntityManager();
    	Object obj = em.find(classType, id);
    	em.close();
    	return obj;
    }
    
    public static Object read(Class<?> classType, long id) {
    	EntityManager em = entityManagerFactory.createEntityManager();
    	Object obj = em.find(classType, id);
    	em.close();
    	return obj;
    }
    
    public static long getMax(Class<?> cl, String field) {
    	EntityManager em = entityManagerFactory.createEntityManager();
    	long max = (long) em.createQuery("SELECT MAX(e." + field + ") FROM " + cl.getName() + " e").getSingleResult();
    	em.close();
    	return max;
    }
    
    public static List<?> readAll(Class<?> cl) {
    	EntityManager em = entityManagerFactory.createEntityManager();
    	List<?> list = em.createQuery("SELECT e FROM " + cl.getName() + " e",cl).getResultList();
    	em.close();
    	return list;
    }
    
    public static String update(Object obj) {
    	EntityManager em = entityManagerFactory.createEntityManager();
    	em.getTransaction().begin();
    	em.merge(obj);
    	em.getTransaction().commit();
    	em.close();
    	return "success";
    }
    
    public static String delete(Object obj) {
    	EntityManager em = entityManagerFactory.createEntityManager();
    	em.getTransaction().begin();
    	em.remove(em.merge(obj));
    	em.getTransaction().commit();
    	em.close();
    	return "success";
    }
}