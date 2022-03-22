package com.example.pizzaorderingapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    companion object {
        const val DB_NAME = "Pizza_Ordering_Db11"
        const val TABLE_PIZZA = "Pizza"
        const val TABLE_SIZE_PRICE = "Pizza_Size_Price"
        const val TABLE_TOPPING = "Topping"
        const val TABLE_USER = "User"
        const val TABLE_ADDRESS = "Address"
        const val TABLE_USER_ADDRESS = "User_Address"
        const val TABLE_CART = "Cart"
        const val TABLE_ITEM = "Item"
        const val TABLE_SELECTED_TOPPING = "Selected_Topping"
        const val TABLE_ORDERS = "Orders"
        const val TABLE_ORDER_ITEMS = "OrderItems"

        const val PIZZA_ID = "Pizza_Id"
        const val PIZZA_NAME = "Pizza_Name"
        const val IMAGE = "Image"
        const val CATEGORY = "Category"

        const val SIZE = "Size"
        const val PRICE = "Price"

        const val TOPPING_ID = "Topping_Id"
        const val TOPPING_NAME = "Topping_Name"
        const val TOPPING_PRICE = "Topping_Price"

        const val USER_ID = "User_Id"
        const val USER_NAME = "User_Name"
        const val PHONE_NUMBER = "Phone_Number"
        const val PASSWORD = "password"
        const val IS_ADMIN = "Is_Admin"

        const val ADDRESS_ID = "Address_Id"
        const val COMPLETE_ADDRESS = "Complete_Address"
        const val ADDRESS_TAG = "Tag"

        const val ITEM_ID = "Item_Id"
        const val QUANTITY = "Quantity"
        const val ITEM_SIZE = "Item_Size"
        const val ITEM_PRICE = "Item_Price"

        const val ORDER_ID = "Order_Id"
        const val TOTAL_PRICE = "Total_Price"
        const val STATUS = "Status"
        const val DATE = "Date"

        const val CREATE_PIZZA_TABLE =
            "CREATE TABLE $TABLE_PIZZA($PIZZA_ID INTEGER PRIMARY KEY AUTOINCREMENT,$PIZZA_NAME TEXT,$IMAGE BLOB, $CATEGORY TEXT)"
        const val CREATE_SIZE_AND_PRICE_TABLE =
            "CREATE TABLE $TABLE_SIZE_PRICE($PIZZA_ID INTEGER, $SIZE TEXT, $PRICE INTEGER)"
        const val CREATE_TOPPING_TABLE =
            "CREATE TABLE $TABLE_TOPPING($TOPPING_ID INTEGER PRIMARY KEY AUTOINCREMENT, $TOPPING_NAME TEXT, $TOPPING_PRICE INTEGER)"
        const val CREATE_USER_TABLE =
            "CREATE TABLE $TABLE_USER($USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, $USER_NAME TEXT, $PHONE_NUMBER TEXT, $PASSWORD TEXT, $IS_ADMIN INTEGER)"
        const val CREATE_ADDRESS_TABLE =
            "CREATE TABLE $TABLE_ADDRESS($ADDRESS_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COMPLETE_ADDRESS TEXT, $ADDRESS_TAG TEXT)"
        const val CREATE_USER_ADDRESS_TABLE =
            "CREATE TABLE $TABLE_USER_ADDRESS($USER_ID INTEGER, $ADDRESS_ID INTEGER)"
        const val CREATE_CART_TABLE = "CREATE TABLE $TABLE_CART($USER_ID INTEGER, $ITEM_ID INTEGER)"
        const val CREATE_ITEM_TABLE =
            "CREATE TABLE $TABLE_ITEM($ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT, $PIZZA_ID INTEGER, $QUANTITY INTEGER, $ITEM_SIZE TEXT, $ITEM_PRICE INTEGER)"
        const val CREATE_SELECTED_TOPPING_TABLE =
            "CREATE TABLE $TABLE_SELECTED_TOPPING($ITEM_ID INTEGER, $TOPPING_ID INTEGER)"
        const val CREATE_ORDERS_TABLE =
            "CREATE TABLE $TABLE_ORDERS($ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT, $USER_ID INTEGER, $TOTAL_PRICE INTEGER, $STATUS TEXT, $DATE TEXT, $ADDRESS_ID INTEGER)"

        const val CREATE_TABLE_ORDER_ITEMS =
            "CREATE TABLE $TABLE_ORDER_ITEMS($ORDER_ID INTEGER, $ITEM_ID INTEGER)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_PIZZA_TABLE)
        db?.execSQL(CREATE_SIZE_AND_PRICE_TABLE)
        db?.execSQL(CREATE_TOPPING_TABLE)
        db?.execSQL(CREATE_USER_TABLE)
        db?.execSQL(CREATE_ADDRESS_TABLE)
        db?.execSQL(CREATE_USER_ADDRESS_TABLE)
        db?.execSQL(CREATE_CART_TABLE)
        db?.execSQL(CREATE_ITEM_TABLE)
        db?.execSQL(CREATE_SELECTED_TOPPING_TABLE)
        db?.execSQL(CREATE_ORDERS_TABLE)
        db?.execSQL(CREATE_TABLE_ORDER_ITEMS)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {


    }

    fun insertDefaultValues() {
        insertUsers("Teena", "9043885171", "abcd", 1)
        insertPizza(
            "Chicken Pizza",
            R.drawable.tandooripaneer,
            Category.NonVeg,
            mutableMapOf(Size.Regular to "399", Size.Medium to "499")
        )
        insertPizza(
            "Tandoori paneer Pizza",
            R.drawable.tandooripaneer,
            Category.Veg,
            mutableMapOf(Size.Regular to "299", Size.Medium to "399", Size.Large to "499")
        )
        insertPizza(
            "Supreme veg Pizza",
            R.drawable.tandooripaneer,
            Category.Veg,
            mutableMapOf(Size.Regular to "299", Size.Medium to "399", Size.Large to "499")
        )
        insertPizza(
            "Tandoori Chicken Pizza",
            R.drawable.tandooripaneer,
            Category.NonVeg,
            mutableMapOf(Size.Regular to "399", Size.Medium to "499", Size.Large to "599")
        )
        insertPizza(
            "Cheese and Corn Pizza",
            R.drawable.tandooripaneer,
            Category.Veg,
            mutableMapOf(Size.Regular to "299", Size.Medium to "399", Size.Large to "499")
        )
        insertPizza(
            "Pepper Barbecue Chicken Pizza",
            R.drawable.tandooripaneer,
            Category.NonVeg,
            mutableMapOf(Size.Medium to "499", Size.Large to "599")
        )
        insertTopping("Cheese",50)
        insertTopping("Red Pepper",50)
        insertTopping("corn",50)
        insertTopping("Jalapeno",60)
        insertTopping("Peri-Peri Chicken",75)
        insertTopping("Chicken balls",80)
        insertTopping("chicken Pepperoni",75)


    }

    fun insertPizza(
        name: String,
        image: Int,
        category: Category,
        sizeAndPrice: MutableMap<Size, String>
    ) {
        val db = this.writableDatabase
        val contentValues = contentValuesOf(
            PIZZA_NAME to name,
            IMAGE to R.drawable.tandooripaneer,
            CATEGORY to category.name
        )
        val id = db.insert(TABLE_PIZZA, null, contentValues)

        db.close()
        insertSizeAndPrice(id.toInt(), sizeAndPrice)

    }

    private fun insertSizeAndPrice(pizzaId: Int, sizeAndPrice: MutableMap<Size, String>) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        for (i in sizeAndPrice) {
            contentValues.put(PIZZA_ID, pizzaId)
            contentValues.put(SIZE, i.key.name)
            contentValues.put(PRICE, i.value)
            db.insert(TABLE_SIZE_PRICE, null, contentValues)
        }
        db.close()
    }

    fun updatePizza(
        pizzaId: Int,
        name: String,
        image: Int,
        category: Category,
        sizeAndPrice: MutableMap<Size, String>
    ) {
        val db = this.writableDatabase
        val contentValues = contentValuesOf(
            PIZZA_NAME to name,
            IMAGE to image,
            CATEGORY to category.name
        )
        db.update(TABLE_PIZZA, contentValues, "$PIZZA_ID = $pizzaId", null)
        db.close()
        updateSizeAndPrice(pizzaId, sizeAndPrice)
    }

    fun getPizza(): ArrayList<Pizza> {
        val db = this.readableDatabase
        val listOfPizza = ArrayList<Pizza>()
        val query = "SELECT * FROM $TABLE_PIZZA"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val pizzaId = cursor.getInt(cursor.getColumnIndexOrThrow(PIZZA_ID))
                val pizzaName = cursor.getString(cursor.getColumnIndexOrThrow(PIZZA_NAME))
                val category =
                    Category.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY)))
                val sizeAndPrice = getSizeAndPrice(pizzaId)

                listOfPizza.add(
                    Pizza(
                        pizzaName,
                        R.drawable.tandooripaneer,
                        sizeAndPrice,
                        category,
                        pizzaId
                    )
                )

            } while (cursor.moveToNext())
        }
        db.close()
        return listOfPizza

    }

    fun getPizzaName(pizzaId: Int): String {
        val db = this.readableDatabase
        val query = "SELECT $PIZZA_NAME FROM $TABLE_PIZZA WHERE $PIZZA_ID = $pizzaId"
        val cursor = db.rawQuery(query, null)
        var pizzaName = ""
        if (cursor.moveToFirst()) {
            pizzaName = cursor.getString(cursor.getColumnIndexOrThrow(PIZZA_NAME))
        }
        db.close()
        return pizzaName

    }

    private fun getSizeAndPrice(pizzaId: Int): MutableMap<Size, String> {
        val db = this.readableDatabase
        val sizeAndPrice = mutableMapOf<Size, String>()
        val query = "SELECT * FROM $TABLE_SIZE_PRICE WHERE $PIZZA_ID = $pizzaId"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val size = Size.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(SIZE)))
                val price = cursor.getInt(cursor.getColumnIndexOrThrow(PRICE)).toString()
                sizeAndPrice.put(size, price)
            } while (cursor.moveToNext())
        }
        db.close()
        return sizeAndPrice
    }

    private fun updateSizeAndPrice(pizzaId: Int, sizeAndPrice: MutableMap<Size, String>) {
        val db = this.writableDatabase
        deleteSizeAndPrice(pizzaId)
        insertSizeAndPrice(pizzaId, sizeAndPrice)
        db.close()
    }

    fun deletePizza(pizzaId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_PIZZA, "$PIZZA_ID = $pizzaId", null)
        db.close()
        deleteSizeAndPrice(pizzaId)
    }

    private fun deleteSizeAndPrice(pizzaId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_SIZE_PRICE, "$PIZZA_ID = $pizzaId", null)
        db.close()
    }

    fun insertTopping(toppingName: String, toppingPrice: Int) {
        val db = this.writableDatabase
        val contentValues =
            contentValuesOf(TOPPING_NAME to toppingName, TOPPING_PRICE to toppingPrice)
        db.insert(TABLE_TOPPING, null, contentValues)
        db.close()
    }

    fun getToppings(): ArrayList<Topping> {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_TOPPING"
        val cursor = db.rawQuery(query, null)
        val listOfToppings: ArrayList<Topping> = arrayListOf()
        if (cursor.moveToFirst()) {
            do {
                val toppingId = cursor.getInt(cursor.getColumnIndexOrThrow(TOPPING_ID))
                val toppingName = cursor.getString(cursor.getColumnIndexOrThrow(TOPPING_NAME))
                val toppingPrice = cursor.getInt(cursor.getColumnIndexOrThrow(TOPPING_PRICE))
                val topping = Topping(toppingName, toppingPrice, toppingId)
                listOfToppings.add(topping)
            } while (cursor.moveToNext())
        }
        db.close()
        return listOfToppings
    }

    fun getToppingName(toppingId: Int): String {
        val db = this.readableDatabase
        val query = "SELECT $TOPPING_NAME FROM $TABLE_TOPPING WHERE $TOPPING_ID = $toppingId"
        val cursor = db.rawQuery(query, null)
        var toppingName = ""
        if (cursor.moveToFirst()) {
            toppingName = cursor.getString(cursor.getColumnIndexOrThrow(TOPPING_NAME))
        }
        db.close()
        return toppingName
    }

    fun updateTopping(toppingId: Int, toppingName: String, toppingPrice: Int) {
        val db = this.writableDatabase
        val contentValues =
            contentValuesOf(TOPPING_NAME to toppingName, TOPPING_PRICE to toppingPrice)
        db.update(TABLE_TOPPING, contentValues, "$TOPPING_ID = $toppingId", null)
        db.close()
    }

    fun deleteTopping(toppingId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_TOPPING, "$TOPPING_ID = $toppingId", null)
        db.close()
    }

    fun insertSelectedToppingData(itemId: Int, toppingId: Int) {
        val db = this.writableDatabase
        val contentValues = contentValuesOf(ITEM_ID to itemId, TOPPING_ID to toppingId)
        db.insert(TABLE_SELECTED_TOPPING, null, contentValues)
        db.close()
    }

    fun getSelectedToppings(itemId: Int): ArrayList<Int> {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_SELECTED_TOPPING WHERE $ITEM_ID = $itemId"
        val cursor = db.rawQuery(query, null)
        val listOfToppingId = arrayListOf<Int>()
        if (cursor.moveToFirst()) {
            do {
                val toppingId = cursor.getInt(cursor.getColumnIndexOrThrow(TOPPING_ID))
                listOfToppingId.add(toppingId)
            } while (cursor.moveToNext())
        }
        db.close()
        return listOfToppingId
    }

    fun insertUsers(name: String, phoneNumber: String, password: String, isAdmin: Int): Int {

        val db = this.writableDatabase
        val contentValues =
            contentValuesOf(
                USER_NAME to name,
                PHONE_NUMBER to phoneNumber,
                PASSWORD to password,
                IS_ADMIN to isAdmin
            )
        val userId = db.insert(TABLE_USER, null, contentValues)
        db.close()
        return userId.toInt()
    }

