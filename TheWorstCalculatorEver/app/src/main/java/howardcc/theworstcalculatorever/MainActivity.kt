package howardcc.theworstcalculatorever

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Action Listener
        btnMultiply.setOnClickListener {

            // First Number
            val firstString: String = firstNumber.text.toString()
            val firstValue = firstString.toInt()

            // Second Number
            val secondString: String = secondNumber.text.toString()
            val secondValue = secondString.toInt()

            // Get the result of two numbers
            val result = multiplyTwoNumbers(firstValue, secondValue)
            txtResult.text = result.toString()
        }
    }

    fun multiplyTwoNumbers(firstNumber: Int, secondNumber: Int): Int{
        // Multiply two numbers
        val result = firstNumber * secondNumber
        return result
    }
}
