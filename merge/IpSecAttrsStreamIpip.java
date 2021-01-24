package com.youdao.analysis.ipparser.merge;

import com.youdao.analysis.ipparser.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class IpSecAttrsStreamIpip implements  IpSecAttrsStream{
    String name;
    //List<String> lines = new ArrayList<>();
    public String line;
    int curPos;
    long settedStartIp = -1L;
    BufferedReader br;

    public IpSecAttrsStreamIpip(String name, String fileName) {
        this.name = name;
        try {
            File file = new File(fileName);
            br = null;
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            line = null;
            curPos = 0;
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
        String[] cols = line.split("\t");
        String[] out = new String[4];
        out[0] = !cols[2].equals("*")?cols[2]:"0";
        out[1] = !cols[3].equals("*")?cols[3]:"0";
        out[2] = !cols[4].equals("*")?cols[4]:"0";
        out[3] = !cols[6].equals("*")?cols[6]:"0";
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
        String[] cols = line.split("\t");
        return Utils.intToLongKeepingBinary(Utils.ipToInt(cols[0]));
    }

    @Override
    public long getEndIp() {
        String[] cols = line.split("\t");
        return Utils.intToLongKeepingBinary(Utils.ipToInt(cols[1]));
    }

    @Override
    public boolean next() {
        curPos ++;
        settedStartIp = -1;
        try{
            line = br.readLine();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
        return line==null?false:true;
    }

    @Override
    public void setStartIp(long startIp) {
        settedStartIp = startIp;
    }
}
