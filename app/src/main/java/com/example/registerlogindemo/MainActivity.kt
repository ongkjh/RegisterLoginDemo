package com.example.registerlogindemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class MainActivity : AppCompatActivity() {

    private var email: String? = null
    private var password: String? = null
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    private val URL :String = "http://10.0.2.2/dbconnects/login.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
    }

    fun login(view: View?) {
        var email = etEmail.getText().toString().trim()
        var password = etPassword.getText().toString().trim()
        if (email != "" && password != "") {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)
                    if (response == "success") {
                        val intent = Intent(this@MainActivity, Success::class.java)
                        startActivity(intent)
                        finish()
                    } else if (response == "failure") {
                        Toast.makeText(
                            this@MainActivity,
                            "Invalid Login Id/Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        this@MainActivity,
                        error.toString().trim { it <= ' ' },
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["email"] = email
                    data["password"] = password
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(applicationContext)
            requestQueue.add(stringRequest)
        } else {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show()
        }
    }

    fun register(view: View?) {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
        finish()
    }
}