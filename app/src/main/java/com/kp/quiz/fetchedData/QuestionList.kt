package com.kp.quiz.fetchedData

data class QuestionList (
    var response_code: Int,
    var results: List<QuestionData>
)