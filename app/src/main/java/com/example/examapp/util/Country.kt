package com.example.examapp.util

enum class Country(val abbreviation: String, val fullName: String) {
    US("US", "United States of America"),
    GB("GB", "United Kingdom"),
    CA("CA", "Canada"),
    AU("AU", "Australia"),
    DE("DE", "Germany"),
    FR("FR", "France"),
    BR("BR", "Brazil"),
    CH("CH", "Switzerland"),
    DK("DK", "Denmark"),
    ES("ES", "Spain"),
    FI("FI", "Finland"),
    IE("IE", "Ireland"),
    IN("IN", "India"),
    IR("IR", "Iran"),
    MX("MX", "Mexico"),
    NL("NL", "Netherlands"),
    NO("NO", "Norway"),
    NZ("NZ", "New Zealand"),
    RS("RS", "Serbia"),
    TR("TR", "Turkey"),
    UA("UA", "Ukraine");

    companion object {
        fun getFullNameByAbbreviation(abbreviation: String): String {
            return entries.find { it.abbreviation == abbreviation }?.fullName ?: "Unknown"
        }
    }
}

