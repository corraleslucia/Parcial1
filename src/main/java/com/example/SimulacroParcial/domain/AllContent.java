package com.example.SimulacroParcial.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllContent {

    private List<Post> posts;
    private List<User> users;
    private List<Comment> comments;
}
