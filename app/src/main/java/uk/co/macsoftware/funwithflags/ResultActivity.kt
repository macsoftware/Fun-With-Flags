package uk.co.macsoftware.funwithflags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val textViewUsername: TextView = findViewById(R.id.textViewName)
        val textViewScore: TextView = findViewById(R.id.textViewScore)
        val buttonFinish: Button = findViewById(R.id.buttonFinish)

        val questions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val score = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        textViewUsername.text = intent.getStringExtra(Constants.USER_NAME)
        textViewScore.text = getString(R.string.your_score_is, score, questions)

        buttonFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}