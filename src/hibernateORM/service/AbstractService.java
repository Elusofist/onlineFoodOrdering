package hibernateORM.service;

import hibernateORM.data.dao.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.awt.print.Book;
import java.util.List;

abstract public class AbstractService<T, K> {
    /**
    public void save(T entity) {
        entityDao.openCurrentSessionwithTransaction();
        bookDao.save(entity);
        bookDao.closeCurrentSessionwithTransaction();
    }

    public void update(T entity) {
        bookDao.openCurrentSessionwithTransaction();
        bookDao.update(entity);
        bookDao.closeCurrentSessionwithTransaction();
    }

    public Book findById(String id) {
        bookDao.openCurrentSession();
        Book book = bookDao.findById(id);
        bookDao.closeCurrentSession();
        return book;
    }

    public void delete(String id) {
        bookDao.openCurrentSessionwithTransaction();
        Book book = bookDao.findById(id);
        bookDao.delete(book);
        bookDao.closeCurrentSessionwithTransaction();
    }

    public List<Book> findAll() {
        bookDao.openCurrentSession();
        List<Book> books = bookDao.findAll();
        bookDao.closeCurrentSession();
        return books;
    }

    public void deleteAll() {
        bookDao.openCurrentSessionwithTransaction();
        bookDao.deleteAll();
        bookDao.closeCurrentSessionwithTransaction();
    }

    public BookDao bookDao() {
        return bookDao;
    }
     **/

    public abstract void save(T entity);

    public abstract void update(T entity);

    public abstract T findById(Integer id);

    public abstract void delete(Integer id);

    public abstract List<T> findAll();

    public abstract void deleteAll();

    public abstract K entityDao();
}