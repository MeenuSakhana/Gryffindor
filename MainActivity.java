package com.example.layout.exp2d;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;
    Button buttonSignIn;
    EditText editTextPassword, editTextEmail;
    private TextView textViewSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), order_place.class));
        }
        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editText2);
        editTextPassword = (EditText) findViewById(R.id.editText3);
        buttonSignIn = (Button) findViewById(R.id.button3);
        textViewSignup  = (TextView) findViewById(R.id.textView10);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        progressDialog = new ProgressDialog(this);

        //attaching click listener
        textViewSignup.setOnClickListener(this);
        buttonSignIn.setOnClickListener(this);

    }


    /*public void alertCheck(View v)
    {
        b3 = (Button)findViewById(R.id.button3);
        b5 = (Button)findViewById(R.id.button5);
        e2 = (EditText)findViewById(R.id.editText2);
        e3 = (EditText)findViewById(R.id.editText3);

        String usr = e2.getText().toString();
        String passwd = e3.getText().toString();

        AlertDialog.Builder alertdb = new AlertDialog.Builder(this);
        if(usr.equals(""))
        {
            alertdb.setTitle("User Name Mandatory!!");
            alertdb.setIcon(R.drawable.ic_error);
            alertdb.setMessage("Enter the Username field...");
            alertdb.setCancelable(false);
            alertdb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(), "Positive works", Toast.LENGTH_SHORT).show();
                }
            });
            alertdb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(), "Negative works", Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alertd = alertdb.create();
            alertd.show();
        }

        if(passwd.equals("")) {
            alertdb.setTitle("Password Mandatory!!");
            alertdb.setIcon(R.drawable.ic_error);
            alertdb.setMessage("Enter the password field...");
            alertdb.setCancelable(false);
            alertdb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   // Toast.makeText(getApplicationContext(), "Positive works", Toast.LENGTH_SHORT).show();
                }
            });
            alertdb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   // Toast.makeText(getApplicationContext(), "Negative works", Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alertd = alertdb.create();
            alertd.show();
        }

        if(!(usr.equals("") && passwd.equals("")))
        {
            Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
            Intent to_order = new Intent(this , order_place.class);
            startActivity(to_order);
        }
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //method for user login
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), order_place.class));
                        }
                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_feedback){
            Intent i1 = new Intent(this,feedback.class);
            startActivity(i1);
            return true;
        }
        else if (id == R.id.action_help){
            return true;
        }
        else{
            return false;
        }

        //return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.signup) {
            Intent intent = new Intent(MainActivity.this,signup.class);
            startActivity(intent);
        } else if (id == R.id.login) {

        } else if (id == R.id.order) {

        } else if (id == R.id.contact) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignIn){
            userLogin();
        }

        if(view == textViewSignup){
            finish();
            startActivity(new Intent(this, signup.class));
        }
    }
}
