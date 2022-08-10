package com.charles.app_php_mysql

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /* val url = "https://android.emobilis.ac.ke/fetch.php"
        //making a request
        //go to the Url and fetch the data

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { responseJson ->
                Log.d("FETCHING", "onCreate: $responseJson")
                val jsonArray = responseJson.getJSONArray("users")
                for (i in 0 until jsonArray.length()) {
                    val userJsonObject = jsonArray.getJSONObject(i)
                    val name = userJsonObject.get("name")
                    val phone = userJsonObject.get("phone")
                    Log.d("USER", "onCreate: $name : $phone")
                    }
            },
            { error ->
                Log.e("FETCHING", "onCreate:Error while fetching", error)
            })
        queue.add(request)*/
/*
        val weatherUrl = "http://api.weatherapi.com/v1/current.json?key=f269d6ac5ca5477896375924220208&q=London"
        val weatherRequest = JsonObjectRequest(Request.Method.GET,weatherUrl,null,
            {
            mainJsonObject ->
                val locationObject = mainJsonObject.getJSONObject("location")
                val city = locationObject.get("name")
                val country = locationObject.get("country")
                Log.d("WEATHER","onCreate: $city in $country")
                val currentObject = mainJsonObject.getJSONObject("current")
                val temp = currentObject.get("temp_c")
                val windSpeed = currentObject.get("wind_kph")
                val visibility = currentObject.get("vis_km")
                Log.d("WEATHER","onCreate: Temperature is $temp c,windspeed is $windSpeed/km/h visibility is $visibility km")
                val condition = mainJsonObject.getJSONObject("current").getJSONObject("condition").get("text")
            },{
                error ->
                Log.d("WEATHER","onCreate: Error while fetching weather data",error)
            })
        queue.add(weatherRequest)
*/

        val queue = Volley.newRequestQueue(this)
        val url = "https://android.emobilis.ac.ke/insert.php"


        val inputName: EditText = findViewById(R.id.editTextName)
        val inputEmail: EditText = findViewById(R.id.editTextTextEmailAddress)
        val inputPhone: EditText = findViewById(R.id.editTextPhone)
        val inputAddress: EditText = findViewById(R.id.editTextTextPostalAddress)
        val inputCity: EditText = findViewById(R.id.editTextCity)
        val inputCountry: EditText = findViewById(R.id.editTextCountry)

        val buttonSave: Button = findViewById(R.id.buttonSave)
        val buttonShow: Button = findViewById(R.id.buttonShow)

        buttonSave.setOnClickListener {
            val name = inputName.text.toString().trim()
            val email = inputEmail.text.toString().trim()
            val phone = inputPhone.text.toString().trim()
            val address = inputAddress.text.toString().trim()
            val city = inputCity.text.toString().trim()
            val country = inputCountry.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty() && city.isNotEmpty() && country.isNotEmpty()){
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("saving.....")
                progressDialog.setMessage("Processing")
                progressDialog.show()
                val request = object : StringRequest(Method.POST, url, {
                    Toast.makeText(this,"$it", Toast.LENGTH_SHORT).show()
                    inputName.text.clear()
                    inputEmail.text.clear()
                    inputPhone.text.clear()
                    inputAddress.text.clear()

                },{
                    progressDialog.dismiss()
                    Log.e("SAVING", "onCreate: ",it )
                    Toast.makeText(this,"Error happened while saving user",Toast.LENGTH_SHORT).show()
                }) {
                    override fun getParams(): MutableMap<String, String>? {
                        val map = HashMap<String, String>()
                        map ["name"] = name
                        map ["email"] = email
                        map ["phone"] = phone
                        map ["address"] = address
                        map ["city"] = city
                        map ["country"] = country
                        return super.getParams()
                    }
                }
                queue.add(request)
            }else{
                Toast.makeText(this,"Fill in all the fields",Toast.LENGTH_SHORT).show()
            }


        }
        buttonShow.setOnClickListener {
            val intent = Intent(this,UsersActivity::class.java)
            startActivity(intent)
        }


    }
}