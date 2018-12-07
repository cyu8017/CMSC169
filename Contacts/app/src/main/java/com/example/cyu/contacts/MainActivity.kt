package com.example.cyu.contacts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var contactList = ArrayList<ContactData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        val database = DatabaseHandler(this)
        contactList = database.fetchContacts("%")

        if(contactList.size > 0) {

            val contactAdapterObj = ContactAdapter(this, contactList)
            
            contact_list.adapter = contactAdapterObj

            // Contact list listener
            contact_list.onItemClickListener = AdapterView.OnItemClickListener {
                    _ , _ , position, _ ->

                // ContactList holds ContactData object
                val fname = contactList[position].firstName
                val lname = contactList[position].lastName
                val email = contactList[position].email
                val phone = contactList[position].phoneNumber
                val id = contactList[position].conID

                // Passing data to ContactManager activity.
                val intent = Intent(this, ContactManager:: class.java)
                intent.putExtra("id", id)
                intent.putExtra("fname", fname)
                intent.putExtra("lname", lname)
                intent.putExtra("email", email)
                intent.putExtra("phone", phone)
                intent.putExtra("action", "edit")
                startActivity(intent)
            }
        } // End Contact Listener

        else {
            Toast.makeText(this, "No Contact Found", Toast.LENGTH_SHORT).show()
        }

        add_contact_btn.setOnClickListener {
            val intent = Intent(this, ContactManager:: class.java)
            startActivity(intent)
        }
    }
}