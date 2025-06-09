package com.example.asciiinsanity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalStdlibApi::class)
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

        val nonPrinting:Array<String> = arrayOf("NUL", "SOH", "STX", "ETX", "EOT", "ENQ", "ACK", "BEL", "BS", "HT", "LF", "VT", "FF", "CR", "SO", "SI", "DLE", "DC1", "DC2", "DC3", "DC4", "NAK", "SYN", "ETB", "CAN", "EM", "SUB", "ESC", "FS", "GS", "RS", "US", "SP")

        //put buttons into array for programmatic assignment
        val buttons:Array<Button> = arrayOf(button0, button1, button2, button3, button4, button5, button6, button7)

        val modes:Array<String> = arrayOf("dec", "hex", "ascii")
        val difficulties:Array<String> = arrayOf("easy", "hard", "expert")

        val asciiTable = findViewById<ImageView>(R.id.asciiTable)    //the character the user is trying to guess
        val displayedAscii = findViewById<TextView>(R.id.displayedAscii)    //the character the user is trying to guess
        val binaryHint = findViewById<TextView>(R.id.binaryHint)    //the binary of displayedAscii (for easier debugging)
        val displayedCurrentGuess = findViewById<TextView>(R.id.displayedCurrentGuess)  //the character the user's current guess corresponds to
        val gameModeButton = findViewById<TextView>(R.id.gameModeButton)
        val gameDifficultyButton = findViewById<TextView>(R.id.gameDifficultyButton)

        var difficulty = 0  //0 = easy, 1 = hard, 2 = expert
        var gameMode = 2    //0 = dec, 1 = hex, 2 = ascii

        //randomly picks a target to guess depending on game mode and difficulty
        fun randomTarget(): Char {
            if(gameMode < 2) {  //if decimal or hex
                when(difficulty) {
                    0 -> return (0..15).random().toChar()   //decimal easy
                    1 -> return (0..255).random().toChar() //decimal hard
                }
            } else {    //if ascii
                when(difficulty) {
                    0 -> return (65..90).random().toChar()  //ascii easy
                    1 -> return (33..126).random().toChar() //ascii hard
                    2 -> return (0..127).random().toChar()  //ascii expert
                }
            }
            return 'X'
        }

        var asciiToGuess = randomTarget()  //random ascii character guess
        //var asciiToGuess = (0..128).random().toChar()  //random ascii character guess
        var currentGuess = 0.toChar()   //set the current guess to 0


        //returns a string representation of any ascii character,
        fun charToAscii(c: Char): String {
            if(c.code in 0..32) return nonPrinting[c.code]
            else if(c.code == 127) return "DEL"
            else return c.toString()
        }
        
        fun updateNumbers() {
            //update the target text, depending on gameMode
            when(gameMode) {
                0 -> displayedAscii.setText(asciiToGuess.code.toString())
                1 -> {
                    if(difficulty == 0) displayedAscii.setText(asciiToGuess.code.toInt().toHexString().substring(7))
                    else displayedAscii.setText(asciiToGuess.code.toInt().toHexString().substring(6))
                }    //truncate bc tohexstring adds leading 0s
                2 -> displayedAscii.setText(charToAscii(asciiToGuess))
            }
            gameDifficultyButton.setText(difficulties[difficulty])
            gameModeButton.setText(modes[gameMode])
            //update the hint text
            binaryHint.setText(asciiToGuess.code.toString(2).padStart(8,'0'))
            //update the current guessed character
            //displayedCurrentGuess.setText(currentGuess.toChar().toString())
            //update the current guess text in binary (for input debugging)
            displayedCurrentGuess.setText(currentGuess.code.toString(2).padStart(8,'0') + " (" + currentGuess.toChar().toString() + ")")
        }

        updateNumbers()

        //cycle through modes on click
        gameModeButton.setOnClickListener(){
            if(gameMode >= 2) gameMode = 0
            else gameMode++

            if(difficulty == 2) difficulty = 1

            updateNumbers()
        }

        //toggle difficulty on click
        gameDifficultyButton.setOnClickListener(){
            if(difficulty >= 1){
                difficulty = 0
                var asciiToGuess = randomTarget()  //random ascii character guess
            } else {
                difficulty++
            }

            updateNumbers()
        }

        gameDifficultyButton.setOnLongClickListener() {
            difficulty = 2
            gameMode = 2

            updateNumbers()
            return@setOnLongClickListener true
        }

        //toggle visibility of ascii table when current guess is tapped
        displayedCurrentGuess.setOnClickListener(){
            asciiTable.isVisible = !asciiTable.isVisible
        }

        //iterate through buttons, flipping bits if the button has been pressed
        for(i in 0..7){
            buttons[i].setOnClickListener() {
                //update guess with flipped bit
                currentGuess = (currentGuess.code xor (1 shl i)).toChar()
                //set text on button to reflect flipped bit
                buttons[i].setText(((currentGuess.code shr i) and 1).toString())
                //update current guess display
                updateNumbers()

                //if the guess is now correct, pop a toast and reset for another char at random
                if(currentGuess == asciiToGuess){
                    //congratulate user
                    Toast.makeText(getApplicationContext(), "You did it!", Toast.LENGTH_LONG).show()
                    //reset target
                    asciiToGuess = randomTarget()
                    //reset guess
                    currentGuess = 0.toChar()
                    
                    //make text reflect changes
                    updateNumbers()
                    
                    //reset buttons
                    for(i: Int in 0..7){
                        buttons[i].setText("0")
                    }
                    //if table was visible, dismiss it
                    asciiTable.isVisible = false
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}