package com.xyzsoft.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.xyzsoft.common.DateUtil.PeriodDatePair;


@RunWith(Parameterized.class)
public class DateUtilTest extends TestCase {
    
    private String startDate;
    private String endDate;
    
    public DateUtilTest(String startDate, String endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Parameters
    public static Collection prepareData()
    {
        // 测试数据
        Object[][] objects = { 
                // close month
                { "2014-01-28", "2014-02-28"}, { "2014-01-24", "2014-02-24"}, { "2014-01-28", "2014-02-24"}, { "2014-01-24", "2014-02-28"},
                // same month
                { "2014-01-27", "2014-02-29"}, { "2014-01-20", "2014-01-24"}, { "2014-01-20", "2014-01-29"},
                // other
                { "2014-01-20", "2014-05-29"}, { "2013-01-20", "2014-03-29"}
                };
        return Arrays.asList(objects);// 将数组转换成集合返回
    }
    
    @Before
    public void beforeMthod(){
        System.out.println("before");
    }
  
    @After
    public void afterMethod(){
        System.out.println("after");
    }
    
    @Test
    public void testGetDateInterval2Pair()
    {
        List<PeriodDatePair> list = DateUtil.getDateInterval2Pair(this.startDate, this.endDate, 26); 
        System.out.println(list);
        System.out.println(list.size());
    }
}
