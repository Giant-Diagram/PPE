package database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-5-26
 * @version 1.0
 *
 * Connector Class, which allows only one connection to the DB
 *
 * */

public class Connector {
    private static Connector instance = null;
    private EntityManagerFactory sessionFactory;
    private EntityManager entityManager = null;

    private Connector() {
        sessionFactory = Persistence.createEntityManagerFactory("POf");
    }

    public EntityManager open() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = sessionFactory.createEntityManager();
        }
        return entityManager;
    }

    public void close() {
        entityManager.close();
    }

    public static Connector getInstance() {
        if (Connector.instance == null) {
            Connector.instance = new Connector();
        }
        return Connector.instance;
    }
}
