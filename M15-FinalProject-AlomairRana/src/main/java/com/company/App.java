package com.company;

import java.util.Scanner;

public class App {
    public static void printMenu(){
        System.out.println("\n-------------------------Menu-------------------------\n");
        System.out.println("Select Number of the Service to continue");
        System.out.println("[1] Weather in a city");
        System.out.println("[2] Location of the International Space Station (ISS)");
        System.out.println("[3] Weather in the Location of the ISS");
        System.out.println("[4] Current Cryptocurrency Prices");
        System.out.println("[5] Exit");
        System.out.println("\n------------------------------------------------------\n");
    }
    public static void weather(){}
    public static void iss(){}
    public static void weather_iss(){}
    public static void crypto(){}

    public static void main(String[] args) {

        printMenu();
        Scanner scn = new Scanner(System.in);
        String option = scn.nextLine();
        while(! option.equals("5")){
            switch (option){
                case "1":
                    Weather.weather_in_city();
                    break;
                case "2":
                    Space.get_iss_location();
                    break;
                case "3":
                    Space.get_iss_weather();
                    break;
                case "4":
                    Crypto.get_crypto_info();
                    break;
                default:
                    System.out.println(">>> Enter a Valid Input to Continue <<<");
                    break;
            }
            printMenu();
            option = scn.nextLine();
        }
        System.exit(0);
    }
}
