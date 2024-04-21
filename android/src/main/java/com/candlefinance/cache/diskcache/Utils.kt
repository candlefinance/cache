package com.candlefinance.cache

@Suppress("unused")
object Utils {

    fun formatKey(str: String?): String {
        val formatted = str!!.replace("[^a-zA-Z0-9]".toRegex(), "").lowercase()
        return formatted.substring(0, if (formatted.length >= 120) 119 else formatted.length)
    }

}
