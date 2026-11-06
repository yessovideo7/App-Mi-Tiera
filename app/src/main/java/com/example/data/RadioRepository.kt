package com.example.data

import com.example.model.DayType
import com.example.model.PodcastEpisode
import com.example.model.ProgramCategory
import com.example.model.ProgramItem
import java.util.Calendar

object RadioRepository {

    const val LIVE_STREAM_URL = "https://play14.tikast.com:20134/stream"
    const val LIVE_STREAM_FALLBACK = "https://play14.tikast.com:20134/index.html?sid=1"
    const val STATION_NAME = "Mi Tierra Radio 88.1 FM"
    const val STATION_SLOGAN = "¡Cómo me gusta!"
    const val STATION_CITY = "Santa Rosa de Osos, Antioquia"
    const val STATION_WHATSAPP = "573104458810" // Direct cabina WhatsApp line
    const val STATION_FREQUENCY = "88.1 FM"

    val weekdayPrograms = listOf(
        ProgramItem(
            id = "w1",
            title = "Franja Musical Crossover",
            scheduleDisplay = "12:00 a. m. – 3:00 a. m.",
            startHour = 0,
            startMinute = 0,
            endHour = 3,
            endMinute = 0,
            dayType = DayType.WEEKDAY,
            description = "La madrugada se vive con variedad, ritmo y grandes éxitos. Una selección crossover para acompañar a nuestra gente en cada momento.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "w2",
            title = "Levantados",
            scheduleDisplay = "3:00 a. m. – 7:00 a. m.",
            startHour = 3,
            startMinute = 0,
            endHour = 7,
            endMinute = 0,
            dayType = DayType.WEEKDAY,
            description = "Cuando nuestra tierra comienza a despertar, Mi Tierra Radio ya está presente. Música, energía y compañía para comenzar el día con la mejor actitud.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "w3",
            title = "Pinceladas de Fe",
            scheduleDisplay = "6:30 a. m. – 6:35 a. m.",
            startHour = 6,
            startMinute = 30,
            endHour = 6,
            endMinute = 35,
            dayType = DayType.WEEKDAY,
            host = "Padre Pompilio Gutiérrez",
            description = "Cinco minutos para hacer una pausa, agradecer y comenzar el día con esperanza. Un mensaje de fe y reflexión para nuestra comunidad.",
            category = ProgramCategory.RELIGIOSO,
            badge = "Cápsula de Fe"
        ),
        ProgramItem(
            id = "w4",
            title = "Ecos del Territorio – Primera Emisión",
            scheduleDisplay = "7:00 a. m. – 8:00 a. m.",
            startHour = 7,
            startMinute = 0,
            endHour = 8,
            endMinute = 0,
            dayType = DayType.WEEKDAY,
            host = "Equipo Periodístico Mi Tierra",
            description = "Noticias con identidad. La información de nuestro territorio, sus protagonistas y los hechos que hacen parte de la actualidad local, regional y nacional.",
            category = ProgramCategory.NOTICIAS,
            badge = "Informativo Central"
        ),
        ProgramItem(
            id = "w5",
            title = "La Apoteca de Sofía",
            scheduleDisplay = "8:00 a. m. – 8:30 a. m.",
            startHour = 8,
            startMinute = 0,
            endHour = 8,
            endMinute = 30,
            dayType = DayType.WEEKDAY,
            host = "Universidad de Antioquia",
            description = "Un espacio de la Universidad de Antioquia que acerca el conocimiento a nuestra comunidad con contenidos educativos, útiles y de interés para la vida cotidiana.",
            category = ProgramCategory.OPINION_CULTURA
        ),
        ProgramItem(
            id = "w6",
            title = "Música Romántica",
            scheduleDisplay = "8:30 a. m. – 9:00 a. m.",
            startHour = 8,
            startMinute = 30,
            endHour = 9,
            endMinute = 0,
            dayType = DayType.WEEKDAY,
            description = "Canciones que hablan de amor, recuerdos y emociones. Grandes voces y melodías para disfrutar, recordar y dedicar.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "w7",
            title = "Programa Institucional",
            scheduleDisplay = "9:00 a. m. – 9:30 a. m.",
            startHour = 9,
            startMinute = 0,
            endHour = 9,
            endMinute = 30,
            dayType = DayType.WEEKDAY,
            host = "Alcaldía Municipal de Santa Rosa de Osos",
            description = "Un puente entre las instituciones y nuestra comunidad. Información de la Alcaldía Municipal y demás instituciones sobre programas, proyectos, servicios y acciones para nuestro territorio.",
            category = ProgramCategory.INSTITUCIONAL,
            badge = "Alcaldía SRO"
        ),
        ProgramItem(
            id = "w8",
            title = "Música Romántica",
            scheduleDisplay = "9:30 a. m. – 10:00 a. m.",
            startHour = 9,
            startMinute = 30,
            endHour = 10,
            endMinute = 0,
            dayType = DayType.WEEKDAY,
            description = "La música que llega al corazón continúa acompañando la mañana con canciones que evocan historias, sentimientos y grandes recuerdos.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "w9",
            title = "Recargas Musicales",
            scheduleDisplay = "10:00 a. m. – 12:00 m.",
            startHour = 10,
            startMinute = 0,
            endHour = 12,
            endMinute = 0,
            dayType = DayType.WEEKDAY,
            description = "Una dosis de música para recargar la energía y continuar la jornada con variedad, ritmo y los éxitos que nos gusta escuchar.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "w10",
            title = "Ecos del Territorio – Segunda Emisión",
            scheduleDisplay = "12:00 m. – 12:30 p. m.",
            startHour = 12,
            startMinute = 0,
            endHour = 12,
            endMinute = 30,
            dayType = DayType.WEEKDAY,
            host = "Equipo Periodístico Mi Tierra",
            description = "Noticias con identidad. Una nueva mirada a la actualidad para mantener a nuestra comunidad informada sobre lo que ocurre en nuestro territorio y más allá de él.",
            category = ProgramCategory.NOTICIAS
        ),
        ProgramItem(
            id = "w11",
            title = "Mundo Deportes",
            scheduleDisplay = "1:00 p. m. – 2:00 p. m.",
            startHour = 13,
            startMinute = 0,
            endHour = 14,
            endMinute = 0,
            dayType = DayType.WEEKDAY,
            host = "Panel Deportivo",
            description = "La pasión por el deporte se vive con noticias, resultados, protagonistas y toda la actualidad de las disciplinas que emocionan a nuestra comunidad.",
            category = ProgramCategory.DEPORTES
        ),
        ProgramItem(
            id = "w12",
            title = "El Parche",
            scheduleDisplay = "2:00 p. m. – 4:00 p. m.",
            startHour = 14,
            startMinute = 0,
            endHour = 16,
            endMinute = 0,
            dayType = DayType.WEEKDAY,
            description = "La tarde se disfruta mejor en compañía. Música, entretenimiento y buena energía en un espacio cercano, alegre y hecho para compartir.",
            category = ProgramCategory.OPINION_CULTURA
        ),
        ProgramItem(
            id = "w13",
            title = "Ranchenato",
            scheduleDisplay = "4:00 p. m. – 6:00 p. m.",
            startHour = 16,
            startMinute = 0,
            endHour = 18,
            endMinute = 0,
            dayType = DayType.WEEKDAY,
            description = "El sentimiento de la ranchera se encuentra con la alegría del vallenato en una franja llena de grandes canciones, historias y emociones.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "w14",
            title = "Sagrada Eucaristía",
            scheduleDisplay = "6:00 p. m. – 6:30 p. m.",
            startHour = 18,
            startMinute = 0,
            endHour = 18,
            endMinute = 30,
            dayType = DayType.WEEKDAY,
            description = "Un momento de fe, oración y reflexión para nuestra comunidad, llevando a cada hogar un mensaje de esperanza, unión y fortaleza.",
            category = ProgramCategory.RELIGIOSO
        ),
        ProgramItem(
            id = "w15",
            title = "Enamórate",
            scheduleDisplay = "6:30 p. m. – 8:00 p. m.",
            startHour = 18,
            startMinute = 30,
            endHour = 20,
            endMinute = 0,
            dayType = DayType.WEEKDAY,
            description = "La noche comienza con canciones que hablan de amor, recuerdos y sentimientos. Un espacio para sentir, recordar y dedicar al ritmo de las grandes melodías románticas.",
            category = ProgramCategory.MUSICA
        )
    )

