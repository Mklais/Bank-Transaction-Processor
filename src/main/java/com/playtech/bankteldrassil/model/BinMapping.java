package com.playtech.bankteldrassil.model;

import com.playtech.bankteldrassil.enums.BinType;

public class BinMapping {

    private String name;
    private long rangeFrom;
    private long rangeTo;
    private BinType type;
    private String country;

    public void setName(String name) {
        this.name = name;
    }

    public long getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(long rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public long getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(long rangeTo) {
        this.rangeTo = rangeTo;
    }

    public BinType getType() {
        return type;
    }

    public void setType(BinType type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "BinMapping{" +
                "name='" + name + '\'' +
                ", rangeFrom=" + rangeFrom +
                ", rangeTo=" + rangeTo +
                ", type=" + type +
                ", country='" + country + '\'' +
                '}';
    }
}
