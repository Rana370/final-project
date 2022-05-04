package com.company.M15FinalProjectAlomairRana;

public class SpaceResponse {
    public String timestamp;
    public String message;
    public IssPosition iss_position;


    public void print() {
        System.out.println("Time :\t" + timestamp);
        System.out.println("Latitude :\t" + iss_position.latitude);
        System.out.println("Longitude :\t" + iss_position.longitude);
    }

}
