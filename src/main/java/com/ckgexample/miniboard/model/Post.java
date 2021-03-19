package com.ckgexample.miniboard.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

/*
 * @Entity 데이터베이스의 테이블과 일대일로 매칭되는 객체단위이며 
 * Entity 객체의 인스턴스 하나가 테이블에서 하나의 레코드 값을 의미
 * 
 * @Id : 데이터베이스에서 의미하는 Primary Key를 의미
 * 
 * spring.jpa.hibernate.ddl-auto 설정이 create/update로 되어 있을 경우
 * Spring Project가 시작될때 Entity Manager가 자동으로 DDL을 수행해 테이블을 생성해 준다. 
 * spring.jpa.hibernate.ddl-auto 
 * - create : 최초에 한번 컬럼이 생성
 * - update : Entity 클래스에 있지만 해당 테이블에 존재하지 않는 컬럼을 추가로 생성
 *            하지만 컬럼의 데이터 타입이 변경되었거나 길이가 변경 되었을때 자동으로 반영하지 않는다.
 *            기존 테이블을 drop후 새롭게 create 하던지 alter table을 통해 직접 ddl 구문을 적용해야한다.
 * - create-drop : 프로젝트가 시작될때 자동으로 기존 테이블을 drop한구 create를 해준다.
 *                 기존 스키마를 전부 삭제되기 때문에 시스템 설계와 개발 시점에만 사용해야하며
 *                 운영 시점에 crate,update,create-drop을 사용하지 않아야한다.
 *    
 * @Table : name 속성을 이용해 데티어베이스상의 실제 테이블 명칭을 지정하지 않는다면 
 * Entity 클래스의 이름 그대로 CamelCase를 유지한채 테이블이 생성되기 때문에 테이블 이름을 명시적으로 작성하는 것이 관례이다.
 * 왜냐하면 데이터베이스상에서 보편적으로 사용되는 명명법은 UnderScore가 원칙이기 때문이다.
 * 
 * @Column : 데이터베이스의 테이블에 있는 컬럼과 동일하게 1:1로 매칭되기 때문에 Entity 클래스 안에 내부 변수로 정의한다.
 * 
 * @GeneratedValue : 데이터 경합으로 인해 발생되는 데드락 같은 현상을 방지하기 위해 대부분 BigInterger.
 *                   즉, Java의 Long을 주로 사용
 * deadLock : 동일한 시점에 요청이 유입되었을때 데이터베이스는 테이블 혹인 레코드를 lock을 걸어
 *            데이터가 변경되지 않도록 막아 놓고 다른 작업을 진행              
 * - 속성 
 * -- strategy = GenrationType.IDENTITY : auto increment 와 동릴 
 * -- strategy = GenrationTYpe.SEQUENCE : 기존 값의 +1을 반환 해 주는 장식, @SequenceGenerator 설정 필요
 * 
 * @EmbeddedId : 복합키를 생성할때 사용되는 어노테이션 Entity에 해당 Value를 PK로 사용한다고 지정
 * @Enumerated : java의 enum 형태로 되어있는 미리 정의되어 있는 코드값이나 구분값을 데이터 타입으로 사용하조가 할때 사용
 * -- Type(EnumType)
 * --- ORDINAL : enum객체에 정의도니 순서가 컬럼의 값으로 사용
 * --- STRING : enum의 문자열 자체가 컬럼의 값으로 사용
 * 
 * @Transient : Entity 객체에 속성으로서 지정되어 있지만 데이터베이스상에 필요없는 속성이라면 
 *              @Transient를 이용해서 해당 속성을 데이터베이스에서 이용하지 않겠다고 정의
 *              이렇게 하면 해당 속정을 Entity 객체에 임시로 값을 담는다는 의미로 사용가능
 *              
 * @ManyToOne : 다대일 관계 설정
 * @OneToMany : 일대다 관계 설정
 * 
 * ### 순환 참조 ###
 * @JsonIgnore: json 데이터에 해당 프로퍼티는 Null로 들어가 된다. 즉, 데이터에 아예 포함이 안되게 된다.
 * @JsonManagedReference/@JsonBackReference : 순환참조를 방어.
 *  부모 클래스에 @JsonManagedReference 를 자식 클래스 측에 @JsonBackReference 추가
 * 참고) DTO 사용 : dto 객체를 만들어 필요한 데이터만 옮겨담아 Client로 리턴하면 순환참조와 관련된 문제는 애초에 방어    
 * 
 * ### Auditing ###
 * @CreateBy, CreatedData, LastModifiedBy, LastModifiedDatae
 * - 네이밍 그대로 누가 언제 생성하였고, 누가 언제 변경하였는지를 의미
 */
@Data
@Entity
@Table(name = "post")
public class Post {
	
	@Id 
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name = "post_id")
	private Long id;
	
	@Column( name = "title", nullable = false )
	@Length(min = 1)
	private String title;
	
	@Column( name = "body", columnDefinition = "TEXT" )
	private String body;
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn( name = "user_id", referencedColumnName = "user_id", nullable = false )
	@NotNull
	private User user;
	
	@OneToMany( mappedBy = "post", cascade = CascadeType.REMOVE )
	private Set<Comment> comments;
	
	@CreatedBy
	@Column( name = "created_by", length = 50, updatable = false )
	private String createdBy;
	
	@LastModifiedBy
	@Column( name = "last_modified_by", length = 50 )
	private String lastModifiedBy;
	
	@LastModifiedDate
	@Column( name = "last_modified_date" )
	private LocalDateTime lastModifiedDate = LocalDateTime.now();
}
