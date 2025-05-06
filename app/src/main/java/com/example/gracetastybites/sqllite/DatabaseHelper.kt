package com.example.gracetastybites.sqllite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.gracetastybites.mockData.MenuList
import com.example.gracetastybites.mockData.Payroll
import com.example.gracetastybites.mockData.Shift
import com.example.gracetastybites.mockData.UserAuth

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "graceTastyBitesDB"
        private const val DATABASE_VERSION = 12

        //staff table
        const val TABLE_STAFF = "staff"
        const val COL_ID = "id"
        const val COL_EMAIL = "email"
        const val COL_PASSWORD = "password"
        const val COL_FIRST_NAME = "firstname"
        const val COL_LAST_NAME = "lastname"
        const val COL_ROLE = "role"
        const val COL_POSITION = "position"
        const val COL_PROFILE_PIC = "profile_pic"

        //menu list items
        const val TABLE_MENU_LIST_ITEMS = "menu_list_items"
        const val COL_MLI_ID = "id"
        const val COL_MLI_NAME = "name"
        const val COL_MLI_PRICE = "price"
        const val COL_MLI_CATEGORY = "category"
        const val COL_MLI_PICTURE = "picture"
        const val COL_MLI_DESCRIPTION = "description"

        //menu column id
        const val TABLE_MENU_COLUMN_ID = "menu_column_id"
        const val COL_MC_ID = "id"
        const val COL_MC_VALUE = "value"

        // shifts table

        const val COL_SHIFTS_ID = "id"
        const val TABLE_SHIFTS = "shifts"
        const val COL_STAFF_ID = "staff_id"
        const val COL_SHIFT_DATE = "shift_date"
        const val COL_START_TIME = "start_time"
        const val COL_END_TIME = "end_time"
        const val COL_STATUS = "status"

        //payroll table
        const val TABLE_PAYROLL = "payroll"
        const val COL_PAYROLL_ID = "id"
        const val COL_P_STAFF_ID = "staff_id"
        const val COL_TOTAL_SHIFTS_WORKED = "total_shifts_worked"
        const val COL_PAY_PER_SHIFT = "pay_per_shift"
        const val COL_TOTAL_EARNINGS = "total_earnings"
        const val COL_FILE_ID = "file_id"
    }

    @SuppressLint("SuspiciousIndentation", "SQLiteString")
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_STAFF (" +
                    "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_FIRST_NAME TEXT, $COL_LAST_NAME TEXT, $COL_EMAIL TEXT, " +
                    "$COL_PASSWORD TEXT, $COL_ROLE TEXT, $COL_POSITION TEXT, $COL_PROFILE_PIC TEXT)"


        val createMLITableQuery =
            "CREATE TABLE $TABLE_MENU_LIST_ITEMS (" +
                    "$COL_MLI_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_MLI_NAME TEXT, $COL_MLI_CATEGORY TEXT, $COL_MLI_PRICE TEXT, $COL_MLI_DESCRIPTION TEXT, $COL_MLI_PICTURE INTEGER)"

        val createMCTableQuery =
            "CREATE TABLE $TABLE_MENU_COLUMN_ID (" +
                    "$COL_MC_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_MC_VALUE INTEGER)"

        val createShiftsTableQuery =
            "CREATE TABLE $TABLE_SHIFTS (" +
                    "$COL_SHIFTS_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_STAFF_ID INTEGER NOT NULL, " +
                    "$COL_SHIFT_DATE TEXT NOT NULL, " +
                    "$COL_START_TIME TEXT NOT NULL, " +
                    "$COL_END_TIME TEXT NOT NULL, " +
                    "$COL_STATUS TEXT DEFAULT 'pending', " +
                    "FOREIGN KEY ($COL_STAFF_ID) REFERENCES $TABLE_STAFF($COL_ID))"

        val createPayrollTableQuery =
            "CREATE TABLE $TABLE_PAYROLL (" +
                    "$COL_PAYROLL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_P_STAFF_ID INTEGER NOT NULL, " +
                    "$COL_TOTAL_SHIFTS_WORKED INTEGER NOT NULL, " +
                    "$COL_PAY_PER_SHIFT REAL NOT NULL, " +
                    "$COL_TOTAL_EARNINGS REAL NOT NULL, " +
                    "$COL_FILE_ID STRING, " +
                    "FOREIGN KEY ($COL_STAFF_ID) REFERENCES $TABLE_STAFF($COL_ID))"

            db?.execSQL(createTableQuery)
            db?.execSQL(createMLITableQuery)
            db?.execSQL(createMCTableQuery)
            db?.execSQL(createShiftsTableQuery)
            db?.execSQL(createPayrollTableQuery)

        val insertDefaultStaffQuery = listOf(
            "INSERT INTO $TABLE_STAFF (" +
                    "$COL_FIRST_NAME, $COL_LAST_NAME, $COL_EMAIL, $COL_PASSWORD, " +
                    "$COL_ROLE, $COL_POSITION, $COL_PROFILE_PIC) VALUES (" +
                    "'Bogdan', 'Burcea', 'grandpoke@live.co.uk', 'plm123', " +
                    "'admin', 'admin', '')",
            "INSERT INTO $TABLE_STAFF (" +
                    "$COL_FIRST_NAME, $COL_LAST_NAME, $COL_EMAIL, $COL_PASSWORD, " +
                    "$COL_ROLE, $COL_POSITION, $COL_PROFILE_PIC) VALUES (" +
                    "'Adrian', 'Wall', 'adrian@aa.com', 'plm123', " +
                    "'staff', 'waiter', '')",
            "INSERT INTO $TABLE_STAFF (" +
                    "$COL_FIRST_NAME, $COL_LAST_NAME, $COL_EMAIL, $COL_PASSWORD, " +
                    "$COL_ROLE, $COL_POSITION, $COL_PROFILE_PIC) VALUES (" +
                    "'Carla', 'Stockes', 'carla@aa.com', 'plm123', " +
                    "'staff', 'chief', '')",
            "INSERT INTO $TABLE_STAFF (" +
                    "$COL_FIRST_NAME, $COL_LAST_NAME, $COL_EMAIL, $COL_PASSWORD, " +
                    "$COL_ROLE, $COL_POSITION, $COL_PROFILE_PIC) VALUES (" +
                    "'Tracy', 'Mark', 'tracy@aa.com', 'plm123', " +
                    "'staff', 'manager', '')",
            "INSERT INTO $TABLE_STAFF (" +
                    "$COL_FIRST_NAME, $COL_LAST_NAME, $COL_EMAIL, $COL_PASSWORD, " +
                    "$COL_ROLE, $COL_POSITION, $COL_PROFILE_PIC) VALUES (" +
                    "'Laura', 'Unit', 'laura@aa.com', 'plm123', " +
                    "'staff', 'cashier', '')",
            "INSERT INTO $TABLE_STAFF (" +
                    "$COL_FIRST_NAME, $COL_LAST_NAME, $COL_EMAIL, $COL_PASSWORD, " +
                    "$COL_ROLE, $COL_POSITION, $COL_PROFILE_PIC) VALUES (" +
                    "'Susan', 'Ashley', 'susan@aa.com', 'plm123', " +
                    "'staff', 'waiter', '')",
            "INSERT INTO $TABLE_STAFF (" +
                    "$COL_FIRST_NAME, $COL_LAST_NAME, $COL_EMAIL, $COL_PASSWORD, " +
                    "$COL_ROLE, $COL_POSITION, $COL_PROFILE_PIC) VALUES (" +
                    "'John', 'Wayne', 'john@aa.com', 'plm123', " +
                    "'staff', 'chief', '')"
        )

        insertDefaultStaffQuery.forEach {query -> db?.execSQL(query)}

        val insertDefaultShifts = listOf(
            "INSERT INTO $TABLE_SHIFTS (" +
                "$COL_STAFF_ID, $COL_SHIFT_DATE, $COL_START_TIME, $COL_END_TIME, $COL_STATUS" +
                ") VALUES (" +
                "2, '2025-05-07', '09:00', '17:00', 'pending');",
            "INSERT INTO $TABLE_SHIFTS (" +
                    "$COL_STAFF_ID, $COL_SHIFT_DATE, $COL_START_TIME, $COL_END_TIME, $COL_STATUS" +
                    ") VALUES (" +
                    "3, '2025-05-07', '09:00', '17:00', 'pending');"
        )

        insertDefaultShifts.forEach{query -> db?.execSQL(query)}

        val insertMenuItems = listOf(
            "INSERT INTO $TABLE_MENU_LIST_ITEMS ($COL_MLI_NAME, $COL_MLI_CATEGORY, $COL_MLI_PRICE, $COL_MLI_DESCRIPTION, $COL_MLI_PICTURE) " +
                    "VALUES ('Big Stack Burger', 'Burgers', '£12.50', 'Delicious double patty burger', 2130968605);",
            "INSERT INTO $TABLE_MENU_LIST_ITEMS ($COL_MLI_NAME, $COL_MLI_CATEGORY, $COL_MLI_PRICE, $COL_MLI_DESCRIPTION, $COL_MLI_PICTURE) " +
                    "VALUES ('Chicken Bones', 'Chicken', '£9.50', 'Juicy chicken wings', 2130968606);",
            "INSERT INTO $TABLE_MENU_LIST_ITEMS ($COL_MLI_NAME, $COL_MLI_CATEGORY, $COL_MLI_PRICE, $COL_MLI_DESCRIPTION, $COL_MLI_PICTURE) " +
                    "VALUES ('Chicken Nuggets', 'Chicken', '£4.50', 'Crispy chicken nuggets', 2130968607);"
        )

        insertMenuItems.forEach { query ->
            db?.execSQL(query)
        }

        val insertMenuColumn = listOf(
            "INSERT INTO $TABLE_MENU_COLUMN_ID ($COL_MC_VALUE) " +
                "VALUES(0);")

        insertMenuColumn.forEach { query ->
            db?.execSQL(query)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_STAFF")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MENU_LIST_ITEMS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MENU_COLUMN_ID")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SHIFTS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PAYROLL")
        onCreate(db)
    }

    fun insertUser(user: UserAuth): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_EMAIL, user.email)
            put(COL_PASSWORD, user.password)
            put(COL_FIRST_NAME, user.firstname)
            put(COL_LAST_NAME, user.lastname)
            put(COL_ROLE, user.role)
            put(COL_POSITION, user.position)
            put(COL_PROFILE_PIC, user.profilePic)
        }
        return db.insert(TABLE_STAFF, null, values)
    }

    fun updateUser(user: UserAuth): Int {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COL_EMAIL, user.email)
            put(COL_PASSWORD, user.password)
            put(COL_FIRST_NAME, user.firstname)
            put(COL_LAST_NAME, user.lastname)
            put(COL_ROLE, user.role)
            put(COL_POSITION, user.position)
            put(COL_PROFILE_PIC, user.profilePic)
        }
        return db.update(TABLE_STAFF, values, "$COL_ID = ?", arrayOf(user.id.toString()))
    }


    fun getAllUsers(filterCategory: String? = null): List<UserAuth> {
        val usersList = mutableListOf<UserAuth>()
        val db = this.readableDatabase
        val cursor: Cursor = if (filterCategory != null) {
            db.rawQuery("SELECT * FROM $TABLE_STAFF WHERE role=?", arrayOf(filterCategory))
        } else {
            db.rawQuery("SELECT * FROM $TABLE_STAFF", null)
        }

        if (cursor.moveToFirst()) {
            do {
                val user = UserAuth(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)),
                    password = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)),
                    firstname = cursor.getString(cursor.getColumnIndexOrThrow(COL_FIRST_NAME)),
                    lastname = cursor.getString(cursor.getColumnIndexOrThrow(COL_LAST_NAME)),
                    position = cursor.getString(cursor.getColumnIndexOrThrow(COL_POSITION)),
                    role = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLE)),
                    profilePic = cursor.getString(cursor.getColumnIndexOrThrow(COL_PROFILE_PIC))
                )
                usersList.add(user)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return usersList
    }

    fun getUserById(id: Int): UserAuth? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_STAFF WHERE $COL_ID = ?", arrayOf(id.toString())
        )
        var user: UserAuth? = null
        if (cursor.moveToFirst()) {
                user = UserAuth(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)),
                    password = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)),
                    firstname = cursor.getString(cursor.getColumnIndexOrThrow(COL_FIRST_NAME)),
                    lastname = cursor.getString(cursor.getColumnIndexOrThrow(COL_LAST_NAME)),
                    position = cursor.getString(cursor.getColumnIndexOrThrow(COL_POSITION)),
                    role = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLE)),
                    profilePic = cursor.getString(cursor.getColumnIndexOrThrow(COL_PROFILE_PIC))
                )
        }

        cursor.close()
        return user
    }

    fun deleteUserById(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_STAFF, "$COL_ID=?", arrayOf(id.toString()))
    }


    fun insertMenuItem(menuItem: MenuList): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_MLI_NAME, menuItem.name)
            put(COL_MLI_PRICE, menuItem.price)
            put(COL_MLI_CATEGORY, menuItem.category)
            put(COL_MLI_PICTURE, menuItem.picture)
        }
        return db.insert(TABLE_MENU_LIST_ITEMS, null, values)
    }

    fun getAllMenuItems(): List<MenuList> {
        val menuItemsList = mutableListOf<MenuList>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_MENU_LIST_ITEMS", null)

        if (cursor.moveToFirst()) {
            do {
                val menuItem = MenuList(
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COL_MLI_NAME)),
                    price = cursor.getString(cursor.getColumnIndexOrThrow(COL_MLI_PRICE)),
                    category = cursor.getString(cursor.getColumnIndexOrThrow(COL_MLI_CATEGORY)),
                    picture = cursor.getInt(cursor.getColumnIndexOrThrow(COL_MLI_PICTURE))
                )
                menuItemsList.add(menuItem)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return menuItemsList
    }

    fun getMenuItemsByCategory(category: String): List<MenuList> {
        val menuItemsList = mutableListOf<MenuList>()
        val db = this.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_MENU_LIST_ITEMS WHERE $COL_MLI_CATEGORY = '$category'", null )
        if (cursor.moveToFirst()) {
            do {
                val menuItem = MenuList(
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COL_MLI_NAME)),
                    price = cursor.getString(cursor.getColumnIndexOrThrow(COL_MLI_PRICE)),
                    category = cursor.getString(cursor.getColumnIndexOrThrow(COL_MLI_CATEGORY)),
                    picture = cursor.getInt(cursor.getColumnIndexOrThrow(COL_MLI_PICTURE))
                )
                menuItemsList.add(menuItem)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return menuItemsList
    }

    fun deleteMenuItemById(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_MENU_LIST_ITEMS, "$COL_MLI_ID=?", arrayOf(id.toString()))
    }

    fun getMenuColValue(): Int {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_MENU_COLUMN_ID", null )

        var navColumnId = 0

        if (cursor.moveToFirst()) {
            do {
                navColumnId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_MC_VALUE))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return navColumnId

    }

    fun updateColValue(itemValue: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL_MC_VALUE, itemValue)
        }
        val result = db.update(TABLE_MENU_COLUMN_ID, contentValues, null, null)
        db.close()
        return result
    }

    fun getAllShifts(filterCategory: String? = null, valueCategory: String? = null): List<Shift> {
        val db = this.readableDatabase

        val cursor: Cursor = if (filterCategory != null) {
            db.rawQuery("SELECT * FROM $TABLE_SHIFTS WHERE $filterCategory = ?", arrayOf(valueCategory))
        } else {
            db.rawQuery("SELECT * FROM $TABLE_SHIFTS", null)
        }

        val allShifts = mutableListOf<Shift>()

        if (cursor.moveToFirst()) {
            do {
                val shiftItem = Shift(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_SHIFTS_ID)),
                    staffId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_STAFF_ID)),
                    shiftDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_SHIFT_DATE)),
                    startTime = cursor.getString(cursor.getColumnIndexOrThrow(COL_START_TIME)),
                    endTime = cursor.getString(cursor.getColumnIndexOrThrow(COL_END_TIME)),
                    status = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS))
                )
                allShifts.add(shiftItem)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return allShifts
    }

    fun getCompletedShifts(staffId: Int, startDate: String, endDate: String): Int {
        val db = this.readableDatabase

        val cursor: Cursor = db.rawQuery(" SELECT COUNT(*) FROM $TABLE_SHIFTS " +
                "WHERE $COL_STAFF_ID = ? " +
                "AND $COL_STATUS = 'accepted' " +
                "AND $COL_SHIFT_DATE BETWEEN ? AND ?", arrayOf(staffId.toString(), startDate, endDate))


        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }

        cursor.close()
        return count
    }


    fun insertShift(staffId: Int, shiftDate: String, startTime: String, endTime: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_STAFF_ID, staffId)
            put(COL_SHIFT_DATE, shiftDate)
            put(COL_START_TIME, startTime)
            put(COL_END_TIME, endTime)
        }
        return db.insert(TABLE_SHIFTS, null, values)
    }

    fun deleteShiftById(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_SHIFTS, "$COL_SHIFTS_ID=?", arrayOf(id.toString()))
    }

    fun insertPayrollItem(payroll: Payroll): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_P_STAFF_ID, payroll.staffId)
            put(COL_TOTAL_SHIFTS_WORKED, payroll.totalShiftsWorked)
            put(COL_PAY_PER_SHIFT, payroll.payPerShift)
            put(COL_TOTAL_EARNINGS, payroll.totalEarnings)
            put(COL_FILE_ID, payroll.fileId)
        }
        return db.insert(TABLE_PAYROLL, null, values)
    }
}

