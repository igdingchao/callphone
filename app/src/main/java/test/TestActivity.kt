package test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.mykotlinphone.R

const val TAG = "TestActivity"

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }
}