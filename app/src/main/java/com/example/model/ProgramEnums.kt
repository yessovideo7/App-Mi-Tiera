package com.example.model

enum class DayType(val label: String, val shortLabel: String) {
    WEEKDAY("Lunes a Viernes", "Lun - Vie"),
    SATURDAY("Sábados", "Sáb"),
    SUNDAY("Domingos", "Dom"),
    HOLIDAY("Festivos", "Festivos")
}

enum class ProgramCategory(val displayName: String) {
    NOTICIAS("Noticias"),
    MUSICA("Música"),
    OPINION_CULTURA("Cultura & Opinión"),
    RELIGIOSO("Espiritual"),
    DEPORTES("Deportes"),
    INSTITUCIONAL("Institucional")
}
