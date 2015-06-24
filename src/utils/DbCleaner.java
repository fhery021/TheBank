package utils;

import javax.persistence.EntityManager;

public class DbCleaner {

	
	public static void deleteTableContentFromDB(EntityManager em){
		em.createQuery("delete from Client").executeUpdate();
		em.createQuery("delete from TheBank").executeUpdate();
	}
}
