package ph.eapesa.apps;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final int DAYS_IN_SECS = 86400;
    public static final int DAYS_IN_MSECS = 86400000;

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); // negative number would decrement the days
        return cal.getTime();
    }
}