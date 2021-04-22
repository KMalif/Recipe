package com.example.resep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.resep.contract.LoginActivityContract
import com.example.resep.databinding.ActivityLoginBinding
import com.example.resep.presenter.LoginActivityPresenter
import com.example.resep.utilities.Constants

class LoginActivity : AppCompatActivity(), LoginActivityContract.LoginView {
    private var presenter : LoginActivityContract.LoginPresenter? = null

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        presenter = LoginActivityPresenter(this)
        doLogin()
    }

    private fun doLogin(){
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()
            if (email.isNotEmpty() && pass.isNotEmpty()){
                if (pass.length > 8){
                    presenter?.login(email, pass, this)
                }else{
                    showToast("Password must be 8 character or more")
                }
            }else{
                showToast("Please fill all forms")
            }
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun successLogin() {
        startActivity(Intent(this, MainActivity::class.java).also {
            finish()
        })
    }

    override fun showLoading() {
        binding.btnLogin.isEnabled = false
        binding.btnRegister.isEnabled = false
        binding.loading.apply {
            isIndeterminate = true
        }
    }

    override fun hideLoading() {
        binding.btnLogin.isEnabled = true
        binding.btnRegister.isEnabled = true
        binding.loading.apply {
            isIndeterminate = false
            progress = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

    override fun onResume() {
        super.onResume()
        val token = Constants.getToken( this)
        if (!token.equals("UNDEFINED")){
            startActivity(Intent(this, MainActivity::class.java).also { finish() })
        }
    }
}
