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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

import static com.example.SimulacroParcial.utils.ExceptionMessages.USER_NOT_FOUND;
import static java.lang.String.format;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("")
    public void add(@RequestBody User user, @RequestHeader Map<String, String> headers) {
        user.setBrowser(headers.get("user-agent"));
        userRepository.save(user);
    }

    @PutMapping("/{id}")
    private User update(@PathVariable Integer id, @RequestBody User newUser) {
        return userRepository.findById(id).map(user -> {
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName());
            user.setBrowser(newUser.getBrowser());
            return userRepository.save(user);
        })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, format(USER_NOT_FOUND.message(), id)));
        List<Post> postsToDelete = user.getPosts();
        List<Comment> commentToDelete = user.getComments();
        postsToDelete.forEach(post -> postRepository.delete(post));
        commentToDelete.forEach(comment -> commentRepository.delete(comment));
        userRepository.deleteById(id);
    }

    @GetMapping("")
    private List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    private User getById(@PathVariable Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, format(USER_NOT_FOUND.message(), id)));
    }


}
