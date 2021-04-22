package com.example.resep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.resep.contract.RegisterActivityContract
import com.example.resep.databinding.ActivityRegisterBinding
import com.example.resep.presenter.RegisterActivityPresenter

class RegisterActivity : AppCompatActivity(),RegisterActivityContract.RegisterActivityView {
    private var presenter : RegisterActivityContract.RegisterActivityPresenter? = null
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = RegisterActivityPresenter(this)
        doRegister()
    }

    private fun doRegister(){
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()
            if (name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty()){
                if (pass.length > 8){
                    if (name.length > 5){
                        presenter?.register(name, email, pass, this)
                    }else{
                        showToast("Name must be 5 characters or more")
                    }
                }else{
                    showToast("Password must be 8 characters or more")
                }
            }else{
                showToast("Please fill all forms first")
            }
        }
        binding.btnBack.setOnClickListener { finish() }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun successRegister() {
        finish()
    }

    override fun showLoading() {
        binding.btnRegister.isEnabled = false
        binding.btnBack.isEnabled = false
        binding.loading.apply {
            isIndeterminate = true
        }
    }

    override fun hideLoading() {
        binding.btnRegister.isEnabled = true
        binding.btnBack.isEnabled = true
        binding.loading.apply{
            isIndeterminate = false
            progress = 0
        }
    }
}