package hibernateORM.data.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

abstract public class AbstractDAO<T> {

    protected static final SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml").buildSessionFactory();
    protected Session session;
    private Transaction currentTransaction;

    public AbstractDAO() {
        this.session = createSession();
        this.currentTransaction = beginTransaction();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session openCurrentSessionWithTransaction() {
        session = getSessionFactory().openSession();
        currentTransaction = session.beginTransaction();
        return session;
    }

    public void closeSessionFactory() {
        getSessionFactory().close();
    }

    public Session createSession() {
        return AbstractDAO.getSessionFactory().openSession();
    }

    protected Transaction beginTransaction() {
        return this.session.beginTransaction();
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public Session getSession() {
        if (this.session == null || !this.session.isOpen()) {
            this.setSession(createSession());
        }
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void closeCurrentSession() {
        session.close();
    }

    public void closeCurrentSessionWithTransaction() {
        currentTransaction.commit();
        session.close();
    }

    public void save(T t) {
        if (t == null) throw new NullPointerException("Null entity.");
        getSession().save(t);
        closeCurrentSessionWithTransaction();
    }

    public void update(T t) {
        if (t == null) throw new NullPointerException("Null entity.");
        getSession().update(t);
        closeCurrentSessionWithTransaction();
    }

    public abstract T findEntityById(int id);

    public void delete(T t) {
        if (t == null) throw new NullPointerException("Null entity.");
        getSession().delete(t);
        closeCurrentSessionWithTransaction();
    }

    @SuppressWarnings("unchecked")
    abstract public List<T> findAll();

    public abstract void deleteAll();
}
