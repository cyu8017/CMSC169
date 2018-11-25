package com.example.cyu.contacts

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteQueryBuilder
import java.util.ArrayList

class DatabaseHandler : SQLiteOpenHelper {

    companion object {

        val Tag = "DatabaseHandler"
        val DBName = "ContactDB"
        val DBVersion = 1

        val tableName = "phoneTable"
        val ConID = "id"
        val FirstName = "fname"
        val LastName = "lname"
        val Number = "number"
        val Email = "email"
    }

    var context: Context? = null
    var sqlObj: SQLiteDatabase

    constructor(context: Context) : super(context, DBName, null, DBVersion) {
        this.context = context
        sqlObj = this.writableDatabase
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        // SQL for creating table
        var sql1: String = "CREATE TABLE IF NOT EXISTS " + tableName + " " +
                "(" + ConID + " INTEGER PRIMARY KEY," +
                FirstName + " TEXT, " + LastName + " TEXT, " + Email +
                " TEXT," + Number + " TEXT );"

        p0!!.execSQL(sql1)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP table IF EXISTS " + tableName)
        onCreate(p0)
    }

    fun AddContact(values: ContentValues): String {
        var MSG: String = "error";
        val ID = sqlObj!!.insert(tableName, "", values)

        if (ID > 0) {
            MSG = "ok"
        }

        return MSG
    }

    fun FetchContacts(keyword: String): ArrayList<ContactData> {
        var arrayList = ArrayList<ContactData>()

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

        var count: Int = arrayList.size

        return arrayList
    }

    fun UpdateContact(values: ContentValues, id: Int): String {

        var selectionArs = arrayOf(id.toString())
        val i = sqlObj!!.update(tableName, values, "id=?", selectionArs)

        if (i > 0) {
            return "ok"
        } else {
            return "error"
        }
    }

    fun RemoveContact(id: Int): String {
        var selectionArs = arrayOf(id.toString())
        val i = sqlObj!!.delete(tableName, "id=?", selectionArs)

        if (i > 0) {
            return "ok"
        } else {
            return "error"
        }
    }
}