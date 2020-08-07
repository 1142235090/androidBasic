package com.smartonet.project.core.util;


import com.smartonet.project.core.bean.Date;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * Creat by hanzhao
 * on 2019/9/19
 **/
public class CommonUtil {
    /**
     * 判断该字符串是否为空
     * @param str
     * @return
     */
    public static Boolean stringIsEmpty(String str){
        if(str==null){
            return true;
        }
        if(str.equals("")){
            return true;
        }
        return false;
    }
    /**
     * 判断该List是否为空
     * @param list
     * @return
     */
    public static Boolean listIsEmpty(List list){
        if(list==null){
            return true;
        }
        if(list.size()<1){
            return true;
        }
        return false;
    }

    /**
     * formatDouble 例如：100 保留两位小数
     * @param format
     * @param number
     * @return
     */
    public static String formatDouble(int format, Double number){
        int fix = (format+"").length()-1;
        Double d = Double.valueOf(number) ;
        String fixStr = "" ;
        while(fix > 0) {
            fixStr = fixStr + "0" ;
            fix-- ;
        }
        fixStr = fixStr.length()>0 ? "#0."+fixStr : "##" ;
        DecimalFormat df=new DecimalFormat(fixStr);
        return df.format(d) ;
    };

    /**
     * 将传入的ISO日期类型数据转换为字符串类型东八区时间
     * @return
     */
    public static String isoDateGetMonth(Long time){
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return sdf.format(time);
    }

    /**
     * 将传入的ISO日期类型数据转换为字符串类型东八区时间
     * @return
     */
    public static String isoDateTOString(Long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return sdf.format(time);
    }

    /**
     * 传入日期格式和日期字符串，返回日期类型数据
     * @param sdf
     */
    public static Date dateFormat(String sdf, String dataStr){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mmm:ss");
        try {
            return new Date(df.parse(dataStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 比较第一个字符串时间是否比第二个大
     * @param format
     * @param oneDateStr
     * @param twoDateStr
     * @return
     */
    public static Boolean dateCompare(String format, String oneDateStr, String twoDateStr){
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date oneDate=new Date( df.parse(oneDateStr));
            Date twoDate=new Date( df.parse(twoDateStr));
            if(oneDate.getTime()>twoDate.getTime()){
                return true;
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    };

    // gidMD5加密取后四位转成ascII码对10取模
    public static String getVerification(String string){
        String returnString="";
        String md5 = MD5Util.MD5(string);
        String subString = md5.substring(md5.length()-4);
        char[] chars = subString.toLowerCase().toCharArray();
        for(int i=0;i<4;i++){
            returnString=returnString+(chars[i]%10);
        }
        return returnString;
    }

    /**
     * format 例如：100 保留两位小数
     * @param format
     * @param number
     * @return
     */
    public static String doubleFformat(int format,Double number){
        int fix = (format+"").length()-1;
        Double d = Double.valueOf(number) ;
        String fixStr = "" ;
        while(fix > 0) {
            fixStr = fixStr + "0" ;
            fix-- ;
        }
        fixStr = fixStr.length()>0 ? "#0."+fixStr : "##" ;
        DecimalFormat df=new DecimalFormat(fixStr);
        return df.format(d) ;
    };
}
