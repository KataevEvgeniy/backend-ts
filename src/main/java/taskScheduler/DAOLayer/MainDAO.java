package taskScheduler.DAOLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import taskScheduler.User;

public class MainDAO {
	public static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
	
    
    public static String create(Object obj) {
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
    	entityManager.getTransaction().begin();
    	entityManager.persist(obj);
		entityManager.getTransaction().commit();
    	entityManager.close();
    	return "success";
    }
    
    public static Object read(Class<?> classType, String id) {
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
    	Object obj = entityManager.find(classType, id);
    	entityManager.close();
    	return obj;
    }
    
    public static String update(Object obj) {
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
    	entityManager.getTransaction().begin();
    	entityManager.merge(obj);
    	entityManager.getTransaction().commit();
    	entityManager.close();
    	return "success";
    }
    
    public static String delete() {
    	
    	return "success";
    }
}