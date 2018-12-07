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

        // When user makes modifications to an existing contact, save the updated information to contacts.
        else {
            save_btn.text = "Update Contact"
            var fname = intent.getStringExtra("fname")
            var lname = intent.getStringExtra("lname")
            var email = intent.getStringExtra("email")
            var phone = intent.getStringExtra("phone")

            fnametxt.setText(fname)
            lnametxt.setText(lname)
            emailtxt.setText(email)
            phone_txt.setText(phone)
        }

        // Save Button
        save_btn.setOnClickListener {

            // Capitalize the first letter of first name and first letter of last name.
            var firstName = fnametxt.text.toString().capitalize()
            var lastName = lnametxt.text.toString().capitalize()

            var emailAddress = emailtxt.text.toString()
            var phoneNumber = phone_txt.text.toString()


            // EditText Validation
            if (isEmpty(firstName)) {
                Toast.makeText(this, "Enter A First Name", Toast.LENGTH_SHORT).show()
            }

            else if (isEmpty(lastName)) {
                Toast.makeText(this, "Enter A Last Name", Toast.LENGTH_SHORT).show()
            }

            else if (isEmpty(emailAddress)) {
                Toast.makeText(this, "Enter a valid Email Address", Toast.LENGTH_SHORT).show()
            }

            else if (isEmpty(phoneNumber)) {
                Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show()
            }

            else {
                var values = ContentValues()
                values.put("fname", firstName)
                values.put("lname", lastName)
                values.put("email", emailAddress)
                values.put("number", phoneNumber)

                // Adding contact
                if (recordID == 0) {
                    var database = DatabaseHandler(this)
                    var response = database.addContact(values)

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
                    var database = DatabaseHandler(this)
                    var res: String = database.updateContact(values, recordID)

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