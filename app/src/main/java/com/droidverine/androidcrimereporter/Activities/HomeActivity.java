package com.droidverine.androidcrimereporter.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.droidverine.androidcrimereporter.R;
import com.droidverine.androidcrimereporter.utils.Connmanager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{
Button FileCompbtn,MyCompbtn;
    ProgressDialog progressDialog;
    GoogleApiClient mGoogleApiClient;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FileCompbtn=(Button)findViewById(R.id.filecompbtn);
        MyCompbtn=(Button)findViewById(R.id.yourcompbtn);
        FileCompbtn.setOnClickListener(this);
        MyCompbtn.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
        Log.d("Login", "Home mGoogleApiClient after");

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_sign_out)
        {

            if (new Connmanager(HomeActivity.this).checkNetworkConnection())
            {


                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to sign out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                progressDialog = new ProgressDialog(HomeActivity.this);
                                progressDialog.setMessage("Signing out");
                                progressDialog.setIndeterminate(true);
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                                mFirebaseAuth.signOut();
                                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                                startActivity(new Intent(HomeActivity.this,SignInActivity.class));

                             //   mUsername = ANONYMOUS;
//            startActivity(new Intent(this, Login.class));
//            finish();
                            //    signOutCall();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


            }
            else
            {

            }
            return true;
        }


        else
        {
          //  startActivity(new Intent(HomeActivity.this, Sponsors.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.filecompbtn:
                startActivity(new Intent(HomeActivity.this,FileComplaintActivity.class));
                break;
            case R.id.yourcompbtn:
                startActivity(new Intent(HomeActivity.this,MyComplaintActivity.class));
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
