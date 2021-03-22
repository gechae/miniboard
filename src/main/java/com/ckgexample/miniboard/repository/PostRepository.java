package com.ckgexample.miniboard.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ckgexample.miniboard.model.Post;
import com.ckgexample.miniboard.model.User;

/*
 * User를 기반으로 User의 Post를 페이징 형태로 조회하기 위한 메소드를 추가
 * 전체 Post를 페이징 형태로 조회하기 위한 메소드를 추가
 * 위 내용으로 메소드의 경우 OrderByDateDesc를 접미사로 붙인 것을 볼 수 있는데 
 * post의 경구 생성된 날짜 역순으로 반환2
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	Page<Post> findByUsersOrderByCreatedDateDesc(User users, Pageable pageable);
	
	Page<Post> findAllByOrderByCreatedDateDesc(Pageable pageable);
	
	Optional<Post> findById(long Id);

}
