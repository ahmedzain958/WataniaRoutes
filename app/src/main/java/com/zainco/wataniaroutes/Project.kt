package com.zainco.wataniaroutes

data class Project(
    val id: Int = 0,
    val AnnualRaise: Double = 0.0,
    val Area: Double = 0.0,
    val Contract: Int = 0,
    val EndDate: String = "",
    val Investor: String = "",
    val Location: String = "",
    val Notes: String = "",
    val Period: Int = 0,
    val Price: Int = 0,
    val ProjectName: String = "",
    val RentType: Int = 0,
    val RentValue: Long = 0,
    val Route: String = "",
    val StartDate: String = ""
)