package com.qa.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)

public class Contacts {
	@JsonProperty("_id")
	private String id;
	private String birthdate;
	private String email;
	private String phone;
	private String firstName;
	private String lastName;
	private String city;
	private String country;
	private Integer postalCode;			
	private String stateProvince;
	private String street1;
	private String street2;	
}
