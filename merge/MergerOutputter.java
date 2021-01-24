package com.youdao.analysis.ipparser.merge;


import java.util.Map;

public interface MergerOutputter {
    void output(long startIp, long endIp, Map<String, String[]> attrMap);
    void finish();
}
