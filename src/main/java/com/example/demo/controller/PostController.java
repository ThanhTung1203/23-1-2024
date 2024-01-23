package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.Status;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    StatusRepository statusRepository;
    @GetMapping
    public ResponseEntity findAll(){
        return new ResponseEntity(postRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Post post){
        return new ResponseEntity(postRepository.save(post),HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity save(@RequestBody Post post, @PathVariable Long id){
        post.setId(id);
        return new ResponseEntity(postRepository.save(post),HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity showEdit(@PathVariable Long id){
        return new ResponseEntity(postRepository.findById(id),HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id){
        postRepository.deleteById(id);
        return new ResponseEntity("delete than cong",HttpStatus.OK);
    }
    @GetMapping("search")
    public ResponseEntity<List<Post>> searchByName(@RequestParam String keyword){
        List<Post> posts = postRepository.findByTitleContainingOrContentContaining(keyword,keyword);
        return new ResponseEntity(posts,HttpStatus.OK);
    }
    @GetMapping("sort")
    public  ResponseEntity sortByCreateAT(){
        List<Post> posts = postRepository.findByOrderByCreateAtDesc();
        return new ResponseEntity(posts,HttpStatus.OK);
    }

    @GetMapping("/public")
    public ResponseEntity<List<Post>> sortByStt() {
        // chỗ này là GPT tại không hiểu làm sao để mặc định id_status là 2(public)
        Long statusId = 2L;
        Optional<Status> status = statusRepository.findById(statusId);
        if (status.isPresent()) {
            List<Post> posts = postRepository.findAllByStatusId(statusId);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
