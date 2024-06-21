package com.capstone.nutritrack.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.capstone.nutritrack.R
import com.capstone.nutritrack.ui.home.HomeFragment
import com.capstone.nutritrack.ui.tracking.TrackingFragment

class FrameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)

        if (intent.getBooleanExtra("NAVIGATE_TO_TRACKING", false)) {
            // Tampilkan TrackingFragment
            loadFragment(TrackingFragment())
        } else {
            // Tampilkan fragment default atau fragment lain
            loadFragment(HomeFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }
}
