package com.youdao.analysis.ipparser.merge;

public interface IpRecordOutputter {
    void output(long startIp, long endIp, String[] attrs);
    void finish();
}
