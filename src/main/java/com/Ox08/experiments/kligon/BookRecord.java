package com.Ox08.experiments.kligon;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.util.Date;
/**
 * A JPA entity, used to store guestbook record in database.
 * Note that JPA does not currently support 'record' type,
 * that's why It's still required to use POJOs.
 *
 * @author <a href="mailto:alex3.145@gmail.com">Alex Chernyshev</a>
 */
@Entity
@Table(name = "t_book_records")
@NamedQueries({
        // common named query to retrieve all existing records
        @NamedQuery(name = "BookRecord.getAllRecords",
                query = "SELECT m FROM BookRecord m")
})
public class BookRecord {
    @Id
    @SequenceGenerator(name = "default_gen", sequenceName = "b_default_pk_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    private Long id; // unique primary key
    @Size(min = 3, max = 255)
    private String title; // a title
    @NotBlank(message = "{validation.message.not-blank}")
    @Lob
    @Column(length = Integer.MAX_VALUE)
    private String message; // message, stored as CLOB in database, 
    //so size is almost unlimited
    @Size(min = 3, max = 30)
    @Email
    private String author; // author's email
    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @Past
    protected Date createdAt; // message creation date and time
    private boolean translateKlingon;
    /**
     * Below are ordinary getters and setters for record's fields.
     * Too boring to being described.
     */
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public boolean isTranslateKlingon() {
        return translateKlingon;
    }
    public void setTranslateKlingon(boolean translateKlingon) {
        this.translateKlingon = translateKlingon;
    }
}
