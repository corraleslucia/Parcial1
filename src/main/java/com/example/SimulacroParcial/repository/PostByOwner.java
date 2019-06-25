package com.example.SimulacroParcial.repository;

import org.springframework.beans.factory.annotation.Value;

public interface PostByOwner {
    public String getTitle();

    @Value("#{target.first_name}")
    public String getFirstName();
    public Integer getQuantity();
}
