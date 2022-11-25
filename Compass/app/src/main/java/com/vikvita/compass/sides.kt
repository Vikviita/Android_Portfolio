package com.vikvita.compass

import java.time.Period

enum class sides(val start:Int,val end:Int,val title:String) {
    N(338,360,"N"),
    W(247,292,"W"),
    E(68,111,"E"),
    S(157,202,"S"),
    NW(292,338,"NW"),
    NE(22,68,"NE"),
    SW(202,247,"SW"),
    SE(111,157,"SE");

    fun period()=start..end


}