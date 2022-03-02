package com.bank.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "yunki")
public class Yanki {
	
	@Id
	private String id;
	private String phonenumber;
	private String typedocument;
	private String documentnumber;
	private String imei;
	private String email;
	private Double balance;
	private String idaccount;
	
	//private static final long serialVersionUID = 1L;
}
