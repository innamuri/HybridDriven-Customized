/*    */package com.fm.craft;

/*    */
/*    */import java.text.DateFormat;
/*    */
import java.text.SimpleDateFormat;
/*    */
import java.util.Calendar;
/*    */
import java.util.Date;
/*    */
import java.util.Properties;

/*    */
/*    */public class Util
/*    */{
	/*    */public static Date getCurrentTime()
	/*    */{
		/* 25 */Calendar calendar = Calendar.getInstance();
		/* 26 */return calendar.getTime();
		/*    */}

	/*    */
	/*    */public static String getCurrentFormattedTime()
	/*    */{
		/* 37 */Properties properties = Settings.getInstance();
		/* 38 */DateFormat dateFormat = new SimpleDateFormat(
				properties.getProperty("DateFormatString"));
		/* 39 */Calendar calendar = Calendar.getInstance();
		/* 40 */return dateFormat.format(calendar.getTime());
		/*    */}

	/*    */
	/*    */public static String getFormattedTime(Date time)
	/*    */{
		/* 51 */Properties properties = Settings.getInstance();
		/* 52 */DateFormat dateFormat = new SimpleDateFormat(
				properties.getProperty("DateFormatString"));
		/* 53 */return dateFormat.format(time);
		/*    */}

	/*    */
	/*    */public static String getTimeDifference(Date startTime, Date endTime)
	/*    */{
		/* 65 */long timeDifference = endTime.getTime() - startTime.getTime();
		/* 66 */timeDifference /= 1000L;
		/* 67 */String timeDifferenceDetailed = Long
				.toString(timeDifference / 60L) + " minute(s), " +
		/* 68 */Long.toString(timeDifference % 60L) + " seconds";
		/* 69 */return timeDifferenceDetailed;
		/*    */}
	/*    */
}
