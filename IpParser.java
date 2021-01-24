package com.youdao.analysis.ipparser;

import com.youdao.analysis.ipparser.ip2region.IpToRegion;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IpParser
{
    public static IpParser createFromClasspath() {
        try {
            return new IpParser(getResourceAsStream("ip2region.db"),
                    readLineAsIntFromResource("ip2region.db.length"));
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


    public static IpParser createFromFile(String fileName)
    {
        try {
            File f = new File(fileName);
            FileInputStream fis = new FileInputStream(f);
            int length = (int)f.length();
            return new IpParser(fis, length);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    IpToRegion ipToRegion = null;

    protected IpParser(
            InputStream dataInputStream,
            int dataLength)
            throws IOException
    {

        ipToRegion = new IpToRegion(copyToByteArrat(dataInputStream, dataLength));
    }

    private static byte[] copyToByteArrat(InputStream in, int length) throws IOException
    {
        DataInputStream dis = new DataInputStream(in);
        byte[] out = new byte[length];
        dis.readFully(out);
        return out;
    }

    private static int readLineAsIntFromResource(String filename)
    {
        try {
            InputStream stream = IpParser.class.getResourceAsStream(filename);
            BufferedReader reader = new BufferedReader( new InputStreamReader(stream));
            int r = Integer.parseInt(reader.readLine());
            reader.close();
            return r;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    // output 数组有4个值：countryNo, provinceNo, cityNo, opNo
//    public boolean parseIp(int inputIp, int[] output) {
//        boolean success = parseDomesticIp(inputIp, output);
//        if(!success){
//            success = parseForeignIp(inputIp, output);
//        }
//        if(!success) {
//            output[0] = 0;
//            output[1] = 0;
//            output[2] = 0;
//            output[3] = 0;
//        }
//        return success;
//    }

//    public boolean parseIp(String inputIp, String[] output) {
//        int[] out = new int[4];
//        boolean b = false;
//        try {
//            b = parseIp(ipToInt(inputIp), out);
//            output[0] = countryDictList[out[0]];
//            output[1] = provinceDictList[out[1]];
//            output[2] = cityDictList[out[2]];
//            output[3] = opDictList[out[3]];
//        }catch(Exception e){
//            output[0] = "ip格式错误";
//            output[1] = "ip格式错误";
//            output[2] = "ip格式错误";
//            output[3] = "ip格式错误";
//        }
//        return b;
//    }

    public boolean parseIp(String inputIp, String[] output) {
        try {
            String[] out = ipToRegion.locate(inputIp);
            output[0] = out[0];
            output[1] = out[1];
            output[2] = out[2];
            output[3] = out[3];
            return true;
        }catch(Exception e){
            output[0] = "ip格式错误";
            output[1] = "ip格式错误";
            output[2] = "ip格式错误";
            output[3] = "ip格式错误";
            return false;
        }
    }

    public static int ipToInt(String ipv4Addr) {
        return Utils.ipToInt(ipv4Addr);
    }

    private static InputStream getResourceAsStream(String filename)
    {
        return IpParser.class.getResourceAsStream(filename);
    }

    // 方便测试jar包的入口
    public static void main(String[] args)
            throws IOException
    {
        if(args.length == 0) {
            System.err.println("usage: <input file>");
            return;
        }

        File file = new File(args[0]);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        IpParser ipParser = IpParser.createFromClasspath();
//        IpParser ipParser = IpParser.createFromFile("data/merger/output/ip2region.db");
        String[] output = new String[4];
        while(true){
            String line = br.readLine();
            if(line == null){
                break;
            }
            ipParser.parseIp(line, output);
            System.out.println("country="+output[0] + "\t" + "province=" + output[1] + "\tcity=" + output[2] + "\top=" + output[3]);
        }
    }


}
