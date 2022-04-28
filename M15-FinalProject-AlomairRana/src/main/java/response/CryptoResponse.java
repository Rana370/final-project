package response;

import com.company.Crypto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.NumberFormat;

public class CryptoResponse {
    String id;
    String name;
    double price_in_usd;
    String formatted_price ;

    public CryptoResponse(String _id, String _name, double _price){
        id = _id;
        name = _name;
        price_in_usd = _price;

        format_price();
    }

    public void format_price(){

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatted_price = formatter.format(price_in_usd);

    }
    public void print(){
        System.out.println("Crypto id:\t" + id);
        System.out.println("Crypto Name:\t" + name);
        System.out.println("Price in USD:\t" + formatted_price);
    }

    public static CryptoResponse parseCryptoResponse(Object obj){
        JSONArray crypto_array = (JSONArray) obj;

        if (crypto_array.size() != 0){
            JSONObject crypto_data = (JSONObject) crypto_array.get(0);
            CryptoResponse crypto_response = new CryptoResponse(crypto_data.get("asset_id").toString(),
                    crypto_data.get("name").toString(),
                    Double.parseDouble( crypto_data.get("price_usd").toString()));

            return crypto_response;
        }
        return null;
    }
}
