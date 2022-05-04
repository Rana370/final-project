package com.company.M15FinalProjectAlomairRana;


import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Scanner;

public class Crypto {

    public static CryptoObject[] get_crypto_data(String id){
        WebClient coinClient = WebClient.create("https://rest.coinapi.io/v1/assets/"+id+"?apikey=CBB5FD17-FCCC-444F-9B43-46072AA4DF68");
        CryptoObject[] coinResponse = null;
        try {
            Mono<CryptoObject[]> response = coinClient
                    .get()
                    .retrieve()
                    .bodyToMono(CryptoObject[].class);

            coinResponse = response.share().block();
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
        return coinResponse;
    }

    public static void get_crypto_info(){
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter id for Crypto");
        String id = scn.nextLine();

        CryptoObject[] cr = get_crypto_data(id);
        if(cr != null){
            //name of crypto, price, id
            System.out.println("Crypto"+cr[0].name);
            System.out.println("price"+cr[0].price_usd);
            System.out.println("id"+cr[0].id_icon);
        }
        else
            System.out.println("No data found for crypto with id: " + id);
    }

}

