package com.example.SimulacroParcial.controller;

import com.example.SimulacroParcial.domain.Comment;
import com.example.SimulacroParcial.domain.Post;
import com.example.SimulacroParcial.domain.User;
import com.example.SimulacroParcial.repository.CommentRepository;
import com.example.SimulacroParcial.repository.PostRepository;
import com.example.SimulacroParcial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import static com.example.SimulacroParcial.utils.ExceptionMessages.COMMENT_NOT_FOUND;
import static com.example.SimulacroParcial.utils.ExceptionMessages.POST_NOT_FOUND;
import static com.example.SimulacroParcial.utils.ExceptionMessages.USER_NOT_FOUND;
import static java.lang.String.format;

@RequestMapping("/comments")
@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/{id_user}/{id_post}")
    private void create(@PathVariable("id_user") Integer idUser, @PathVariable("id_post") Integer idPost, @RequestBody Comment comment) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, format(USER_NOT_FOUND.message(), idUser)));
        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, format(POST_NOT_FOUND.message(), idPost)));
        user.getComments().add(comment);
        post.getComments().add(comment);
        comment.setUser(user);
        comment.setPost(post);
        userRepository.save(user);
        commentRepository.save(comment);
        postRepository.save(post);
    }


    @DeleteMapping("/{id}")
    private void delete(@PathVariable Integer id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, format(COMMENT_NOT_FOUND.message(), id)));
        User user = userRepository.findById(comment.getUser().getId())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, format(USER_NOT_FOUND.message(), id)));
        Post post = postRepository.findById(comment.getPost().getId())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, format(POST_NOT_FOUND.message(), id)));
        user.getComments().remove(comment);
        post.getComments().remove(comment);
        commentRepository.delete(comment);
        userRepository.save(user);
        postRepository.save(post);
    }
}
