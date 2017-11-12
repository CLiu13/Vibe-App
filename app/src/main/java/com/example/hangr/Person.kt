package com.example.hangr

import org.jetbrains.anko.*

/**
 * Created by christophrrb on 11/9/17.
 */
class Person(val id: String, val fbName: String, val fbEmail: String, val fbOnline: Boolean) {
    constructor() : this("","","", false)
    /* A blank constructor is needed to read values from the database.
     * this calls the primary constructor (which I think is the class(Person(val... etc.
     * The parenthetical values of this are defaults for the data types of the primary constructor.
       An int could have a value of 0 as its default for instance.
     */
}