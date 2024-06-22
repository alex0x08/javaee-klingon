package com.Ox08.experiments.kligon;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;
/**
 * Guestbook database service
 * Here we fetch or save data records from/to database.
 *
 * @author <a href="mailto:alex3.145@gmail.com">Alex Chernyshev</a>
 */
@Named
@ApplicationScoped
public class BookService {
    @PersistenceContext(unitName = "bookPU")
    private EntityManager em; // injected JPA entity manager
    /**
     * Retrieves number of guestbook records in database
     *
     * @return number of records
     */
    public Long fetchRecordsCount() {
        final CriteriaBuilder qb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(BookRecord.class)));
        return em.createQuery(cq).getSingleResult();
    }
    /**
     * Fetches all guestbook records from database
     *
     * @return list of book records
     */
    public List<BookRecord> fetchRecords() {
        return em
                .createNamedQuery("BookRecord.getAllRecords", BookRecord.class)
                .getResultList();
    }
    /**
     * Persists guestbook record in database
     *
     * @param record
     *          a record to persist
     * @return saved record (with id)
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public BookRecord save(BookRecord record) {
        if (record.getId() == null) {
            // set creation date&time
            record.setCreatedAt(new Date());
        }
        return em.merge(record);
    }
}
