package database;

import database.Connector;

import javax.persistence.EntityManager;
import java.util.List;

public class DAOGeneric<T> {
    public Class<T> type;

    public List<T> selectAll() {
        EntityManager entityManager = Connector.getInstance().open();
        List<T> result = null;
        try {
            entityManager.getTransaction().begin();
            result = entityManager.createQuery("from " + type.getSimpleName(), type).getResultList();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if(entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            Connector.getInstance().close();
        }
        return result;
    }

    public T insert(T klasse) {
        EntityManager entityManager = Connector.getInstance().open();
        T data = null;
        try {
            entityManager.getTransaction().begin();
            data = entityManager.merge(klasse);
            entityManager.persist(data);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if(entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            Connector.getInstance().close();
        }
        return data;
    }

    public T delete(int id) {
        EntityManager entityManager = Connector.getInstance().open();
        T data = null;
        try {
            entityManager.getTransaction().begin();
            data = entityManager.createQuery("from " + type.getName() + " e where e.id = :id", type).setParameter("id", id).getSingleResult();
            entityManager.remove(data);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if(entityManager.getTransaction() != null)
                entityManager.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            Connector.getInstance().close();
        }
        return data;
    }
}
