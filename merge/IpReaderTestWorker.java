package com.youdao.analysis.ipparser.merge;

import java.util.List;

public abstract class IpReaderTestWorker {
    public IpSecAttrsStream stream;

    public IpReaderTestWorker(IpSecAttrsStream stream){
        this.stream = stream;
    }

    public abstract void test();
}
