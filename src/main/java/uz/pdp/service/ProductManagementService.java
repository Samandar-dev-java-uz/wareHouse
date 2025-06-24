package uz.pdp.service;

import uz.pdp.entity.Measure;
import uz.pdp.entity.Product;

import java.io.*;
import java.util.*;

import static uz.pdp.utils.Utils.*;

public class ProductManagementService {
    public List<Product> getAllProduct(){

        try{
            read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }
    private void read() throws IOException {
        BufferedReader read = new BufferedReader( new FileReader(fileProduct));
        String text;
        StringBuilder txt = new StringBuilder("");
        while ((text= read.readLine())!=null){
            txt.append(text);
        }
        read.close();
        Product[] readProduct = gson.fromJson(txt.toString(), Product[].class);
        productList = new ArrayList<>(Arrays.asList(readProduct));

    }
    private void write() throws IOException {
        BufferedWriter writer = new BufferedWriter( new FileWriter(fileProduct));
        writer.write(gson.toJson(productList));
        writer.close();
    }

    public void saveProduct(String select_category_token, String select_token_company, String product_name, Double price_product, String select_measure){
        try{
            read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Product product = Product.builder()
                .id(UUID.randomUUID().toString())
                .name(product_name)
                .price(price_product)
                .Category_id(select_category_token)
                .Company_id(select_token_company)
                .measure(Measure.valueOf(select_measure))
                .count(0)
                .build();
        productList.add(product);
        try{
            write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void editProduct(String select_edit_product,Double price_product,String product_name){
        productList.stream().filter(product -> Objects.equals(product.getId(),select_edit_product)).findFirst().ifPresent(product -> {product.setName(product_name);product.setPrice(price_product);});
        try{
            write();
        } catch (IOException e) {
                throw new RuntimeException(e);
        }
    }

    public void deleteProduct(String select_delete_product_token) {
        Iterator<Product> deleteProduct = productList.iterator();
        while(deleteProduct.hasNext()){
            Product product = (Product) deleteProduct.next();
            if (Objects.equals(product.getId(),select_delete_product_token))
                deleteProduct.remove();
        }
        try{
            write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void Count(int count, String select_product_token) {

        productList.stream().filter(product -> Objects.equals(product.getId(),select_product_token))
                .findFirst().ifPresent(product -> product.setCount(count));

        try{
            write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
