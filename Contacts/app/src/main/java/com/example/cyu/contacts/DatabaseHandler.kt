package com.example.cyu.contacts

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteQueryBuilder
import java.util.ArrayList

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, dbName, null, dbVersion) {

    // Define constant companion objects
    companion object {
        const val dbName = "ContactDB"
        const val dbVersion = 1
        const val tableName = "phoneTable"
        const val conID = "id"
        const val firstName = "fname"
        const val lastName = "lname"
        const val phoneNumber = "number"
        const val email = "email"
        const val organization = "organization"
    }

    private var sqlObj: SQLiteDatabase = this.writableDatabase

    // Create the contact
    override fun onCreate(p0: SQLiteDatabase?) {
        // SQL for creating table
        val sql1: String = "CREATE TABLE IF NOT EXISTS " + tableName + " " + "" + "(" +
                conID + " INTEGER PRIMARY KEY, " +
                firstName + " TEXT, "+
                lastName + " TEXT, " +
                email + " TEXT," +
                phoneNumber + " TEXT," +
                organization + " TEXT);"

        p0!!.execSQL(sql1)
    }

    // Update the information on a existing contact
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("")
        onCreate(p0)
    }

    // Function Add Contact
    fun addContact(values: ContentValues): String {
        var message = "error"
        val id = sqlObj.insert(tableName, "", values)

        if (id > 0) {
            message = "ok"
        }

        return message
    } // End Add Contact

    // Function Fetch Contact
    fun fetchContacts(keyword: String): ArrayList<ContactData> {
        val arrayList = ArrayList<ContactData>()

        val sqb = SQLiteQueryBuilder()
        sqb.tables = tableName

        // Declare columns and rows
        val cols = arrayOf("id", "fname", "lname", "email", "number", "organization")
        val rowSelArg = arrayOf(keyword)
        val cur = sqb.query(sqlObj, cols, "fname like ?", rowSelArg, null, null, "fname")

        if (cur.moveToFirst()) {
            do {
                val id = cur.getInt(cur.getColumnIndex("id"))
                val fname = cur.getString(cur.getColumnIndex("fname"))
                val lname = cur.getString(cur.getColumnIndex("lname"))
                val email = cur.getString(cur.getColumnIndex("email"))
                val number = cur.getString(cur.getColumnIndex("number"))
                val organization = cur.getString(cur.getColumnIndex("organization"))
                arrayList.add(ContactData(id, fname, lname, email, number, organization))
            } while (cur.moveToNext())
        }

        return arrayList
    } // End Fetch Contact

    // Function Update Contact
    fun updateContact(values: ContentValues, id: Int): String {

        val selectionArguments = arrayOf(id.toString())
        val i = sqlObj.update(tableName, values, "id=?", selectionArguments)

        return if (i > 0) "ok" else "error"
    } // End Update Contact

    // Function Remove Contact
    fun removeContact(id: Int): String {
        val selectionArguments = arrayOf(id.toString())
        val i = sqlObj.delete(tableName, "id=?", selectionArguments)

        return if (i > 0) "ok" else "error"

    } // End Remove Contact
} // End DatabaseHandler