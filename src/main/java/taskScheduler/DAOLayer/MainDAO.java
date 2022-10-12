package taskScheduler.DAOLayer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainDAO {
	public static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
	
    
    public static String create(Object obj) {
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