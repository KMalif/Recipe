package com.example.resep.contract

import com.example.resep.models.Post

interface MainActivityContract {

    interface MainActivityView{
        fun showToast(message : String)
        fun attachToRecycler(posts : List<Post>)
    }

    interface MainActivityPresenter{
        fun all(token : String)
        fun destroy()
    }
}