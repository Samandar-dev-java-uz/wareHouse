package uz.pdp.service;

import uz.pdp.entity.Shop;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import static uz.pdp.utils.Utils.*;


public class ShopManagementService {
    public List<Shop> GetAllShop(){
        try{
            read();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return shopList;
    }
    private  void write(){
        try{
        BufferedWriter writer = new BufferedWriter( new FileWriter(fileShop));
        writer.write(gson.toJson(shopList));
        writer.close();
        } catch (IOException e) {
            System.out.println(" shop write method error ");
        }
    }
    private  void read(){
        try {
        BufferedReader read = new BufferedReader(new FileReader(fileShop));
        StringBuilder txt = new StringBuilder();
        String text;

        while ((text = read.readLine()) != null) {
            txt.append(text);
        }

        read.close();

        Shop[] shopRead = gson.fromJson(txt.toString(), Shop[].class);
        shopList = new ArrayList<>(Arrays.asList(shopRead));

    } catch (Exception e) {
        System.out.println("Shop read method error: " + e.getMessage());
        e.printStackTrace();
    }
    }
    public void Create() {
        try{
            read();
        } catch (Exception e) {
            System.out.println(" shop  create method error ");
        }
        Shop shop = Shop.builder()
                .Id(UUID.randomUUID().toString())
                .name(scannerStr.nextLine())
                .build();
        shopList.add(shop);
        try {
            write();
        } catch (Exception e) {
            System.out.println(" shop create method error");
        }

    }

    public void editShop(String select_token) {

        shopList.stream()
                .filter(shop -> Objects.equals(shop.getId(),select_token))
                .findFirst()
                .ifPresent(shop -> shop.setName(scannerStr.nextLine()));
        try{
            write();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteShop(String select_Token) {
        try{
            read();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Iterator deleteShop = shopList.iterator();
        while (deleteShop.hasNext()){
            Shop shop = (Shop) deleteShop.next();
            if (Objects.equals(shop.getId(),select_Token)){
                deleteShop.remove();
            }
        }
        try{
            write();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
