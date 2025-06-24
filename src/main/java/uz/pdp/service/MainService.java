package uz.pdp.service;

import com.google.gson.Gson;
import uz.pdp.entity.Company;
import uz.pdp.entity.InoutProduct;
import uz.pdp.entity.OutputProduct;
import uz.pdp.entity.Product;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static uz.pdp.utils.Utils.*;

public class MainService {



 public void service() throws Exception {
     while(true){
         System.out.println("""
                 0 exit
                 1 company mange
                 2 shop mange
                 3 product mange
                 4 input output mange
                 5 category mange
                 """);
         switch (number()){

             case 0 ->{ return;}
             case 1 ->{ CompanyMange();  }
             case 2 ->{ shopMange();  }
             case 3 ->{ productMange();  }
             case 4 ->{ InputAndOutputManagement();  }
             case 5 -> categoryMange();
             default -> {
                 System.out.println(" not found method ");
                 return;
             }
         }



     }


 }

    private void InputAndOutputManagement() {
     while(true){
         System.out.println("""
                 0 exit
                 1 Input product
                 2 output product
                 3 show input product
                 4 show output product
                 """);
         switch (number()){
             case 0 ->{return;}
             case 1 -> inputProduct();
             case 2 -> outputProduct();
             case 3 -> ShowInputProduct();
         }
     }
    }

    private void ShowInputProduct() {

    }

    private void outputProduct() {
        shopManagement.GetAllShop().stream().forEach(shop -> System.out.println(" Shop ->"+shop.getName()+"  :token ->"+shop.getId()));
        System.out.println(" select shop token ");
        String select_shop_token = scannerStr.nextLine();
        shopManagement.GetAllShop().stream().filter(shop -> Objects.equals(shop.getId(),select_shop_token)).findFirst()
                .ifPresentOrElse(
                        shop -> {
                    companyManagement.getAll().stream().forEach(company -> System.out.println( " Company -> "+ company.getName() +"  Token ->" +company.getId() ));
                    System.out.println(" select company token ");
                    String company_token_output = scannerStr.nextLine();
                    List<Company> CompanyProduct = companyManagement.getAll().stream().filter(company -> Objects.equals(company.getId(), company_token_output)).collect(Collectors.toList());

                            if(CompanyProduct==null){
                                   System.out.println("\u2716 not found company product  " + company_token_output);
                                }
                                else
                                 {
                                   productManagement.getAllProduct().stream().filter(product -> Objects.equals(product.getCompany_id(),company_token_output)).forEach(product -> System.out.println(product));
                                   System.out.println(" select product token ");
                                   String select_product_token = scannerStr.nextLine();
                                   productManagement.getAllProduct().stream().filter(product -> Objects.equals(product.getId(),select_product_token)).findFirst()
                                  .ifPresentOrElse(
                                        product -> {
                                            System.out.println("  enter  over all price");
                                            Double price = price();
                                            String id = inputOutputService.addOutputProduct(product,price,select_shop_token);
                                            Boolean loop = true;
                                            while(loop){
                                                System.out.println(" enter product count ");
                                                Integer count = number();
                                                OutputProduct outputProduct = OutputProduct.builder()
                                                        .Product_id(product.getId())
                                                        .id(UUID.randomUUID().toString())
                                                        .count(count)
                                                        .build();
                                                inputOutputService.saveOutputProduct(id,outputProduct);
                                                if (product.getCount()<count){
                                                    System.out.println(" has not count ");
                                                    return;
                                                }
                                                productManagement.Count((product.getCount()-count),product.getId());
                                                System.out.println("""
                                                        0 exit
                                                        1 add
                                                        """);
                                                switch (number()){
                                                    case 0 ->{ loop = false;}
                                                    case 1 ->{ loop = true;}
                                                    default -> {
                                                        System.out.println(" return 0");
                                                        return;
                                                    }
                                                }
                                            }


                                        },
                                        ()->{
                                            System.out.println("\u2716 not found company product  " +select_product_token);
                                        }
                                );
                    }

                },
                  () ->{           System.out.println(" \u2716 not found shop "); }
                );



    }

