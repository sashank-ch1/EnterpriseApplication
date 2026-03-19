package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Products;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
public ProductRepository prorepo;
	
	public List<Products> addproducts(List<Products> productlist){
		List<Products> savepro=prorepo.saveAll(productlist);
		return savepro;
	}
	
	public List<Products> getproducts( ){
		List<Products> getlist=prorepo.findAll();
		return getlist;
	}
	public Object getProduct(String productId) {
	    return prorepo.findByProductId(productId);
	}
	public String updateprod(Products pid) {
		prorepo.save(pid);
		return "product updated from the list";
	}
	public String delproduct(Long id) {
		prorepo.deleteById(id);
		return "product deleted from the cart";
	}
	
	
	
}
