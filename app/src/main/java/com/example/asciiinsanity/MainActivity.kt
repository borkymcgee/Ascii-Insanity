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

        //put buttons into array for programmatic assignment
        val buttons:Array<Button> = arrayOf(button0, button1, button2, button3, button4, button5, button6, button7)

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

        //iterate through buttons, flipping bits if the button has been pressed
        for(i in 0..7){
            buttons[i].setOnClickListener() {
                //update guess with flipped bit
                currentGuess = (currentGuess.code xor (1 shl i)).toChar()
                //set text on button to reflect flipped bit
                buttons[i].setText(((currentGuess.code shr i) and 1).toString())
                //update current guess display
                displayedCurrentGuess.setText(currentGuess.code.toString(2).padStart(8,'0'))
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}