package com.youdao.analysis.ipparser.ip2region;


import java.lang.reflect.Method;

public class IpToRegion {
    private  DbSearcher searcher;
    private Method method;
    public IpToRegion(String DbFilename){
        try {
            DbConfig config = new DbConfig();
            searcher = new DbSearcher(config, DbFilename);
        }
        catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    public IpToRegion(byte[] binaryData){
        try {
            DbConfig config = new DbConfig();
            searcher = new DbSearcher(config, binaryData);
        }
        catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    public String[] locate(String ip){
        try {
            ip = ip.trim();
            DataBlock dataBlock = null;
            dataBlock = searcher.memorySearch(ip);
            String s= dataBlock.getRegion();
            String[] cols = s.split("[|]");
            String[] ret = new String[4];
            ret[0] = normalize(cols[0]);
            ret[1] = normalize(cols[2]);
            ret[2] = normalize(cols[3]);
            ret[3] = normalize(cols[4]);
            return ret;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String normalize(String s) {
        if(s.equals("0")) {
            return "不能识别";
        }else{
            return s;
        }
    }

}
