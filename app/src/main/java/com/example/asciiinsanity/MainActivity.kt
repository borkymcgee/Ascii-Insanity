package com.example.asciiinsanity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //connect buttons in xml to buttons here (there must be a programmatic way to do this)
        val button0 = findViewById<Button>(R.id.button0)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)

        val displayedAscii = findViewById<TextView>(R.id.displayedAscii)    //the character the user is trying to guess
        val binaryHint = findViewById<TextView>(R.id.binaryHint)    //the binary of displayedAscii (for easier debugging)
        val displayedCurrentGuess = findViewById<TextView>(R.id.displayedCurrentGuess)  //the character the user's current guess corresponds to


        var asciiToGuess = 'f'  //hardcoded guess for now
        var currentGuess = 0.toChar()   //set the current guess to 0

        //update the display values
        displayedAscii.setText(asciiToGuess.toString())
        binaryHint.setText(asciiToGuess.code.toString(2).padStart(8,'0'))

        //display current guess in binary for input debugging
        displayedCurrentGuess.setText(currentGuess.code.toString(2).padStart(8,'0'))



        //when buttons are clicked, update currentGuess, and change their text to reflect their new value
        //this can be done programmatically, will do this once i create a git repo
        button0.setOnClickListener() {
            currentGuess = (currentGuess.code xor 0b1).toChar()
            button0.setText((currentGuess.code and 1).toString())
            displayedCurrentGuess.setText(currentGuess.code.toString(2).padStart(8,'0'))
        }
        button1.setOnClickListener() {
            currentGuess = (currentGuess.code xor 0b10).toChar()
            button1.setText(((currentGuess.code shr 1) and 1).toString())
            displayedCurrentGuess.setText(currentGuess.code.toString(2).padStart(8,'0'))
        }
        button2.setOnClickListener() {
            currentGuess = (currentGuess.code xor 0b100).toChar()
            button2.setText(((currentGuess.code shr 2) and 1).toString())
            displayedCurrentGuess.setText(currentGuess.code.toString(2).padStart(8,'0'))
        }
        button3.setOnClickListener() {
            currentGuess = (currentGuess.code xor 0b1000).toChar()
            button3.setText(((currentGuess.code shr 3) and 1).toString())
            displayedCurrentGuess.setText(currentGuess.code.toString(2).padStart(8,'0'))
        }
        button4.setOnClickListener() {
            currentGuess = (currentGuess.code xor 0b10000).toChar()
            button4.setText(((currentGuess.code shr 4) and 1).toString())
            displayedCurrentGuess.setText(currentGuess.code.toString(2).padStart(8,'0'))
        }
        button5.setOnClickListener() {
            currentGuess = (currentGuess.code xor 0b100000).toChar()
            button5.setText(((currentGuess.code shr 5) and 1).toString())
            displayedCurrentGuess.setText(currentGuess.code.toString(2).padStart(8,'0'))
        }
        button6.setOnClickListener() {
            currentGuess = (currentGuess.code xor 0b1000000).toChar()
            button6.setText(((currentGuess.code shr 6) and 1).toString())
            displayedCurrentGuess.setText(currentGuess.code.toString(2).padStart(8,'0'))
        }
        button7.setOnClickListener() {
            currentGuess = (currentGuess.code xor 0b10000000).toChar()
            button7.setText(((currentGuess.code shr 7) and 1).toString())
            displayedCurrentGuess.setText(currentGuess.code.toString(2).padStart(8,'0'))
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}