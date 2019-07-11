package com.zainco.wataniaroutes

interface ICreateActivity {
    fun createNew(value: String)
}

interface IEditActivity {
    fun edit(value: String)
}

interface ICreateCurrencyActivity {
    fun createNew(value: String, egyptianValue: String)
}

interface EditCurrencyActivity {
    fun editCurrency(value: String, newEgyptianValue: String)
}