package com.umkc.travelplanner.eat;

public class Venue {

    private String mName;
    private String mContextLine;
    private String mFormattedaAddress;


    public Venue() {

    }

    public Venue(String name, String contextLine, String formattedaAddress) {
        mName = name;
        mContextLine = contextLine;
        mFormattedaAddress = formattedaAddress;
    }

    public void setContextLine(String contextLine) {
        mContextLine = contextLine;
    }

    public String getContextLine() {
        return mContextLine;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getFormattedaAddress() {
        return mFormattedaAddress;
    }

    public void setFormattedaAddress(String formattedaAddress) {
        mFormattedaAddress = formattedaAddress;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "mName='" + mName + '\'' +
                ", mContextLine='" + mContextLine + '\'' +
                ", mFormattedaAddress='" + mFormattedaAddress + '\'' +
                '}';
    }
}
