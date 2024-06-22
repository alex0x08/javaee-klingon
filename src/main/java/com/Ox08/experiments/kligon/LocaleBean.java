package com.Ox08.experiments.kligon;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Logger;
/**
 * This bean stores selected locale, attached to user's session
 *
 * @author <a href="mailto:alex3.145@gmail.com">Alex Chernyshev</a>
 */
@Named("i18n")
@SessionScoped
public class LocaleBean implements Serializable {
    @Inject
    private transient Logger log;
    // current locale
    private Locale locale;
    /**
     * Initializes current locale value
     */
    @PostConstruct
    void init() {
        // take current locale from request
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        log.log(java.util.logging.Level.INFO,
                "Current locale {0} , variant: {1}",
                new Object[]{locale.toLanguageTag(), locale.getVariant()});
    }
    public Locale getLocale() {
        return locale;
    }
    public String getLanguage() {
        return locale == null ? null : locale.toLanguageTag();
    }
    public void setLanguage(String language) {
        // get Locale object from language tag
        locale = Locale.forLanguageTag(language);
        // set it to current view root
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        log.log(java.util.logging.Level.INFO,
                "Switched locale to  {0} , variant: {1}",
                new Object[]{locale.toLanguageTag(), locale.getVariant()});
    }
}
