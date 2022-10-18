import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;




public class Main {
    public static  void main(String [] args) {


        List<Products> allProducts;

        //Filepath
        File productFile = new File("product.json");


        if (productFile.exists()) {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Products>>() {
                }.getType();
                FileReader fileReader = new FileReader(productFile);
                allProducts = gson.fromJson(fileReader, type);

            } catch (IOException e) {
                System.err.println("Error whit writing file");
                throw new RuntimeException(e);
            }

            //If file doesn't exist, do a new file.
        } else {
            allProducts = new ArrayList<>(List.of(
                    new Products("Frukt", "Banan", "Ecuador", 2339325000005L, 19.90),
                    new Products("Frukt", "Äpple", "Granny Smith", 2317413900000L, 31.95),
                    new Products("Frukt", "Päron", "Conferece", 2317301700002L, 32.95),
                    new Products("Bröd", "Jättefranska", "Pågen", 7311070008463L, 35.95),
                    new Products("Mejeri", "Mellanmjölk", "Arla", 7310865001818L, 18.95)));
        }

        //User scanner input.
        Scanner sc = new Scanner(System.in);
        Menu.firstMenu();

        String userChoice = sc.nextLine();


        //While, with the user choice.
        while (!userChoice.equalsIgnoreCase("e")) {


            switch (userChoice) {
                case "1" -> {
                    //Product information with inventory balance
                    allProducts.forEach(System.out::println);
                    System.out.println("\n");
                    System.out.println("Det finns totalt " + allProducts.size() + " st varor i listan.");
                }
                case "2" -> {
                    //Insert new product to the list.
                    System.out.println("Ange ny Kategori");
                    String cat = sc.nextLine();
                    System.out.println("ange typ");
                    String typ = sc.nextLine();
                    System.out.println("ange Varumärke");
                    String brandName = sc.nextLine();
                    System.out.println("ange Ean kod");
                    Long eanCode = sc.nextLong();
                    System.out.println("ange pris");

                    double price = sc.nextDouble();
                    sc.nextLine();

                    //adding the new product from user to the list.
                    allProducts.add(new Products(cat, typ, brandName, eanCode, price));
                    allProducts.forEach(System.out::println);
                }
                case "3" -> {
                    //Remove a product with the products uniq Ea-number.
                    System.out.println("ta bort en produkt. Ange Ean kod");
                    Long productToRemove = sc.nextLong();
                    allProducts = allProducts.stream().filter(product -> !Objects.equals(product.getEaCode(), productToRemove))
                            .collect(Collectors.toCollection(ArrayList::new));
                    allProducts.forEach(System.out::println);
                }
                case "4" -> {
                    //Searching a product from the list with different metods.
                    Menu.searchMenu();
                    String search = sc.nextLine();
                    switch (search) {
                        case "1" -> {
                            //Searching based on the Category
                            System.out.println("Ange kategori");
                            String categorySearch = sc.nextLine();
                            List<Products> searchByCategory = allProducts.stream().filter(product -> product.getCategory().equals(categorySearch)).toList();
                            searchByCategory.forEach(System.out::println);
                        }
                        case "2" -> {
                            //Searching based on the type
                            System.out.println("Ange typ");
                            String type = sc.nextLine();
                            List<Products> searchByType = allProducts.stream()
                                    .filter(product -> product.getType().equals(type)).toList();
                            searchByType.forEach(System.out::println);
                        }
                        case "3" -> {
                            //Searching based on the brand name.
                            System.out.println("Ange varumärke");
                            String brandName = sc.nextLine();
                            List<Products> searchByBrandName = allProducts.stream()
                                    .filter(product -> product.getBrandName().equals(brandName)).toList();
                            searchByBrandName.forEach(System.out::println);
                        }
                        case "4" -> {
                            //Finding a product based on the ea-number
                            System.out.println("Hitta en produkt utifrån Ea kod");
                            Long eaCode = sc.nextLong();
                            sc.nextLine();
                            try {
                                Products searchByEa = allProducts.stream()
                                        .filter(product -> product.getEaCode().equals(eaCode)).toList().get(0);
                                System.out.println(searchByEa);

                                //If the ea-code not exist, we catch it and the user can select another ea-code
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.err.println("Ea-kode finns inte. var god försök med en annan");
                            }
                        }
                        case "5" -> {
                            //searching a product based on the price range.
                            System.out.println("Ange lägsta pris");
                            Double minVal = sc.nextDouble();
                            System.out.println("Ange högsta pris");
                            Double maxVal = sc.nextDouble();
                            sc.nextLine();
                            List<Products> searchByPriceInterval = allProducts.stream()
                                    .filter(product -> product.getPrice() > minVal && product.getPrice() < maxVal).toList();
                            searchByPriceInterval.forEach(System.out::println);
                        }
                    }
                }
                case "5" -> {
                    //user can modify the product based from the uniq ea-number.
                    System.out.println("Vilken produkt vill du ändra? Ange ean kod");
                    Long eaCodeFromUser = sc.nextLong();
                    sc.nextLine();
                    Products productToModify = allProducts.stream().filter(product -> product.getEaCode().equals(eaCodeFromUser)).toList()
                            .get(0);
                    Menu.editMenu();
                    String changeProduct = sc.nextLine();

                    //Modifying the category
                    if (changeProduct.equals("1")) {
                        System.out.println("Ange ny kategori");
                        productToModify.setCategory(sc.nextLine());

                    }
                    //Modifying the Type
                    if (changeProduct.equals("2")) {
                        System.out.println("Ange ny typ");
                        productToModify.setType(sc.nextLine());

                    }
                    //Modifying the brand name
                    if (changeProduct.equals("3")) {
                        System.out.println("Ange nytt Varumärke");
                        productToModify.setBrandName(sc.nextLine());
                    }
                    //Modifying the price
                    if (changeProduct.equals("4")) {
                        System.out.println("Ange nytt pris");
                        productToModify.setPrice(sc.nextDouble());
                        sc.nextLine();
                    }
                }
            }
            Menu.firstMenu();
            userChoice = sc.nextLine();


        }

        Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
        String json = gson.toJson(allProducts);

        //saving and closing data to the file  with user choice e.
        try {
            Files.writeString(Path.of("product.json"), json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}