    val saturdayPrograms = listOf(
        ProgramItem(
            id = "s1",
            title = "Franja Musical Crossover",
            scheduleDisplay = "12:00 a. m. – 3:00 a. m.",
            startHour = 0,
            startMinute = 0,
            endHour = 3,
            endMinute = 0,
            dayType = DayType.SATURDAY,
            description = "Una selección crossover para acompañar la madrugada del sábado con variedad, ritmo y grandes éxitos.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "s2",
            title = "Levantados",
            scheduleDisplay = "3:00 a. m. – 7:30 a. m.",
            startHour = 3,
            startMinute = 0,
            endHour = 7,
            endMinute = 30,
            dayType = DayType.SATURDAY,
            description = "Música, energía y buena compañía para quienes madrugan y comienzan su sábado junto a Mi Tierra Radio.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "s3",
            title = "El Calidoscopio",
            scheduleDisplay = "7:30 a. m. – 8:00 a. m.",
            startHour = 7,
            startMinute = 30,
            endHour = 8,
            endMinute = 0,
            dayType = DayType.SATURDAY,
            host = "Leonel Ramírez",
            description = "Con Leonel Ramírez. Un espacio de historias, contenidos y diferentes perspectivas para descubrir nuevos matices de nuestra realidad.",
            category = ProgramCategory.OPINION_CULTURA
        ),
        ProgramItem(
            id = "s4",
            title = "Viejoteca",
            scheduleDisplay = "8:00 a. m. – 9:00 a. m.",
            startHour = 8,
            startMinute = 0,
            endHour = 9,
            endMinute = 0,
            dayType = DayType.SATURDAY,
            description = "Los clásicos que nunca pasan de moda. Canciones y voces inolvidables para viajar por los recuerdos y revivir grandes momentos.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "s5",
            title = "Batalla de los Artistas",
            scheduleDisplay = "9:00 a. m. – 11:00 a. m.",
            startHour = 9,
            startMinute = 0,
            endHour = 11,
            endMinute = 0,
            dayType = DayType.SATURDAY,
            description = "Grandes artistas se encuentran en una batalla musical donde las canciones, los recuerdos y nuestra audiencia tienen la última palabra.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "s6",
            title = "Informativo Municipal – Renace la Esperanza",
            scheduleDisplay = "11:00 a. m. – 12:00 m.",
            startHour = 11,
            startMinute = 0,
            endHour = 12,
            endMinute = 0,
            dayType = DayType.SATURDAY,
            host = "Alcalde Luis Bernardo Molina Granda",
            description = "Con el alcalde Luis Bernardo Molina Granda. Un espacio para conocer la gestión municipal, sus proyectos, avances y acciones en beneficio de nuestra comunidad.",
            category = ProgramCategory.INSTITUCIONAL,
            badge = "Alcalde en Vivo"
        ),
        ProgramItem(
            id = "s7",
            title = "Canta Mi Colombia",
            scheduleDisplay = "12:00 m. – 1:00 p. m.",
            startHour = 12,
            startMinute = 0,
            endHour = 13,
            endMinute = 0,
            dayType = DayType.SATURDAY,
            host = "Pablo Emilio Sanabria Salamanca",
            description = "Con Pablo Emilio Sanabria Salamanca. Un recorrido por nuestras raíces, artistas y géneros para celebrar la riqueza musical y cultural de Colombia.",
            category = ProgramCategory.OPINION_CULTURA
        )
    )

