package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import response.SpaceResponse;
import response.WeatherResponse;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Space {

    public static SpaceResponse get_iss_cords(){

        String API_URL = "http://api.open-notify.org/iss-now.json?callback=?";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(API_URL)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String str = response.body();
            str = str.replace("?", "");
            str = str.replace(")", "");
            str = str.replace("(", "");

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(str);
            SpaceResponse res = SpaceResponse.parseSpaceResponse(obj);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static void get_iss_location(){
        SpaceResponse res = get_iss_cords();

        if(res != null){
            System.out.println("International Space Station Current Location");
            res.print();
            String name = get_iss_city_name(res.getlat(), res.getlon());
            if(name.equals("-1")){
                System.out.println("ISS is not in a Country right now");
            }
            else
                System.out.println("\nCity Name at ISS Location:" + name);
        }
        else{
            System.out.println("Could not get the Location of ISS");
        }
    }
    public static void get_iss_weather(){
        SpaceResponse res = get_iss_cords();

        if(res != null){
            System.out.println("International Space Station Current Location");
            res.print();

            WeatherResponse we_res = Weather.getWeather(res.getlat(), res.getlon());
            String name = get_iss_city_name(res.getlat(), res.getlon());

            if(!name.equals("-1"))
                System.out.println("City Name at ISS Location:" + name);
            else
                System.out.println("ISS is not in a country right now");

            System.out.println("\nWeather at International Space Station Location:");
            System.out.println("---------------------------------------------");
            we_res.print();
        }
        else{
            System.out.println("Could not get the Location of ISS");
        }
    }
    public static String get_iss_city_name(String lat, String lon) {

        String API_URL = "http://api.openweathermap.org/geo/1.0/reverse?lat=" + lat + "&lon=" + lon + "&limit=1&appid=ea4ab6dd3f5aba401d4be5f80e4d0496";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(API_URL)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String resp = response.body();
            if (resp.equals("[]")) {
                return "-1";
            }
            else
            {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(response.body().toString());
                JSONArray city_names_array = (JSONArray) obj;

                if (city_names_array.size() != 0){
                    JSONObject city_names = (JSONObject) city_names_array.get(0);
                    String city_name = city_names.get("name").toString();
                    return city_name;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "-1";
    }



}
