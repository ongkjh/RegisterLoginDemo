package com.example.registerlogindemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class Register : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var etReenterPassword: EditText
    lateinit var tvStatus: TextView
    lateinit var btnRegister: Button
    var name: String? = ""
    var email: String? = ""
    var password: String? = ""
    var reenterPassword: String? =""

    private val URL: String ="http://10.0.2.2/dbconnects/register.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etReenterPassword = findViewById(R.id.etReenterPassword)
        tvStatus = findViewById(R.id.tvStatus)
        btnRegister = findViewById(R.id.btnRegister)

    }

    fun save(view: View?) {
        name = etName.text.toString().trim { it <= ' ' }
        email = etEmail.text.toString().trim { it <= ' ' }
        password = etPassword.text.toString().trim { it <= ' ' }
        reenterPassword = etReenterPassword.text.toString().trim { it <= ' ' }
        if (password != reenterPassword) {
            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show()
        } else if (name != "" && email != "" && password != "") {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    tvStatus.text = response.toString()
                    Log.d("Register",response)
                    if (response == "success") {
                        tvStatus.text = "Successfully registered."
                        btnRegister.isClickable = false
                    } else if (response == "failure") {
                        tvStatus.text = "Something went wrong!"
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        applicationContext,
                        error.toString().trim { it <= ' ' },
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["name"] = name!!
                    data["email"] = email!!
                    data["password"] = password!!
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(applicationContext)
            requestQueue.add(stringRequest)
        }
    }

    fun login(view: View?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}