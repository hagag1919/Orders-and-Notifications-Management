package com.example.ordernotificationmanagment.Services;

import com.example.ordernotificationmanagment.Models.Category;
import com.example.ordernotificationmanagment.Models.Product;

import java.util.List;

public interface IProduct_CategoryServices
{
    public String addProduct(Product product);

    public String addCategory(Category category);

    public List<Product> displayAvailableProducts();


    public int displayRemanining_SpecificCategory(int categoryId);


}