    private void inputProduct() {
        companyManagement.getAll().stream().forEach(company -> System.out.println( " Company -> "+ company.getName() +"  Token ->" +company.getId() ));
        System.out.println(" select company token ");
        String company_token_input = scannerStr.nextLine();
        List<Product>  CompanyProducts = productManagement.getAllProduct().stream()
                .filter(product -> Objects.equals(company_token_input, product.getCompany_id())).
                collect(Collectors.toList());
                if (CompanyProducts.isEmpty())
                {
                System.out.println("\u2716 not found company product  " + company_token_input);
                }
                else{
                CompanyProducts.stream().forEach(product -> System.out.println("product name->"+product.getName()+" :price-> "+product.getPrice() + " :  token ->" +product.getId()));
                String select_product_token = scannerStr.nextLine();
                productManagement.getAllProduct().stream().filter(product -> Objects.equals(product.getId(),select_product_token)).findFirst()
                        .ifPresentOrElse(
                                product -> {
                                    System.out.println(" over All Price ");
                                    Double priceAll = price();

                                       String id  =  inputOutputService.addInputProduct(product,priceAll);
                                       Boolean loop = true;
                                       while(loop){
                                           System.out.println(" enter block ");
                                           int block = number();
                                           System.out.println(" enter count each block ");
                                           int count = number();

                                           InoutProduct inoutProduct = InoutProduct.builder()
                                                   .id(UUID.randomUUID().toString())
                                                   .Product_id(product.getId())
                                                   .block(block)
                                                   .countEachBlock(count)
                                                   .build();
                                           inputOutputService.saveInputProduct(id,inoutProduct);
                                          productManagement.Count(  (product.getCount()+block*count) ,product.getId());
                                           System.out.println("""
                                                   o exit
                                                   1 add product
                                                   """);


                                           switch (number()){
                                               case 1 ->{ loop = true;}
                                               case 0 ->{ loop = false;}
                                               default -> {
                                                   System.out.println(" return ");
                                                   return;
                                               }
                                           }
                                       }
                                    System.out.println("\u2714 successful ");



                                },
                                ()->{
                                    System.out.println(" \u2716 not found product " +select_product_token);
                                }

                        );


        }

    }

    private void categoryMange() throws Exception {
      while (true){
          System.out.println("""
                  0 exit
                  1 create Category
                  2 edit Category
                  3 delete Category
                  4 show Category
                  """);
          switch (number()){
              case 0 ->{return;}
              case 1 -> createCategoryM();
              case 2 -> editCategory();
              case 3 -> deleteCategory();
              case 4 -> ShowCategory();
              default -> {
                  System.out.println(" not found method");
                  return;
              }
          }
      }

    }

    private void ShowCategory() {
     categoryManagement.getAllCategory().stream().forEach(category -> System.out.println(category));
    }

    private void deleteCategory() {
        categoryManagement.getAllCategory().stream().forEach(category -> System.out.println(" Category ->" + category.getName() + "  : token ->"+category.getId()));
        System.out.println(" select category token");
        String select_category_token = scannerStr.nextLine();
        categoryManagement.getAllCategory().stream().filter(category -> Objects.equals(category.getId(),select_category_token)).findFirst()
                        .ifPresentOrElse(category -> {
                                    categoryManagement.deleteCategory(select_category_token);
                                    System.out.println("\u2714 successful ");
                                },
                                ()->{
                                    System.out.println( " \u2716  not found category ");
                                    return;
                                });
    }

    private void editCategory() {
     categoryManagement.getAllCategory().stream().forEach(category -> System.out.println(" Category ->" + category.getName() + "  : token ->"+category.getId()));
        System.out.println(" select category token");
        String select_edit_category_token = scannerStr.nextLine();
        categoryManagement.getAllCategory().stream().filter(category -> Objects.equals(category.getId(),select_edit_category_token)).findFirst()
                        .ifPresentOrElse(category -> {
                                    categoryManagement.edit(select_edit_category_token);
                                    System.out.println("\u2714 successful ");
                                },
                                ()->{
                                    System.out.println( " \u2716  not found category ");
                                    return;
                                });

    }

    private void createCategoryM() {
        System.out.println(" enter category name");
        String name = scannerStr.nextLine();
          Boolean test = categoryManagement.getAllCategory().stream().anyMatch(category -> Objects.equals(category.getName(),name));
          if (test.equals(true)){
              System.out.println("⚠ Category with this name already exists!");
              return;
          }
        categoryManagement.createCategory(name);

    }

    private void productMange() {

        while(true){
            System.out.println("""
                    0 exit
                    1 create product
                    2 edit product
                    3 delete product
                    4 show Product
                    """);
            switch (number()){
                case 0 ->{return;}
                case 1 -> createProduct();
                case 2 -> EditProduct();
                case 3 -> DeleteProduct();
                case 4 -> ShowProduct();
                default -> {
                    System.out.println(" not found ");
                    return;
                }


            }
        }




    }

