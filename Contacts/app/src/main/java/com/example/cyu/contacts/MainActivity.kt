package com.example.cyu.contacts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var contactList = ArrayList<ContactData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        var database = DatabaseHandler(this)
        contactList = database.fetchContacts("%")

        if(contactList.size > 0) {

            var contactAdapterObj = ContactAdapter(this, contactList)
            
            contact_list.adapter = contactAdapterObj

            contact_list.onItemClickListener = AdapterView.OnItemClickListener {
                    _ , _ , position, _ ->

                // ContactList holds ContactData object
                var fname = contactList[position].firstName
                var lname = contactList[position].lastName
                var email = contactList[position].email
                var phone = contactList[position].phoneNumber
                var id = contactList[position].conID

                // Passing data to ContactManager activity.
                var intent = Intent(this, ContactManager:: class.java)
                intent.putExtra("id", id)
                intent.putExtra("fname", fname)
                intent.putExtra("lname", lname)
                intent.putExtra("email", email)
                intent.putExtra("phone", phone)
                intent.putExtra("action", "edit")
                startActivity(intent)
            }
        }

        else {
            Toast.makeText(this, "No Contact Found", Toast.LENGTH_SHORT).show()
        }

        add_contact_btn.setOnClickListener {
            var intent = Intent(this, ContactManager:: class.java)
            startActivity(intent)
        }
    }
}