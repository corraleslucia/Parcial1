package com.example.SimulacroParcial.repository;

import com.example.SimulacroParcial.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/comments")
@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
