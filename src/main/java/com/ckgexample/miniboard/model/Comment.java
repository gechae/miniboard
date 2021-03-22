package com.ckgexample.miniboard.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table( name="comments")
@Data
public class Comment {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column( name="comments_id" )
	private Long id;
	
	@Column( name="body", columnDefinition="TEXT" )
	private String body;
	
	@Column( name="create_date", nullable=false, updatable=false )
	@NotNull
	private LocalDateTime createDate;
	
	@ManyToOne 
	@JoinColumn( name="posts_id", referencedColumnName="posts_id", nullable=false)
	@NotNull
	private Post posts;
	 
	@ManyToOne
	@JoinColumn( name="users_id", referencedColumnName=" users_id", nullable=false) 
	@NotNull 
	private User users;
	 
}
