package com.mibaldi.kidbeacon.Auth;



import com.mibaldi.kidbeacon.Data.Database.Models.User;

import rx.Observable;

public interface AuthenticationService {

  Observable<User> createUser(String email, String password);

  Observable<User> loginUser(String email, String password);
}
