package com.example.SimulacroParcial.services;

import com.example.SimulacroParcial.domain.Comment;
import com.example.SimulacroParcial.domain.Post;
import com.example.SimulacroParcial.domain.User;
import com.example.SimulacroParcial.repository.CommentRepository;
import com.example.SimulacroParcial.repository.PostRepository;
import com.example.SimulacroParcial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

@Service
public class AllContentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<Post>> getAllPosts() {
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(postRepository.findAll());
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<User>> getAllUsers() {
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(userRepository.findAll());
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<Comment>> getAllComments() {
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(commentRepository.findAll());
    }
}
