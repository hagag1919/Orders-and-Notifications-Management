package com.example.ordernotificationmanagment.Controller;

import com.example.ordernotificationmanagment.Models.Category;
import com.example.ordernotificationmanagment.Models.Product;
import com.example.ordernotificationmanagment.Services.IProduct_CategoryServices;
import com.example.ordernotificationmanagment.Services.Product_CategoryServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productCategory")
public class Product_Category_Controller
{
    private final IProduct_CategoryServices services = new Product_CategoryServices();


    @PostMapping(value="/product/add")
    public String addProduct(@RequestBody Product product)
    {
        return this.services.addProduct(product);
    }


    @PostMapping(value="/category/add")
    public String addCategory(@RequestBody Category category)
    {
        return this.services.addCategory(category);
    }


    @GetMapping(value="/products/get")
    public List<Product> getProducts()
    {
        return this.services.displayAvailableProducts();
    }


    @GetMapping(value="/categoryRemaning/{id}")
    public int getRemaning(@PathVariable("id") int id)
    {
        return this.services.displayRemanining_SpecificCategory(id);
    }

}