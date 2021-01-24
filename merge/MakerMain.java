package com.youdao.analysis.ipparser.merge;

import com.youdao.analysis.ipparser.ip2region.DbConfig;
import com.youdao.analysis.ipparser.ip2region.DbMaker;
import com.youdao.analysis.ipparser.ip2region.DbMakerConfigException;

import java.io.*;

public class MakerMain {
    public static void main(String[] argv) throws Exception{
        // data/merger/input/xxxx
        String[] args = new String[]{"-ipip", "data/merger/input/ipip.txt",
                "-iplocation", "data/merger/input/ip_location.txt",
                "-ipstat", "data/merger/input/ipstat.txt",
                "-ip2region", "data/merger/input/ip2region.txt",
                "-citymap", "data/merger/input/ipip-citymap.csv",
                "-output", "data/merger/output/ip.merge.txt"
        };
        MergerMain.main(args);

        DbConfig config = new DbConfig();
        DbMaker dbMaker = new DbMaker(
                config,
                "data/merger/output/ip.merge.txt",
                "data/merger/input/global_region.csv"
        );

        String outputFilePath = "data/merger/output/ip2region.db";
        dbMaker.make(outputFilePath);

        writeLongAsText(new File(outputFilePath).length(), new File(outputFilePath+".length"));
    }

    private static void writeLongAsText(long longVal, File file)
    {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(file));
            pw.print(longVal);
            pw.close();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

//-ipip test-data/data/ipip.txt -iplocation test-data/data/ip_location.txt -ipstat test-data/data/ipstat.txt -ip2region test-data/data/ip2region.txt -citymap test-data/data/ipip-citymap.csv -output test-data/data/ouput.txt
