package com.example.myapplication

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.myapplication.ui.dashboard.DashboardViewModel
import com.example.myapplication.ui.home.HomeViewModel
import com.example.myapplication.ui.notifications.NotificationsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.navigation_home),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        val aaa: Toolbar = findViewById(R.id.toolbar)
        aaa.inflateMenu(R.menu.bottom_nav_menu)
        aaa.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.navigation_home -> {
                    //翻譯選項
                    showRadioConfirmationDialog()
                }
            }
            false
        }
        NavigationUI.setupWithNavController(aaa, navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id != R.id.navigation_home) {
                aaa.title=JSONObject(ViewModelProviders.of(this).get(HomeViewModel::class.java).text.value).getString("name")
                aaa.menu.findItem(R.id.navigation_home).isVisible = false
            } else{
                aaa.menu.findItem(R.id.navigation_home).isVisible = true
            }
        }

        val handleTask = GetDataTask()
        handleTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this,"zh-tw")

        ViewModelProviders.of(this).get(HomeViewModel::class.java).text.observe(
            this
        ) {
            if (it != "") {
                navController.navigate(R.id.navigation_dashboard)
                ViewModelProviders.of(this).get(DashboardViewModel::class.java).settext(it)
            }
        }

        ViewModelProviders.of(this).get(NotificationsViewModel::class.java).text.observe(
            this
        ) {
            if (it != "") {
                navController.navigate(R.id.navigation_notifications)
            }
        }
    }

    private lateinit var selectedLanguage: String
    private lateinit var selectedLanguage2: String
    private var selectedLanguageIndex: Int = 0
    private val language = arrayOf("正體中文", "簡體中文", "英文", "日文", "韓文",
        "西班牙文",  "泰文", "越南文")
    private val language2 = arrayOf("zh-tw", "zh-cn", "en", "ja", "ko",
        "es",  "th", "vi")


    private fun showRadioConfirmationDialog() {
        selectedLanguage = language[selectedLanguageIndex]
        selectedLanguage2 = language2[selectedLanguageIndex]
        MaterialAlertDialogBuilder(this)
            .setTitle("List of Fruits")
            .setSingleChoiceItems(language, selectedLanguageIndex) { dialog_, which ->
                selectedLanguageIndex = which
                selectedLanguage = language[which]
                selectedLanguage2 = language2[which]
            }
            .setPositiveButton("Ok") { dialog, which ->
                Toast.makeText(this, "$selectedLanguage", Toast.LENGTH_SHORT)
                    .show()
                val handleTask = GetDataTask()
                handleTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this,
                    selectedLanguage2
                )
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
}