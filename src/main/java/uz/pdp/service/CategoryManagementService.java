package uz.pdp.service;

import uz.pdp.entity.Category;
import uz.pdp.entity.InoutProduct;
import uz.pdp.entity.Input;
import uz.pdp.entity.Product;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static uz.pdp.utils.Utils.*;

public class CategoryManagementService {
    private void write() throws IOException {
        BufferedWriter writer = new BufferedWriter( new FileWriter(fileCategory));
        writer.write(gson.toJson(categoryList));
        writer.close();
    }

    private void read() throws Exception {
        BufferedReader read = new BufferedReader(new FileReader(fileCategory));
        String text ;
        StringBuilder txt = new StringBuilder("");
        while ((text = read.readLine())!=null){
            txt.append(text);
        }
        read.close();
        Category[] readCategorys = gson.fromJson(txt.toString(), Category[].class);
        categoryList= new ArrayList<>(Arrays.asList(readCategorys));

    }
    public List<Category> getAllCategory(){
        try{
            read();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return categoryList;
    }

    public void createCategory(){
        try{
            read();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Category category = Category.builder()
                .id(UUID.randomUUID().toString())
                .name(scannerStr.nextLine())
                .build();
        categoryList.add(category);
        try{
            write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void edit(String select_token) {
        categoryList.stream()
                .filter(category -> Objects.equals(category.getId(),select_token))
                .findFirst()
                .ifPresentOrElse(
                        category ->{
                            category.setName(scannerStr.nextLine());
                        },
                                ()->{
                             System.out.println(" not found category ");
                                        });

        try{
            write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void deleteCategory( String select_token) {
         Iterator<Category> deleteIterator = categoryList.iterator();
         while (deleteIterator.hasNext()){
             Category category = (Category) deleteIterator.next();
             if (Objects.equals(category.getId(),select_token)){
                 deleteIterator.remove();
             }
         }
         try{
             write();
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
    }

    public static class InputOutputService {

        private void write() throws IOException {
            BufferedWriter write = new BufferedWriter(new FileWriter(fileInt));
            write.write(gson.toJson(inputList));
            write.close();
        }
        private void read() throws IOException {
             BufferedReader read = new BufferedReader( new FileReader(fileInt));
             String text;
             StringBuilder txt = new StringBuilder("");

             while ((text= read.readLine())!=null){

                 txt.append(text);

             }
             read.close();
            Input[] inputs = gson.fromJson(txt.toString(), Input[].class);
            inputList = new ArrayList<>(Arrays.asList(inputs));

        }



        public String addInputProduct(Product product, Double price) {
            try{
                read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Input input = Input.builder()
                    .id(UUID.randomUUID().toString())
                    .Company_Id(product.getCompany_id())
                    .date(LocalDate.now().toString())
                    .overallPrice(price)
                    .build();
            inputList.add(input);
            try{
                write();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return input.getId();
        }

        public void saveInputProduct(String id, InoutProduct inoutProduct) {
            try{
                read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            inputList.stream()
                    .filter(input -> Objects.equals(input.getId(), id))
                    .findFirst()
                    .ifPresentOrElse(
                            input -> {
                                List<InoutProduct> productList1 = input.getProductList();

                                if (productList1 == null) {
                                    productList1 = new ArrayList<>();
                                    input.setProductList(productList1);
                                }

                                productList1.add(inoutProduct);
                            },
                            () -> {

                            }
                    );
            try{
                write();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }



    }
}
