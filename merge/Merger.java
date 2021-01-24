package com.youdao.analysis.ipparser.merge;

import java.util.*;

public class Merger {
    MergerOutputter outputter;
    List<IpSecAttrsStream> streamList;
    public Merger(List<IpSecAttrsStream> inputStreamList, MergerOutputter outputter) {
        this.outputter = outputter;
        streamList = inputStreamList;
    }
    /*
    private long findMinEndIp(List<IpSecAttrsStream> curIpSecList){
        long minVal = curIpSecList.get(0).getEndIp();
        for(int i = 1; i < curIpSecList.size(); i++){
            if(minVal > curIpSecList.get(i).getEndIp()){
                minVal = curIpSecList.get(i).getEndIp();
            }
        }

        return minVal;
    } */

    private void cutAndPutToQueue(List<IpSecAttrsStream> curIpSecList, long endIp, PriorityQueue q){
        for(IpSecAttrsStream st:curIpSecList){
            if(st.getEndIp() > endIp){
                st.setStartIp(endIp+1);
                q.add(st);
            }
            else{
                if(st.next()){
                    q.add(st);
                }
            }
        }
    }

    public void run() {
        PriorityQueue<IpSecAttrsStream> q = new PriorityQueue<IpSecAttrsStream>(new Comparator<IpSecAttrsStream>() {
            @Override
            public int compare(IpSecAttrsStream o1, IpSecAttrsStream o2) {
                if( o1.getStartIp() != o2.getStartIp()){
                    return Long.compare(o1.getStartIp(), o2.getStartIp());
                }
                else {
                    return Long.compare(o1.getEndIp(), o2.getEndIp());
                }
            }
        });

        for(IpSecAttrsStream stream:streamList) {
            if(stream.next()) {
                q.add(stream);
            }
        }


        List<IpSecAttrsStream> curIpSecList = new ArrayList();

        // 当前拆分段要依据的多个“属性集合”
        Map<String, String[]> curIpSecAttrMap = new HashMap<>();

        // 本拆分段的起始ip
        long startIp = -1;

        while( true ){
            IpSecAttrsStream next = q.peek();
            if( (next != null && next.getStartIp() == startIp) ||(startIp==-1) ){
                next = q.poll();
                curIpSecList.add(next);
                curIpSecAttrMap.put(next.getName(), next.getAttr());
                startIp = next.getStartIp();
            }
            else{
                long nextStartIp = (next != null) ? next.getStartIp():Long.MAX_VALUE;
                long minEndIp =curIpSecList.get(0).getEndIp();
                if(minEndIp < nextStartIp){
                    outputter.output(startIp, minEndIp, curIpSecAttrMap);
                    cutAndPutToQueue( curIpSecList, minEndIp, q);
                }
                else {
                    outputter.output(startIp, nextStartIp - 1, curIpSecAttrMap);
                    cutAndPutToQueue( curIpSecList, nextStartIp - 1, q);
                }
                startIp = -1;
                curIpSecAttrMap.clear();
                curIpSecList.clear();
            }

            //如果为空
            if(q.isEmpty()){
                if(curIpSecList.size() == 0){
                    break;
                }
                outputter.output(curIpSecList.get(0).getStartIp(), curIpSecList.get(0).getEndIp(), curIpSecAttrMap);
                cutAndPutToQueue(curIpSecList, curIpSecList.get(0).getEndIp(), q);
                startIp = -1;
                curIpSecAttrMap.clear();
                curIpSecList.clear();
                if(q.isEmpty()){
                    break;
                }
            }
        }

        outputter.finish();

    }
}
