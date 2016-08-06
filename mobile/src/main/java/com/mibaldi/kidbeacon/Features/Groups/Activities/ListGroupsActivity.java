package com.mibaldi.kidbeacon.Features.Groups.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.Features.Auth.SocialAuthActivity;
import com.mibaldi.kidbeacon.Features.Groups.Fragments.ListGroupsFragment;
import com.mibaldi.kidbeacon.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class ListGroupsActivity extends AppCompatActivity {


    private GoogleApiClient mGoogleApiClient = SocialAuthActivity.client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_groups);

        mGoogleApiClient.connect();
        ButterKnife.bind(this);
        ListGroupsFragment listGroupsFragment = ListGroupsFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,listGroupsFragment).commit();
    }
    @OnClick(R.id.logout)
    public void logout(){

        if (mGoogleApiClient.isConnected()) {
            Timber.d("conectado se puede desconectar");
            FirebaseAuth.getInstance().signOut();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            Logout();

        }else {
            Timber.d("sin conectar");
        }

    }
    @OnClick(R.id.addGroup)
    public void addGroup(){
        Intent intent = new Intent(this,GroupSettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }
    public  void Logout(){
        if (this != null) {
            Intent intent = new Intent(this,SocialAuthActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.startActivity(intent);
        }
    }
}
