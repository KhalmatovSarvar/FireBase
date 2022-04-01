package com.example.firebase.managers

import com.example.firebase.model.Post

interface DatabaseHandler {
    fun onSuccess(post: Post? =null,posts:ArrayList<Post> = ArrayList())
    fun onError()
}