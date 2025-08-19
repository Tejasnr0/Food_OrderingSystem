package com.Tej.Entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfo {

	private String email;
	private String mobile;
	private String instagram;
	private String Website;
}