    val sundayPrograms = listOf(
        ProgramItem(
            id = "su1",
            title = "Franja Musical Crossover",
            scheduleDisplay = "12:00 a. m. – 3:00 a. m.",
            startHour = 0,
            startMinute = 0,
            endHour = 3,
            endMinute = 0,
            dayType = DayType.SUNDAY,
            description = "Variedad, ritmo y grandes éxitos para acompañar la madrugada del domingo junto a Mi Tierra Radio.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "su2",
            title = "Viejoteca",
            scheduleDisplay = "7:00 a. m. – 8:00 a. m.",
            startHour = 7,
            startMinute = 0,
            endHour = 8,
            endMinute = 0,
            dayType = DayType.SUNDAY,
            description = "Los clásicos de siempre llegan para comenzar el domingo recordando canciones que permanecen en la memoria y el corazón.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "su3",
            title = "Evocando el Pasado",
            scheduleDisplay = "8:00 a. m. – 12:00 m.",
            startHour = 8,
            startMinute = 0,
            endHour = 12,
            endMinute = 0,
            dayType = DayType.SUNDAY,
            description = "Un viaje por la memoria a través de canciones, artistas e historias que marcaron generaciones y siguen haciendo parte de nuestra identidad.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "su4",
            title = "Sagrada Eucaristía",
            scheduleDisplay = "12:00 m. – 1:00 p. p.",
            startHour = 12,
            startMinute = 0,
            endHour = 13,
            endMinute = 0,
            dayType = DayType.SUNDAY,
            description = "Un encuentro con la fe y la oración para compartir en comunidad un mensaje de esperanza, reflexión y unión.",
            category = ProgramCategory.RELIGIOSO
        ),
        ProgramItem(
            id = "su5",
            title = "Canta Mi Colombia",
            scheduleDisplay = "2:00 p. m. – 3:00 p. m.",
            startHour = 14,
            startMinute = 0,
            endHour = 15,
            endMinute = 0,
            dayType = DayType.SUNDAY,
            host = "Pablo Emilio Sanabria Salamanca",
            description = "Con Pablo Emilio Sanabria Salamanca. La música colombiana es protagonista en un recorrido por nuestras raíces, nuestros artistas y las canciones que nos identifican.",
            category = ProgramCategory.OPINION_CULTURA
        )
    )

