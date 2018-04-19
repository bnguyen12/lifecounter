package edu.washington.bennyn.lifecounter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var HPs : () -> Array<TextView>
    lateinit var layouts : () -> Array<LinearLayout>
    lateinit var loserText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HPs = {
            var oneHP: TextView = findViewById(R.id.oneHP)
            var twoHP: TextView = findViewById(R.id.twoHP)
            var threeHP: TextView = findViewById(R.id.threeHP)
            var fourHP: TextView = findViewById(R.id.fourHP)
            arrayOf(oneHP, twoHP, threeHP, fourHP)
        }
        layouts = {
            val layoutOne: LinearLayout = findViewById(R.id.layoutOne)
            val layoutTwo: LinearLayout = findViewById(R.id.layoutTwo)
            val layoutThree: LinearLayout = findViewById(R.id.layoutThree)
            val layoutFour: LinearLayout = findViewById(R.id.layoutFour)
            arrayOf(layoutOne, layoutTwo, layoutThree, layoutFour)
        }

        loserText = findViewById(R.id.loser)
    }

    override fun onStart() {
        super.onStart()
        var counter = 0
        for (layout in layouts()) {
            var buttons = layout.touchables
            for (button in buttons) {
                button.tag = counter
                button.setOnClickListener {
                    it as Button
                    var increment = 0
                    when (it.text.toString()) {
                        "+" -> increment = 1
                        "-" -> increment = -1
                        "+5" -> increment = 5
                        else -> increment = -5
                    }
                    var currText = HPs()[button.tag as Int].text.substring(13)
                    val result = (currText.toInt() + increment).toString()
                    HPs()[button.tag as Int].text = HPs()[button.tag as Int].text.substring(0, 13) + result

                    if (result.toInt() <= 0) {
                        loserText.text = HPs()[button.tag as Int].text.substring(0, 9) + "LOSES!"
                    }

                    checkLosers()
                }
            }
            counter += 1
        }
    }

    // Checks to see if anybody is below 0. If not, the bottom loser announcement is blank
    fun checkLosers() {
        var counter = 0
        for (HP in HPs()) {
            if (HP.text.substring(13).toInt() > 0) {
                counter += 1
            }
        }

        if (counter == 4) {
            loserText.text = ""
        }
    }
}
