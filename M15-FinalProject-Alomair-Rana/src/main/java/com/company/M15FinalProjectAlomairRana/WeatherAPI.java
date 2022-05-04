package com.company.M15FinalProjectAlomairRana;


import com.company.M15FinalProjectAlomairRana.Weather.WeatherResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Scanner;

public class WeatherAPI {

    //"http://api.openweathermap.org/geo/1.0/direct?q=" + city_name + "&limit=5&appid=ea4ab6dd3f5aba401d4be5f80e4d0496"
    public static WeatherResponse get_cords_from_api(String city_name) {

        WebClient weatherClient = WebClient.create("https://api.openweathermap.org/data/2.5/weather?q="+city_name+"&appid=ea4ab6dd3f5aba401d4be5f80e4d0496");
        WeatherResponse weather = null;
        try {
            Mono<WeatherResponse> response = weatherClient
                    .get()
                    .retrieve()
                    .bodyToMono(WeatherResponse.class);

            weather = response.share().block();
        }
        catch (WebClientResponseException we){
            int statusCode = we.getRawStatusCode();
            if(statusCode >= 400 && statusCode <500)
                System.out.println("Client Error");
            else if(statusCode >= 500 && statusCode <600)
                System.out.println("Server Error");
        }
        catch (Exception e){
            System.out.println("An error occurred: "+e.getMessage());
        }
        return weather;
        }
        //"https://api.openweathermap.org/data/2.5/weather?lat="+ lat +"&lon="+ lon +"&appid=ea4ab6dd3f5aba401d4be5f80e4d0496"


        public static WeatherResponse getWeather(String lat, String lon){
            WebClient weatherClient = WebClient.create("https://api.openweathermap.org/data/2.5/weather?lat="+ lat +"&lon="+ lon +"&appid=ea4ab6dd3f5aba401d4be5f80e4d0496");
            WeatherResponse weather = null;
            try {
                Mono<WeatherResponse> response = weatherClient
                        .get()
                        .retrieve()
                        .bodyToMono(WeatherResponse.class);

                weather = response.share().block();
            }
            catch (WebClientResponseException we){
                int statusCode = we.getRawStatusCode();
                if(statusCode >= 400 && statusCode <500)
                    System.out.println("Client Error");
                else if(statusCode >= 500 && statusCode <600)
                    System.out.println("Server Error");
            }
            catch (Exception e){
                System.out.println("An error occurred: "+e.getMessage());
            }

            return weather;

        }

        public static void weather_in_city(){
            Scanner scn = new Scanner(System.in);
            System.out.println("Enter Name of City to Find its Weather");
            String city_name = scn.nextLine();

            WeatherResponse cords = get_cords_from_api(city_name);
            if(cords != null){
              System.out.println("print clouds" + cords.main.temp);
            }
        }
}

