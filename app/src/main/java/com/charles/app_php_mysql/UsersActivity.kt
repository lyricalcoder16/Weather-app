package com.charles.app_php_mysql

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.util.ArrayList

class UsersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        val recycleUsers: RecyclerView = findViewById(R.id.recyclerViewUsers)
        val usersList = ArrayList<User>()
        val layoutManager = LinearLayoutManager(this)
        recycleUsers.layoutManager = layoutManager

        //divider
        val divider = DividerItemDecoration(this, layoutManager.orientation)
        recycleUsers.addItemDecoration(divider)

        //adapter
        val adapter = CustomAdapter(usersList)
        recycleUsers.adapter = adapter

        //fetch data
        val queue = Volley.newRequestQueue(this)
        val url = "https://android.emobilis.ac.ke/fetch.php"
        val request = JsonObjectRequest(Request.Method.POST, url, null, { response ->
            val jsonArray = response.getJSONArray("users")
            for (i in 0 until jsonArray.length()) {
                val userObject = jsonArray.getJSONObject(i)
                val id = userObject.get("id").toString()
                val name = userObject.get("name").toString()
                val email = userObject.get("email").toString()
                val phone = userObject.get("phone").toString()
                val address = userObject.get("address").toString()
                val city = userObject.get("city").toString()
                val country = userObject.get("country").toString()
                val person = User(id, name, email, phone, address, city, country)
            }
            adapter.notifyDataSetChanged()
        }, {
            Toast.makeText(this, "Something bad happenned", Toast.LENGTH_SHORT).show()
        })
        queue.add(request)


    }
}