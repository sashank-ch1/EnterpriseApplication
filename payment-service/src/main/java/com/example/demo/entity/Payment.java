package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
public class Payment {
	@Id
private Long id;
	@Column(nullable=false)
private String status;
	
	public Payment() {
		
	}
	public Payment(Long id,String status) {
		this.id=id;
		this.status=status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Payment [id=" + id + ", status=" + status + "]";
	}
	
}
