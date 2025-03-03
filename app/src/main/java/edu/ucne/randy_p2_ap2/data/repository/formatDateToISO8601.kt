package edu.ucne.randy_p2_ap2.data.repository

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun formatDateToISO8601(date: Instant): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .withZone(ZoneId.of("UTC"))
    return formatter.format(date)
}