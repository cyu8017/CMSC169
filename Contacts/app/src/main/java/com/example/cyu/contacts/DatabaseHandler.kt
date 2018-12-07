package com.example.cyu.contacts

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteQueryBuilder
import java.util.ArrayList

class DatabaseHandler : SQLiteOpenHelper {

    // Define constant objects
    companion object {

        const val dbName = "ContactDB"
        const val dbVersion = 1
        const val tableName = "phoneTable"
        const val conID = "id"
        const val firstName = "fname"
        const val lastName = "lname"
        const val phoneNumber = "number"
        const val email = "email"
    }

    private var context: Context? = null
    private var sqlObj: SQLiteDatabase

    constructor(context: Context) : super(context, dbName, null, dbVersion) {
        this.context = context
        sqlObj = this.writableDatabase
    }

    // Create the contact
    override fun onCreate(p0: SQLiteDatabase?) {
        // SQL for creating table
        val sql1: String = "CREATE TABLE IF NOT EXISTS " + tableName + " " + "(" + conID + " INTEGER PRIMARY KEY," +
                firstName + " TEXT, " + lastName + " TEXT, " + email + " TEXT," + phoneNumber + " TEXT );"

        p0!!.execSQL(sql1)
    }

    // Update the information on a existing contact
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //p0!!.execSQL("DROP table IF EXISTS" + tableName)
        //p0!!.execSQL("$tableName")
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
        val cols = arrayOf("id", "fname", "lname", "email", "number")
        val rowSelArg = arrayOf(keyword)
        val cur = sqb.query(sqlObj, cols, "fname like ?", rowSelArg, null, null, "fname")

        if (cur.moveToFirst()) {
            do {
                val id = cur.getInt(cur.getColumnIndex("id"))
                val fname = cur.getString(cur.getColumnIndex("fname"))
                val lname = cur.getString(cur.getColumnIndex("lname"))
                val email = cur.getString(cur.getColumnIndex("email"))
                val number = cur.getString(cur.getColumnIndex("number"))
                arrayList.add(ContactData(id, fname, lname, email, number))
            } while (cur.moveToNext())
        }

        return arrayList
    } // End Fetch Contact

    // Function Update Contact
    fun updateContact(values: ContentValues, id: Int): String {

        val selectionArguments = arrayOf(id.toString())
        val i = sqlObj.update(tableName, values, "id=?", selectionArguments)

        if (i > 0) return "ok" else return "error"
    } // End Update Contact

    // Function Remove Contact
    fun removeContact(id: Int): String {
        val selectionArguments = arrayOf(id.toString())
        val i = sqlObj.delete(tableName, "id=?", selectionArguments)

        if (i > 0)
            return "ok"

        else
            return "error"

    } // End Remove Contact
}