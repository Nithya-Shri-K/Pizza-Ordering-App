package com.example.pizzaorderingapp

object Database {
    val pizza1 = Pizza(
        "Tandoori Paneer", R.drawable.tandooripaneer,
        mutableMapOf(Size.Regular to "299", Size.Medium to "499", Size.Large to "699"), Category.Veg
    )
    val pizza2 = Pizza(
        "Tandoori Chicken pizza",
        R.drawable.tandooripaneer,
        mutableMapOf(Size.Medium to "599", Size.Large to "799"),
        Category.NonVeg
    )
    val pizza3 = Pizza(
        "Chicken Tikka Supreme",
        R.drawable.tandooripaneer,
        mutableMapOf(Size.Regular to "599", Size.Large to "799"),
        Category.NonVeg
    )
    val pizza4 = Pizza(
        "Veggie Supreme",
        R.drawable.tandooripaneer,
        mutableMapOf(Size.Regular to "399", Size.Medium to "499", Size.Large to "799"),
        Category.Veg
    )
    val listOfItems = arrayListOf<Pizza>(
        pizza1, pizza2, pizza3, pizza4
    )
    val listOfToppings = arrayListOf<Topping>(
        Topping("Cheese", 50),
        Topping("Mushrooms", 70),
        Topping("Green Capsicum", 50),
        Topping("Red Paprika", 50),
        Topping("Chicken Balls", 100)
    )
    val listOfUsers = arrayListOf<User>(
        User(
            "Nithya",
            "7904788454",
            "Nithya",
            arrayListOf(Address("Home", "30, 1st street, Moogambigai Nagar, M K Kottai,Trichy 11")),
            arrayListOf(),
            false
        ),
        User("Rosy", "9043885171", "abcd", arrayListOf(), arrayListOf(), true)
    )
    val listOfOrders = arrayListOf<Order>(
        Order(
            arrayListOf(Item(pizza1, 1, arrayListOf(), Size.Regular, 299)),
            299,
            Status.Waiting,
            "04-03-2022",
            1,
            "30, 1st street, Moogambigai Nagar, M K Kottai,Trichy 11"
        ),
        Order(
            arrayListOf(Item(pizza1, 1, arrayListOf(), Size.Regular, 299)),
            299,
            Status.Waiting,
            "05-03-2022",
            1,
            "30, 1st street, Moogambigai Nagar, M K Kottai,Trichy 11"
        ),
        Order(
            arrayListOf(Item(pizza1, 1, arrayListOf(), Size.Regular, 299)),
            299,
            Status.Waiting,
            "04-03-2022",
            1,
            "30, 1st street, Moogambigai Nagar, M K Kottai,Trichy 11"
        )
    )
}