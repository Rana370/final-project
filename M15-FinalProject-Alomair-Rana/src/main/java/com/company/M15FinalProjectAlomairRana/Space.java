package com.company.M15FinalProjectAlomairRana;


import com.company.M15FinalProjectAlomairRana.Weather.WeatherResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

//"http://api.open-notify.org/iss-now.json?callback=?"
public class Space {
    static SpaceResponse spaceResponse = null;
    public static SpaceResponse get_iss_cords(){
        WebClient spaceClient = WebClient.create("http://api.open-notify.org/iss-now.json");

        try {
            Mono<SpaceResponse> response = spaceClient
                    .get()
                    .retrieve()
                    .bodyToMono(SpaceResponse.class);

             spaceResponse = response.share().block();
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
        return spaceResponse;
    }

        public static void get_iss_location(){
            SpaceResponse res = get_iss_cords();

            if(res != null){
                System.out.println("International Space Station Current Location");
                res.print();
                String name = get_iss_city_name(res.iss_position.latitude, res.iss_position.longitude);
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

                WeatherResponse we_res = WeatherAPI.getWeather(res.iss_position.latitude, res.iss_position.longitude);

                System.out.println("\nWeather at International Space Station Location:");
                System.out.println("---------------------------------------------");
                System.out.println(we_res.sys.country);
                System.out.println(we_res.main.temp);
                System.out.println(we_res.weather[0].description);


            }
            else{
                System.out.println("Could not get the Location of ISS");
            }
        }

        public static String get_iss_city_name(String lat, String lon) {

            WebClient spaceClient = WebClient.create("http://api.openweathermap.org/data/2.5/weather?" + lat + "&lon=" + lon + "&units=imperial&appid=ea4ab6dd3f5aba401d4be5f80e4d0496");
            WeatherResponse spaceResponse = null;
            try {
                Mono<WeatherResponse> response = spaceClient
                        .get()
                        .retrieve()
                        .bodyToMono(WeatherResponse.class);

                spaceResponse = response.share().block();
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
            if (spaceResponse.sys.country == null) {
                return "";
            }
            return spaceResponse.name;
        }



    }

