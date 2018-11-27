package com.example.cyu.contacts

class ContactData {

    var conID: Int? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var phoneNumber: String? = null

    constructor(id:Int, fname: String, lname: String, email: String, pnum: String) {
        this.conID = id
        this.firstName = fname
        this.lastName = lname
        this.email = email
        this.phoneNumber = pnum
    }
}