package com.bank.model;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bootcoin {
	
	@Id
	private String id;
	private String typedocument;
	private String documentnumber;
	private String phonenumber;
	private String email;
	private Double bootcoin;
	
	private String idyankiaccount;
	private List<AccountNumber> accountnumber;
	private Boolean typebuyer=true;
	private Boolean typeseller=false;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime creationdate;
	
	private Boolean status=true;
}
