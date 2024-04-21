package com.example.messegerkotlin

import java.io.Serializable

data class User(
    val id: String = "",
    val name: String = "",
    val lastName: String = "",
    val age: String = "",
    val online: Boolean = false
): Serializable
{
}