package com.kp.quiz.fetchedData

data class QuestionData (
    var catrgory: String,
    var type: String,
    var difficulty:String,
    var question: String,
    var correct_answer: String,
    var incorrect_answers: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuestionData

        if (catrgory != other.catrgory) return false
        if (type != other.type) return false
        if (difficulty != other.difficulty) return false
        if (question != other.question) return false
        if (correct_answer != other.correct_answer) return false
        if (!incorrect_answers.contentEquals(other.incorrect_answers)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = catrgory.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + difficulty.hashCode()
        result = 31 * result + question.hashCode()
        result = 31 * result + correct_answer.hashCode()
        result = 31 * result + incorrect_answers.contentHashCode()
        return result
    }
}