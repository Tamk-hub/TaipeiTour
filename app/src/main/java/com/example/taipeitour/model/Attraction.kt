package com.example.taipeitour.model

data class Attraction(
    var name: String,
    var introduction: String,
    var image: List<List<String>>,
    var open_time: String,
    var address: String,
    var tel: String,
    var url: String

){
    constructor() :this("","",emptyList(),"","","","")

    override fun toString(): String {
        return super.toString()
    }

}