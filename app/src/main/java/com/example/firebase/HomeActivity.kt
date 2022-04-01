package com.example.firebase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.adapter.PostAdapter
import com.example.firebase.managers.AuthManager
import com.example.firebase.managers.DatabaseHandler
import com.example.firebase.managers.DatabaseManager
import com.example.firebase.model.Post
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : BaseActivity() {
        lateinit var recyclerView: RecyclerView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)
            initViews()
        }

        fun initViews() {
            recyclerView = findViewById(R.id.recyclerView)
            recyclerView.setLayoutManager(GridLayoutManager(this, 1))

            var iv_logout = findViewById<ImageView>(R.id.iv_logout)
            iv_logout.setOnClickListener {
                AuthManager.signOut()
                callSignInActivity(this)
            }

            var fab_create = findViewById<FloatingActionButton>(R.id.fab_create)
            fab_create.setOnClickListener {
                callCreateActivity()
            }

            apiLoadPosts()
        }

        fun apiLoadPosts() {
            showLoading(this)
            DatabaseManager.apiLoadPosts(object : DatabaseHandler {
                override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                    dismissLoading()
                    refreshAdapter(posts)
                }

                override fun onError() {
                    dismissLoading()
                }
            })
        }

        fun apiDeletePost(post: Post) {
            DatabaseManager.apiDeletePost(post, object: DatabaseHandler{
                override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                    apiLoadPosts()
                }

                override fun onError() {
                    TODO("Not yet implemented")
                }
            })
        }

        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                // Load all posts...
                apiLoadPosts()
            }
        }

        fun callCreateActivity() {
            val intent = Intent(this,CreateActivity::class.java)
            resultLauncher.launch(intent)
        }

        fun refreshAdapter(posts: ArrayList<Post>) {
            var adapter = PostAdapter(this, posts)
            recyclerView.adapter = adapter
        }
    }