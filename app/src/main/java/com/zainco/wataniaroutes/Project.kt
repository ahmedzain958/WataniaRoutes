package com.zainco.wataniaroutes

data class Project constructor(
    private val id: Int,
    private val AnnualRaise: Double,
    private val Area: Double,
    private val Contract: Int,
    private val EndDate: String,
    private val Investor: String,
    private val Location: String,
    private val Notes: String,
    private val Period: Int,
    private val Price: Int,
    private val PrjectName: Int,
    private val RentType: Int,
    private val RentValue: Long,
    private val Route: String,
    private val StartDate: String
)