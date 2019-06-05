package com.example.SimulacroParcial.utils;

public enum ExceptionMessages {

  USER_NOT_FOUND("No existe el usuario con el id: %s"),
  POST_NOT_FOUND("No existe la publicacion con el id: %s"),
  COMMENT_NOT_FOUND("No existe el comentario con el id: %s");

  private String message;

  private ExceptionMessages(String message){
    this.message = message;
  }
  public String message(){
    return message;
  }
}
