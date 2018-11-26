package com.example.cyu.contacts

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var ContactList = ArrayList<ContactData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        var DB:DatabaseHandler = DatabaseHandler(this)
        ContactList = DB.FetchContacts("%")

        if(ContactList.size > 0) {

            var ContactAdapterObj = ContactAdapter(this, ContactList)
            
            contact_list.adapter = ContactAdapterObj

            contact_list.onItemClickListener = AdapterView.OnItemClickListener {
                    adapterView, view, position, id ->

                // ContactList holds ContactData object
                var fname = ContactList[position].FirstName
                var lname = ContactList[position].LastName
                var email = ContactList[position].Email
                var phone = ContactList[position].PhoneNumber
                var id = ContactList[position].conID

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

        add_contact_btn.setOnClickListener() {
            var intent = Intent(this, ContactManager:: class.java)
            startActivity(intent)
        }
    }
}