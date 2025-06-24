package uz.pdp.service;

import uz.pdp.entity.Company;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static uz.pdp.utils.Utils.*;
public class CompanyManagementService {
    private  void  read() throws Exception {
        BufferedReader read = new BufferedReader(new FileReader(fileCompany));
         StringBuilder text = new StringBuilder("");
         String txt ;
         while ((txt=read.readLine())!=null){
             text.append(txt);
         }
         read.close();
        Company[] companiesText = gson.fromJson(text.toString(), Company[].class);
        companies = new ArrayList<>(Arrays.asList(companiesText));
    }
    public List<Company> getAll(){
        try{
            read();
        } catch (Exception e) {
            System.out.println(" GetAll  method error");
        }
        return companies;

    }
    public void editCompany (String select_Token){

        companies.stream()
                .filter(company -> Objects.equals(company.getId(),select_Token))
                .findFirst()
                .ifPresent(company -> company.setName(scannerStr.nextLine()));

        try {
            write();
        } catch (Exception e) {
            System.out.println(" Edit Company method write error ");
        }

    }
    private  void write() throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileCompany));
        writer.write(gson.toJson(companies));
        writer.close();
    }
    public  void create( String name) throws Exception {
        read();
        Company company =  Company.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .build();
        companies.add(company);
        write();

    }


    public void delectCompany(String select_token) {
        try{
            read();
        } catch (Exception e) {
            System.out.println(" delect Company read method error");
        }
        Iterator<Company> iteratorDelete = companies.iterator();
        while (iteratorDelete.hasNext()){
            Company deleteCompany = iteratorDelete.next();
            if (Objects.equals(deleteCompany.getId(),select_token)){
                iteratorDelete.remove();

            }
        }
        try{
            write();
        } catch (Exception e) {
            System.out.println(" delete Company write method error");
        }


    }



}
