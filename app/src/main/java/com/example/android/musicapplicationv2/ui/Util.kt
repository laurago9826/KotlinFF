package com.example.android.musicapplicationv2.ui

fun formatDuration(sec: Int) : String {
    var min = sec / 60
    var sec = sec - min * 60
    return "$min:$sec"
}

fun parseStringDuration(duration: String?) : Int? {
    var min_and_sec = duration?.split(':')
    var number = true
    if(min_and_sec != null &&  min_and_sec!!.size == 2 ) {
        var num1 = 0
        var num2 = 0
        try {
            num1 = Integer.parseInt(min_and_sec[0])
            num2 = Integer.parseInt(min_and_sec[1])
        } catch (e: NumberFormatException) {
            number = false
        }
        if (number && num1 < 100 && num2 < 60) {
            return num1*60 + num2
        }
    }
    return null //incorrect format or ranges
}