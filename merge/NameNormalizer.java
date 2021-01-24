package com.youdao.analysis.ipparser.merge;

public class NameNormalizer implements IpSecAttrsStream{
    IpSecAttrsStream stream;
    public NameNormalizer(IpSecAttrsStream stream) {
        this.stream = stream;
    }
    @Override
    public String getName() {
        return stream.getName();
    }

    @Override
    public String[] getAttr() {
        //
        String[] result = stream.getAttr();
        String[] out = new String[4];
        out[0] = result[0];
        out[1] = result[1];
        out[2] = result[2];
        out[3] = result[3];
        if( out[1].endsWith("省") ){
            out[1]  = out[1].substring(0, out[1].length()-1);
        }
        if(out[2].endsWith("市")){
            out[2]  = out[2].substring(0, out[2].length()-1);
        }

        return out;
    }

    @Override
    public long getValue() {
        return stream.getValue();
    }

    @Override
    public long getStartIp() {
        return stream.getStartIp();
    }

    @Override
    public long getEndIp() {
        return stream.getEndIp();
    }

    @Override
    public boolean next() {
        return stream.next();
    }

    @Override
    public void setStartIp(long startIp) {
        stream.setStartIp(startIp);
    }
}
