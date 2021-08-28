package com.example.testrishabhabbas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.testrishabhabbas.R
import com.example.testrishabhabbas.model.AnswerOptions
import com.example.testrishabhabbas.model.QuestionsAnswersModel


class TestAdapter(
    var alQuestions: ArrayList<QuestionsAnswersModel>,
    var mContext: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val SINGLECHOICE = 100
    val MULTIPLECHOICE = 200
    val SINGLELINE = 300
    val MULTIPLELINE = 400

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var view: View
        if (viewType == SINGLECHOICE) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_singlechoice, parent, false)
            return SingleChoiceViewHolder(view)
        } else if (viewType == MULTIPLECHOICE) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_multiplechoice, parent, false)
            return MultipleChoiceViewHolder(view)
        } else if (viewType == MULTIPLELINE) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.row_longdesc, parent, false)
            return MultipleLineViewHolder(view)
        } else {
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.row_shortdesc, parent, false)
            return SingleLineViewHolder(view)
        }
    }

    class SingleChoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvQuestion = itemView.findViewById(R.id.tvQuestion) as TextView
        var rgAll = itemView.findViewById(R.id.radiogroup) as RadioGroup
    }


    class MultipleChoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvQuestion = itemView.findViewById(R.id.tvQuestion) as TextView
        var llMain = itemView.findViewById(R.id.llMain) as LinearLayout
    }


    class SingleLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(model: QuestionsAnswersModel) {
            var tvQuestion = itemView.findViewById(R.id.tvQuestion) as TextView
            tvQuestion.text = model.question

        }
    }

    class MultipleLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(model: QuestionsAnswersModel) {
            var tvQuestion = itemView.findViewById(R.id.tvQuestion) as TextView
            tvQuestion.text = model.question

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            SINGLECHOICE -> {
                var singleholder = holder as SingleChoiceViewHolder
                singleholder.tvQuestion.text = alQuestions[position].question
                addRadioButtons(singleholder.rgAll, alQuestions[position].answerOptions)

            }
            MULTIPLECHOICE -> {
                var multipleholder = (holder as MultipleChoiceViewHolder)
                multipleholder.tvQuestion.text = alQuestions[position].question
                addCheckBox(multipleholder.llMain, alQuestions[position].answerOptions)

            }
            SINGLELINE -> {
                (holder as SingleLineViewHolder).bindItems(alQuestions[position])
            }
            MULTIPLELINE -> {
                (holder as MultipleLineViewHolder).bindItems(alQuestions[position])
            }

        }
    }


    fun addRadioButtons(rgAll: RadioGroup, options: ArrayList<AnswerOptions>) {
        rgAll.setOrientation(LinearLayout.VERTICAL)
        for (i in 0 until options.size) {
            val rdbtn = RadioButton(mContext)
            rdbtn.id = View.generateViewId()
            rdbtn.text = options[i].option
            rgAll.addView(rdbtn)
        }
    }

    fun addCheckBox(llm: LinearLayout, options: ArrayList<AnswerOptions>) {
        for (i in 0 until options.size) {
            var cb=CheckBox(mContext)
            cb.text=options[i].option
            llm.addView(cb);
        }
    }

    override fun getItemCount(): Int {
        return alQuestions.size
    }

    override fun getItemViewType(position: Int): Int {
        val questionType = alQuestions[position].type
        when (questionType) {
            "SingleChoice" -> return SINGLECHOICE
            "MultipleChoice" -> return MULTIPLECHOICE
            "LongDesc" -> return MULTIPLELINE
            else -> return SINGLELINE
        }
    }
}