    val holidayPrograms = listOf(
        ProgramItem(
            id = "h1",
            title = "Franja Musical Crossover",
            scheduleDisplay = "12:00 a. m. – 3:00 a. m.",
            startHour = 0,
            startMinute = 0,
            endHour = 3,
            endMinute = 0,
            dayType = DayType.HOLIDAY,
            description = "La madrugada festiva se disfruta con una selección crossover llena de variedad, grandes éxitos y buena música.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "h2",
            title = "Viejoteca",
            scheduleDisplay = "7:00 a. m. – 8:00 a. m.",
            startHour = 7,
            startMinute = 0,
            endHour = 8,
            endMinute = 0,
            dayType = DayType.HOLIDAY,
            description = "Los grandes clásicos acompañan el inicio del día para recordar, cantar y disfrutar esas canciones que nunca pasan de moda.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "h3",
            title = "Ídolos de Siempre",
            scheduleDisplay = "8:00 a. m. – 11:00 a. m.",
            startHour = 8,
            startMinute = 0,
            endHour = 11,
            endMinute = 0,
            dayType = DayType.HOLIDAY,
            description = "Un homenaje a los grandes artistas que dejaron huella en la historia de la música y que permanecen para siempre en el corazón de sus seguidores.",
            category = ProgramCategory.MUSICA
        ),
        ProgramItem(
            id = "h4",
            title = "Especial Festivo",
            scheduleDisplay = "11:00 a. m. – 12:00 m.",
            startHour = 11,
            startMinute = 0,
            endHour = 12,
            endMinute = 0,
            dayType = DayType.HOLIDAY,
            description = "Una programación especial para continuar disfrutando el día festivo con música, entretenimiento y la compañía cercana de Mi Tierra Radio.",
            category = ProgramCategory.OPINION_CULTURA
        ),
        ProgramItem(
            id = "h5",
            title = "Sagrada Eucaristía",
            scheduleDisplay = "12:00 m. – 1:00 p. m.",
            startHour = 12,
            startMinute = 0,
            endHour = 13,
            endMinute = 0,
            dayType = DayType.HOLIDAY,
            description = "Un momento de fe y oración para acompañar a nuestra comunidad con un mensaje de esperanza, unión y espiritualidad.",
            category = ProgramCategory.RELIGIOSO
        )
    )

    fun getProgramsForDay(dayType: DayType): List<ProgramItem> {
        return when (dayType) {
            DayType.WEEKDAY -> weekdayPrograms
            DayType.SATURDAY -> saturdayPrograms
            DayType.SUNDAY -> sundayPrograms
            DayType.HOLIDAY -> holidayPrograms
        }
    }

