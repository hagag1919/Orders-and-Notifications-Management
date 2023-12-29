package com.example.ordernotificationmanagment.Services;

import com.example.ordernotificationmanagment.Database.Db;
import com.example.ordernotificationmanagment.Models.Category;
import com.example.ordernotificationmanagment.Models.Product;

import java.util.List;

public class Product_CategoryServices implements IProduct_CategoryServices
{
    private final Db db = Db.getInstance();

    @Override
    public String addProduct(Product product)
    {
        //Check IF Product Exisited Before
        for(int i = 0; i< db.getProducts().size() ; i++)
        {
            if(db.getProducts().get(i).getSerialNumber().equals(product.getSerialNumber()))
            {
                return "Product With This SerialNumber Already Exisited";
            }
        }

        //Adding New Product To Specific Category
        for(int i =0 ; i<db.getCategories().size() ; i++)
        {
            if(db.getCategories().get(i).getId() == product.getCategory_Id())
            {
                db.getProducts().add(product);
                db.getCategories().get(i).setProducts(product);
                return "Product Added Successfully";
            }
        }

        //CategoryId Assigned To Product Is not Exisit
        return "Category Not Found";

    }

    @Override
    public String addCategory(Category category)
    {
        //Check IF Category Already Exisited
        for(int i =0 ; i<db.getCategories().size(); i++)
        {
            if(db.getCategories().get(i).getId() == category.getId())
            {
                return "Category Already Exisited";
            }
        }
        //Adding New Category
        db.getCategories().add(category);

        //Adding Products To Products Database
        for(int i = 0 ; i < category.getProducts().size() ; i++)
        {
            db.getProducts().add(category.getProducts().get(i));
        }

        return "Category Added Successfully";
    }

    @Override
    public List<Product> displayAvailableProducts()
    {
        return db.getProducts();
    }


    @Override
    public int displayRemanining_SpecificCategory(int categoryId)
    {
        //Check IF Category Already Exisited
        //IF Exisited Return Remanining OF Products
        for(int i =0 ; i<db.getCategories().size(); i++)
        {
            if(db.getCategories().get(i).getId() == categoryId)
            {
                return db.getCategories().get(i).getRemaning() ;
            }
        }

        return 0;
    }

}