    private void ShowProduct() {
     productManagement.getAllProduct().stream().forEach(product -> System.out.println(product));
    }

    private void DeleteProduct() {
        productManagement.getAllProduct().stream().forEach(product -> System.out.println("product name->"+product.getName()+" :price-> "+product.getPrice() + " :  token ->" +product.getId()));
        System.out.println(" select product token ");
        String select_delete_token = scannerStr.nextLine();
        productManagement.getAllProduct().stream().filter(product -> Objects.equals(product.getId(),select_delete_token)).findFirst()
                .ifPresentOrElse(product -> {

                            productManagement.deleteProduct(select_delete_token);
                            System.out.println("\u2714 successful ");

                        },
                        ()->{
                    System.out.println("   \u2716 not found product");
                            return;});



    }

    private void EditProduct() {
        productManagement.getAllProduct().stream().forEach(product -> System.out.println("product name->"+product.getName()+" :price-> "+product.getPrice() + " :  token ->" +product.getId()));
        System.out.println(" select product token ");
        String select_edit_token = scannerStr.nextLine();
        productManagement.getAllProduct().stream().filter(product -> Objects.equals(product.getId(),select_edit_token)).findFirst().ifPresentOrElse(
                product ->{
                    System.out.println(" enter product name");
                    String product_name = scannerStr.nextLine();
                    System.out.println(" enter product price ");
                    Double price_product = scannerInt.nextDouble();
                    productManagement.editProduct(select_edit_token,price_product,product_name);
                    System.out.println("\u2714 successful ");
                },
                ()->{
                    System.out.println("   \u2716 not found product");
                    return;
                });
    }

    private void createProduct() {

        categoryManagement.getAllCategory().forEach(category ->
                System.out.println(" Category -> " + category.getName() + "  : token -> " + category.getId())
        );

        System.out.println("Select category token:");
        String select_token_category = scannerStr.nextLine();

        categoryManagement.getAllCategory().stream()
                .filter(category -> Objects.equals(category.getId(), select_token_category))
                .findFirst()
                .ifPresentOrElse(category -> {

                    companyManagement.getAll().forEach(company ->
                            System.out.println(" Company -> " + company.getName() + "  Token -> " + company.getId())
                    );

                    System.out.println("Select company token:");
                    String select_token_company = scannerStr.nextLine();

                    companyManagement.getAll().stream()
                            .filter(company -> Objects.equals(company.getId(), select_token_company))
                            .findFirst()
                            .ifPresentOrElse(company -> {

                                System.out.println("Enter product name:");
                                String product_name = scannerStr.nextLine();

                                boolean productExists = productManagement.getAllProduct().stream()
                                        .anyMatch(product -> Objects.equals(product.getName(), product_name));

                                if (productExists) {
                                    System.out.println("⚠ Product with this name already exists!");
                                    return;
                                }


                                System.out.println("Enter product price:");
                                Double price_product = price();

                                System.out.println("""
                        1 KG
                        2 GR
                        3 METER
                        4 LITER
                        5 SM
                    """);

                                switch (number()) {
                                    case 1 -> {
                                        productManagement.saveProduct(select_token_category, select_token_company, product_name, price_product, "KG");
                                        System.out.println("✔ Successfully created!");
                                    }
                                    case 2 -> {
                                        productManagement.saveProduct(select_token_category, select_token_company, product_name, price_product, "GR");
                                        System.out.println("✔ Successfully created!");
                                    }
                                    case 3 -> {
                                        productManagement.saveProduct(select_token_category, select_token_company, product_name, price_product, "METER");
                                        System.out.println("✔ Successfully created!");
                                    }
                                    case 4 -> {
                                        productManagement.saveProduct(select_token_category, select_token_company, product_name, price_product, "LITER");
                                        System.out.println("✔ Successfully created!");
                                    }
                                    case 5 -> {
                                        productManagement.saveProduct(select_token_category, select_token_company, product_name, price_product, "SM");
                                        System.out.println("✔ Successfully created!");
                                    }
                                    default -> System.out.println("✖ Not found method");
                                }
                            }, () -> {
                                System.out.println("✖ Company not found");
                            });

                }, () -> {
                    System.out.println("✖ Category not found");
                });
    }