//    private fun insertUsers(
//        name: String,
//        phoneNumber: String,
//        password: String,
//        isAdmin: Int,
//        db: SQLiteDatabase?
//    ) {
//        val contentValues =
//            contentValuesOf(
//                USER_NAME to name,
//                PHONE_NUMBER to phoneNumber,
//                PASSWORD to password,
//                IS_ADMIN to isAdmin
//            )
//        val userId = db?.insert(TABLE_USER, null, contentValues)
//
//    }

    fun isAccountExist(phoneNumber: String): Int {
        val db = this.readableDatabase
        val getAccountQuery = "SELECT * FROM $TABLE_USER WHERE $PHONE_NUMBER = $phoneNumber"
        val accountCursor = db.rawQuery(getAccountQuery, null)
        if (accountCursor.moveToFirst()) {
            val userId = accountCursor.getInt(accountCursor.getColumnIndexOrThrow(USER_ID))
            return userId
        }
        return -1
    }

    fun getPassword(userId: Int): String {
        val db = this.readableDatabase
        val query = "SELECT $PASSWORD FROM $TABLE_USER WHERE $USER_ID = $userId"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            val password =
                cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD))
            return password
        }
        return ""

    }

    fun getUser(userId: Int): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USER WHERE $USER_ID = $userId"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            val userId = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID))
            val userName = cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME))
            val phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(PHONE_NUMBER))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD))
            val isAdmin = cursor.getInt(cursor.getColumnIndexOrThrow(IS_ADMIN))
            val user = User(userId, userName, phoneNumber, password, isAdmin)
            return user
        }
        return null
    }

    fun getUserType(userId: Int): Int {
        val db = this.writableDatabase
        val query = "SELECT $IS_ADMIN FROM $TABLE_USER WHERE $USER_ID = $userId"
        val cursor = db.rawQuery(query, null)
        var isAdmin = 0
        if (cursor.moveToFirst()) {
            isAdmin = cursor.getInt(cursor.getColumnIndexOrThrow(IS_ADMIN))
        }
        return isAdmin
    }

    fun insertItemData(
        pizzaId: Int,
        quantity: Int,
        size: Size,
        price: Int,
        toppingsSelected: ArrayList<Int>
    ): Int {
        val db = this.writableDatabase
        val contentValues = contentValuesOf(
            PIZZA_ID to pizzaId,
            QUANTITY to quantity,
            ITEM_SIZE to size.name,
            ITEM_PRICE to price
        )
        val itemId = db.insert(TABLE_ITEM, null, contentValues)
        if (toppingsSelected.isNotEmpty()) {
            for (toppingId in toppingsSelected)
                insertSelectedToppingData(itemId.toInt(), toppingId)
        }
        db.close()
        return itemId.toInt()
    }

    fun insertCartData(userId: Int, itemId: Int) {
        val db = this.writableDatabase
        val contentValues = contentValuesOf(USER_ID to userId, ITEM_ID to itemId)
        db.insert(TABLE_CART, null, contentValues)
        db.close()
    }

    fun getCartItems(userId: Int): ArrayList<Item> {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_CART WHERE $USER_ID = $userId"
        val cursor = db.rawQuery(query, null)
        val listOfItemId = arrayListOf<Int>()

        if (cursor.moveToFirst()) {
            do {
                val itemId = cursor.getInt(cursor.getColumnIndexOrThrow(ITEM_ID))
                listOfItemId.add(itemId)
            } while (cursor.moveToNext())
        }
        val listOfItems = getItem(listOfItemId)
        return listOfItems

    }

    fun getItem(listOfItemId: ArrayList<Int>): ArrayList<Item> {
        val db = this.readableDatabase
        val listOfItems = arrayListOf<Item>()
        for (itemId in listOfItemId) {
            val query = "SELECT * FROM $TABLE_ITEM WHERE $ITEM_ID = $itemId"
            val cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                val itemId = cursor.getInt(cursor.getColumnIndexOrThrow(ITEM_ID))
                val pizzaId = cursor.getInt(cursor.getColumnIndexOrThrow(PIZZA_ID))
                val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(QUANTITY))
                val size = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_SIZE))
                val price = cursor.getInt(cursor.getColumnIndexOrThrow(ITEM_PRICE))
                val item = Item(itemId, pizzaId, quantity, Size.valueOf(size), price)
                listOfItems.add(item)
            }
        }
        return listOfItems
    }

    fun updateCartUserId(userId: Int) {
        val db = this.writableDatabase
        val contentValues = contentValuesOf(USER_ID to userId)
        db.update(TABLE_CART, contentValues, "$USER_ID = 0", null)
        db.close()
    }

    fun deleteItemFromCart(itemId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_CART, "$ITEM_ID = $itemId", null)
        db.close()
    }

    fun deleteCart(userId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_CART, "$USER_ID = $userId", null)
        db.close()
    }

    fun insertAddress(completeAddress: String, addressTag: String): Int {
        val db = this.writableDatabase
        val contentValues =
            contentValuesOf(COMPLETE_ADDRESS to completeAddress, ADDRESS_TAG to addressTag)
        val addressId = db.insert(TABLE_ADDRESS, null, contentValues)
        db.close()
        return addressId.toInt()
    }

    fun getAddress(listOfAddressId: MutableList<Int>): ArrayList<Address> {
        val db = this.readableDatabase
        val listOfAddress = arrayListOf<Address>()
        for (addressId in listOfAddressId) {
            val query = "SELECT * FROM $TABLE_ADDRESS WHERE $ADDRESS_ID = $addressId"
            val cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                val addressId = cursor.getInt(cursor.getColumnIndexOrThrow(ADDRESS_ID))
                val completeAddress =
                    cursor.getString(cursor.getColumnIndexOrThrow(COMPLETE_ADDRESS))
                val addressTag = cursor.getString(cursor.getColumnIndexOrThrow(ADDRESS_TAG))
                val address = Address(addressId, addressTag, completeAddress)
                listOfAddress.add(address)

            }
        }
        return listOfAddress
    }

    fun getCompleteAddress(addressId: Int): String {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_ADDRESS WHERE $ADDRESS_ID = $addressId"
        val cursor = db.rawQuery(query, null)
        var completeAddress = ""
        if (cursor.moveToFirst()) {
            completeAddress =
                cursor.getString(cursor.getColumnIndexOrThrow(COMPLETE_ADDRESS))
            println(completeAddress)
        }
        return completeAddress
    }


    fun deleteAddress(addressId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_ADDRESS, "$ADDRESS_ID = $addressId", null)
        db.close()
    }

    fun insertUserAddress(userId: Int, addressId: Int) {
        val db = this.writableDatabase
        val contentValues = contentValuesOf(USER_ID to userId, ADDRESS_ID to addressId)
        val userAddressId = db.insert(TABLE_USER_ADDRESS, null, contentValues)
        db.close()
    }

    fun getUserAddress(userId: Int): ArrayList<Address> {
        val db = this.readableDatabase
        val query = "SELECT $ADDRESS_ID FROM $TABLE_USER_ADDRESS WHERE $USER_ID = $userId"
        val cursor = db.rawQuery(query, null)
        val listOfAddressId = mutableListOf<Int>()

        if (cursor.moveToFirst()) {
            do {
                val addressId = cursor.getInt(cursor.getColumnIndexOrThrow(ADDRESS_ID))
                listOfAddressId.add(addressId)
            } while (cursor.moveToNext())
        }
        val listOfAddress = getAddress(listOfAddressId)
        return listOfAddress
    }

    fun insertOrderData(
        userId: Int,
        totalPrice: Int,
        status: String,
        date: String,
        deliveryAddressId: Int
    ): Int {
        val db = this.writableDatabase
        val contentValues = contentValuesOf(
            USER_ID to userId, TOTAL_PRICE to totalPrice, STATUS to status, DATE to date,
            ADDRESS_ID to deliveryAddressId
        )
        val orderId = db.insert(TABLE_ORDERS, null, contentValues)
        insertOrderItems(orderId.toInt(), userId)
        db.close()
        return orderId.toInt()
    }

    fun insertOrderItems(orderId: Int, userId: Int) {
        val listOfItems = getCartItems(userId)
        val db = this.writableDatabase
        for (i in listOfItems) {
            val contentValues = contentValuesOf(ORDER_ID to orderId, ITEM_ID to i.itemId)
            db.insert(TABLE_ORDER_ITEMS, null, contentValues)
        }
        db.close()
    }

    fun getOrdersHistory(userId: Int): ArrayList<Order> {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_ORDERS WHERE $USER_ID = $userId"
        val cursor = db.rawQuery(query, null)
        val listOfOrders = arrayListOf<Order>()
        if (cursor.moveToFirst()) {
            do {
                val orderId = cursor.getInt(cursor.getColumnIndexOrThrow(ORDER_ID))
                val userId = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID))
                val totalPrice = cursor.getInt(cursor.getColumnIndexOrThrow(TOTAL_PRICE))
                val status = Status.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(STATUS)))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(DATE))
                val deliveryAddressId = cursor.getInt(cursor.getColumnIndexOrThrow(ADDRESS_ID))
                val order = Order(orderId, userId, totalPrice, status, date, deliveryAddressId)
                listOfOrders.add(order)
            } while (cursor.moveToNext())
        }
        return listOfOrders
    }

    fun filterOrdersByDate(date: String): ArrayList<Order> {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_ORDERS WHERE $DATE = '$date'"
        val cursor = db.rawQuery(query, null)
        val listOfOrders = arrayListOf<Order>()
        if (cursor.moveToFirst()) {
            do {
                val orderId = cursor.getInt(cursor.getColumnIndexOrThrow(ORDER_ID))
                val userId = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID))
                val totalPrice = cursor.getInt(cursor.getColumnIndexOrThrow(TOTAL_PRICE))
                val status = Status.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(STATUS)))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(DATE))
                val deliveryAddressId = cursor.getInt(cursor.getColumnIndexOrThrow(ADDRESS_ID))
                val order = Order(orderId, userId, totalPrice, status, date, deliveryAddressId)
                listOfOrders.add(order)
            } while (cursor.moveToNext())
        }
        return listOfOrders
    }

    fun getOrderTotalAmount(orderId: Int): Int {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_ORDERS WHERE $ORDER_ID = $orderId"
        val cursor = db.rawQuery(query, null)
        var totalPrice = 0
        if (cursor.moveToFirst()) {
            totalPrice = cursor.getInt(cursor.getColumnIndexOrThrow(TOTAL_PRICE))
        }
        return totalPrice
    }

    fun updateOrderStatus(orderId: Int, status: String) {
        val db = this.writableDatabase
        val contentValues = contentValuesOf(STATUS to status)
        db.update(TABLE_ORDERS, contentValues, "$ORDER_ID = $orderId", null)
        db.close()
    }

    fun getItemsInOrder(orderId: Int): ArrayList<Int> {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_ORDER_ITEMS WHERE $ORDER_ID = $orderId"
        val cursor = db.rawQuery(query, null)
        val listOfItemId = arrayListOf<Int>()
        if (cursor.moveToFirst()) {
            do {
                val itemId = cursor.getInt(cursor.getColumnIndexOrThrow(ITEM_ID))
                listOfItemId.add(itemId)
            } while (cursor.moveToNext())
        }
        return listOfItemId
    }

}