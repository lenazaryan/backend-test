package com.geekbrains.builder;
import com.geekbrains.db.dao.CategoriesMapper;
import com.geekbrains.db.dao.ProductsMapper;
import com.geekbrains.db.model.Categories;
import com.geekbrains.db.model.MinimarketService;
import com.geekbrains.db.model.ObjectResponse;
import com.geekbrains.db.model.Products;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;


public class Main {


    public static void main(String[] args) throws IOException {

//        User user = User.builder()
//                .setName("Ivan")
//                .setSurname("Ivanov")
//                .setAddress("Address")
//                .setEmail("123@ya.ru")
//                .build();
//        // DSL - domain specific language
//        RequestSpecification requestSpecification =
//                new RequestSpecBuilder()
//                        .build();


//        SpoonacularService spoonacularService = new SpoonacularService();
//        ApiSearchResults recipes = spoonacularService.findRecipes("Bread", 3);
//        System.out.println(recipes);
//
//
//        ApiUserConnectResult user = spoonacularService.connect(new ApiUserConnectRequest(
//                "username",
//                "firstname",
//                "lastname",
//                "email@gmail.com"));
//        System.out.println(user);
//
//
//        ShoppingListResponse shopList = spoonacularService.generate(
//                "orville41", 2000-12-12, 2000-12-12,
//                "3e633e8b71919a216e114cedfed16137cc788464");
//        System.out.println(shopList);
//
//
//        ShoppingListResponse addProduct = spoonacularService.addItem(
//                new AddToShoppingListRequest("1kg apple pie", "pie", true),
//                "3e633e8b71919a216e114cedfed16137cc788464", "orville41");
//        System.out.println(addProduct);
//
//
//        ShoppingListResponse deleteProduct = spoonacularService.deleteItem(
//                "3e633e8b71919a216e114cedfed16137cc788464", "orville41", 1301765);
//        System.out.println(deleteProduct);

        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("myBatisConfig.xml"));

        try (SqlSession session = sessionFactory.openSession()) {
            ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
            CategoriesMapper categoriesMapper = session.getMapper(CategoriesMapper.class);
            Products product = productsMapper.selectByPrimaryKey(444L);
            System.out.println(product);
            Categories category = categoriesMapper.selectByPrimaryKey(product.getCategoryId());
            System.out.println(category);
            MinimarketService service2 = new MinimarketService();
            ObjectResponse object2 = service2.createProduct();
            System.out.println(object2);
        }
    }
}