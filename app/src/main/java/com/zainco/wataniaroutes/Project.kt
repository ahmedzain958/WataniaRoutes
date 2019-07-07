package com.zainco.wataniaroutes

import java.io.Serializable

data class Project(
    var AnnualRaise: Double? = 0.0,
    var Area: Double? = 0.0,
    var EndDate: String? = "",
    var Investor: String = "",
    var Location: String = "",
    var Notes: String? = "",
    var Period: String? = "",
    var Price: Int? = 0,
    var ProjectName: String = "",
    var RentRate: String = "",
    var RentValue: Double? = 0.0,
    var Route: String = "",
    var StartDate: String? = ""
):Serializable