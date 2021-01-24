package com.youdao.analysis.ipparser.merge;

import java.util.ArrayList;

public class MergerMain {

    public static void main(String[] argv){
        //-ipip xxx
        //-iplocation xxx
        //-ipstat xxx
        //-citymap xxx
        //-ip2region xxx
        //-output xxx
        String ipipPath = "", iplocationPath = "", ipstatPath ="", citymapPath ="", ip2regionPath="";
        String outputPath = "";
        for(int i = 0; i < argv.length; i++){
            if(argv[i].equals("-ipip") ){
                ipipPath = argv[++i];
            }
            else if(argv[i].equals("-iplocation") ){
                iplocationPath = argv[++i];
            }
            else if(argv[i].equals("-ipstat") ){
                ipstatPath = argv[++i];
            }
            else if(argv[i].equals("-citymap") ){
                citymapPath = argv[++i];
            }
            else if(argv[i].equals("-ip2region") ){
                ip2regionPath = argv[++i];
            }
            else if(argv[i].equals("-output")){
                outputPath = argv[++i];
            }
        }

        //读取数据
        IpSecAttrsStream ipSecAttrsStreamIpip = new NameNormalizer(new IpSecAttrsStreamIpip(MergerConsts.IPIP, ipipPath));
        IpSecAttrsStream ipSecAttrsStreamIplocation = new NameNormalizer(new IpSecAttrsStreamIplocation(MergerConsts.IPLOCATION, iplocationPath));
        IpSecAttrsStream ipSecAttrsStreamIpstat = new NameNormalizer(new IpSecAttrsStreamIpstat(MergerConsts.IPSTAT, ipstatPath, citymapPath));
        IpSecAttrsStream ipSecAttrsStreamIp2region = new NameNormalizer(new IpSecAttrsStreamIp2region(MergerConsts.IP2REGION, ip2regionPath));

        ArrayList<IpSecAttrsStream> inputStreamList = new ArrayList<>();
        inputStreamList.add(ipSecAttrsStreamIp2region);
        inputStreamList.add(ipSecAttrsStreamIpip);
        inputStreamList.add(ipSecAttrsStreamIplocation);
        inputStreamList.add(ipSecAttrsStreamIpstat);

        Ip2regionTextDbWriter ip2regionTextDbWriter = new Ip2regionTextDbWriter(outputPath);

        IpSecMerger ipSecMerger = new IpSecMerger(ip2regionTextDbWriter);
        AttrSetMerger attrSetMerger = new AttrSetMerger(ipSecMerger);
        Merger merger = new Merger(inputStreamList, attrSetMerger);
        merger.run();
    }
}
