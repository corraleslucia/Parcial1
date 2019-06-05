package com.example.SimulacroParcial.repository;

import com.example.SimulacroParcial.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/posts")
@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
}
