package com.youdao.analysis.ipparser.merge;

import java.util.Map;

public class AttrSetMerger implements MergerOutputter {
    private IpRecordOutputter outputter;
    public AttrSetMerger(IpRecordOutputter outputter) {
        this.outputter = outputter;
    }
    @Override
    public void output(long startIp, long endIp, Map<String, String[]> attrMap) {
        // 这里是合并属性集合的逻辑
        String[] ipToRegionAttr = attrMap.get(MergerConsts.IP2REGION);
        String[] ipipAttr = attrMap.get(MergerConsts.IPIP);
        String[] ipLocationAttr = attrMap.get(MergerConsts.IPLOCATION);
        String[] ipStat = attrMap.get(MergerConsts.IPSTAT);

        String[] attrs = new String[4];
        for(int i = 0; i < 4; i++){
            if(ipToRegionAttr != null && !ipToRegionAttr[i].equals(MergerConsts.EMPTY_FIELD)){
                attrs[i] = ipToRegionAttr[i];
            }
            else if(ipipAttr != null && !ipipAttr[i].equals(MergerConsts.EMPTY_FIELD)){
                attrs[i]= ipipAttr[i];
            }
            else if(ipLocationAttr != null && !ipLocationAttr[i].equals(MergerConsts.EMPTY_FIELD)){
                attrs[i]= ipLocationAttr[i];
            }
            else{
                attrs[i] = ipStat==null?MergerConsts.EMPTY_FIELD:ipStat[i];
            }
        }
        outputter.output(startIp, endIp, attrs);
    }

    @Override
    public void finish() {
        this.outputter.finish();
    }
}
