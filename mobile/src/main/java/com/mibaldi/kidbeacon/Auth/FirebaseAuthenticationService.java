package com.mibaldi.kidbeacon.Auth;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mibaldi.kidbeacon.Data.Database.Models.User;

import rx.AsyncEmitter;
import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;

public class FirebaseAuthenticationService implements AuthenticationService {

  private FirebaseAuth auth;
  private FirebaseAuth.AuthStateListener authListener;

  public FirebaseAuthenticationService() {
    auth = FirebaseAuth.getInstance();

    authListener = firebaseAuth -> {
      FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
      if (firebaseUser != null) {
        Timber.d("User is logged in");
      } else {
        Timber.d("User is not logged in");
      }
    };
  }

  private Observable<User> createFirebaseUser(String email, String password) {

    return Observable.fromAsync(emitter -> auth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener(authResult -> {
          FirebaseUser user = authResult.getUser();
          emitter.onNext(new User(user.getUid(), user.getEmail()));
          emitter.onCompleted();
        })
        .addOnFailureListener(emitter::onError), AsyncEmitter.BackpressureMode.BUFFER);
  }

  @Override
  public Observable<User> createUser(String email, String password) {
    return Observable.create(new Observable.OnSubscribe<User>() {
      @Override
      public void call(Subscriber<? super User> subscriber) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

          if (task.isSuccessful()) {
            FirebaseUser user = task.getResult().getUser();

            Timber.d("User created: %s", user.getUid());

            subscriber.onNext(new User(user.getUid(), user.getEmail()));
            subscriber.onCompleted();
          } else {
            Timber.e("Error: %s", task.getException());
            subscriber.onError(task.getException());
          }
        });
      }
    });
  }

  @Override
  public Observable<User> loginUser(String email, String password) {
    return null;
  }
}
