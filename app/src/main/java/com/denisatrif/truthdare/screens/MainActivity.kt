package com.denisatrif.truthdare.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.databinding.ActivityMainBinding
import android.util.DisplayMetrics

import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setAppLanguage()
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun setAppLanguage(){
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val appLang = "ro_RO" //sharedPreferences.getString("appLanguage", "ro")

        val myLocale = Locale(appLang)
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration
        conf.locale = myLocale
        Locale.setDefault(myLocale)
        conf.setLayoutDirection(myLocale)
        createConfigurationContext(conf)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}