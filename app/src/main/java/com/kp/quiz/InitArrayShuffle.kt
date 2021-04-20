package com.kp.quiz

import kotlin.random.Random


public fun <T> Array<T>.shuffle(): Array<T> {


    for (index in this.indices) {
        val randomIndex = Random.nextInt(0,index+1)

        val temp = this[index]
        this[index] = this[randomIndex]
        this[randomIndex] = temp
    }

    return this
}