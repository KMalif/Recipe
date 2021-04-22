package com.example.resep.contract

interface RecipeActivityContract {
    interface RecipeActivityView{
        fun showLoading()

        fun hideLoading()

        fun toast(message : String)

        fun success()
    }

    interface RecipeActivityPresenter{

        fun create(token : String, title : String, content : String)

        fun update (token: String, id : String, title: String, content: String)

        fun delete(token: String, id: String)

        fun destroy()
    }
}