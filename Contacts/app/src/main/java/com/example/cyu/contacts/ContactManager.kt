package com.example.cyu.contacts

import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_contact_manager.*

class ContactManager : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_manager)

        // Getting id value pass by MainActivity via Intent
        // If no value pass, it will return 0
        var recordID = intent.getIntExtra("id", 0)

        if (recordID == 0) {
            save_btn.text = "Add Contact"
        }

        else {
            save_btn.text = "Update Contact"
            var _fname = intent.getStringExtra("fname")
            var _lname = intent.getStringExtra("lname")
            var _email = intent.getStringExtra("email")
            var _phone = intent.getStringExtra("phone")

            fnametxt.setText(_fname)
            lnametxt.setText(_lname)
            emailtxt.setText(_email)
            phone_txt.setText(_phone)
        }

        // Save Button
        save_btn.setOnClickListener {

            // Capitalize the first letter of first name and first letter of last name.
            var a = fnametxt.text.toString().capitalize()
            var b = lnametxt.text.toString().capitalize()

            var c = emailtxt.text.toString()
            var d = phone_txt.text.toString()


            // If first name text field is empty display Toast Message
            if (isEmpty(a)) {
                Toast.makeText(this, "Enter A First Name", Toast.LENGTH_SHORT).show()
            }

            else if (isEmpty(b)) {
                Toast.makeText(this, "Enter A Last Name", Toast.LENGTH_SHORT).show()
            }

            else if (isEmpty(c)) {
                Toast.makeText(this, "Enter a valid Email Address", Toast.LENGTH_SHORT).show()
            }

            else if (isEmpty(d)) {
                Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show()
            }

            else {
                var values = ContentValues()
                values.put("fname", a)
                values.put("lname", b)
                values.put("email", c)
                values.put("number", d)

                // Adding contact
                if (recordID == 0) {
                    var DB = DatabaseHandler(this)
                    var response = DB.addContact(values)

                    if (response == "ok") {
                        Toast.makeText(this, "Contact Added", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this, MainActivity:: class.java)
                        startActivity(intent)
                        finish()
                    }

                    else {
                        Toast.makeText(this, "Not Added.. Try again", Toast.LENGTH_SHORT).show()
                    }
                }

                else {
                    var DB = DatabaseHandler(this)
                    var res: String = DB.updateContact(values, recordID)

                    if (res == "ok") {
                        Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this, MainActivity:: class.java)
                        startActivity(intent)
                        finish()
                    }

                    // Error in updating contact information.
                    else {
                        Toast.makeText(this, "Error, Try Again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } // End Save Button

        // Delete Button
        delete_btn.setOnClickListener {

            var database = DatabaseHandler(this)
            var res: String = database.removeContact(recordID)

            // When the contact is successfully deleted, display this Toast Message
            if (res == "ok") {
                Toast.makeText(this, "Contact Deleted", Toast.LENGTH_SHORT).show()
            }

            // If contact is not successfully deleted, display this Toast Message
            else {
                Toast.makeText(this, "Error.. Try Again", Toast.LENGTH_SHORT).show()
            }

            var intent = Intent(this, MainActivity:: class.java)
            startActivity(intent)
            finish()
        } // End Delete Button
    }
}