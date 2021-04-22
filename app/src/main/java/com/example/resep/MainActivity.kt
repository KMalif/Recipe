package com.example.resep

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.resep.adapter.PostAdapter
import com.example.resep.contract.MainActivityContract
import com.example.resep.databinding.ActivityMainBinding
import com.example.resep.models.Post
import com.example.resep.presenter.MainActivityPresenter
import com.example.resep.utilities.Constants
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), MainActivityContract.MainActivityView {
    private var presenter : MainActivityContract.MainActivityPresenter? = null

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        presenter = MainActivityPresenter(this)
        binding.fab.setOnClickListener {
            startActivity(Intent(this, RecipeActivity::class.java).apply {
                putExtra("IS_NEW", true)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                val b = AlertDialog.Builder(this@MainActivity)
                b.apply {
                    setMessage("Are you sure want to log out?")
                    setPositiveButton("LOGOUT"){ dialogInterface, i ->
                        Constants.clearToken(this@MainActivity)
                        checkIsLoggedIn()
                    }
                    setNegativeButton("CANCEL"){ dialogInterface, i -> dialogInterface.cancel()  }
                }
                val alert = b.create()
                alert.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun getPosts() = presenter?.all("Bearer ${Constants.getToken(this@MainActivity)}")

    override fun attachToRecycler(posts: List<Post>) {
        rv_post.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PostAdapter(posts, object : PostAdapter.OnAdapterListener{
                override fun Onclick(post: Post) {
                    startActivity(Intent(this@MainActivity, RecipeActivity::class.java).apply {
                        putExtra("IS_NEW", false)
                        putExtra("POST", post)
                    })
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

    private fun checkIsLoggedIn(){
        val token = Constants.getToken(this@MainActivity)
        if(token == null || token.equals("UNDEFINED")){
            startActivity(Intent(this@MainActivity, LoginActivity::class.java).also { finish() })
        }
    }

    override fun onResume() {
        super.onResume()
        getPosts()
    }



}