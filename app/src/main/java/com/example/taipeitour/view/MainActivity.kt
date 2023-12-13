package com.example.taipeitour.view

import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import com.example.taipeitour.R
import com.example.taipeitour.databinding.ActivityMainBinding
import com.example.taipeitour.viewmodel.MainViewModel
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        toolbar = binding.toolbar
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))

        setStatusBarColor(R.color.blue)


        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

//        viewModel.languagesLiveData.observe(this, Observer { languages ->
//
//        })

    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setStatusBarColor(colorResId: Int) {
        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        windowInsetsController.isAppearanceLightStatusBars = true // 根據你的 UI 選擇亮色或暗色

        window.statusBarColor = ContextCompat.getColor(this, colorResId)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {
                showLanguageSelectionDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLanguageSelectionDialog() {
        // 直接使用 LiveData 中的語言數據
        val builder = AlertDialog.Builder(this)
        builder.setItems(viewModel.languagesLiveData.value?.toTypedArray()) { _, which ->
            viewModel.onLanguageSelected(viewModel.languagesLiveData.value?.get(which) ?: "")
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}