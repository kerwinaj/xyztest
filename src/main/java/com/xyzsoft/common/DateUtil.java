/*
 * Copyright (c) 2010 by XUANWU INFORMATION TECHNOLOGY CO.
 *             All rights reserved
 */
package com.xyzsoft.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class DateUtil {

	/** 时间格式 **/
	public static final String DAY = "yyyy-MM-dd";
	public static final String DAY2 = "yyyy/M/dd";
	public static final String DAY3 = "yyyyMMdd";
	public static final String TIME = "HH:mm:ss";
	public static final String DATE = "yyyy-MM-dd HH:mm:ss";
	public static final String MONTH = "yyyy-MM";
	public static final String DEFAULT_DATE = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取前N个月数组
	 *
	 * @param n
	 * @return List<String>
	 */
	public static List<String> getPreMonths(int n) {
		List<String> result = new ArrayList<String>();
		if (n <= 0) {
			return result;
		}
		for (int i = 0; i < n; i++) {
			result.add(getPreMoth(i));
		}
		return result;
	}

	/**
	 * 获取前N月数据
	 *
	 * @param pre
	 * @return String
	 */
	public static String getPreMoth(int pre) {
		if (pre < 0) {
			return null;
		}
		Calendar begin = Calendar.getInstance();
		begin.set(Calendar.MONTH, begin.get(Calendar.MONTH) - pre + 1);
		begin.set(Calendar.DAY_OF_MONTH, 0);
		begin.set(Calendar.HOUR_OF_DAY, 0);
		begin.set(Calendar.MINUTE, 0);
		begin.set(Calendar.SECOND, 0);
		return format(begin.getTime(), MONTH);
	}

	/**
	 * 获取当前天
	 *
	 * @return String
	 */
	public static String getCurDayStr() {
		return new SimpleDateFormat(DAY).format(new Date());
	}

	/**
	 * 获取前一个月
	 *
	 * @param date
	 * @return Date
	 */
	public static Date getPreviousMonth(Date date) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.set(Calendar.MONTH, cd.get(Calendar.MONTH) - 1);
		return cd.getTime();
	}
	
	/**
	 * 获取下一个月
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextMonth(Date date){
	    Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.set(Calendar.MONTH, cd.get(Calendar.MONTH) + 1);
        return cd.getTime();
	}
    
    /**
     * 获取下一个月的指定一天的前一天
     * 
     * @param date
     * @return
     * @throws ParseException 
     */
    public static Date getTheDayInNextMonth(Date date, int pointDay) {
        Calendar cd = Calendar.getInstance();
        if(pointDay==1){
            try{
                cd.setTime(DateUtil.parse(getMonthEnd(date),DateTimeType.Day));
            } catch (ParseException pe){
                //TODO :
//                Logger.error("getTheDayInNextMonth parse fail", pe.toString());
            }
        } else {
            cd.setTime(getNextMonth(date));
            cd.set(Calendar.DAY_OF_MONTH, pointDay-1);
        }
        return cd.getTime();
    }
	/**
	 * 获取前一天
	 *
	 * @return Date
	 */
	public static Date getPreviousDate() {
		Calendar begin = Calendar.getInstance();
		begin.set(Calendar.DAY_OF_MONTH, begin.get(Calendar.DAY_OF_MONTH) - 1);
		begin.set(Calendar.HOUR_OF_DAY, 0);
		begin.set(Calendar.MINUTE, 0);
		begin.set(Calendar.SECOND, 0);
		return begin.getTime();
	}

	/**
	 * 获取下N天
	 *
	 * @return Date
	 */
	public static Date getNextDate(Date date, int n) {
		Calendar begin = Calendar.getInstance();
		begin.setTime(date);
		begin.set(Calendar.DAY_OF_MONTH, begin.get(Calendar.DAY_OF_MONTH) + n);
		begin.set(Calendar.HOUR_OF_DAY, 0);
		begin.set(Calendar.MINUTE, 0);
		begin.set(Calendar.SECOND, 0);
		begin.set(Calendar.MILLISECOND, 0);
		return begin.getTime();
	}

	/**
	 * 获取年度第一个月
	 *
	 * @param date
	 * @return String
	 */
	public static String getFirstMonth(Date date) {
		Calendar begin = Calendar.getInstance();
		begin.setTime(date);
		begin.set(Calendar.MONTH, 0);
		return format(begin.getTime(), DateTimeType.Month);
	}

	/**
	 * 获取时间戳
	 *
	 * @return long
	 */
	public static long getTimeStamp(Date time) {
		return null == time ? 0 : time.getTime();
	}

	/**
	 * 检查格式
	 *
	 * @param str
	 * @param sdf
	 * @return boolean
	 */
	public static boolean isParse(String str, String sdf) {
		try {
			return null != parse(str, sdf);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 解析字符串(yyyy-MM-dd HH:mm:ss)
	 *
	 * @param str
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parse(String str) throws ParseException {
		if (StringUtils.isEmpty(str))
			return null;
		return new SimpleDateFormat(DEFAULT_DATE).parse(str);
	}

	/**
	 * 解析字符串
	 *
	 * @param str
	 * @param sdf
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parse(String str, String sdf) throws ParseException {
		if (StringUtils.isEmpty(str) || StringUtils.isEmpty(sdf))
			return null;
		return new SimpleDateFormat(sdf).parse(str);
	}

	/**
	 * 解析字符串
	 *
	 * @param str
	 * @param type
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parse(String str, DateTimeType type) throws ParseException {
		if (StringUtils.isBlank(str))
			return null;
		switch (type) {
		case Date: {
			return new SimpleDateFormat(DAY).parse(str); // SimpleDateFormat有线程安全问题，需要独享
		}
		case Time: {
			return new SimpleDateFormat(TIME).parse(str);
		}
		case Day: {
			return new SimpleDateFormat(DAY).parse(str);
		}
		default: {
			return new SimpleDateFormat(DATE).parse(str);
		}
		}
	}

	/**
	 * 格式化日期, 根据用户传进来的格式
	 *
	 * @param date
	 * @param sdf
	 * @return
	 */
	public static String format(Date date, String sdf) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(sdf).format(date);
	}

	/**
	 * 格式化时间戳
	 *
	 * @param timestamp
	 * @return String
	 */
	public static String format(long timestamp) {
		if (timestamp < 0) {
			return "";
		}
		return format(new Date(timestamp), DateTimeType.Date);
	}

	/**
	 * 格式化时间戳
	 *
	 * @param timestamp
	 * @return String
	 */
	public static String format(long timestamp, String sdf) {
		if (timestamp < 0) {
			return "";
		}
		return new SimpleDateFormat(sdf).format(new Date(timestamp));
	}

	/**
	 * 格式化时间戳
	 *
	 * @param timestamp
	 * @param type
	 * @return String
	 */
	public static String format(long timestamp, DateTimeType type) {
		if (timestamp < 0) {
			return "";
		}
		return format(new Date(timestamp), type);
	}

	/**
	 * 格式化日期
	 *
	 * @param date
	 * @return String
	 */
	public static String format(Date date) {
		return format(date, DateTimeType.DateTime.Date);
	}

	/**
	 * 格式化日期
	 *
	 * @param date
	 * @param type
	 * @return String
	 */
	public static String format(Date date, DateTimeType type) {
		if (null == date) {
			return "";
		}
		switch (type) {
		case Date: {
			return new SimpleDateFormat(DATE).format(date); // SimpleDateFormat有线程安全问题，需要独享
		}
		case Time: {
			return new SimpleDateFormat(TIME).format(date);
		}
		case Day: {
			return new SimpleDateFormat(DAY).format(date);
		}
		case Month: {
			return new SimpleDateFormat(MONTH).format(date);
		}
		default: {
			return new SimpleDateFormat(DATE).format(date);
		}
		}
	}

	/**
	 * 获取一天起始时间
	 *
	 * @param date
	 * @return Date
	 */
	public static Date getFisrtTime(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取一天结束时间
	 *
	 * @param date
	 * @return Date
	 */
	public static Date getEndTime(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取天
	 *
	 * @param date
	 * @return Date
	 */
	public static Date getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取年月
	 *
	 * @param date
	 * @return int
	 */
	public static int getYearMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR) * 100 + (calendar.get(Calendar.MONTH) + 1); // 获取月份
	}

	/**
	 * @description 时间枚举
	 */
	public enum DateTimeType {
		DateTime(0), // 日期时间
		Date(1), // 日期
		Time(2), // 时间
		Month(3), // 年月
		Day(4);// 中文时间格式

		private int index;

		private DateTimeType(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	}
	
	public static List<PeriodDatePair> getDateInterval2Pair(Date begin, Date end) {
	    List<PeriodDatePair> datePairList = new ArrayList<DateUtil.PeriodDatePair>();
	    PeriodDatePair periodDatePair = null;
		// 开始日期不能大于结束日期
	    if (begin.equals(end)) {
	        periodDatePair = new DateUtil().new PeriodDatePair();
	        periodDatePair.setStartDate(begin);
	        periodDatePair.setEndDate(end);
	        datePairList.add(periodDatePair);
	        return datePairList;
	    } else if (!begin.before(end)) {
			return null;
		}

		Calendar cal_begin = Calendar.getInstance();
		cal_begin.setTime(begin);

		Calendar cal_end = Calendar.getInstance();
		cal_end.setTime(end);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		StringBuffer strbuf = new StringBuffer();

		while (true) {
			periodDatePair = new DateUtil().new PeriodDatePair();
			if (cal_begin.get(Calendar.YEAR) == cal_end.get(Calendar.YEAR)
					&& cal_begin.get(Calendar.MONTH) == cal_end.get(Calendar.MONTH)) {
				periodDatePair.setStartDate(cal_begin.getTime());
				periodDatePair.setEndDate(cal_end.getTime());
				datePairList.add(periodDatePair);
				break;
			}
			String str_begin = sdf.format(cal_begin.getTime());
			String str_end = getMonthEnd(cal_begin.getTime());
			strbuf.append(str_begin).append("~").append(str_end).append("\r\n");
			try{
				periodDatePair.setStartDate(parse(str_begin,DateTimeType.Date));
				periodDatePair.setEndDate(parse(str_end,DateTimeType.Date));
				datePairList.add(periodDatePair);
			}catch(ParseException e) {
			    // TODO 
//			    Logger.error("日期输入格式不对", e.toString());
				return Collections.emptyList();
			}
			cal_begin.add(Calendar.MONTH, 1);
			cal_begin.set(Calendar.DAY_OF_MONTH, 1);
		}

		return datePairList;
	}

	/**
	 * 取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public String getMonthBegin(Date date) {
		return parseDate2Str(date, "yyyy-MM") + "-01";
	}

	/**
	 * 取得指定月份的最后一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static String getMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		//1.设为下个月的第一天，肯定是1号
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		//2.减一天就是上个月的最后一天
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return parseDate2Str(calendar.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 以指定的格式来格式化日期
	 * 
	 * @param date
	 *            Date
	 * @param format
	 *            String
	 * @return String
	 */
	public static String parseDate2Str(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public class PeriodDatePair {
		private Date startDate;
		private Date endDate;
		private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		@Override
		public String toString() {
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(sdf.format(startDate))
				.append("~")
				.append(sdf.format(endDate));
			return strbuf.toString();
		}
	}
	
	public static Date getCurrentMonthPointDay(Date theDay, int pointDay){
	    Calendar theDayCalendar = Calendar.getInstance();
	    theDayCalendar.setTime(theDay);
	    theDayCalendar.set(Calendar.DAY_OF_MONTH, pointDay);
	    return theDayCalendar.getTime();
	}
	
	/**
	 * @param begin
	 *     开始日期
	 * @param end
	 *     结束日期
	 * @param pointDay
	 *     分割日期，目前是26
	 *     支持的所有值为1-28,（29,30,31不支持，因为不一定每个月都有）
	 * @return
	 *     返回 1.26-2.25,2.26-3.25
	 */
	public static List<PeriodDatePair> getDateInterval2Pair(Date begin, Date end, int pointDay) {
        List<PeriodDatePair> datePairList = new ArrayList<DateUtil.PeriodDatePair>();
        PeriodDatePair periodDatePair = null;
        // 开始日期不能大于结束日期
        if (begin.equals(end)) {
            periodDatePair = new DateUtil().new PeriodDatePair();
            periodDatePair.setStartDate(begin);
            periodDatePair.setEndDate(end);
            datePairList.add(periodDatePair);
            return datePairList;
        } else if (!begin.before(end)) {
            return null;
        }

        Calendar cal_begin = Calendar.getInstance();
        cal_begin.setTime(begin);

        Calendar cal_end = Calendar.getInstance();
        cal_end.setTime(end);
       
        while (true) {
            boolean isClose = false;
            boolean isSame = false;
            // 1.相邻月
            if(cal_begin.get(Calendar.YEAR) == cal_end.get(Calendar.YEAR)
                    && (cal_begin.get(Calendar.MONTH) + 1) == cal_end.get(Calendar.MONTH)){
                isClose = true;
            }
            if((cal_begin.get(Calendar.YEAR)+1) == cal_end.get(Calendar.YEAR)
                    && cal_begin.get(Calendar.MONTH) == 11
                    && cal_end.get(Calendar.MONTH) == 0){
                isClose = true;
            }
            // 2.相同月
            if(cal_begin.get(Calendar.YEAR) == cal_end.get(Calendar.YEAR)
                    && cal_begin.get(Calendar.MONTH) == cal_end.get(Calendar.MONTH)){
                isSame = true;
            }
            periodDatePair = new DateUtil().new PeriodDatePair();
            Date theDayBeforePointInNextMonth = getTheDayInNextMonth(cal_begin.getTime(), pointDay);//下月25
            Date thePointDayInNextMonth = getNextDate(theDayBeforePointInNextMonth, 1);//下月26
            Date theDayBeforePointInCurrentMonth = getCurrentMonthPointDay(cal_begin.getTime(), pointDay-1);//当月25
            Date thePointDayInCurrentMonth = getNextDate(theDayBeforePointInCurrentMonth, 1);//当月26
            // 1.相邻月
            if (isClose) {
                if((cal_begin.get(Calendar.DAY_OF_MONTH) >= pointDay && cal_end.get(Calendar.DAY_OF_MONTH) >= pointDay)) {//2014-01-28,2014-02-28
                    //2014-01-28,2014-02-25
                    periodDatePair.setStartDate(cal_begin.getTime());
                    periodDatePair.setEndDate(theDayBeforePointInNextMonth);
                    datePairList.add(periodDatePair);
                    
                    //2014-01-26,2014-02-28
                    periodDatePair = new DateUtil().new PeriodDatePair();
                    periodDatePair.setStartDate(thePointDayInNextMonth);
                    periodDatePair.setEndDate(cal_end.getTime());
                    datePairList.add(periodDatePair);
                    break;
                } else if (cal_begin.get(Calendar.DAY_OF_MONTH) < pointDay && cal_end.get(Calendar.DAY_OF_MONTH) < pointDay){// 2014-01-24,2014-02-24
                    // 2014-01-24,2014-01-25
                    periodDatePair.setStartDate(cal_begin.getTime());
                    periodDatePair.setEndDate(theDayBeforePointInCurrentMonth);
                    datePairList.add(periodDatePair);

                    // 2014-01-26,2014-02-24
                    periodDatePair = new DateUtil().new PeriodDatePair();
                    periodDatePair.setStartDate(thePointDayInCurrentMonth);
                    periodDatePair.setEndDate(cal_end.getTime());
                    datePairList.add(periodDatePair);
                    break;
                } else if (cal_begin.get(Calendar.DAY_OF_MONTH) >= pointDay && cal_end.get(Calendar.DAY_OF_MONTH) < pointDay){// 2014-01-28,2014-02-24
                    // 2014-01-28,2014-02-24
                    periodDatePair.setStartDate(cal_begin.getTime());
                    periodDatePair.setEndDate(cal_end.getTime());
                    datePairList.add(periodDatePair);
                    break;
                }else {// 2014-01-24,2014-02-28
                    // 2014-01-24,2014-01-25
                    periodDatePair.setStartDate(cal_begin.getTime());
                    periodDatePair.setEndDate(theDayBeforePointInCurrentMonth);
                    datePairList.add(periodDatePair);
                    
                    // 2014-01-26,2014-02-25
                    periodDatePair = new DateUtil().new PeriodDatePair();
                    periodDatePair.setStartDate(thePointDayInCurrentMonth);
                    periodDatePair.setEndDate(theDayBeforePointInNextMonth);
                    datePairList.add(periodDatePair);
                    
                    // 2014-02-25,2014-02-28
                    periodDatePair = new DateUtil().new PeriodDatePair();
                    periodDatePair.setStartDate(thePointDayInNextMonth);
                    periodDatePair.setEndDate(cal_end.getTime());
                    datePairList.add(periodDatePair);
                    break;
                }
            }
            // 2.相同月
            if (isSame) {
                if((cal_begin.get(Calendar.DAY_OF_MONTH) >= pointDay && cal_end.get(Calendar.DAY_OF_MONTH) >= pointDay)
                      || (cal_begin.get(Calendar.DAY_OF_MONTH) < pointDay && cal_end.get(Calendar.DAY_OF_MONTH) < pointDay)) {
                    // 1.1 start和end都>=26,2014-01-27,2014-01-29
                    // 1.2 start和end都<26,2014-01-20,2014-01-24
                    periodDatePair.setStartDate(cal_begin.getTime());
                    periodDatePair.setEndDate(cal_end.getTime());
                    datePairList.add(periodDatePair);
                    break;
                } else {
                    // 1.3 start<26 end >=26,2014-01-20,2014-01-29
                    periodDatePair.setStartDate(cal_begin.getTime());
                    periodDatePair.setEndDate(theDayBeforePointInCurrentMonth);
                    datePairList.add(periodDatePair);
                    
                    periodDatePair = new DateUtil().new PeriodDatePair();
                    periodDatePair.setStartDate(thePointDayInCurrentMonth);
                    periodDatePair.setEndDate(cal_end.getTime());
                    datePairList.add(periodDatePair);
                    break;
                }
            }
            
            // 3.非相邻月
            if(cal_begin.get(Calendar.DAY_OF_MONTH) >= pointDay){
                periodDatePair.setStartDate(cal_begin.getTime());
                periodDatePair.setEndDate(theDayBeforePointInNextMonth);
                datePairList.add(periodDatePair);
                cal_begin.setTime(getNextDate(theDayBeforePointInNextMonth, 1));
            } else {
                periodDatePair.setStartDate(cal_begin.getTime());
                periodDatePair.setEndDate(theDayBeforePointInCurrentMonth);
                datePairList.add(periodDatePair);
                cal_begin.setTime(getCurrentMonthPointDay(cal_begin.getTime(), pointDay));
            }
            
        }

        return datePairList;
    }
	
	public static List<PeriodDatePair> getDateInterval2Pair(String begin, String end, int pointDay){
	    Date startDate = null;
	    Date endDate = null;
	    try{
	        startDate = DateUtil.parse(begin, DateTimeType.Day);
	        endDate = DateUtil.parse(end, DateTimeType.Day);
	    }catch(ParseException pe){
	        //TODO:
	        System.out.println(pe.toString());
	        return Collections.emptyList();
	    }
	    return getDateInterval2Pair(startDate, endDate, pointDay);
	}
	
}
