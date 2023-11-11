package com.techsultan.mynotes

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.techsultan.mynotes.databinding.ActivityMainBinding
import com.techsultan.mynotes.viewmodel.NoteViewModel
import com.techsultan.mynotes.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setSupportActionBar(binding.materialToolBar)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

       // setupActionBar(navController, appBarConfiguration)
       // hideNavBars(navController)

        showStatusNotification(this)
    }


    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    /*private fun hideNavBars(navController: NavController) {

        val appBarLayout = binding.materialToolBar


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.newNoteFragment -> {
                    appBarLayout.visibility = View.GONE
                }

                else -> {
                    appBarLayout.visibility = View.VISIBLE

                }
            }
        }

    }*/


    private fun showStatusNotification(context: Context) {

        val channelId = "note_saving"
        val note = "Saving note"

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Saving")
            .setContentText(note)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.ic_save)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
            .build()

        val notificationManager = NotificationManagerCompat.from(applicationContext)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(1, notification)
    }

}