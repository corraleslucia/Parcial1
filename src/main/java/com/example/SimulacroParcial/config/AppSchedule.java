package com.example.SimulacroParcial.config;

import com.example.SimulacroParcial.domain.Comment;
import com.example.SimulacroParcial.domain.Post;
import com.example.SimulacroParcial.domain.User;
import com.example.SimulacroParcial.repository.CommentRepository;
import com.example.SimulacroParcial.repository.PostRepository;
import com.example.SimulacroParcial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.example.SimulacroParcial.properties.ScheduledProperties.SCHEDULED_PROPERTIES;
import static java.lang.Integer.valueOf;

@Component
public class AppSchedule {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Scheduled(fixedRate = 60000)
    public void deleteOldComments() {
        List<Comment> commentsToDelete = commentRepository.findAll();
        commentsToDelete.forEach(comment -> {
            if (LocalDateTime.from(comment.getDate()).until(LocalDateTime.now(), ChronoUnit.MINUTES) >= valueOf(SCHEDULED_PROPERTIES.getConditionTime())) {
                Post post = postRepository.findById(comment.getPost().getId()).get();
                post.getComments().remove(comment);
                postRepository.save(post);
                User user = userRepository.findById(comment.getUser().getId()).get();
                user.getComments().remove(comment);
                userRepository.save(user);
                commentRepository.delete(comment);
            }
        });
    }
}



