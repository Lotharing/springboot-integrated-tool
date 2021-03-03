package top.lothar.util;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdfShortDay = new SimpleDateFormat("yyyy.MM.dd");
    private static final SimpleDateFormat sdfCurrentDay = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy年MM月");
    private static final SimpleDateFormat sdfYearMonthDay = new SimpleDateFormat("yyyy年MM月dd日");
    private static final SimpleDateFormat sdfYearMonthDayWithTime = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分");
    private static final SimpleDateFormat sdfMonthDay = new SimpleDateFormat("MM月dd日");
    public static final String PATTERN_YEAR_MONTH_DAY_HH_MM = "yyyy.MM.dd HH:mm";
    public static final String PATTERN_LINE_YEAR_MONTH_DAY_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String PATTERN_DOT_YYYY_MM_DD = "yyyy.MM.dd";
    public static final String PATTERN_DOT_M_D = "M.d";
    public static final String PATTERN_YY_MM_DD_HH_MM_SS = "yyyyMMddHHmmssSSS";
    public static final String PATTERN_YYYY = "yyyy";
    public static final String PATTERN_MM = "MM";
    public static final String PATTERN_DD = "dd";
    public static final String PATTERN_YYYY_MM_DD_HH_MM_CHINESE = "yyyy年M月d日HH点mm分";
    public static final String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_YYYYMM = "yyyyMM";

    private static final String DEFAULT_PURCHASE_DATE = "2018-01-01 00:00:00";

    /**
     * @param asklong
     * @return
     */
    public static String formatTime(int asklong) {
        int min = asklong / 60;
        int sec = asklong % 60;
        String res = "";
        if (min < 10)
            res = "0";
        res = res + min + ":";
        if (sec < 10)
            res += "0";
        res += sec;
        return res;
    }

    /**
     * 格式化日期操作
     *
     * @param date
     * @return
     */
    public static String formatNormalDate(Date date) {
        return sdf.format(date);
    }

    /**
     * 格式化日期操作
     *
     * @param date
     * @return
     */
    public static String formatNormalDayDate(Date date) {
        return sdfDay.format(date);
    }

    /**
     * 格式化成短的日期格式
     *
     * @param date
     * @return
     */
    public static String formatShortDayDate(Date date) {
        return sdfShortDay.format(date);
    }

    /**
     * 将日期字符串格式化成日期格式
     *
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        try {
            return sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDayDate(String date) {
        try {
            return sdfDay.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 判断两个日期是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 获取第二天凌晨0点毫秒数
     *
     * @return
     */
    public static long nextDayFirstDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime().getTime();
    }

    public static Date nextDayFirstDateDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date calcNextYearDayEnd(Date originalDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(originalDate);
        cal.add(Calendar.YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 判断所给定日期和当前日期的大小
     *
     * @param expireTime
     * @return
     */
    public static boolean isExpired(String expireTime) {
        if (StringUtils.isEmpty(expireTime)) {
            return false;
        }
        try {
            Date date = sdf.parse(expireTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            long timestamp = cal.getTimeInMillis();
            System.out.println(System.currentTimeMillis() + " " + timestamp + "  " + (timestamp >= System.currentTimeMillis()));
            long systimestamp = System.currentTimeMillis();
            return timestamp >= systimestamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 是否过期，匹配当前时间
     *
     * @param expireTime
     * @return
     */
    public static boolean isBeforeToday(String expireTime) {
        if (StringUtils.isEmpty(expireTime)) {
            return false;
        }
        try {
            Date now = new Date();
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(now);
            // 将时分秒,毫秒域清零
            cal1.set(Calendar.HOUR_OF_DAY, 0);
            cal1.set(Calendar.MINUTE, 0);
            cal1.set(Calendar.SECOND, 0);
            cal1.set(Calendar.MILLISECOND, 0);

            return cal1.getTime().after(sdfDay.parse(expireTime));
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 是否过期，匹配当前时间
     *
     * @param expireTime
     * @return
     */
    public static boolean isBeforeToday(Date expireTime) {
        if (StringUtils.isEmpty(expireTime)) {
            return false;
        }
        String et = sdfDay.format(expireTime);
        return isBeforeToday(et);
    }

    public static boolean isExpired(long expireTime) {
        return expireTime < System.currentTimeMillis();
    }

    public static boolean isExpired(Date expireTime) {
        if (expireTime == null) {
            return true;
        }
        return expireTime.getTime() < System.currentTimeMillis();
    }

    /**
     * 日期转星期
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        try {
            smdate = sdfDay.parse(sdfDay.format(smdate));
            bdate = sdfDay.parse(sdfDay.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);

            return Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 计算两个日期之间相差的分钟
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetweenMinutes(Date smdate, Date bdate) {
        try {
            smdate = sdfDay.parse(sdfDay.format(smdate));
            bdate = sdfDay.parse(sdfDay.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 60);

            return Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            return -1;
        }
    }

    public static int betweenMinutes(Date smdate, Date bdate) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 60);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            return -1;
        }
    }




    /**
     * 格式化年月
     *
     * @param date
     * @return
     */
    public static String formatYearMonth(Date date) {
        return sdfMonth.format(date);
    }


    /**
     * 获取下月的第一天
     *
     * @param dt
     * @return
     */
    public static Date setNextMonthFirstDay(Date dt) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(dt);
        ca.add(Calendar.MONTH, 1);
        // 设置为1号,当前日期既为本月第一天
        ca.set(Calendar.DAY_OF_MONTH, 1);
        return ca.getTime();
    }

    /**
     * 获取下月的第一天
     *
     * @param dt
     * @return
     */
    public static Date setNextMonthFirstDay(String dt) throws ParseException {
        Date date = sdf.parse(dt);
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MONTH, 1);
        // 设置为1号,当前日期既为本月第一天
        ca.set(Calendar.DAY_OF_MONTH, 1);
        return ca.getTime();
    }

    /**
     * 获取本月的第一天
     *
     * @param dt
     * @return
     */
    public static Date setMonthFirstDay(Date dt) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(dt);
        ca.add(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        ca.set(Calendar.DAY_OF_MONTH, 1);
        return ca.getTime();
    }

    /**
     * 获取本月的最后一天
     *
     * @param dt
     * @return
     */
    public static Date setMonthLastDay(Date dt) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(dt);
        ca.add(Calendar.YEAR, 1);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        return ca.getTime();
    }

    /**
     * 返回当前年月日的字符串
     *
     * @param date
     * @return
     */
    public static String formatNowDay(Date date) {
        return sdfYearMonthDay.format(date);
    }

    /**
     * 返回月日的字符串
     *
     * @param date
     * @return
     */
    public static String formatMonthDay(Date date) {
        return sdfMonthDay.format(date);
    }

    /**
     * 一天开始的时间
     *
     * @return
     */
    public static String getTodayBeginStr() {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        String timeStr = sdf.format(cal.getTime());
        return timeStr;
    }

    /**
     * @return
     */
    public static String getNowStr() {
        String timeStr = sdf.format(new Date());
        return timeStr;
    }

    /**
     * 获取当前时间的格式化yyyyMMdd
     *
     * @return
     */
    public static String getCurrentFormatStr() {
        return sdfCurrentDay.format(new Date());
    }

    /**
     * 根据时间新增多少小时
     * <p>
     * Calendar.HOUR_OF_DAY是24小时制
     * Calendar.HOUR是12小时制
     *
     * @param dt
     * @param hours
     * @return
     */
    public static Date setAddHoursWithDate(Date dt, int hours) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(dt);
        ca.add(Calendar.HOUR_OF_DAY, hours);
        return ca.getTime();
    }

    /**
     * 新增一个月时间
     *
     * @param dt
     * @return
     */
    public static String getAddMonth(Date dt) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        String timeStr = sdf.format(cal.getTime());
        return timeStr;
    }

    /**
     * 新增天数 返回String
     *
     * @param dt
     * @param days
     * @return
     */
    public static String getStrAddDays(Date dt, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(Calendar.DAY_OF_MONTH, days);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return sdf.format(cal.getTime());
    }

    /**
     * 判断是否解锁状态
     *
     * @param fDate
     * @param oDate
     * @return
     */
    public static boolean isLockDay(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        int days = day2 - day1;
        return days >= 0;
    }

    /**
     * 根据时间新增多少天
     *
     * @param dt
     * @param days
     * @return
     */
    public static Date setAddDaysWithDate(Date dt, int days) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(dt);
        ca.add(Calendar.DATE, days);
        return ca.getTime();
    }

    public static Date setAddMinutesWithDate(Date dt, int minutes) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(dt);
        ca.add(Calendar.MINUTE, minutes);
        return ca.getTime();
    }

    /**
     * 根据日期返回格式化的字符串日期
     *
     * @param dt
     * @return
     */
    public static String formatDayWithTime(Date dt) {
        return sdfYearMonthDayWithTime.format(dt);
    }


    public static int expiresSeconds(Long time){
        Calendar c = Calendar.getInstance();
//        System.out.println(c.getTimeInMillis());
        return (int) (time - c.getTimeInMillis()/1000);
    }

    public static int betweenSeconds(Date from, Date to) {

        Calendar c = Calendar.getInstance();
        c.setTime(from);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(to);
        return (int) ((c1.getTimeInMillis() - c.getTimeInMillis()) / 1000);
    }

    public static Date parse(final String dateStr, final String pattern) {
        try {
            DateFormat df = new SimpleDateFormat(pattern);
            return df.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }

    }

    /**
     * 将日期类型转换为字符串表示，默认yyyy-MM-dd格式
     *
     * @param date 日期对象
     * @return 日期对象的字符串表示
     */
    public static String date2String(Date date) {
        return date2String(date, "yyyy-MM-dd");
    }

    /**
     * 将日期类型转换为字符串表示
     *
     * @param date    日期对象
     * @param pattern 日期格式
     * @return 日期对象的字符串表示
     */
    public static String date2String(Date date, String pattern) {
        if (date == null) return "";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }


    public static String getTimeDiff(Date past) {
        if (past == null) return "";
        Date now = new Date();
        int seconds = betweenSeconds(past, now);
        return seconds < 60 ? seconds + "秒前" :
                seconds < (60 * 60) ? seconds / 60 + "分钟前" :
                        seconds < (24 * 60 * 60) ? seconds / (60 * 60) + "小时前" :
                                (seconds >= 24 * 60 * 60 && seconds < 30 * 24 * 60 * 60) ? seconds / (24 * 60 * 60) + "天前" : DateUtil.date2String(past);
    }

    /**
     * 月份3个大写英文字母
     *
     * @param date
     * @return
     */
    public static String getMonthCase(Date date) {
        if (date == null) return "";
        String j = date.toString();
        String[] k = j.split(" ");
        return k[1].toUpperCase();
    }

    public static void main(String[] args) {
//        System.out.println(date2String(new Date(), PATTERN_YEAR_MONTH_DAY_HH_MM));
//        String mData = "2019-01-01";
//        System.out.println(date2String(parse(mData, PATTERN_YYYY_MM_DD), "MM月dd日"));
//        System.out.println(getMonthCase(parse("2019-02-28 00:00", PATTERN_LINE_YEAR_MONTH_DAY_HH_MM)));

        Date from = parse("2020-05-07 10:11:00", PATTERN_LINE_YEAR_MONTH_DAY_HH_MM);
        Date to = new Date();
        int hour = betweenMinutes(to, from);
        System.out.println(hour);
        System.out.println("》》》》》》》》》》");
//        System.out.println(date2String(new Date(), PATTERN_YYYYMM));
//        System.out.println(isExpired("1573615718"));
//        System.out.println(System.currentTimeMillis());
//        System.out.println(new Date().getTime());
//        String time = "1573615718";
//        System.out.println(time);

        System.out.println(expiresSeconds(1591928944L));

    }

    /**
     * 判断是否在日期之前
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isBeforeDate(Date startTime, Date endTime) {
        if (startTime == null || endTime == null) {
            return false;
        }
        try {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(endTime);
            return cal1.getTime().after(startTime);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 时间相差次数
     * <p>
     * 第一个超过时间算1个， 往后每一个小时+1次
     *
     * @param from
     * @param to
     * @return
     */
    public static int betweenHoursTimes(Date from, Date to) {

        Calendar c = Calendar.getInstance();
        c.setTime(from);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(to);

        long diff = c1.getTimeInMillis() - c.getTimeInMillis();
        int _retTimes = 1;
        if (diff <= 0) {
            _retTimes = 0;
        } else {
            int _diff_hour = (int) (diff / 1000 / 60 / 60);
            _retTimes += _diff_hour;
        }
        return _retTimes;
    }

    /**
     * 获取第二天0点到现在的秒数
     *
     * @return
     */
    public static Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

    /**
     *      * 将当前时间转换为16进制
     *      * @return
     *     
     */
    public static String Dateto16Hex(Date date) {
        Long ab = date.getTime() / 1000;
        String a = Long.toHexString(ab);
        return a;
    }

}
