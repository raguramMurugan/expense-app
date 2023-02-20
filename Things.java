package com.hubino.expensetrackingapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "things")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Things {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer thingsId;
	
	private String thingsName;
	private String description;
	
	private Integer availableQuantity;
	private Double price;

}
