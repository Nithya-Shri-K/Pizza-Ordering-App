package com.example.pizzaorderingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrdersAdapter(val orders : ArrayList<Order>,val userType : String) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {
    inner class OrderViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val orderId = view.findViewById<TextView>(R.id.order_id)
        val orderItems = view.findViewById<RecyclerView>(R.id.order_items)
        val context = view.context
        val statusAdmin = view.findViewById<Spinner>(R.id.status_admin)
        val statusUser = view.findViewById<TextView>(R.id.status_user)
        val date = view.findViewById<TextView>(R.id.date)
        val price = view.findViewById<TextView>(R.id.price)
        fun setSpinnerData(){

            if(userType == "user"){
                statusAdmin.visibility = View.GONE
                statusUser.visibility = View.VISIBLE
                statusUser.text = orders[adapterPosition].status.name

            }
            else{
                statusAdmin.visibility= View.VISIBLE
                statusUser.visibility = View.GONE
                val statusData = arrayListOf<String>("Waiting","Accepted","Delivered")
                val arrayAdapter = ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, statusData)
                statusAdmin.adapter = arrayAdapter
                statusAdmin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, selectedStatus: Int, id: Long) {
                        val status = Status.valueOf(adapterView?.getItemAtPosition(selectedStatus).toString())
                        orders[adapterPosition].status = status

                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }

                }
                         }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.order,parent,false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.orderId.text = orders[position].orderId.toString()
        holder.setSpinnerData()
        holder.orderItems.layoutManager = LinearLayoutManager(holder.context,LinearLayoutManager.VERTICAL,false)
        holder.orderItems.adapter = ItemAdapter(orders[position].items)
        holder.date.text = orders[position].date
        holder.price.text = "Rs. ${orders[position].totalPrice}"


    }

    override fun getItemCount(): Int {
        return orders.size
    }
}