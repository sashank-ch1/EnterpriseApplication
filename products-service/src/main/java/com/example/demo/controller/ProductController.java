package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Products;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
	public ProductService proserv;
    
	@PostMapping("/post")
    public List<Products> AddProducts(@RequestBody List<Products> productlist){
    	List<Products> addproducts=proserv.addproducts(productlist);
    	return addproducts;
    }
	
	// Get single product by productId
    @GetMapping("/{productId}")
    public Object getProduct(@PathVariable String productId) {
        return proserv.getProduct(productId);
    }
    @GetMapping("/getlist")
    public List<Products> GetProducts(){
    	return proserv.getproducts();
    }
    @PutMapping("/upd")
    public String UpdProducts(@RequestBody Products pid) {
    	proserv.updateprod(pid);
    	return "Poducts List updated";
    }
    @DeleteMapping("/del/{id}")
    public String DelProducts(@PathVariable Long id) {
    	proserv.delproduct(id);
    	return "Products deleted from the list";
    }
}
