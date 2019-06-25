package com.example.SimulacroParcial.repository;

import com.example.SimulacroParcial.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/posts")
@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query(value="select p.title, u.first_name, count(*) as quantity from Post p inner join User u on p.user_id = u.id inner join comment c on u.id = c.user_id group by u.id",nativeQuery = true)
    List<PostByOwner> getNumberPostsByOwner();
}
