package com.example.layout.exp2d;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class order_place extends Activity  implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    //we will use these constants later to pass the artist name and id to another activity
    public static final String NAME = "com.example.layout.exp2d.name";
    public static final String ADDRESS = "com.example.layout.exp2d.address";
    //Button button1;
    FirebaseAuth firebaseAuth;
    //a list to store all the artist from firebase database
    List<Order_Details> orders;

    //our database reference object
    DatabaseReference databaseOrders;
    String[] bankNames={"", "1L Bottle", "20L Can", "Bulk orders"};
    private TextView textViewUserEmail;
    private Button buttonLogout, buttonOrder;
    private EditText editTextName, editTextAddress, editTextQuantity;
    private Spinner spinnerType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_place_xml);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bankNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        //getting the reference of artists node
        databaseOrders = FirebaseDatabase.getInstance().getReference("orders");

        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textView14);
        buttonLogout = (Button) findViewById(R.id.button8);
        buttonOrder = (Button) findViewById(R.id.button6);

        //displaying logged in user name
        textViewUserEmail.setText("Welcome " + user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);
        //list to store artists
        orders = new ArrayList<>();


        //adding an onclicklistener to button
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addOrders();

            }
        });
    }

    @SuppressLint("CutPasteId")
    private void addOrders() {
        editTextName = (EditText) findViewById(R.id.editText);
        editTextAddress = (EditText) findViewById(R.id.editText8);
        editTextQuantity = (EditText) findViewById(R.id.editText7);
        Spinner spinnerType = (Spinner) findViewById(R.id.spinner);

        //getting the values to save
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String qty = editTextQuantity.getText().toString().trim();
        String type = spinnerType.getSelectedItem().toString();

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseOrders.push().getKey();

            //creating an Artist Object
            Order_Details order = new Order_Details(id, name, address, qty, type);

            //Saving the Artist
            databaseOrders.child(id).setValue(order);

            //setting edittext to blank again
            editTextName.setText("");

            //displaying a success toast
            Toast.makeText(getApplicationContext(), "ORDER PLACED SUCCESSFULLY", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }

    /*public void prog(View v)
    {
        Toast.makeText(getApplicationContext(), "ORDER PLACED SUCCESSFULLY", Toast.LENGTH_LONG).show();
        //Intent i = new Intent(this,progress.class);
        //startActivity(i);
    }*/
    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        //Toast.makeText(getApplicationContext(), bankNames[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub
    }

    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
