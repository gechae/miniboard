package com.ckgexample.miniboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ckgexample.miniboard.model.User;

/*
 *  Spring Data JPA에서 제공하는 JpaRepository는 인터페이스를 상속하는 것만으로
 *  직접 쿼리를 만들지 않고 몇가지 규칙을 이용하여 데이터 추출을 위한 메소드를 만들수 있다.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	//id 로 User를 조회 
	Optional<User> findOneById(Long id);
	// email로 User를 조회
	Optional<User> findONeByEmail(String email);
}
