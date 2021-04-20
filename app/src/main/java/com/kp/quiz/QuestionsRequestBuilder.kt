package com.kp.quiz

class QuestionsRequestBuilder {

    private val apiUrl = "https://opentdb.com/api.php?"
    private var category = ""
    private var amount = ""
    private var token = ""

    fun setCategory(category_id: String): QuestionsRequestBuilder{
        category = "&category=$category_id"
        return this
    }
    fun setCategory(category_id: Int): QuestionsRequestBuilder{
        category = "&category=$category_id"
        return this
    }

    fun setCategoryToAny(): QuestionsRequestBuilder {
        category = ""
        return this
    }

    fun setAmount(amount: Int): QuestionsRequestBuilder{
        this.amount = "amount=$amount"
        return this
    }

    fun setToken(token: String): QuestionsRequestBuilder{
        this.token = "&token=$token"
        return this
    }

    fun getProduct() :String{
        return "$apiUrl$amount$category&type=multiple$token"
    }
}