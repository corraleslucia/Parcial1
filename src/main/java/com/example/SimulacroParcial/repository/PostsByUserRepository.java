package com.example.SimulacroParcial.repository;

import com.example.SimulacroParcial.domain.PostsByUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsByUserRepository extends JpaRepository<PostsByUser,String> {

    @Query(value="select p.title, u.first_name, count(u.id) as quantity from Post p inner join User u on p.user_id = u.id inner join comment c on u.id = c.user_id group by u.id",nativeQuery = true)
    List<PostsByUser> getNumberPostsByOwner();
}
