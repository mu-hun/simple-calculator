package com.example.project4_1

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private fun calculate(char: Char, A: Float, B: Float): Float {
        if (char == '+')
            return A + B
        if (char == '-')
            return A - B
        if (char == '*')
            return A * B
        return A / B
    }

    private fun closeKeyboard(): Boolean {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow((this.currentFocus as View).windowToken, 0)
        return true
    }

    val toIntOrNull = { etText: EditText -> etText.text.toString().toIntOrNull() }
    val toIntOrNullPair = { a: EditText, b:EditText -> Pair(toIntOrNull(a), toIntOrNull(b)) }

    val toastShort = { str: String -> Toast.makeText(applicationContext,str, Toast.LENGTH_SHORT).show() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.layout).setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean { closeKeyboard()
                return v?.performClick() ?: true }})

        fun wrapper(char: Char) {
            val (a, b) = toIntOrNullPair(inputA, inputB)
            if (a == null && b == null)
                return toastShort("A and B is not valid")
            if (a == null) return toastShort("A is not valid")
            if (b == null) return toastShort("B is not valid")

            var text = calculate(char, a.toFloat(), b.toFloat()).toString()
            if (text.contains(".0")) text = text.replace(".0", "")
            if (text == "0") return toastShort("result is 0")
            resultView.text = text
        }

        plus.setOnClickListener { wrapper('+') }
        minus.setOnClickListener { wrapper('-')}
        multiply.setOnClickListener { wrapper('*') }
        division.setOnClickListener { wrapper('/') }
    }
}
