package me.lucky.catcher

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings

import me.lucky.catcher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        setup()
    }

    private fun init() {
        NotificationManager(this).createNotificationChannels()
        updateDatabase()
    }

    private fun updateDatabase() {
        val db = AppDatabase.getInstance(this).packageDao()
        db.deleteAll()
        for (app in packageManager
            .getInstalledApplications(0)
            .filterNot { Utils.hasInternet(this, it.packageName) })
        {
            try {
                db.insert(Package(0, app.packageName))
            } catch (exc: SQLiteConstraintException) {}
        }
    }

    private fun setup() = binding.apply {
        gotoButton.setOnClickListener {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }
    }
}