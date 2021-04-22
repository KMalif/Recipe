package com.example.resep

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.resep.contract.RecipeActivityContract
import com.example.resep.databinding.ActivityRecipeBinding
import com.example.resep.models.Post
import com.example.resep.presenter.RecipeActivityPresenter
import com.example.resep.utilities.Constants
import kotlinx.android.synthetic.main.content_recipe.*

class RecipeActivity : AppCompatActivity(), RecipeActivityContract.RecipeActivityView {
    private var presenter : RecipeActivityContract.RecipeActivityPresenter? = null
    private lateinit var binding: ActivityRecipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setOnClickListener{finish()}
        presenter = RecipeActivityPresenter(this)
        saveChange()
        fill()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(!isNew()){
            menuInflater.inflate(R.menu.menu_recipe, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_delete ->{
                val builder = AlertDialog.Builder(this)
                builder.apply {
                    setMessage("Are you sure to delete this ?")
                    setPositiveButton("DELETE"){ dialogInterface, i ->
                        delete()
                    }
                    setNegativeButton("Cancel"){dialogInterfce, i ->
                        dialogInterfce.cancel()
                    }
                }
                val alert = builder.create()
                alert.show()
                return true
            }
            else -> {return super.onOptionsItemSelected(item)}
        }
    }

    private fun isNew () : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getPost() : Post = intent.getParcelableExtra("POST")!!

    private fun fill(){
        if (!isNew()){
            et_title.setText(getPost().title)
            et_content.setText(getPost().content)
        }
    }

    private fun saveChange(){
        binding.fab.setOnClickListener {view ->
            val title = et_title.text.toString().trim()
            val content = et_content.text.toString().trim()
            if (title.isNotEmpty() && content.isNotEmpty()){
                if (title.length > 5 && content.length > 20){
                    if (isNew()){
                        presenter?.create("Bearer ${Constants.getToken(this)}", title, content)
                    }else{
                        presenter?.update("Bearer ${Constants.getToken(this)}", getPost().id.toString(), title, content)
                    }
                }else{
                    toast("Title must be at least 5 characters and description at least 20 chars or more")
                }
            }else{
                toast("Please fill all forms")
            }

        }
    }
    override fun showLoading() {
        binding.fab.hide()
    }

    override fun hideLoading() {
        binding.fab.show()
    }

    override fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun success() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
    private fun delete (){
        presenter?.delete("Bearer ${Constants.getToken(this)}", getPost().id.toString())
    }
}