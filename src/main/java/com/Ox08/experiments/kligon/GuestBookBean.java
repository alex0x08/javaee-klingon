package com.Ox08.experiments.kligon;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This is the main CDI managed bean, used for guestbook actions.
 *
 * @author <a href="mailto:alex3.145@gmail.com">Alex Chernyshev</a>
 */
@Named("gbook")
@RequestScoped
public class GuestBookBean {
    @Inject
    private Logger log;
    @Inject
    private BookService bs;
    @Inject
    private FacesContext facesContext; // JSF context
    /**
     * For simplicity, we use an instance of record entity as DTO.
     * Sure this is not good or secure, so reasonable only for a demo project.
     */
    private BookRecord current = new BookRecord();
    /**
     * @return
     *         random Klingon motivation quote
     */
    public String getRandomKlingonPhrase() {
        final Locale locale = facesContext.getViewRoot().getLocale();
        final ResourceBundle bundle = ResourceBundle
                .getBundle(KlingonedResourceBundle.class.getName(), locale);
        int r = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        final String s = String.format("klingon.phrases.%d.text", r);
        log.log(Level.INFO, "motivation quote:  {0}", s);
        // no null there
        return bundle.getObject(s).toString();
    }
    public BookRecord getCurrent() {
        return current;
    }
    public void setCurrent(BookRecord current) {
        this.current = current;
    }
    public List<BookRecord> fetchRecords() {
        return bs.fetchRecords();
    }
    /**
     * Saves guest book record
     * @return
     *      page to redirect after saving
     */
    public String save() {
        // get current principal
        final Principal p = FacesContext.getCurrentInstance()
                .getExternalContext().getUserPrincipal();
        // if user is anonymous
        if (p != null && !"UNAUTHENTICATED".equalsIgnoreCase(p.getName()))
            current.setAuthor(p.getName());

        // if 'translation check' is set - try to encode Klingon glyphs
        if (current.isTranslateKlingon())
            current.setMessage(KlingonTranslator.transliterate(current.getMessage()));
        // persist record
        bs.save(current);
        // does redirect
        return "/guestbook.xhtml?faces-redirect=true";
    }
}
