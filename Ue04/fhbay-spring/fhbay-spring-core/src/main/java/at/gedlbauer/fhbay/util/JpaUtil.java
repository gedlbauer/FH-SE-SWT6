package at.gedlbauer.fhbay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class JpaUtil {

    private static final Logger logger = LoggerFactory.getLogger(JpaUtil.class);



    public static EntityManager openEntityManager(EntityManagerFactory entityManagerFactory) {
        try {
            EntityManager em = getEntityManager(entityManagerFactory);
            if (em == null) {
                logger.trace("Opening JPA EntityManager");
                em = entityManagerFactory.createEntityManager();
            }

            TransactionSynchronizationManager.bindResource(entityManagerFactory,
                    new EntityManagerHolder(em));
            return em;
        } catch (IllegalStateException ex) {
            throw new DataAccessResourceFailureException("Could not open JPA EntityManager", ex);
        }
    }

    public static void closeEntityManager(EntityManagerFactory entityManagerFactory) {
        try {
            EntityManager em = getEntityManager(entityManagerFactory);
            if (em != null) {
                TransactionSynchronizationManager.unbindResource(entityManagerFactory);
                em.close();
                logger.trace("Closed JPA EntityManager");
            }
        } catch (IllegalStateException ex) {
            throw new DataAccessResourceFailureException("Could not close JPA EntityManager", ex);
        }
    }

    public static EntityManager getEntityManager(EntityManagerFactory entityManagerFactory) {
        if (entityManagerFactory == null) return null;
        return EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
    }
}