package uk.co.macsoftware.funwithflags

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mUserName: String? = null
    private var mCorrectAnswers: Int = 0

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

        mUserName = intent.getStringExtra(Constants.USER_NAME)

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

    /**
     * Clears the correct and wrong answers then sets the next question on the activity
     */
    private fun setQuestion() {
        defaultOptionsView()
        val question: Question = mQuestionsList!![mCurrentPosition - 1]
        progressBar?.progress = mCurrentPosition
        progressBar?.max = mQuestionsList!!.size
        imageViewImage?.setImageResource(question.image)
        textViewProgress?.text = getString(R.string.progress_current_pos, mCurrentPosition, progressBar?.max)
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

    /**
     * Clears the formatting for all the previous answers and guesses to the default format.
     */
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

    /**
     * Changes the format of the selected option to give visual feedback.
     */
    private fun selectedOptionView(tv: TextView, selectedOptionNum:Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,
            R.drawable.selected_option_border_bg)
    }

    /**
     * Decides what it wants to do, be it selecting an option or checking to see if the answer
     * is correct.
     */
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
                submitted()
            }
        }
    }

    /**
     * Checks to see if you have selected a guess and if not, asks you to.
     * If you have then it checks to see if it is the right answer and feeds that back.
     */
    private fun submitted(){
        if(mSelectedOptionPosition == 0){
            mCurrentPosition++

            when{
                mCurrentPosition <= mQuestionsList!!.size -> {
                    setQuestion()
                }else -> {
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra(Constants.USER_NAME, mUserName)
                    intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList?.size)
                    intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                    startActivity(intent)
                    finish()
                }
            }
        }else{
            val question = mQuestionsList?.get(mCurrentPosition - 1)
            if(question!!.correctAnswer != mSelectedOptionPosition){
                answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
            }else{
                mCorrectAnswers++
            }
            answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

            if(mCurrentPosition == mQuestionsList!!.size){
                buttonSubmit?.text = getString(R.string.finish)
            }else{
                buttonSubmit?.text = getString(R.string.go_to_next_question)
            }

            mSelectedOptionPosition = 0
        }
    }

    /**
     * Sets the view for the correct answer and also shows if you selected the wrong answer.
     */
    private fun answerView(answer: Int, drawableView: Int){

        when(answer){
            1 -> {
                textViewOption1?.background = ContextCompat.getDrawable(
                    this, drawableView)
            }
            2 -> {
                textViewOption2?.background = ContextCompat.getDrawable(
                    this, drawableView)
            }
            3 -> {
                textViewOption3?.background = ContextCompat.getDrawable(
                    this, drawableView)
            }
            4 -> {
                textViewOption4?.background = ContextCompat.getDrawable(
                    this, drawableView)
            }
        }

    }

}