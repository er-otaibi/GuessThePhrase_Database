package com.example.guessthephrase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddPhrase : AppCompatActivity() {
    lateinit var etPhrase: EditText
    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_phrase)

        etPhrase = findViewById(R.id.etPhrase)
        button = findViewById(R.id.button)

        button.setOnClickListener {
            var phrase = etPhrase.text.toString()
            var helper = DBHelper(applicationContext)
            helper.savePhrase(phrase)
            Toast.makeText(this, "new phrase is added successfully" , Toast.LENGTH_LONG).show()
            etPhrase.setText("")

        }

    }
}