    /**
     * Determines current day type based on system calendar
     */
    fun getCurrentDayType(): DayType {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SATURDAY -> DayType.SATURDAY
            Calendar.SUNDAY -> DayType.SUNDAY
            else -> DayType.WEEKDAY
        }
    }

    /**
     * Finds the currently broadcasting program based on current hour & minute.
     */
    fun getCurrentLiveProgram(): Pair<ProgramItem?, ProgramItem?> {
        val calendar = Calendar.getInstance()
        val currentMinutes = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
        val programs = getProgramsForDay(getCurrentDayType())

        val current = programs.firstOrNull { it.isOnAir(currentMinutes) }
        val currentIndex = if (current != null) programs.indexOf(current) else -1
        val next = if (currentIndex != -1 && currentIndex + 1 < programs.size) {
            programs[currentIndex + 1]
        } else {
            programs.firstOrNull { (it.startHour * 60 + it.startMinute) > currentMinutes }
        }

        return Pair(current, next)
    }

    // Curated catalog of podcast episodes & audio segments available for on-demand & offline download
    val defaultPodcasts = listOf(
        PodcastEpisode(
            id = "pod_ecos_01",
            programTitle = "Ecos del Territorio",
            episodeTitle = "Resumen Semanal: Obras y Comunidad en Santa Rosa",
            description = "Las noticias más destacadas de la semana en Santa Rosa de Osos y el Norte antioqueño con el equipo periodístico de Mi Tierra Radio.",
            durationText = "18:42",
            dateText = "Esta semana",
            streamUrl = "https://play14.tikast.com:20134/stream",
            fileSizeBytes = 14_800_000L,
            fileSizeFormatted = "14.1 MB"
        ),
        PodcastEpisode(
            id = "pod_fe_01",
            programTitle = "Pinceladas de Fe",
            episodeTitle = "Esperanza y Gratitud en la Familia",
            description = "Reflexión del Padre Pompilio Gutiérrez invitando a la reconciliación y la paz en cada hogar santarrosano.",
            durationText = "05:14",
            dateText = "Ayer",
            streamUrl = "https://play14.tikast.com:20134/stream",
            fileSizeBytes = 4_200_000L,
            fileSizeFormatted = "4.0 MB"
        ),
        PodcastEpisode(
            id = "pod_apoteca_01",
            programTitle = "La Apoteca de Sofía (UdeA)",
            episodeTitle = "Plantas Medicinales de Nuestro Páramo",
            description = "Espacio educativo de la Universidad de Antioquia sobre saberes ancestrales y el uso saludable de la flora local.",
            durationText = "22:10",
            dateText = "Hace 3 días",
            streamUrl = "https://play14.tikast.com:20134/stream",
            fileSizeBytes = 18_400_000L,
            fileSizeFormatted = "17.5 MB"
        ),
        PodcastEpisode(
            id = "pod_renace_01",
            programTitle = "Renace la Esperanza",
            episodeTitle = "Balance de Gestión Municipal y Proyectos Rurales",
            description = "El alcalde Luis Bernardo Molina Granda dialoga sobre pavimentaciones, acueductos veredales y apoyo a los campesinos.",
            durationText = "31:05",
            dateText = "Sábado pasado",
            streamUrl = "https://play14.tikast.com:20134/stream",
            fileSizeBytes = 25_100_000L,
            fileSizeFormatted = "23.9 MB"
        ),
        PodcastEpisode(
            id = "pod_colombia_01",
            programTitle = "Canta Mi Colombia",
            episodeTitle = "Homenaje a los Bambucos y Pasillos Antioqueños",
            description = "Con Pablo Emilio Sanabria Salamanca, un recorrido sonoro por las melodías que definen el alma de nuestra tierra.",
            durationText = "27:40",
            dateText = "Domingo pasado",
            streamUrl = "https://play14.tikast.com:20134/stream",
            fileSizeBytes = 21_900_000L,
            fileSizeFormatted = "20.8 MB"
        ),
        PodcastEpisode(
            id = "pod_calidoscopio_01",
            programTitle = "El Calidoscopio",
            episodeTitle = "Historias y Tradiciones de la Basílica de Santa Rosa",
            description = "Leonel Ramírez explora la memoria arquitectónica y las crónicas orales que dan vida a nuestra ciudad histórica.",
            durationText = "16:20",
            dateText = "Hace 1 semana",
            streamUrl = "https://play14.tikast.com:20134/stream",
            fileSizeBytes = 12_500_000L,
            fileSizeFormatted = "11.9 MB"
        )
    )
}
