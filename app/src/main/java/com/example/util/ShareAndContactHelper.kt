package com.example.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.data.RadioRepository

object ShareAndContactHelper {

    fun openWhatsAppCabina(context: Context, programName: String? = null) {
        val extraText = if (!programName.isNullOrEmpty()) " mientras disfruto de $programName" else ""
        val text = "¡Hola Mi Tierra Radio 88.1 FM! Un saludo especial para la cabina desde Santa Rosa de Osos$extraText."
        val url = "https://api.whatsapp.com/send?phone=${RadioRepository.STATION_WHATSAPP}&text=${Uri.encode(text)}"
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "WhatsApp no está instalado en este dispositivo", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareApp(context: Context, currentShow: String? = null) {
        val showInfo = if (!currentShow.isNullOrEmpty()) " al aire: $currentShow." else "."
        val shareMessage = "¡Te invito a sintonizar Mi Tierra Radio 88.1 FM (Santa Rosa de Osos)! ¡Cómo me gusta!$showInfo Escúchala en vivo en su aplicación oficial o en línea: ${RadioRepository.LIVE_STREAM_URL}"

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Mi Tierra Radio 88.1 FM")
            putExtra(Intent.EXTRA_TEXT, shareMessage)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val chooser = Intent.createChooser(intent, "Compartir Mi Tierra Radio").apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(chooser)
    }

    fun openWebUrl(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No se pudo abrir el enlace web", Toast.LENGTH_SHORT).show()
        }
    }
}
