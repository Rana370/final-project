package response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.function.Predicate;

public class WeatherResponse {

    cords cords;
    weather weather;
    String base;
    main mian;
    long visibility;
    clouds clouds;
    long dt;
    sys sys;
    long timezone;
    long id;
    String name;
    String cod;

    public WeatherResponse(cords _cords, weather _weather, String _base, main _mian, long _visibility, clouds _clouds, long _dt, sys _sys, long _timezone, long _id, String _name ,String _cod){
        this.cords = _cords;
        this.weather = _weather;
        this.base = _base;
        this.mian = _mian;
        this.visibility = _visibility;
        this.clouds = _clouds;
        this.dt = _dt;
        this.sys = _sys;
        this.timezone = _timezone;
        this.id = _id;
        this.name = _name;
        this.cod = _cod;
    }
    public void print(){
        cords.print();
        weather.print();
        System.out.println("base:\t" + base);
        mian.print();
        System.out.println("visibility:\t" + visibility);
        clouds.print();
        System.out.println("dt:\t" + dt);
        sys.print();
        System.out.println("timezone:\t" + timezone);
        System.out.println("id:\t" + id);
        System.out.println("name:\t" + name);
        System.out.println("cod:\t" + cod);
    }
    public static WeatherResponse parseWeatherResponse(Object obj){

        JSONObject weather_response = (JSONObject) obj;
        if (weather_response.size() != 0) {
            JSONObject cords = (JSONObject) weather_response.get("coord");

            // coordinates
            cords _cd = new cords(cords.get("lat").toString(),
                                cords.get("lon").toString());

            JSONArray wtr_array = (JSONArray) weather_response.get("weather");
            JSONObject wtr = (JSONObject) wtr_array.get(0);

            // weather
            weather _wt = new weather(wtr.get("id").toString(),
                    wtr.get("main").toString(),
                    wtr.get("description").toString(),
                    wtr.get("icon").toString());

            // base

            String _base = weather_response.get("base").toString();

            // main
            JSONObject main = (JSONObject) weather_response.get("main");
            String _temp  = main.get("temp").toString();
            String _feels_like = main.get("feels_like").toString();
            String _temp_min = main.get("temp_min").toString();
            String _temp_max = main.get("temp_max").toString();
            String _pressure = main.get("pressure").toString();
            String _humidity = main.get("humidity").toString();

            main _main = new main(_temp, _feels_like, _temp_min, _temp_max, _pressure, _humidity);

            // visibility
            String visibility = weather_response.get("visibility").toString();
            long _visibility = Long.parseLong(visibility);

            // wind
            JSONObject wind = (JSONObject) weather_response.get("wind");
            String speed  = wind.get("speed").toString();
            String deg = wind.get("deg").toString();

            wind _wind = new wind(speed, deg);

            // clouds
            JSONObject cloud = (JSONObject) weather_response.get("clouds");
            String all  = cloud.get("all").toString();

            clouds _clouds = new clouds(all);

            // dt
            String dt = weather_response.get("dt").toString();
            long _dt = Long.parseLong(dt);

            //sys
            JSONObject sys = (JSONObject) weather_response.get("sys");
            String _sunrise = sys.get("sunrise").toString();
            String _sunset = sys.get("sunset").toString();
            sys _sys = new sys(_sunrise, _sunset);

            //timezone
            String timezone = weather_response.get("timezone").toString();
            long _timezone = Long.parseLong(timezone);

            //id
            String idd = weather_response.get("id").toString();
            long _idd = Long.parseLong(idd);

            //name
            String _name = weather_response.get("name").toString();

            //cod
            String _cod = weather_response.get("cod").toString();

            WeatherResponse res = new WeatherResponse(_cd, _wt, _base, _main, _visibility, _clouds, _dt, _sys,_timezone, _idd, _name, _cod );
            return res;
        }
        else {
            System.out.println("Weather of given coordinates was not found using the weather api.");
        }
        return null;
    }
}

class cords{
    double lon;
    double lat;
    cords(String _lon, String _lat){
        lon = Double.parseDouble(_lon);
        lat = Double.parseDouble(_lat);
    }
    void print(){
        System.out.println("Cords:");
        System.out.println("\tLongitude:\t" + lon);
        System.out.println("\tLatitude:\t" + lat);

    }
}
class weather{
    String icon;
    String description;
    String main;
    String id;

    public weather(String _id, String _description, String _main, String _icon){
        icon = _icon;
        description = _description;
        main =  _main;
        id = _id;
    }

    public void print(){
        System.out.println("Weather: ");
        System.out.println("\ticon: " + icon);
        System.out.println("\tdescription: " + description);
        System.out.println("\tmain: " + main);
        System.out.println("\tid: " + id);
    }
}
class wind{
    double speed;
    double degree;
    wind(String _speed, String _degree){
        speed = Double.parseDouble(_speed);
        degree = Double.parseDouble(_degree);
    }
    void print(){
        System.out.println("Wind:");
        System.out.println("\tSpeed:\t" + speed);
        System.out.println("\tDeg:\t" + degree);

    }
}
class clouds{
    long all;

    clouds(String _all){
        all = Long.parseLong(_all);
    }
    void print(){
        System.out.println("Colouds:");
        System.out.println("\tall:\t" + all);
    }
}
class sys{
    long sunrise;
    long sunset;

    public sys(String _sunrise, String _sunset){
        sunrise = Long.parseLong(_sunrise);
        sunset = Long.parseLong(_sunset);
    }

    public void print(){
        System.out.println("sys: ");
        System.out.println("\tsunrise: " + sunrise);
        System.out.println("\tsunset: " + sunset);

    }
}

class main{
    double temp;
    double feels_like;
    double temp_min;
    double temp_max;
    double pressure;
    double humidity;

    public main(String _temp, String _feels_like,String _temp_min,String _temp_max,String _pressure,String _humidity ){
        temp = Double.parseDouble(_temp);
        feels_like = Double.parseDouble(_feels_like);
        temp_min = Double.parseDouble(_temp_min);
        temp_max = Double.parseDouble(_temp_max);
        pressure = Double.parseDouble(_pressure);
        humidity = Double.parseDouble(_humidity);

    }

    public void print(){
        System.out.println("main: ");
        System.out.println("\ttemp: " + temp);
        System.out.println("\tfeels_like: " + feels_like);
        System.out.println("\ttemp_min: " + temp_min);
        System.out.println("\ttemp_max: " + temp_max);
        System.out.println("\tpressure: " + pressure);
        System.out.println("\thumidity: " + humidity);

    }
}
