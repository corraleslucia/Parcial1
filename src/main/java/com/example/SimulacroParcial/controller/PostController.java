package com.example.SimulacroParcial.controller;

import com.example.SimulacroParcial.domain.Post;
import com.example.SimulacroParcial.domain.User;
import com.example.SimulacroParcial.repository.PostRepository;
import com.example.SimulacroParcial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.example.SimulacroParcial.utils.ExceptionMessages.USER_NOT_FOUND;
import static java.lang.String.format;

@RequestMapping("/posts")
@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{id}")
    private void create(@PathVariable Integer id, @RequestBody Post post) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, format(USER_NOT_FOUND.message(), id)));
        user.getPosts().add(post);
        post.setUser(user);
        userRepository.save(user);
        postRepository.save(post);
    }

    @GetMapping("")
    private List<Post> getAll() {
        return postRepository.findAll();
    }


}
