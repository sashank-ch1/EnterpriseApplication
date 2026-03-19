package com.example.demo.dto;

public class ProductDTO {
	
	private String productId;
    private String name;
    private double price;
    private int qunt;
    public ProductDTO() {
    	
    }
public ProductDTO(String productId,String name,double price,int qunt) {
    	this.productId=productId;
    	this.name=name;
    	this.price=price;
    	this.qunt=qunt;
    }
public String getProductId() {
	return productId;
}
public void setProductId(String productId) {
	this.productId = productId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public int getQunt() {
	return qunt;
}
public void setQunt(int qunt) {
	this.qunt = qunt;
}
@Override
public String toString() {
	return "ProductDTO [productId=" + productId + ", name=" + name + ", price=" + price + ", qunt=" + qunt + "]";
}

}
