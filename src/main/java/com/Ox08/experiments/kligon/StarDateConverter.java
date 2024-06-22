package com.Ox08.experiments.kligon;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
/**
 * <a href="https://en.wikipedia.org/wiki/Mojibake">...</a>

 * <a href="https://github.com/chrisoei/stardate/blob/master/Java/StarDate%20Executable/src/me/oei/util/StarDateExecutable.java">...</a>
 * https://github.com/mars-sim/mars-sim/blob/6c86a7a90a40931fa0b0f62292aed271f5336557/mars-sim-core/src/main/java/org/mars_sim/msp/core/time/ClockUtils.java
 * <a href="https://github.com/XhinLiang/LunarCalendar">...</a>
 * https://github.com/hadilq/java-persian-calendar
 * <a href="https://github.com/razeghi71/JalaliCalendar">...</a>

 * <a href="https://subscription.packtpub.com/book/web_development/9781847199522/1/ch01lvl1sec12/creating-and-using-a-custom-converter">...</a>
 *
 * @author alex
 */
@FacesConverter(value = "stardateConverter")
public class StarDateConverter implements Converter<Date> {
    @Override
    public Date getAsObject(FacesContext fc, UIComponent uic, String string) {
        final Locale l = fc.getViewRoot().getLocale();
        if ("KLINGON".equals(l.getVariant()))
            return StarDate.parseStarDate(string).getDate();

        return Date.from(ZonedDateTime.parse(string, 
                DateTimeFormatter.ISO_DATE_TIME
                        .withZone(ZoneId.systemDefault())).toInstant());
    }
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Date t) {
        final Locale l = fc.getViewRoot().getLocale();
        if ("KLINGON".equals(l.getVariant()))
            return StarDate.newInstance(t).toString();
        return DateTimeFormatter.ISO_DATE_TIME
                .withZone(ZoneId.systemDefault()).format(t.toInstant());
    }
}