    private void shopMange() {

     while(true){
         System.out.println("""
                 0 exit
                 1 create shop
                 2 edit shop
                 3 delete shop
                 4 Show Shop
                 """);
         switch (number()){
             case 0 ->{return;}
             case 1 -> CreateShop();
             case 2 -> editShop();
             case 3 -> deleteShop();
             case 4 -> ShowShop();
             default -> {
                 System.out.println(" \u2716 not found method");
                 return;
             }
         }
     }
    }

    private void ShowShop() {
     shopManagement.GetAllShop().stream().forEach(shop -> System.out.println(shop));
    }

    private void deleteShop() {
     shopManagement.GetAllShop().stream().forEach(shop -> System.out.println(" Shop ->"+shop.getName()+"  :token ->"+shop.getId()));
        System.out.println(" select shop  token ");
     String select_delete_token =scannerStr.nextLine();
        shopManagement.GetAllShop().stream().filter(shop -> Objects.equals(shop.getId(),select_delete_token)).findFirst().
                ifPresentOrElse(shop -> {
                            shopManagement.deleteShop(select_delete_token);
                            System.out.println("\u2714 successful ");
                        },
                        ()->{
                            System.out.println(" \u2716 not found shop ");
                        });


    }

    private void editShop() {

         shopManagement.GetAllShop().stream()
             .forEach(shop -> System.out.println(" Shop-> "+shop.getName()+" : Token ->"+shop.getId()));

        System.out.println(" select shop  token ");
        String select_shop_token = scannerStr.nextLine();

        shopManagement.GetAllShop().stream().filter(shop -> Objects.equals(shop.getId(),select_shop_token)).findFirst()
                        .ifPresentOrElse(shop -> {

                                    System.out.println(" enter shop name ");
                                    shopManagement.editShop(select_shop_token);
                                    System.out.println("\u2714 successful ");

                                },
                                ()->{
                                    System.out.println(" \u2716 not found shop ");
                                    return;
                                });


    }

    private void CreateShop() {
        System.out.println(" enter shop name ");
        String name = scannerStr.nextLine();
        boolean test = shopManagement.GetAllShop().stream().anyMatch(shop -> Objects.equals(shop.getName(),name));
        if (test == true){
            System.out.println("⚠ shop with this name already exists!");
            return;
        }
        shopManagement.CreateShop(name);
        System.out.println("\u2714 successful ");
    }

    private void CompanyMange() throws Exception {

        while(true){
            System.out.println("""
                    0 exit
                    1 create company
                    2 edit company
                    3 delete company
                    4 show company
                    """);
            switch (number()){
                case 0 ->{return;}
                case 1 -> createCompany();
                case 2 -> EditCompany();
                case 3 -> deleteCompany();
                case 4 -> showCompany();
                default -> {
                    System.out.println("\u2716  not found method ");
                    return;
                }

            }

        }
    }

    private void showCompany() {
     companyManagement.getAll().stream().forEach(company -> System.out.println(company));
    }

    private void deleteCompany() {
     companyManagement.getAll().stream().forEach(company -> System.out.println( " Company -> "+ company.getName() +"  Token ->" +company.getId() ));
        System.out.println(" select Token ");
        String select_company_token = scannerStr.nextLine();
        companyManagement.getAll().stream().filter(company -> Objects.equals(company.getId(),select_company_token)).findFirst()
                        .ifPresentOrElse(company -> {
                                    companyManagement.delectCompany(select_company_token);
                                    System.out.println("\u2714 successful ");
                                }
                        ,()->{
                                    System.out.println(" \u2716  not found company ");
                                    return;
                                });

    }

    private void EditCompany() {
     companyManagement.getAll().stream().forEach(company -> System.out.println( " Company -> "+ company.getName() +"  Token ->" +company.getId() ));
     System.out.println(" select Token ");
     String select_edit_token = scannerStr.nextLine();
        companyManagement.getAll().stream().filter(company -> Objects.equals(company.getId(),select_edit_token)).findFirst()
                        .ifPresentOrElse(company -> {
                                    System.out.println(" enter name");
                                    companyManagement.editCompany(select_edit_token);
                                    System.out.println("\u2714 successful ");


                                },
                                ()->{  System.out.println(" \u2716  not found company ");
                                    return;});


    }

    private void createCompany() throws Exception {
        System.out.println(" enter company name ");
        String name = scannerStr.nextLine();
        if(companyManagement.getAll().stream().anyMatch(company -> Objects.equals(company.getName(),name))){
            System.out.println("⚠ Company with this name already exists!");
            return;
        }
      companyManagement.create(name);
    }

}
