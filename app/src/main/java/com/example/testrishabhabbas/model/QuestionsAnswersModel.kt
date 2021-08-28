package com.example.testrishabhabbas.model

import java.io.Serializable

data class QuestionsAnswersModel(
    var type: String,
    var question: String,
    var answertext: String,
    var answerOptions: ArrayList<AnswerOptions>
) : Serializable


