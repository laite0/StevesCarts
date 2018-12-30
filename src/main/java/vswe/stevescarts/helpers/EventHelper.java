package vswe.stevescarts.helpers;

import vswe.stevescarts.Constants;
import vswe.stevescarts.SCConfig;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class EventHelper {

    private static final Calendar CALENDAR = Calendar.getInstance();
    private static final int YEAR = CALENDAR.get(Calendar.YEAR);
    private static final long TIME = CALENDAR.getTime().getTime();

    public static void setupEvents() {
        Constants.isChristmas = SCConfig.enableChristmas && isActive(Calendar.DECEMBER, 1, 31);
        Constants.isHalloween = SCConfig.enableHalloween && isActive(Calendar.OCTOBER, 23, 8);
        Constants.isEaster = SCConfig.enableEaster && isActive(Calendar.MARCH, 23, 31);
    }

    private static boolean isActive(int month, int dayOfMonth, int durationDays) {
        long start = new GregorianCalendar(YEAR, month, dayOfMonth, 0, 0).getTime().getTime();
        long dayDiff = TimeUnit.DAYS.convert(TIME - start, TimeUnit.MILLISECONDS);
        return dayDiff >= 0 && dayDiff <= durationDays;
    }

}
