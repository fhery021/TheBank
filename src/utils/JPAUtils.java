package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPAUtils {
	final public static String EMF_NAME = "TheBank";

	static EntityManagerFactory entityManagerFactory = null;
	static EntityManager em = null;
	static EntityTransaction userTransaction = null;

	/**
	 * @param args
	 */
	public static EntityManager getEntityManager() {

		entityManagerFactory = Persistence.createEntityManagerFactory(EMF_NAME);
		em = entityManagerFactory.createEntityManager();
		userTransaction = em.getTransaction();
		userTransaction.begin();

		return em;
	}

	public static void close() {
		try {
			userTransaction.commit();
			em.close();
			entityManagerFactory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isAttached(final Object obj) {
		return ((org.hibernate.ejb.EntityManagerImpl) em).contains(obj);
	}
}
