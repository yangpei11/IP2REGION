package com.youdao.analysis.ipparser.ip2region;

public class RegionLocation {
    private String country;
    private String province;
    private String city;
    private String operator;

    public RegionLocation(String country, String province, String city, String operator) {
        this.province = province;
        this.city = city;
        this.country = country;
        this.operator = operator;
    }

    public String getCountry() {
        return this.country;
    }

    public String getProvince() {
        return this.province;
    }

    public String getCity(){
        return this.city;
    }

    public String getOperator(){
        return this.operator;
    }

}
