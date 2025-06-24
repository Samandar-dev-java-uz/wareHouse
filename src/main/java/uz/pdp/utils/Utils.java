package uz.pdp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uz.pdp.entity.*;
import uz.pdp.service.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static Scanner scannerStr = new Scanner(System.in);
    public static Scanner scannerInt = new Scanner(System.in);
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static String fileCompany = "/home/samandar/IdeaProjects/WaheHouses/src/main/java/uz/pdp/data/company.json";
    public static String fileCategory = "/home/samandar/IdeaProjects/WaheHouses/src/main/java/uz/pdp/data/category.json";
    public static String fileInt = "/home/samandar/IdeaProjects/WaheHouses/src/main/java/uz/pdp/data/inputProduct.json";
    public static String fileOut = "/home/samandar/IdeaProjects/WaheHouses/src/main/java/uz/pdp/data/outPutProduct.json";
    public static String fileProduct = "/home/samandar/IdeaProjects/WaheHouses/src/main/java/uz/pdp/data/product.json";
    public static String fileShop = "/home/samandar/IdeaProjects/WaheHouses/src/main/java/uz/pdp/data/shop.json";
    public static List<Company> companies = new ArrayList<>();
    public static List<Shop> shopList = new ArrayList<>();
    public static List<Category> categoryList = new ArrayList<>();
    public static List<Product> productList = new ArrayList<>();
    public static List<Input> inputList = new ArrayList<>();
    public  static  List<Output> outputList = new ArrayList<>();
    public static   ShopManagementService shopManagement = new ShopManagementService();
    public static CompanyManagementService companyManagement = new CompanyManagementService();
    public static CategoryManagementService categoryManagement = new CategoryManagementService();
    public static ProductManagementService productManagement = new ProductManagementService();
    public static InputOutputService inputOutputService = new InputOutputService();
    public static int number(){
       int number =0;
       while (true){
           try{
               number = scannerInt.nextInt();
               break;
           }
           catch ( InputMismatchException e){
               System.out.println(" enter number ");
               scannerInt = new Scanner(System.in);
           }

       }
       return number;
   }
   public static  Double price(){
        double price =0;
        while (true){
         try{
             price = scannerInt.nextDouble();
             break;
         } catch (Exception e) {
             System.out.println(" enter  price number  ");
             scannerInt = new Scanner( System.in);
         }
        }
        return price;
   }
}
