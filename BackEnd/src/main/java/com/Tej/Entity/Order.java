package com.Tej.Entity;

import java.util.Date;
import java.util.List;

import com.Tej.DTO.RestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Table(name = "Orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private User customer;
	
	@JsonIgnore
	@ManyToOne
	private Restaurant res;
	
	private Long totalAmount;
	
	private String orderStatus;
	
	private Date createdAt;
	
	@ManyToOne
	private Address deliveryAddress;
	
	@OneToMany
	private List<OrderItem> items;
	
	private Integer totalItem;
	
	private Long totalPrice;
	
	
}
