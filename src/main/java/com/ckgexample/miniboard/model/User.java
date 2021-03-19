package com.ckgexample.miniboard.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name= "user")
@Data
public class User {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column( name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email
	@Size( min = 5, max = 30)
	@Column( length = 30, unique = true, nullable = false)
	private String email;
	
	@JsonIgnore
	@NotNull
	@Size( min = 60, max = 60)
	@Column( name = "password_hash", length = 60, nullable = false)
	private String password;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable( 
			name = "user_authority"
			, joinColumns = {@JoinColumn( name = "user_id", referencedColumnName = "user_id")}
			, inverseJoinColumns = {@JoinColumn( name = "authority_name", referencedColumnName = "name")})
	@BatchSize( size = 20 )
	private Set<Authority> authorities = new HashSet<>();
	
	@OneToMany(mappedBy = "user")
	@JsonBackReference
	private Set<Post> posts;
	
	@CreatedBy
	@Column( name = "created_by", length = 50, updatable = false)
	@JsonIgnore
	private String createdBy;
		
	@CreatedDate
	@Column( name = "created_date")
	@JsonIgnore
	private LocalDateTime createdDate = LocalDateTime.now();
	
	@LastModifiedBy
	@Column( name = "last_modified_by", length = 50)
	@JsonIgnore
	private String lastModifiedBy;
	
	@LastModifiedDate
	@Column( name = "last_modified_date")
	@JsonIgnore
	private LocalDateTime lastMOdifiedDate = LocalDateTime.now();
	
}

