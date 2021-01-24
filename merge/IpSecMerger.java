package com.youdao.analysis.ipparser.merge;

public class IpSecMerger implements IpRecordOutputter{
    class E{
        long startIp;
        long endIp;
        String[] attrs;
    }
    E preRecord = null;
    IpRecordOutputter myOutputter;
    public IpSecMerger(IpRecordOutputter myOutputer) {
        this.myOutputter = myOutputer;
    }

    //地址段合并
    @Override
    public void output(long startIp, long endIp, String[] attrs) {
        if(preRecord == null){
            preRecord = new E();
            preRecord.startIp = startIp;
            preRecord.endIp = endIp;
            preRecord.attrs = attrs;
        }
        else{
            if(startIp - preRecord.endIp == 1 && attrs[0].equals(preRecord.attrs[0]) && attrs[1].equals(preRecord.attrs[1]) && attrs[2].equals(preRecord.attrs[2]) && attrs[3].equals(preRecord.attrs[3])){
                preRecord.endIp = endIp;
            }
            else{
                myOutputter.output(preRecord.startIp, preRecord.endIp, preRecord.attrs);
                preRecord.startIp = startIp;
                preRecord.endIp = endIp;
                preRecord.attrs = attrs;
            }
        }
    }

    @Override
    public void finish() {
        if(preRecord != null){
            myOutputter.output(preRecord.startIp,preRecord.endIp ,preRecord.attrs);
        }
        myOutputter.finish();
    }
}
