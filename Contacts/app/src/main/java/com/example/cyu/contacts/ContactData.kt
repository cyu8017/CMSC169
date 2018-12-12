package com.example.cyu.contacts

// Convert to primary constructor
class ContactData (id: Int, fname: String, lname: String, email: String, phone: String, organization: String) {
    var conID: Int? = id
    var firstName: String? = fname
    var lastName: String? = lname
    var email: String? = email
    var phoneNumber: String? = phone
    var organization: String? = organization
} // End ContactData
