package com.mibaldi.kidbeacon.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mibaldi.kidbeacon.BeaconReferences.MonitoringActivity;
import com.mibaldi.kidbeacon.R;

import timber.log.Timber;

public class SocialAuthActivity extends AppCompatActivity implements View.OnClickListener {

  private GoogleApiClient client;
  private FirebaseAuth firebaseAuth;
  private FirebaseAuth.AuthStateListener authListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_social_auth);

    GoogleSignInOptions gso =
        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

    client = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

    firebaseAuth = FirebaseAuth.getInstance();

    findViewById(R.id.sign_in_button).setOnClickListener(this);

    authListener = firebaseAuth1 -> {
      FirebaseUser user = firebaseAuth1.getCurrentUser();
      if (user != null) {
        ((TextView)findViewById(R.id.tvName)).setText(user.getDisplayName());
        Timber.d("onAuthStateChanged:signed_in: %s", user.getUid());
      } else {
        Timber.d("onAuthStateChanged:signed_out");
      }
    };
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 1337) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      handleSignInResult(result);

    }
  }

  private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

    //String idToken = acct.getIdToken();
    //Timber.d("Token %s", idToken);

    AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
    firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
      Timber.d("signInWithCredential: onComplete: %s", task.isSuccessful());
      if (!task.isSuccessful()) {
        Timber.e("signInWithCredential: %s", task.getException());
        Toast.makeText(SocialAuthActivity.this, "Authentication failed.", Toast.LENGTH_SHORT)
            .show();
      }
      else {
        Intent intent = new Intent(getApplicationContext(), MonitoringActivity.class);
        startActivity(intent);

      }
    });
  }

  private void handleSignInResult(GoogleSignInResult result) {
    if (result.isSuccess()) {
      GoogleSignInAccount acct = result.getSignInAccount();
      firebaseAuthWithGoogle(acct);
      Timber.d("Display name: %s", acct.getDisplayName());
      Timber.d("Email: %s", acct.getEmail());
    } else {
      Timber.d("Signed out");
    }
  }

  @Override
  public void onClick(View v) {
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(client);
    startActivityForResult(signInIntent, 1337);
  }

  @Override
  protected void onStart() {
    super.onStart();
    firebaseAuth.addAuthStateListener(authListener);
  }

  @Override
  protected void onStop() {
    super.onStop();
    firebaseAuth.removeAuthStateListener(authListener);
  }
}
