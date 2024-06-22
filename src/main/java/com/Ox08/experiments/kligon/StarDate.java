package com.Ox08.experiments.kligon;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
/**
 * This is bit optimized version of Star Date class, taken from
 * <a href="https://github.com/chrisoei/stardate/blob/master/Java/StarDate%20Executable/src/me/oei/util/StarDateExecutable.java">...</a>
 *
 *@author Chris Oei
 */
public class StarDate implements Comparable<StarDate> {
    /*
     * One second, measured in units of StarDate.
     */
    //public static final double SECOND = 3.1688765e-08,
    /*
     * One minute, measured in units of StarDate.
     */
   // MINUTE = 1.9013259e-06,
    /*
     * One hour, measured in units of StarDate.
     */
    //HOUR = 0.00011407955,
    /*
     * One day, measured in units of StarDate.
     */
    //DAY = 0.0027379093;
    
    /**
     * The double-precision floating point representation.
     */
    private final double y;
    // Factory Methods
    // --------------------------------------------------------------------------------------
    // See Effective Java: 41.8/731
    /**
     * Create a StarDate object using the time in milliseconds past the epoch.
     *
     * @param timeInMillis the time in milliseconds past the epoch
     * @return a new StarDate object
     *
     */
    public static StarDate newInstance(long timeInMillis) {
        Calendar calutc = getDefaultCalendar();
        calutc.setTimeInMillis(timeInMillis);
        int year = calutc.get(Calendar.YEAR);
        double y0 = StartOfYear.getStartOfYear(year),y1 = StartOfYear.getStartOfYear(year + 1);
        return newInstance(year + (timeInMillis - y0) / (y1 - y0));
    }
    /**
     * Create a StarDate object using the date/time of a Calendar object.
     *
     * @param cal the input calendar object (any time zone)
     * @return a new StarDate object
     */
    public static StarDate newInstance(Calendar cal) {
        if (cal.getTimeZone() == TimeZone.getTimeZone("UTC")) {
            // Special case. No need to convert to UTC, so go for speed.
            int year = cal.get(Calendar.YEAR);
            double y0 = StartOfYear.getStartOfYear(year),y1 = StartOfYear.getStartOfYear(year + 1);
            return newInstance(year + (cal.getTimeInMillis() - y0) / (y1 - y0));
        }
        return newInstance(cal.getTimeInMillis());
    }
    /**
     * Create a StarDate object using the double-precision float representation
     * of a StarDate.
     *
     * @param x the input double-precision float
     * @return a new StarDate object
     */
    public static StarDate newInstance(double x) {
        return new StarDate(x);
    }
    /**
     * The StarDate copy factory method.
     *
     * @param sd the StarDate object to copy
     * @return a new StarDate object
     */
    public static StarDate newInstance(StarDate sd) {
        return newInstance(sd.y);
    }
    /**
     * Create a StarDate object using a string representation of the StarDate.
     *
     * @param x the string representation of a StarDate (example: "2010.12345")
     * @return a new StarDate object
     */
    public static StarDate parseStarDate(String x) {
        return newInstance(Double.parseDouble(x));
    }
    /**
     * Create a StarDate object using the date/time stored in a Date object.
     *
     * @param d the input Date object
     * @return a new StarDate object
     */
    public static StarDate newInstance(Date d) {
        Calendar cal = getDefaultCalendar();
        cal.setTime(d);
        return newInstance(cal);
    }
    /**
     * Create a StarDate object using the current date/time.
     *
     * @return a new StarDate object
     */
    public static StarDate newInstance() {
        return newInstance(new Date());
    }
    /**
     * @param tz a TimeZone object
     * @param year the (integer) year
     * @param month the month (January = 1, ..., December = 12)
     * @param day the day (1 to 31 inclusive)
     * @param hour the hour (0 to 23 inclusive)
     * @param minute the minute (0 to 59 inclusive)
     * @param second the second (0 to 59 inclusive)
     * @return a new StarDate object
     */
    public static StarDate newInstance(TimeZone tz, int year, int month,
            int day, int hour, int minute, int second) {
       final Calendar cal = new GregorianCalendar(tz, Locale.ENGLISH);
        cal.set(Calendar.YEAR, year);
        // Don't forget month starts with 0
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        return newInstance(cal);
    }
    /**
     * Creates a new StarDate object from the last modification time of File x.
     *
     * @param x the File object to examine.
     * @return a new StarDate object
     */
    public static StarDate newInstance(File x) {
        return StarDate.newInstance(new Date(x.lastModified()));
    }
    /**
     * @param x the date in RFC2822 format (used in emails)
     * @return a new StarDate object
     * @throws ParseException if date cannot be parsed
     */
    public static StarDate parseRFC2822(String x) throws ParseException {
        return StarDate.newInstance(new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss Z").parse(x));
    }
    /**
     * @param x The date in the format git uses
     * @return A new StarDate object
     * @throws ParseException if date cannot be parsed
     */
    public static StarDate parseGitDate(String x) throws ParseException {
        return StarDate.newInstance(new SimpleDateFormat(
                "EEE MMM dd HH:mm:ss yyyy Z").parse(x));
    }
    // ------------------------------------------------------------------------------------------------
    // Clients should use the factory methods instead of the constructor.
    // A private constructor and the lack of mutators makes this class
    // immutable.
    private StarDate() {
        y = 0;
        throw new AssertionError();
    }
    private StarDate(double x) {
        y = x;
        //if (x < 1) {
       //     log.fine("StarDates < 1.0 have not been unit tested.");
       // }
    }
    // Getters
    // ----------------------------------------------------------------------------------------------
    /**
     * @return the double representation of the StarDate
     */
    public double getDouble() {
        return y;
    }
    /**
     * @return the milliseconds past the epoch that represents the StarDate
     */
    public long getTimeInMillis() {
        int y0 = (int) y;
        long t0 = StartOfYear.getStartOfYear(y0),t1 = StartOfYear.getStartOfYear(y0 + 1);
        return (long) (t0 + (t1 - t0) * (y - y0));
    }
    /**
     * Returns the StarDate as a string with precision of approximately 1/3 day.
     *
     * @return A string representation of the floating-point StarDate.
     */
    public String getApproximate() {
        return String.format("%.3f", y);
    }
  
    /**
     * Get the Calendar representation of the StarDate object.
     *
     * @return the Calendar object
     */
    public Calendar getCalendar() {
        Calendar cal = getDefaultCalendar();
        cal.setTimeInMillis(getTimeInMillis());
        return cal;
    }
    /**
     * Get the Date representation of the StarDate object.
     *
     * @return the Date object
     */
    public Date getDate() {
        return new Date(getTimeInMillis());
    }
    /**
     * @return the "year" part of the StarDate, represented as a String
     */
    public String getYearString() {
        return Integer.toString((int) y);
    }
    // Utilities
    // ------------------------------------------------------------------------------------------------
    private static Calendar getDefaultCalendar() {
        return new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.ENGLISH);
     }
    // Object overrides
    // ----------------------------------------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        // See Effective Java: 92.9/731
        if (o instanceof StarDate) { // instanceof catches null case
            return y == ((StarDate) o).y;
        }
        return false;
    }
    @Override
    public int hashCode() {
        // See Effective Java: 121.2/731
        return Double.hashCode(y);
    }
    /**
     * Returns the string representation of this StarDate. The string consists
     * of a 4-digit year, followed by a period, followed by exactly 13 digits
     * (padded in back with zeros if necessary.)
	 *
     */
    @Override
    public String toString() {
        return String.format("%.13f", y);
    }
  
    @Override
    public int compareTo(StarDate x) {
        return Double.compare(y, x.y);
    }
    static class StartOfYear {
        private static final ConcurrentHashMap<Integer, Long> START_OF_YEAR =  new ConcurrentHashMap<Integer, Long>();
        static { // Pre-fill cache with most commonly queried years.
            // No need to synchronize within static initializer, according to the
            // Java Language Specifications.
            START_OF_YEAR.put(2009, 1230768000000L);
            START_OF_YEAR.put(2010, 1262304000000L);
            START_OF_YEAR.put(2011, 1293840000000L);
            START_OF_YEAR.put(2012, 1325376000000L);
        }
        /**
         * This function caches the result of the (expensive) Calendar
         * functions. There is a better implementation in Java Concurrency In
         * Practice listing 5.19, but this should work well enough for our
         * purposes.
         *
         * @param y the (integer) year
         * @return the (long) time in milliseconds from the epoch (UTC) to the
         * start of year y
         */
        public static long getStartOfYear(int y) {
            Integer y0 = y;
            if (START_OF_YEAR.containsKey(y0)) {
                return START_OF_YEAR.get(y0);
            }
            Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"),
                    Locale.ENGLISH);
            cal.set(Calendar.YEAR, y);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            START_OF_YEAR.put(y0, cal.getTimeInMillis());
//			log.info("cache store for year " + y + ": " + START_OF_YEAR.get(y0));
            return START_OF_YEAR.get(y0);
        }
    }
}
