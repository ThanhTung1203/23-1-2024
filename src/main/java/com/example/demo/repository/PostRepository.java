package com.example.demo.repository;

import com.example.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByTitleContainingOrContentContaining(String word,String word2);
    List<Post> findByOrderByCreateAtDesc();
    List<Post> findAllByStatusId(Long status_id);

}
