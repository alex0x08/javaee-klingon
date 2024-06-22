package com.Ox08.experiments.kligon;
import jakarta.annotation.Nonnull;
import jakarta.faces.context.FacesContext;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Extended resource bundle, used to inject Klingon glyphs if Klingon locale
 * used
 *
 * @author <a href="mailto:alex3.145@gmail.com">Alex Chernyshev</a>
 */
public class KlingonedResourceBundle extends ResourceBundle {
    public KlingonedResourceBundle() {
        setParent(ResourceBundle.getBundle("i18n.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale()));
    }
    @Override
    public final void setParent(ResourceBundle parent) {
        super.setParent(parent);
    }
    @Override
    protected Object handleGetObject(@Nonnull String key) {
        // here will be extracted and substituted value
        final Object v = parent.getObject(key);
        if (!(v instanceof String vstring)) {
            return v;
        }
        LOG.log(Level.INFO, "handleGetObject : {0}", vstring);
        // current locale
        final Locale l = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        // check if its Klingon  and transliterate to glyphs 
        if ("KLINGON".equals(l.getVariant()))
            return KlingonTranslator.transliterate(vstring);
        // .. and for Rlyeh
        if ("RLYEH".equals(l.getVariant()))
            return RlyehTranslator.translate(vstring);

        // otherwise - just respond 'as-is'
        return v;
    }
    @Override
    @Nonnull
    public Enumeration<String> getKeys() {
        return parent.getKeys();
    }
    private static final Logger LOG = Logger.getLogger("BUNDLE-KLINGON");
}
