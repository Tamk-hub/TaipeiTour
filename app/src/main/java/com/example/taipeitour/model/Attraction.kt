package com.example.taipeitour.model

data class Attraction(
    var name: String,
    var introduction: String,
    var image: String,
    var open_time: String,
    var address: String,
    var tel: String,
    var url: String

){
    constructor() :this("","","","","","","")

    override fun toString(): String {
        return super.toString()
    }

}