package com.youdao.analysis.ipparser.ip2region.test;


import com.youdao.analysis.ipparser.ip2region.DbConfig;
import com.youdao.analysis.ipparser.ip2region.DbMaker;
import com.youdao.analysis.ipparser.ip2region.DbMakerConfigException;

import java.io.IOException;

public class TestMaker {
    public static void main(String[] argv)
    {
        try {
            DbConfig config = new DbConfig();
            DbMaker dbMaker = new DbMaker(
                    config,
                    "data/ip.merge.txt",
                    "data/global_region.csv"
            );

            dbMaker.make("data/pre.db");
        } catch (DbMakerConfigException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
