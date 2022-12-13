package uk.co.macsoftware.funwithflags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    private fun init() {

        val editTextName: EditText = findViewById(R.id.editTextName)
        val buttonStart: Button = findViewById(R.id.buttonStart)

        buttonStart.setOnClickListener {
            if (editTextName.text.isEmpty()) {
                Toast.makeText(this,
                    getString(R.string.enter_your_name), Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, editTextName.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }


}