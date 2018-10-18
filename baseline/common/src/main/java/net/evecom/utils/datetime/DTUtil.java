package net.evecom.utils.datetime;

import net.evecom.utils.verify.CheckUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: DTUtil
 * @Description: 日期时间操作组件 @author： zhengc @date： 2007年8月26日
 */
public class DTUtil {
	private static SimpleDateFormat sdf = null;
	private static Date dtTime = new Date();

	private DTUtil() {
	}

	public static boolean isFistMonthOfThisYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) == 0;
	}

	public static int getNextMonthZeroTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (int) (c.getTime().getTime() / 1000);
	}

	/**
	 * 日期long值转换为日期字符串
	 */
	public static String fmtLongTime(long time, String pattern) {
		sdf = new SimpleDateFormat(pattern);
		dtTime.setTime(time * 1000);
		return sdf.format(dtTime);
	}

	/**
	 * 日期long值转换为日期字符串yyyy-MM-dd HH:mm:ss
	 */
	public static String fmtLongTime(long time) {
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dtTime.setTime(time * 1000);
		return sdf.format(dtTime);
	}

	/**
	 * 日期long值转换为日期字符串yyyy-MM-dd
	 */
	public static String fmtLongTime2(long time) {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		dtTime.setTime(time * 1000);
		return sdf.format(dtTime);
	}

	public static String getNowMilliTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return sdf.format(date);
	}

	public static long getNowLongTime() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	public static int getNowIntTime() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	/**
	 * 日期字符串转换为日期long值
	 *
	 * @param dt
	 */
	public static long fmtStrTime(String dt, String pattern) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		try {
			date = sdf.parse(dt);
		} catch (ParseException e) {
			return 0;
		}

		return date.getTime() / 1000;
	}

	public static int getCurrentYear() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.YEAR);
	}

	public static int getOldYear(long oldLongTime) {
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(oldLongTime * 1000);
		return now.get(Calendar.YEAR);
	}

	public static int getCurrentMonth() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.MONTH) + 1;
	}

	public static int getCurrentDay() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.DAY_OF_MONTH);
	}

	public static int getCurrentHour() {
		Calendar now = Calendar.getInstance();

		return now.get(Calendar.HOUR_OF_DAY);
	}

	public static int getCurrentYear2(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	public static int getCurrentMonth2(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	public static int getCurrentMonth3(int datetime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(datetime * 1000l);
		return c.get(Calendar.MONTH) + 1;
	}

	public static int getCurrentDay2(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getCurrentDay3(int datetime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(datetime * 1000l);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getCurrentHour2(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static int getCurrentHour3(int datetime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(datetime * 1000l);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static String SecToHour(long second) {
		long min = second / 60;
		long sec = second % 60;
		long hour = 0;

		if (min >= 60) {
			hour = min / 60;
			min = min - (hour * 60);
		}

		StringBuffer sb = new StringBuffer();

		if (hour != 0) {
			sb.append(hour);
			sb.append("小时");
		}

		if (min != 0) {
			sb.append(min);
			sb.append("分");
		}

		sb.append(sec);
		sb.append("秒");

		return sb.toString();
	}

	/**
	 * 获取今天日期字符串yyyy-MM-dd
	 */
	public static String getTodayString() {
		long now = System.currentTimeMillis() / 1000;

		return fmtLongTime(now, "yyyy-MM-dd");
	}

	/**
	 * 获取今天日期字符串yyyy-MM-dd HH:mm:ss
	 */
	public static String getTodayString2() {
		long now = System.currentTimeMillis() / 1000;

		return fmtLongTime(now, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取今天日期字符串yyyy-MM-dd HH:mm:ss
	 */
	public static String getTodayString4() {
		long now = System.currentTimeMillis() / 1000;

		return fmtLongTime(now, "yyyyMMddHHmmss");
	}

	/**
	 * 获取今天日期字符串yyyyMMdd
	 */
	public static String getTodayString3() {
		long now = System.currentTimeMillis() / 1000;

		return fmtLongTime(now, "yyyyMMdd");
	}

	/**
	 * 获取从今天开始倒退一个月日期字符串
	 *
	 * @return
	 */
	public static String getOneMonthAgoString() {
		long now = (System.currentTimeMillis() / 1000) - 2592000;

		return fmtLongTime(now, "yyyy-MM-dd");
	}

	public static int getDate(int time) {
		return (int) DateStr2LongTime(fmtLongTime(time, "yyyy-M-dd"), "yyyy-M-dd");
	}

	/**
	 * 格式化日期
	 *
	 * @param time
	 * @param digit
	 * @param style
	 * @return
	 */
	public static String fmtLongTime(long time, int digit, int style) {
		String dtStyle = "";

		switch (style) {
		case 1:
			dtStyle = "yyyy-M-dd HH:mm:ss";

			break;

		case 2:
			dtStyle = "yyyy-M-dd";

			break;

		case 3:
			dtStyle = "HH:mm:ss";

			break;

		case 4:
			dtStyle = "yy-M-dd";

			break;

		case 5:
			dtStyle = "M-dd HH";

			break;

		case 6:
			dtStyle = "yyyy年M月dd日";

			break;

		case 7:
			dtStyle = "yyyy年M月dd日 HH:mm:ss";

			break;

		case 8:
			dtStyle = "yyyy-M-dd HH:mm";

			break;
		case 9:
			dtStyle = "yyyyMM";

			break;

		default:
			dtStyle = "yyyy年M月dd日 HH:mm:ss";

			break;
		}

		if (digit == 10) {
			time = time * 1000;
		}

		sdf = new SimpleDateFormat(dtStyle);
		dtTime.setTime(time);

		return sdf.format(dtTime);
	}

	/**
	 * 获取今天日期long值
	 *
	 * @return
	 */
	public static long getTodayLongTime() {
		String dt = getTodayString();
		return DateStr2LongTime(dt, "yyyy-M-dd");
	}

	/**
	 * 得到小时数
	 */
	@SuppressWarnings("deprecation")
	public static int DateStr3LongTime(String dt, int unit) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		try {
			date = sdf.parse(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return (date.getHours() * 60 + date.getMinutes()) / (unit / 60);
	}

	/**
	 * 日期字符串转换为日期long值
	 */
	public static long DateStr2LongTime(String dt, String pattern) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			date = sdf.parse(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime() / 1000;
	}

	/**
	 * 获取当前时间的月份
	 */
	public static int getWeekOfMonth(long time) {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(time * 1000);
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 获取这个时间所在月的天数
	 */
	public static int getMonthDayCount(int datetime) {
		return getCurrentDay2(getLastDayOfThisMonth(datetime));
	}

	/**
	 * 获取这个年、月、第几个月的第一天
	 *
	 * @param year
	 * @param month
	 * @param weekOfMonth
	 * @return
	 */
	public static long getLongTimeByYearMonthWeekOfMonth(int year, int month, int weekOfMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.WEEK_OF_MONTH, weekOfMonth);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTimeInMillis() / 1000;
	}

	/**
	 * 输出当前月的第一天
	 *
	 * @return
	 */
	public static Date getFirstDayOfThisMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();// 当前日期
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);// 设为当前月的1号
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 输出当前月的第一天
	 *
	 * @return
	 */
	public static int getFirstDayOfThisMonth(int datetime) {
		Calendar calendar = Calendar.getInstance();// 当前日期
		calendar.setTimeInMillis(datetime * 1000l);
		calendar.set(Calendar.DATE, 1);// 设为当前月的1号
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return (int) (calendar.getTime().getTime() / 1000l);
	}

	/**
	 * 输出当前月的第一天
	 *
	 * @return
	 */
	public static int getFirstDayOfThisYear(int datetime) {
		Calendar calendar = Calendar.getInstance();// 当前日期
		calendar.setTimeInMillis(datetime * 1000l);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DATE, 1);// 设为当前月的1号
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return (int) (calendar.getTime().getTime() / 1000l);
	}

	/**
	 * 输出当前月的最后一天
	 *
	 * @return
	 */
	public static Date getLastDayOfThisMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();// 当前日期
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.add(Calendar.MONTH, 1);// 下个月
		calendar.set(Calendar.DATE, 1);// 下个月1号
		calendar.add(Calendar.DATE, -1);// 这个月最后一天
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 输出当前月的最后一天
	 *
	 * @return
	 */
	public static Date getLastDayOfThisMonth(int datetime) {
		Calendar calendar = Calendar.getInstance();// 当前日期
		calendar.setTimeInMillis(datetime * 1000l);
		calendar.add(Calendar.MONTH, 1);// 下个月
		calendar.set(Calendar.DATE, 1);// 下个月1号
		calendar.add(Calendar.DATE, -1);// 这个月最后一天
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 输出当前月的最后一天
	 *
	 * @return
	 */
	public static int getLastDayOfThisMonth2(int datetime) {
		Calendar calendar = Calendar.getInstance();// 当前日期
		calendar.setTimeInMillis(datetime * 1000l);
		calendar.add(Calendar.MONTH, 1);// 下个月
		calendar.set(Calendar.DATE, 1);// 下个月1号
		calendar.add(Calendar.DATE, -1);// 这个月最后一天
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return (int) (calendar.getTime().getTime() / 1000l);
	}

	/**
	 * 输出当前月的最后一天
	 *
	 * @return
	 */
	public static int getLastDayOfThisYear(int datetime) {
		Calendar calendar = Calendar.getInstance();// 当前日期
		calendar.setTimeInMillis(datetime * 1000l);
		calendar.set(Calendar.MONTH, 11);
		calendar.add(Calendar.MONTH, 1);// 下个月
		calendar.set(Calendar.DATE, 1);// 下个月1号
		calendar.add(Calendar.DATE, -1);// 这个月最后一天
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return (int) (calendar.getTime().getTime() / 1000l);
	}

	public static long getFirstDayOfThisMonthByLogic(int year, int month) {
		long firstday = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(DTUtil.getFirstDayOfThisMonth(year, month));
		int f = cal.get(Calendar.DAY_OF_WEEK);
		if (f > 2)
			firstday = DTUtil.getFirstDayOfThisMonth(year, month).getTime() + (7 - f + 1) * 86400000;
		else
			firstday = DTUtil.getFirstDayOfThisMonth(year, month).getTime() - (f - 1) * 86400000;
		return firstday;
	}

	public static long getLastDayOfThisMonthByLogic(int year, int month) {
		long lastday = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(DTUtil.getLastDayOfThisMonth(year, month));
		int l = cal.get(Calendar.DAY_OF_WEEK);
		if (l > 1)
			lastday = DTUtil.getLastDayOfThisMonth(year, month).getTime() + (7 - l + 1) * 86400000;
		else
			lastday = DTUtil.getLastDayOfThisMonth(year, month).getTime() - 86400000;
		return lastday;
	}

	public static int getHourZeroTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (int) (c.getTime().getTime() / 1000);
	}

	public static int getTodayZeroTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (int) (c.getTime().getTime() / 1000);
	}

	public static int getFirstMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (int) (c.getTime().getTime() / 1000);
	}

	public static int getLastMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (int) (c.getTime().getTime() / 1000);
	}

	public static int getNearMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (int) (c.getTime().getTime() / 1000);
	}

	public static int getNextMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (int) (c.getTime().getTime() / 1000);
	}

	public static int getMonthZeroTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (int) (c.getTime().getTime() / 1000);
	}

	public static int getLastMonthZeroTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (int) (c.getTime().getTime() / 1000);
	}

	public static long getHourZeroTime(int time) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(time * 1000l));
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime().getTime();
	}

	public static boolean isFistDayOfThisMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH) == 1;
	}

	public static String GetCurrentDateTimeToString(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date cday = new Date();
		return sdf.format(cday);
	}

	/**
	 * @param time_misec
	 *            时间截
	 * @return 用时时间，12000=12秒
	 */
	public static String getTimeStrByLongTime(long time_misec) {
		StringBuffer bu = new StringBuffer(20);
		long parent = time_misec;
		long leav = parent % 1000;

		if (leav != 0) {
			bu.insert(0, leav + "毫秒");
		}

		long child = parent / 1000;

		if (child == 0) {
			return bu.toString();
		}

		leav = child % 60;

		if (leav != 0) {
			bu.insert(0, leav + "秒");
		}

		child = child / 60;

		if (child == 0) {
			return bu.toString();
		}

		leav = child % 60;

		if (leav != 0) {
			bu.insert(0, leav + "分钟");
		}

		child = child / 60;

		if (child == 0) {
			return bu.toString();
		}

		leav = child % 24;

		if (leav != 0) {
			bu.insert(0, leav + "小时");
		}

		child = child / 24;

		if (child == 0) {
			return bu.toString();
		}

		leav = child % 30;

		if (leav != 0) {
			bu.insert(0, leav + "天");
		}

		child = child / 30;

		if (child == 0) {
			return bu.toString();
		}

		leav = child % 12;

		if (leav != 0) {
			bu.insert(0, leav + "月");
		}

		child = child / 12;

		if (child == 0) {
			return bu.toString();
		}

		bu.insert(0, child + "年");

		return bu.toString();
	}

	public static int getDayZeroTime(int time) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / 1000 - 86400);
	}

	public static String stringDateToStringDate(String fmt) {
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			dtTime = sdf.parse(fmt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(dtTime);
	}

	public static String getNowDataStr() {
		System.setProperty("user.timezone", "GMT +08");
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new Date());
		return datetime;
	}

	public static final ZoneId ZONE = ZoneId.of("+08:00");

	public static LocalDateTime now() {
		return LocalDateTime.now(ZONE);
	}

	public static String nowStr() {
		return now().toString();
	}

	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZONE).toInstant());
	}

	public static Date nowDate() {
		return Date.from(now().atZone(ZONE).toInstant());
	}
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static String dateToString(Date date) {
		return dateToString(date, DATE_TIME_PATTERN);
	}

	public static String dateToString(Date date, String pattern) {
		if (date == null) {
			date = new Date();
		}
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * String TO Date
	 *
	 * @param dateStr
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String dateStr, String pattern) throws ParseException {
		if (!CheckUtil.isNull(pattern)) {
			pattern = DATE_PATTERN;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = sdf.parse(dateStr);
		return date;
	}
}
