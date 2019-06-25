package com.example.SimulacroParcial.controller;

import com.example.SimulacroParcial.domain.AllContent;
import com.example.SimulacroParcial.domain.Comment;
import com.example.SimulacroParcial.domain.Post;
import com.example.SimulacroParcial.domain.PostsByUser;
import com.example.SimulacroParcial.domain.User;
import com.example.SimulacroParcial.repository.PostByOwner;
import com.example.SimulacroParcial.repository.PostRepository;
import com.example.SimulacroParcial.repository.PostsByUserRepository;
import com.example.SimulacroParcial.repository.UserRepository;
import com.example.SimulacroParcial.services.AllContentService;
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
import java.util.concurrent.CompletableFuture;

import static com.example.SimulacroParcial.utils.ExceptionMessages.USER_NOT_FOUND;
import static java.lang.String.format;

@RequestMapping("/posts")
@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostsByUserRepository postsByUserRepository;

    @Autowired
    private AllContentService allContentService;

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

    @GetMapping("/PostsByUsers")
    private List<PostByOwner> getPostsCount(){
        return postRepository.getNumberPostsByOwner();
    }

    @GetMapping("/PostsByUsersEx")
    private List<PostsByUser> getPostsCountEx(){
        return postsByUserRepository.getNumberPostsByOwner();
    }

    @GetMapping("/allContent")
    private AllContent getAllContent(){
        CompletableFuture<List<Post>> posts = allContentService.getAllPosts();
        CompletableFuture<List<Comment>> comments = allContentService.getAllComments();
        CompletableFuture<List<User>> users = allContentService.getAllUsers();
        return new AllContent(posts.join(),users.join(),comments.join());
    }
}
