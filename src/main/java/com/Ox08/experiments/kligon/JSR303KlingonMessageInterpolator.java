package com.Ox08.experiments.kligon;
import jakarta.validation.MessageInterpolator;
import jakarta.validation.Validation;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Custom JSR 303 Message Interpolator, used to inject Klingon glyphs into
 * JSR303 validation
 *
 * @author <a href="mailto:alex3.145@gmail.com">Alex Chernyshev</a>
 */
public class JSR303KlingonMessageInterpolator implements MessageInterpolator {
    // we need to have existing MessageInterpolator, to being used as parent
    private final MessageInterpolator delegate;
    public JSR303KlingonMessageInterpolator() {
        // take default implementation from JSR303  configuration
        this.delegate = Validation.byDefaultProvider()
                .configure().getDefaultMessageInterpolator();
    }
    @Override
    public String interpolate(String string, Context cntxt) {
        LOG.log(Level.INFO, "interpolating {0}", string);
        // without specified locale - just pass interpolation to delegate
        return delegate.interpolate(string, cntxt);
    }
    @Override
    public String interpolate(String string, Context cntxt, Locale locale) {
        LOG.log(Level.INFO,
                "interpolating {0} with locale: {1}",
                new Object[]{string, locale.toLanguageTag()});
        // here will be extracted and substituted value
        final String result = delegate.interpolate(string, cntxt, locale);
        // check for Klingon locale and transliterate to glyphs 
        if ("KLINGON".equals(locale.getVariant()))
            return KlingonTranslator.transliterate(result);

        if ("RLYEH".equals(locale.getVariant()))
            return RlyehTranslator.translate(result);

        return result;
    }
    private static final Logger LOG = Logger.getLogger("JSR303-KLINGON");
}
