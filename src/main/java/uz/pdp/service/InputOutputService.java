package uz.pdp.service;

import uz.pdp.entity.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static uz.pdp.utils.Utils.*;
import static uz.pdp.utils.Utils.fileInt;
import static uz.pdp.utils.Utils.gson;
import static uz.pdp.utils.Utils.inputList;

public class InputOutputService {

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
 private  void outRead() throws IOException {
        BufferedReader read = new BufferedReader( new FileReader(fileOut));
        String text;
        StringBuilder txt = new StringBuilder();
        while ((text= read.readLine())!=null){
            txt.append(text);
        }
        read.close();
     Output[] outputsProduct = gson.fromJson(txt.toString(), Output[].class);
     outputList = new ArrayList<>(Arrays.asList(outputsProduct));
 }
 private  void outWrite() throws IOException {
        BufferedWriter writer = new BufferedWriter( new FileWriter(fileOut));
        writer.write(gson.toJson(outputList));
        writer.close();
 }

    public String addOutputProduct(Product product ,Double price, String  shop_id) {
        try{
            outRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Output output = Output.builder()
                .date(LocalDate.now().toString())
                .id(UUID.randomUUID().toString())
                .Shop_id(shop_id)
                .overallPrice(price)
                .build();
        outputList.add(output);

        try{
            outWrite();
            } catch (IOException e) {

            throw new RuntimeException(e);
        }
        return output.getId();
    }

    public void saveOutputProduct(String id, OutputProduct outputProduct) {
        try{
            outRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        outputList.stream().filter(output -> Objects.equals(output.getId(),id)).findFirst()
                .ifPresent(output -> {
                    List<OutputProduct> productOutput= output.getProductList();

                    if (productOutput == null) {
                        productOutput = new ArrayList<>();
                        output.setProductList(productOutput);
                    }

                    productOutput.add(outputProduct);
                });
        try{
            outWrite();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}