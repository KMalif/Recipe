package com.example.resep.contract

import android.content.Context

interface RegisterActivityContract {

    interface RegisterActivityView{
        fun showToast(message : String)
        fun successRegister()
        fun showLoading()
        fun hideLoading()
    }

    interface RegisterActivityPresenter{
        fun register(name: String, email : String, password : String, context: Context)

        fun destroy()
    }
}