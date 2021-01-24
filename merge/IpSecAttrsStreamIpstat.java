package com.youdao.analysis.ipparser.merge;

import com.youdao.analysis.ipparser.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;


public class IpSecAttrsStreamIpstat  implements IpSecAttrsStream{
    public static  class IpstatRecord {
        long ip;
        String province;
        String city;
    }
    String name;
    //List<String> lines = new ArrayList<>();
    int curPos;
    long settedStartIp = -1L;
    BufferedReader br;
    HashMap<Integer, String> cityMap = new HashMap<>();
    ArrayList<IpstatRecord> records = new ArrayList<IpstatRecord>();

    public IpSecAttrsStreamIpstat(String name, String fileName, String cityMapPath) {
        this.name = name;
        try {
            File file = new File(fileName);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            File file1 = new File(cityMapPath);
            BufferedReader cityMapReader = new BufferedReader(new InputStreamReader(new FileInputStream(file1), "UTF-8"));
            String cityLine;
            while ((cityLine = cityMapReader.readLine()) != null) {
                String[] attrs = cityLine.trim().split(",");
                if(cityLine.startsWith("#")) {
                    continue;
                }
                cityMap.put(Integer.parseInt(attrs[0]), attrs[1]);
            }
            String record;
            while( (record = br.readLine()) != null){
                String[] attrs = record.trim().split("\\(")[1].split("\\)")[0].split(",");
                IpstatRecord ir = new IpstatRecord();
                try{
                    ir.ip = Utils.intToLongKeepingBinary(Utils.ipToInt(attrs[0]) );
                }
                catch(Exception e){
                    continue;
                }
                //ir.ip = Utils.intToLongKeepingBinary(Utils.ipToInt(attrs[0]) );
                ir.province = cityMap.get(Integer.parseInt(attrs[1]));
                ir.city = cityMap.get(Integer.parseInt(attrs[2]));
                records.add(ir);
            }

            records.sort(new Comparator<IpstatRecord>() {
                @Override
                public int compare(IpstatRecord o1, IpstatRecord o2) {
                    return Long.compare(o1.ip, o2.ip);
                }
            });

            //line = br.readLine()
            curPos = -1;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getAttr() {
        String[] out = new String[4];
        out[0] = "中国";
        out[1] = records.get(curPos).province;
        out[2] = records.get(curPos).city;
        out[3] = "0";
        return out;
    }

    @Override
    public long getValue() {
        return getStartIp();
    }

    @Override
    public long getStartIp() {
        if(settedStartIp != -1) {
            return settedStartIp;
        }
        return records.get(curPos).ip;
    }

    @Override
    public long getEndIp() {
        return records.get(curPos).ip;
    }

    @Override
    public boolean next() {
        curPos ++;
        settedStartIp = -1;
        return curPos < records.size();
    }

    @Override
    public void setStartIp(long startIp) {
        throw new RuntimeException("error!");
    }
}
