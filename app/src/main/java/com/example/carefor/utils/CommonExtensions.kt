package com.example.carefor.utils

import android.app.Activity
import android.widget.Toast

/*fun add(a:Int, b:Int):Int {
    *//*val ans = a+b
    return ans*//*

    return a+b
}*/

//fun addTwoNumbers(a:Int, b:Int) = a+b

fun Activity.showToast(message:String) = Toast.makeText(this,message, Toast.LENGTH_SHORT).show()