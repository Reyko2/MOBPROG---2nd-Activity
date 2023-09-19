package com.example.a2ndactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Button

class MainActivity : AppCompatActivity() {

    //private variables I declared, currentText is for the letters, currentValue is for the numbers, ispluspressed, isminuspressed are boolean and for the code to know that the button + or - is pressed and pressing any of 6 buttons means its function is about to happen
    //isFirstFunctionPerformed is for validation that after the function of a button is done, it needs to press the + or - button again so it can perform the next function
    private var currentText = ""
    private var currentValue = 0
    private var isPlusPressed = false
    private var isMinusPressed = false
    private var isFirstFunctionPerformed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonPlus = findViewById<Button>(R.id.buttonPlus)
        val buttonMinus = findViewById<Button>(R.id.buttonMinus)

        // + Button click listener
        buttonPlus.setOnClickListener {
            isPlusPressed = true
            isMinusPressed = false
            isFirstFunctionPerformed = false
        }

        // - Button click listener
        buttonMinus.setOnClickListener {
            if (!isPlusPressed) {
                // if - is pressed first then it toasts "impossible" (because theres no value yet anyways)
                showToast("Impossible")
            } else {
                isMinusPressed = true
                isFirstFunctionPerformed = false
            }
        }

        // letter listeners (abc)
        val buttonA = findViewById<Button>(R.id.buttonA)
        val buttonB = findViewById<Button>(R.id.buttonB)
        val buttonC = findViewById<Button>(R.id.buttonC)

        val letterClickListener = View.OnClickListener { v ->
            if (!(isPlusPressed || isMinusPressed) || isFirstFunctionPerformed) {
                //displays validation that you should press + or - first before pressing any of the buttons to give it a function, or if the function is already performed
                showToast("Press the + or - button first")
            } else if (isMinusPressed) {
                if (currentText.isNotEmpty()) {
                    //remove previous input in the letter string
                    currentText = currentText.substring(0, currentText.length - 1)
                    showToast(currentText)
                    isFirstFunctionPerformed = true
                    if (currentText.isEmpty()) {
                        //display "impossible" if subtracting beyond empty string
                        showToast("Impossible")
                        isFirstFunctionPerformed = true
                    }
                } else {
                    //display "impossible" if subtracting beyond empty string
                    showToast("Impossible")
                    isFirstFunctionPerformed = true
                }

                //if + is pressed then it adds the button to the string and saves it, and it saves its phase as a functionperformed
            } else if (isPlusPressed) {
                val letterButton = v as Button
                val letter = letterButton.text.toString()
                currentText += letter
                showToast(currentText)
                isFirstFunctionPerformed = true
            }
        }

        buttonA.setOnClickListener(letterClickListener)
        buttonB.setOnClickListener(letterClickListener)
        buttonC.setOnClickListener(letterClickListener)

        //number button listeners (123)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)

        val numberClickListener = View.OnClickListener { v ->
            if (!(isPlusPressed || isMinusPressed) || isFirstFunctionPerformed) {
                //displays validation that you should press + or - first before pressing any of the buttons to give it a function, or if the function is already performed
                showToast("Press the + or - button first")
            } else if (isMinusPressed) {
                //subtract the number to the total
                val numberButton = v as Button
                val number = numberButton.text.toString().toInt()
                currentValue -= number
                isFirstFunctionPerformed = true

                if (currentValue >= 0) {
                    showToast(currentValue.toString())
                    isFirstFunctionPerformed = true
                } else {
                    //shows "impossible" if its negative
                    showToast("Impossible")
                    currentValue += number //reverts the operation of the subtraction so it doesnt show the negative
                    isFirstFunctionPerformed = true
                }
            } else if (isPlusPressed) {
                //adding
                val numberButton = v as Button
                val number = numberButton.text.toString().toInt()
                currentValue += number
                showToast(currentValue.toString())
                isFirstFunctionPerformed = true
            }
        }

        button1.setOnClickListener(numberClickListener)
        button2.setOnClickListener(numberClickListener)
        button3.setOnClickListener(numberClickListener)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}