package com.ckgexample.miniboard.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;



@Entity
@Table(name = "authority")
@Data
public class Authority extends BaseModel {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 0, max = 20)
	@Id
	@Column(name = "name", length = 20)
	private String name;

	@Override public boolean equals(Object o) {
		if (this == o) {
			return true; 
		}
		if (o == null || getClass() != o.getClass()) {
			return false; 
		}
	 
		Authority authority = (Authority) o;
	
		return !(name != null ? !name.equals(authority.name) : authority.name != null); 
	}
	  
	@Override public int hashCode() { 
		return name != null ? name.hashCode() : 0;
	}

}
