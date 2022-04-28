package response;

import org.json.simple.JSONObject;

import java.sql.Time;
import java.time.Instant;
import java.util.Date;

public class SpaceResponse {
    String timestamp;
    String lat;
    String lon;
    String message;
    Instant tm;

    public SpaceResponse(String _time, String _lat, String _lon, String _message) {
        timestamp = _time;
        lat = _lat;
        lon = _lon;
        message = _message;

        tm = Instant.ofEpochSecond(Long.valueOf(timestamp));
        timestamp = tm.toString();
        timestamp = timestamp.replace("T", " ");
        timestamp = timestamp.replace("Z", " ");
    }

    public void print() {
        System.out.println("Time :\t" + timestamp);
        System.out.println("Latitude :\t" + lat);
        System.out.println("Longitude :\t" + lon);
    }

    public String getlat() {
        return lat;
    }

    public String getlon() {
        return lon;
    }

    public static SpaceResponse parseSpaceResponse(Object obj) {
        JSONObject iss_data = (JSONObject) obj;

        if (iss_data.size() != 0) {
            String time = iss_data.get("timestamp").toString();
            String message = iss_data.get("message").toString();
            JSONObject cords = (JSONObject) iss_data.get("iss_position");

            String lat = cords.get("latitude").toString();
            String lon = cords.get("longitude").toString();

            SpaceResponse res = new SpaceResponse(time, lat, lon, message);

            return res;
        }
        return null;
    }
}