package com.youdao.analysis.ipparser.merge;

import com.youdao.analysis.ipparser.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class Ip2regionTextDbWriter implements IpRecordOutputter {
    FileOutputStream fos;
    OutputStreamWriter osw;
    BufferedWriter bw;
    public Ip2regionTextDbWriter(String outputFilePath) {
        //open file
        try {
            fos = new FileOutputStream(new File(outputFilePath));
            osw = new OutputStreamWriter(fos, "UTF-8");
            bw = new BufferedWriter(osw);
        }
        catch (Exception e){
            throw new RuntimeException();
        }

    }

    @Override
    public void output(long startIp, long endIp, String[] attrs) {
        //write to file
        try {
            if(attrs[0].equals("0") && attrs[1].equals("0") && attrs[2].equals("0") && attrs[3].equals("0")){

            }else{
                bw.write(Utils.longToIp(startIp) + "|" + Utils.longToIp(endIp) + "|" + attrs[0]+ "|0|"  + attrs[1] + "|" + attrs[2] + "|" + attrs[3] + "\n");
            }

        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    public void finish() {
        //close file
        try {
            bw.close();
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }
}
