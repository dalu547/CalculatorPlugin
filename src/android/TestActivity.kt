package cordova.plugin.calculator

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle
import android.widget.Toast

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_test)

        Toast.makeText(this,"TestActivity",Toast.LENGTH_SHORT).show();
        
    }
}
