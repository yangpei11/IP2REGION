package com.youdao.analysis.ipparser;

import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
     * 判断是否为ipv4地址
     * @param ipv4Addr
     * @return
     */
//    private static String lower = "(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])"; // 0-255的数字
    private static String lower = "(000|0?0?\\d|0?[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])";
    private static String regex = lower + "\\." + lower + "\\." + lower + "\\." + lower;
    private static Pattern pattern = Pattern.compile(regex);

    private static Matcher matchIPv4Address(String ipv4Addr) {
        Matcher matcher = pattern.matcher(ipv4Addr);
        return matcher;
    }

    public static int ipToInt(String ipv4Addr) {
        // 判断是否是ip格式的
        Matcher m = matchIPv4Address(ipv4Addr);

        if (!m.matches())
            throw new RuntimeException("Invalid ip address: " + ipv4Addr);


        // 取出每个数
        int v1 = Integer.parseInt(m.group(1));
        int v2 = Integer.parseInt(m.group(2));
        int v3 = Integer.parseInt(m.group(3));
        int v4 = Integer.parseInt(m.group(4));

        // 解析
        int result = v4 | v3 << 8 | v2 << 16 | v1 << 24;
        return result & 0xFFFFFFFF;
    }

    public static String intToIp(int ipInput){
        long ip = ipInput & 0xFFFFFFFFL;
        String str1 = "";
        StringBuilder sb = new StringBuilder(str1);
        int i = 0;
        while(i < 4){
            if(i != 0)
                sb.insert(0, Long.valueOf(ip%256) + ".");
            else {
                sb.insert(0, Long.valueOf(ip % 256));
            }

            i ++;
            ip = ip/256;

        }
        return sb.toString();
    }

    public static String longToIp(long ip){
        String str1 = "";
        StringBuilder sb = new StringBuilder(str1);
        int i = 0;
        while(i<4){
            if(i != 0)
                sb.insert(0, Long.valueOf(ip%256) + ".");
            else {
                sb.insert(0, Long.valueOf(ip % 256));
            }
            i ++;
            ip = ip/256;

        }
        return sb.toString();
    }

   public static int ipIntFromBytes(ByteBuffer ipData, int start)
    {
        // 必须做“按位操作”才能保持原样
        int b1 = ipData.get(start) & 0xFF;
        int b2 = ipData.get(start+1) & 0xFF;
        int b3 = ipData.get(start+2) & 0xFF;
        int b4 = ipData.get(start+3) & 0xFF;
        return  b1 << 8 * 3
                | b2 << 8 * 2
                | b3 << 8
                | b4;
    }

    public static long intToLongKeepingBinary(int in) {
        return Integer.toUnsignedLong(in);
    }

    public static int twoBytesToIntKeepingBinary(byte high, byte low)
    {
        return Byte.toUnsignedInt(high) << 8 | Byte.toUnsignedInt(low);
    }

    public static int threeBytesToIntKeepingBinary(byte high, byte mid, byte low)
    {
        return Byte.toUnsignedInt(high) << 16 | Byte.toUnsignedInt(mid) << 8 | Byte.toUnsignedInt(low);
    }
}