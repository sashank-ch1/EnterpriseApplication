package com.example.demo.entity;

import jakarta.persistence.*;

@Entity

public class Orders {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
	@Column(nullable=false)
private String productid;
	private double price;
	
	public Orders() {
		
	}

	public Orders(Long id,String productid,double price) {
		this.id=id;
		this.productid=productid;
		this.price=price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	@Override
	public String toString() {
		return "Orders [id=" + id + ", productid=" + productid + "]";
	}

	public void setPrice(double price) {
		this.price=price;
		
	}
  public void getPrice(double price)
  {
	  this.price=price;
  }
	
}
