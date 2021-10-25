package com.example.guessthephrase

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

object MyList{

    var mylist = arrayListOf<String>()
}

class MainActivity : AppCompatActivity() {
    lateinit var myText: EditText
    private lateinit var myButton: Button
    private lateinit var phaseText: TextView
    private lateinit var scoreText: TextView
    private var highscore = 0
    private var score = 0
    private lateinit var myRV: RecyclerView
    private var myList = arrayListOf<String>()
    private  var myPhase = ""
    private var letters = arrayListOf<Char>()
    private lateinit var sharedPreferences: SharedPreferences
    private var str=""
    var guesses = 10
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences(
        getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        highscore = sharedPreferences.getInt("highscore", 0)

        myText = findViewById(R.id.guessPhase)
        myButton = findViewById(R.id.guessButton)
        phaseText = findViewById(R.id.tvMain)
        myRV = findViewById(R.id.rvMain)
        scoreText = findViewById(R.id.tvHS)

        myRV.adapter = RecyclerViewAdapter(myList)
        myRV.layoutManager = LinearLayoutManager(this)

        var helper = DBHelper(applicationContext)
        helper.readData()

        var randomIndex = Random.nextInt(MyList.mylist.size)
        myPhase = MyList.mylist[randomIndex]


        for (i in myPhase){
            letters.add(i)
        }

        for (i in myPhase){
            if (i.isWhitespace()){
                str+=" "
            }else{
                str+="*"
            }
        }

        phaseText.text = "Phrase: $str \n Guessed letter: "
        scoreText.text = "High Score: $score"
        myButton.setOnClickListener { guessingGame() }



    }

    @SuppressLint("SetTextI18n")
    fun guessingGame() {
        var guessAnswer1 = myText.text.toString()
        var counter = 0
        if (guessAnswer1.length > 1) {
            if (guessAnswer1 != myPhase) {
                guesses--
                myList.add("Wrong Guess: i don't know")
                myList.add("$guesses guesses remaining")
            } else {

                    myList.add("yes you have the guessing word! $myPhase")
                    phaseText.text = "Phrase: $myPhase \n Guessed letter:  "
                    myList.add("Correct Guess: $myPhase")

            }
        }else {
             val guessAnswer = myText.text.toString().trim()[0]
            when {

                (!letters.contains(guessAnswer))-> {myList.add("wrong guess") }
                else -> {
                    myList.add("found letter")
                    score++
                    save()
                    scoreText.text = "High Score: $score"
                    for ((index, i) in letters.withIndex()) {
                        if (i == guessAnswer) {
                            str = str.substring(0, index) + i + str.substring(index + 1)
                            counter++
                            phaseText.text = "Phrase: $str \n Guessed letter:$i "

                        }

                    }
                    guesses--
                    myList.add("Found $counter $guessAnswer(s)")
                    myList.add("$guesses guesses remaining")
                    myRV.adapter?.notifyDataSetChanged()


                }
            }

        }

               myRV.adapter?.notifyDataSetChanged()
        }


    fun save() {
        if (score<highscore) {
            with(sharedPreferences.edit()) {
                putInt("highscore", score)
                apply()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.add -> {
                val intent = Intent(this, AddPhrase::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

        return super.onOptionsItemSelected(item)
    }




}