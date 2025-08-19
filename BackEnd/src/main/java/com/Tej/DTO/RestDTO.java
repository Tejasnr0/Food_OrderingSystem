package com.Tej.DTO;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * So, the frontend gets only the needed info, 
 * not the full Restaurant entity
 *  (which may contain contact info, address, timestamps, etc.).
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable //OPtional
public class RestDTO {

	private String title;
	
	
	@Column(length = 1000)
	private List<String> image;
	
	private String description;
	
	private Long id;
}
