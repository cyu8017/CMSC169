package com.example.cyu.contacts

class ContactData {

    var conID: Int? = null
    var FirstName: String? = null
    var LastName: String? = null
    var Email: String? = null
    var PhoneNumber: String? = null

    constructor(id:Int, fname: String, lname: String, email: String, pnum: String) {
        this.conID = id
        this.FirstName = fname
        this.LastName = lname
        this.Email = email
        this.PhoneNumber = pnum
    }
}