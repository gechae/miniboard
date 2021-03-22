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
@Table(name="users")
@Data
public class User extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="users_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email
	@Size(min=5,max=30)
	@Column(name="email",length=30, unique=true, nullable=false)
	private String email;
	
	/*\
	// 추가
    @Size(min = 1, max = 30)
    @Column(name = "user_name", length = 30, nullable = false)
    private String userName;
    */
	@JsonIgnore
	@NotNull
	@Size(min=60,max=60)
	@Column( name="password_hash", length=60, nullable=false)
	private String password;
	
	
	@JsonIgnore
	@ManyToMany 
	@JoinTable( name="users_authority" 
				,joinColumns = @JoinColumn(	name="users_id", referencedColumnName="users_id" ) 
				,inverseJoinColumns= @JoinColumn( name="authority_name", referencedColumnName="name" ))
	@BatchSize( size = 20 )
	private Set<Authority> authorities = new HashSet<>();
	
	@OneToMany(mappedBy="users")
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
	private LocalDateTime lastModifiedDate = LocalDateTime.now();
	
	/*
	// 추가
	public User() {}

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;
        return !(user.getId() == null || getId() == null) && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }*/
}

