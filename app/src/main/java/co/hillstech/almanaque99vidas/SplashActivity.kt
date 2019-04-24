package co.hillstech.almanaque99vidas

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Session.userId = 1

        startActivity(Intent(this, MainActivity::class.java))
    }
}
