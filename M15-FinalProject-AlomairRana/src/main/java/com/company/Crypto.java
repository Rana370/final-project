package com.company;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import response.CryptoResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Crypto {

    public static CryptoResponse get_crypto_data(String id){

        String API_URL = "https://rest.coinapi.io/v1/assets/" + id;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("X-CoinAPI-Key", "CBB5FD17-FCCC-444F-9B43-46072AA4DF68")
                .uri(URI.create(API_URL)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(response.body());

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.body().toString());
            CryptoResponse res = CryptoResponse.parseCryptoResponse(obj);
            return res;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void get_crypto_info(){
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter id fo Crypto");
        String id = scn.nextLine();

        CryptoResponse cr = get_crypto_data(id);
        if(cr != null){
            cr.print();
        }
        else
            System.out.println("No data found for crypto with id: " + id);
    }

}
