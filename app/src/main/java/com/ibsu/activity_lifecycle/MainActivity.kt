package com.ibsu.activity_lifecycle

import android.app.Service
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ibsu.activity_lifecycle.databinding.ActivityMainBinding

//What is the difference between val and const val in kotlin?

//1. Main difference between val and const val is that val can be assigned a value at runtime, whereas const val must be assigned a value at compile time and cannot be changed afterward(their values are hardcoded).
//2. const val can only be declared at the top level of a file or inside an object declaration, whereas val can be declared anywhere within a function, class or object.
//3. const val can improve performance by eliminating runtime computations, whereas val cannot.
//4. value of const val can only be assigned a primitive like type or a String.

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d("Function call, onCreate", "onCreate called")

        //here we set UI content

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Material Alert Dialog opened")
                //this does not cause onPause() to be called
                .setNeutralButton("Ok") { _, _ ->

                }.show()
        }

        binding.buttonCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is not granted
                // Request the permission

                //this causes onPause() to be called
                //permission dialogs are opened by Android SDK in another (transparent) activity
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            } else {
                Toast.makeText(this, "Permission already granted", Toast.LENGTH_LONG).show()
            }
        }

        val obj1 = Person("Tom" ,"Hanks", 67)
        val obj2 = Person("Tom" ,"Hanks", 67)

        d("2 =", (obj1 == obj2).toString()) //by default(if not overridden, Kotlin only compares the data or variables) so true
        d("3 =", (obj1 === obj2).toString()) //false
        d("equals =", (obj1.equals(obj2)).toString()) // 2= (==) calls .equals which can be overridden and that overridden function will be called
    }


    override fun onStart() {
        super.onStart()
        d("Function call, onStart", "onStart called")

        //activity started
        //became visible to user

    }

    override fun onResume() {
        super.onResume()
        d("Function call, onResume", "onResume called")

        //Activity is in foreground
        //user can interact with it

        //An activity is considered to be in the foreground when it is the currently focused and active component on the screen.
    }

    override fun onPause() {
        super.onPause()
        d("Function call, onPause", "onPause called")

        //all resources activity needs will be kept in memory
        //because user might come back to activity

        //and if activity is in paused state and user does comes back to activity
        //onResume() will be called

        //onPause() -> onResume()

//        An activity is considered to be visible to the user when any part of its UI is visible on the screen, even if it is not in the foreground.
//        This includes scenarios where the activity's UI is partially visible, such as when it is partially covered by another activity or a system dialog.
    }

    override fun onStop() {
        super.onStop()
        d("Function call, onStop", "onStop called")

        //activity (whole window) is not visible to user

        //if activity is in stopped state and user comes back to activity
        //onRestart() is called and then on onStart() is called

        //onStop() -> onRestart() -> onStart() -> onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        d("Function call, onDestroy", "onDestroy called")

        //user intentionally closed activity,
        //or user clicked back button,
        //or system kills your app

        //or when global configuration changes (e.x rotation change)
    }

    override fun onRestart() {
        super.onRestart()

        d("Function call, onRestart", "onRestart called")

//        called when user comes back to app that was in background
    }

}