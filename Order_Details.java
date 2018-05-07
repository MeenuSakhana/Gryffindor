package com.example.layout.exp2d;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Order_Details {

        private String Id;
        private String Name;
        private String Address;
        private String Quantity;
        private String Type;

        public Order_Details(String id, EditText editTextName, EditText editTextAddress, EditText editTextQuantity, Spinner spinnerType)
        {
            //this constructor is required
        }

        public Order_Details(String Id, String Name, String Address, String Quantity, String Type) {
            this.Id = Id;
            this.Name = Name;
            this.Address = Address;
            this.Quantity = Quantity;
            this.Type = Type;
        }

        public String getId() {
            return Id;
        }

        public String getName() {
            return Name;
        }

        public String getAddress() {
            return Address;
        }

        public String getQuantity() {
            return Quantity;
        }

        public String getType() {
            return Type;
        }
    }

