package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import response.WeatherResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class Weather {

    public static ArrayList<String> get_cords_from_api(String city_name) {

        String API_URL = "http://api.openweathermap.org/geo/1.0/direct?q=" + city_name + "&limit=5&appid=ea4ab6dd3f5aba401d4be5f80e4d0496";
        HttpURLConnection connection = null;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(API_URL)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.body().toString());
            //System.out.print(obj);
            JSONArray city_list = (JSONArray) obj;
            if (city_list.size() != 0) {
                JSONObject city = (JSONObject) city_list.get(0);
                String lat = city.get("lat").toString();
                String lon = city.get("lon").toString();

                ArrayList<String> cords = new ArrayList<>(2);
                cords.add(0, lat);
                cords.add(0, lon);

                return cords;

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static WeatherResponse getWeather(String lat, String lon){


        String API_URL = "https://api.openweathermap.org/data/2.5/weather?lat="+ lat +"&lon="+ lon +"&appid=ea4ab6dd3f5aba401d4be5f80e4d0496";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(API_URL)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(response.body());

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.body().toString());

            WeatherResponse res = WeatherResponse.parseWeatherResponse(obj);
            return res;

        } catch (IOException e) {
            System.out.println("Could not establish connection with the API");
            //e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Could not Connect to the API");
            //e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void weather_in_city(){
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter Name of City to Find its Weather");
        String city_name = scn.nextLine();

        ArrayList<String> cords = get_cords_from_api(city_name);
        if(cords != null){
            String lat = cords.get(0);
            String lon = cords.get(1);
            WeatherResponse res = getWeather(lat, lon);
            if(res != null){
                res.print();
            }
        }
    }
}
