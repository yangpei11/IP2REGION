package com.youdao.analysis.ipparser.merge;

public interface IpSecAttrsStream {
    // 当前Stream的名称
    String getName();

    // 当前记录的属性
    String[] getAttr();

    // 小顶堆依据startIp做排序
    long getValue();

    // 当前记录的起始ip
    long getStartIp();

    // 当前记录的结束ip
    long getEndIp();

    // 下一条记录；如果当前已经是最后一条记录，返回false
    boolean next();

    void setStartIp(long startIp);
}
