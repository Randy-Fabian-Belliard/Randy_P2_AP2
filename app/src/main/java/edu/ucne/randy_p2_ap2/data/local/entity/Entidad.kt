package edu.ucne.randy_p2_ap2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Entidad")
abstract class Entidad (
    @PrimaryKey(autoGenerate = true)
    var id: Int

)