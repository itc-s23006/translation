package jp.ac.it_college.std.s23006.translation

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = RetrofitClient.instance
        val service = retrofit.create(TranslationService::class.java)

        val inputText = findViewById<EditText>(R.id.inputText)
        val translateButton = findViewById<Button>(R.id.translateButton)
        val resultText = findViewById<TextView>(R.id.resultText)

        translateButton.setOnClickListener {
            val text = inputText.text.toString()
            val call = service.translate(text, "en", "ja")

            call.enqueue(object : Callback<TranslationResponse> {
                override fun onResponse(call: Call<TranslationResponse>, response: Response<TranslationResponse>) {
                    if (response.isSuccessful) {
                        val translatedText = response.body()?.translatedText
                        resultText.text = translatedText ?: getString(R.string.translation_error)
                    } else {
                        resultText.text = getString(R.string.translation_error_with_code, response.code())
                    }
                }

                override fun onFailure(call: Call<TranslationResponse>, t: Throwable) {
                    resultText.text = getString(R.string.translation_error_with_message, t.message)
                }
            })
        }
    }
}
