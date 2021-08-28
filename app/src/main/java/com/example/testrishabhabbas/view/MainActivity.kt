package com.example.testrishabhabbas.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testrishabhabbas.R
import com.example.testrishabhabbas.adapter.TestAdapter
import com.example.testrishabhabbas.model.AnswerOptions
import com.example.testrishabhabbas.model.QuestionsAnswersModel
import java.io.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    var alQuestions= ArrayList<QuestionsAnswersModel>()
    var testAdapter: TestAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAdapter()
        readJsonFile()
    }
    private fun readJsonFile(){
        val inputStream = resources.openRawResource(R.raw.test)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        } finally {
            inputStream.close()
        }
        val jsonString= writer.toString()
        convertjsontoAl(jsonString)
    }


    private fun convertjsontoAl(json:String){
        alQuestions.clear()
        var jObj= JSONObject(json.replace("\\",""))
        var sections=jObj.optJSONArray("sections")
        for(i in 0 until sections.length()) {
            var sectObj=sections.optJSONObject(i)
            var quesstionsarray=sectObj.optJSONArray("questionsList")
            for(i in 0 until quesstionsarray.length()) {
                var quesstionobj=quesstionsarray.optJSONObject(i)
                var type = quesstionobj.optString("questionType")
                var detailsObj = quesstionobj.optJSONObject("details")
                var question = detailsObj.optString("question")
                var options = detailsObj.optJSONArray("options")
                var optionsList=ArrayList<AnswerOptions>()
                if(options!=null && options.length()>0) {
                    for (i in 0 until options.length()) {
                        var model1 = AnswerOptions(false, options[i] as String)
                        optionsList.add(model1)
                    }
                }
                var model=
                    QuestionsAnswersModel(type=type,question=question,answertext = "",answerOptions = optionsList)
                alQuestions.add(model)
            }
        }

        testAdapter?.notifyDataSetChanged()

    }

    private fun setAdapter(){
        testAdapter= TestAdapter(alQuestions,this@MainActivity)
        val llm=LinearLayoutManager(this@MainActivity)
        rcvQuestions.layoutManager=llm
        rcvQuestions.adapter=testAdapter
    }
}