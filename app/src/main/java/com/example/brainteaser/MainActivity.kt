package com.example.brainteaser

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var go:Button
    lateinit var gametimer:CountDownTimer
    lateinit var playAgain:Button
    var locationofCorrectAnswer=0
    //var answers=Array<Int>(4){0}
    lateinit var result:TextView
    lateinit var scoreCard:TextView
    lateinit var question:TextView
    lateinit var option0:Button
    lateinit var option1:Button
    lateinit var option2:Button
    lateinit var option3:Button
    lateinit var timer:TextView
    var totalQuestions=0
    var myScore=0
    lateinit var audio:MediaPlayer

    fun start(androidView: View)
    {
        go.visibility=View.INVISIBLE
        option0.visibility=View.VISIBLE
        option1.visibility=View.VISIBLE
        option2.visibility=View.VISIBLE
        option3.visibility=View.VISIBLE
        timer.visibility=View.VISIBLE
        scoreCard.visibility=View.VISIBLE
        ques.visibility=View.VISIBLE
        var score=(myScore.toString())+"/"+(totalQuestions.toString())
        scoreCard.setText(score)
        newQuestion()
        gametimer = object : CountDownTimer(30100,1000)
        {
            override fun onTick(p0: Long) {
                var s=(p0/1000)
                var r=""
                if(s<10)
                {
                    r="00:0"+s.toString()
                }
                else
                {
                    r="00:"+s.toString()
                }
                Log.i("Time",r)
                timer.setText(r)
            }

            override fun onFinish() {
                result.visibility=View.VISIBLE
                result.textSize=32f
                result.setText("Time Over")
                playAgain.visibility=View.VISIBLE
                option0.isEnabled=false
                option1.isEnabled=false
                option2.isEnabled=false
                option3.isEnabled=false
                audio=MediaPlayer.create(this@MainActivity,R.raw.ship)
                audio.start()
            }

        }.start()

    }

    fun chooseAns(androidView: View)
    {
        var option= androidView as Button
        Log.i("Option Clicked",option.getTag().toString())
        if(option.getTag().toString()==(locationofCorrectAnswer).toString())
        {
            Log.i("Result","Correct Answer")
            result.setText("Correct!")
            result.visibility=View.VISIBLE
            myScore++
        }
        else if(option.getTag().toString()!=(locationofCorrectAnswer).toString())
        {
            Log.i("Result","Wrong Answer")
            result.setText("Wrong Answer:(")
            result.visibility=View.VISIBLE
        }
        totalQuestions++
        scoreCard.setText((myScore.toString())+"/"+(totalQuestions.toString()))
        newQuestion()

    }

    fun newQuestion()
    {
        var num1=(0..31).random() //generate random no between 1-30
        var num2=(0..31).random()
        question.setText((num1.toString())+ "+" + (num2.toString()))

        locationofCorrectAnswer=(0..3).random()

        var answers=Array<Int>(4){0}
        var i=0
        for(i in 0..3)
        {
            if(i==locationofCorrectAnswer)
            {
                answers[i]=num1+num2
            }
            else
            {
                var wrongAnswer=(0..60).random()

                while(wrongAnswer==num1+num2)
                {
                    wrongAnswer=(0..61).random()
                }

                answers[i]=wrongAnswer
            }
        }

        option0.setText(answers[0].toString())
        option1.setText(answers[1].toString())
        option2.setText(answers[2].toString())
        option3.setText(answers[3].toString())
    }

    fun playAgain(androidView: View)
    {
        audio.stop()
        option0.isEnabled=true
        option1.isEnabled=true
        option2.isEnabled=true
        option3.isEnabled=true
        myScore=0
        totalQuestions=0
        scoreCard.setText((myScore.toString())+"/"+(totalQuestions.toString()))
        gametimer.start()
        playAgain.visibility=View.INVISIBLE
        result.visibility=View.INVISIBLE

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        go=findViewById<Button>(R.id.go)
        option0=findViewById<Button>(R.id.option0)
        option1=findViewById<Button>(R.id.option1)
        option2=findViewById<Button>(R.id.option2)
        option3=findViewById<Button>(R.id.option3)
        playAgain=findViewById<Button>(R.id.again)

        timer=findViewById<TextView>(R.id.timer)
        scoreCard=findViewById<TextView>(R.id.scoreCard)
        question=findViewById<TextView>(R.id.ques)
        result=findViewById<TextView>(R.id.result)

    }
}