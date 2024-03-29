package com.mibaldi.kidbeacon.Data.Database.Models;

public final class User {

  private final String id;
  private final String email;

  public User(String id, String email) {
    this.id = id;
    this.email = email;
  }

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }
}
