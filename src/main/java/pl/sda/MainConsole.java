package pl.sda;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.sda.dao.AdminAreaDAO;
import pl.sda.dao.CityDAO;
import pl.sda.dao.TemperatureDAO;
import pl.sda.entity.AdminArea;
import pl.sda.entity.City;
import pl.sda.entity.TemperatureMeasurement;
import pl.sda.mapper.TemperatureMapper;
import pl.sda.responseModels.Temperature;
import pl.sda.service.AccuWeatherService;
import pl.sda.service.AdminAreaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MainConsole {

    private AdminAreaService aas;
    private AccuWeatherService aws;
    private CityDAO cd;
    private TemperatureDAO td;
    private TemperatureMapper temperatureMapper;
    public static void main(String[] args) {
        MainConsole mc = new MainConsole();
        mc.mainMenu();
    }

    public MainConsole(){
        SessionFactory sf =
                new Configuration().configure("hibernate.cfg.xml")
                        .addAnnotatedClass(City.class)
                        .addAnnotatedClass(TemperatureMeasurement.class)
                        .buildSessionFactory();

        aws = new AccuWeatherService();
        AdminAreaDAO aad = new AdminAreaDAO(sf);
        aas = new AdminAreaService(aad, aws);

        cd = new CityDAO(sf);
        td = new TemperatureDAO(sf);
        temperatureMapper = new TemperatureMapper();
        instantiate();
    }

    private void instantiate() {
        City c = City.builder().locationKey(274945).name("Opole").build();
        cd.save(c);
    }

    public void mainMenu(){
        int choice=0;
        while(choice!=4) {
            System.out.println("You are in main menu. Below are available options:");
            System.out.println("1. Print available locations");
            System.out.println("2. Update available locations");
            System.out.println("3. Check weather at location");
            System.out.println("4. Quit");

            System.out.print("Choose option:");
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();

            if (choice == 1) {
                locationMenu();
            } else if (choice == 2) {
                updateAvailableLocationsMenu();
            } else if (choice == 3) {
                checkWeatherAtAvailableLocationMenu();
            }
        }
    }



    public void locationMenu(){
        int choice=0;
        while(choice!=2){
            System.out.println("You are in location print menu. Do you want to print available locations");
            System.out.println("1. Yes");
            System.out.println("2. Return");

            System.out.print("Choose option:");
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();

            if(choice==1){
                System.out.println("List of available locations:");
                List<AdminArea> adminAreas = aas.getAvailableAdminAreas();
                for(int i=0; i<adminAreas.size();i++){
                    System.out.println(i+". "+adminAreas.get(i));
                }
            }
        }

    }

    private void updateAvailableLocationsMenu() {
        int choice=0;
        while(choice!=2){
            System.out.println("You are in location update menu. Do you want to update available locations");
            System.out.println("1. Yes");
            System.out.println("2. Return");

            System.out.print("Choose option:");
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();

            if(choice==1){
                aas.updateAvailableLocations();
            }
        }
    }

    private void checkWeatherAtAvailableLocationMenu() {
        int choice=1;
        while(choice!=0){
            System.out.println("You are in check current weather menu. Choose available location");
            System.out.println("0. Return");
            List<City> cities = cd.getAvailableCities();
            for(int i=1; i<=cities.size(); i++){
                System.out.println(i+". "+cities.get(i-1));
            }

            System.out.print("Choose option:");
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();

            if(choice!=0){
                Temperature t = aws.getCurrentWeatherConditions(cities.get(choice-1));
                TemperatureMeasurement tm = temperatureMapper.map(t);

                tm.setMeasureWhen(LocalDateTime.now());
                td.save(tm);
            }


        }
    }


}
