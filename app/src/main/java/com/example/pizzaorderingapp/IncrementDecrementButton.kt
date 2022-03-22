package com.example.pizzaorderingapp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView

class IncrementDecrementButton : LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributes: AttributeSet) : super(context, attributes)

    constructor(
        context: Context,
        attributes: AttributeSet,
        defStyle: Int
    ) : super(context, attributes, defStyle)

    var value = 1

    init {
        LayoutInflater.from(context).inflate(R.layout.increment_decrement_button, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val decrementButton = findViewById<TextView>(R.id.button_decrement)
        val incrementButton = findViewById<TextView>(R.id.button_increment)
        val textValue = findViewById<TextView>(R.id.text_value)
        decrementButton.setOnClickListener { decrementValue(textValue) }
        incrementButton.setOnClickListener { incrementValue(textValue) }
    }

    private fun incrementValue(textValue: TextView) {
        value += 1
        textValue.text = value.toString()
        (context as CustomizeActivity).onQuantityIncrement(value)
    }

    private fun decrementValue(textValue: TextView) {
        value -= 1
        textValue.text = value.toString()
        (context as CustomizeActivity).onQuantityIncrement(value)
    }


}