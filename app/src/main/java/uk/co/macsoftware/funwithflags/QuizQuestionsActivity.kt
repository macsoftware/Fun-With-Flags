package uk.co.macsoftware.funwithflags

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList:ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0

    private var progressBar: ProgressBar? = null
    private var textViewProgress: TextView? = null

    private var textViewQuestion: TextView? = null

    private var imageViewImage: ImageView? = null

    private var textViewOption1: TextView? = null
    private var textViewOption2: TextView? = null
    private var textViewOption3: TextView? = null
    private var textViewOption4: TextView? = null

    private var buttonSubmit: Button? = null

    private fun init(){
        progressBar = findViewById(R.id.progressBar)
        textViewProgress = findViewById(R.id.textViewProgress)

        textViewQuestion = findViewById(R.id.textViewQuestion)

        imageViewImage = findViewById(R.id.imageViewImage)

        textViewOption1 = findViewById(R.id.textViewOption1)
        textViewOption2 = findViewById(R.id.textViewOption2)
        textViewOption3 = findViewById(R.id.textViewOption3)
        textViewOption4 = findViewById(R.id.textViewOption4)

        buttonSubmit = findViewById(R.id.buttonSubmit)


        textViewOption1?.setOnClickListener(this)
        textViewOption2?.setOnClickListener(this)
        textViewOption3?.setOnClickListener(this)
        textViewOption4?.setOnClickListener(this)

        buttonSubmit?.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        init()
        mQuestionsList = Constants.getQuestions()

        setQuestion()
    }

    private fun setQuestion() {

        val question: Question = mQuestionsList!![mCurrentPosition - 1]
        progressBar?.progress = mCurrentPosition
        imageViewImage?.setImageResource(question.image)
        textViewProgress?.text = "$mCurrentPosition/${progressBar?.max}"
        textViewQuestion?.text = question.question
        textViewOption1?.text = question.optionOne
        textViewOption2?.text = question.optionTwo
        textViewOption3?.text = question.optionThree
        textViewOption4?.text = question.optionFour

        if(mCurrentPosition == mQuestionsList!!.size){
            buttonSubmit?.text = getString(R.string.finish)
        }else{
            buttonSubmit?.text = getString(R.string.submit)
        }

    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        textViewOption1?.let {
            options.add(0, it)
        }
        textViewOption2?.let {
            options.add(1, it)
        }
        textViewOption3?.let {
            options.add(2, it)
        }
        textViewOption4?.let {
            options.add(3, it)
        }

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this,
                                                          R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum:Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,
            R.drawable.selected_option_border_bg)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.textViewOption1 -> {
                textViewOption1?.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.textViewOption2 -> {
                textViewOption2?.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.textViewOption3 -> {
                textViewOption3?.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.textViewOption4 -> {
                textViewOption4?.let {
                    selectedOptionView(it, 4)
                }
            }
            R.id.buttonSubmit -> {
                //TODO: Implement button submit
            }
        }
    }

}