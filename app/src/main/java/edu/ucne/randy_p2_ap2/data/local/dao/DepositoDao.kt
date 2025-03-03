package edu.ucne.randy_p2_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

import edu.ucne.randy_p2_ap2.data.local.entity.DepositoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DepositoDao {
    @Upsert
    suspend fun save(entity: DepositoEntity)

    @Delete
    suspend fun delete(entity: DepositoEntity)

    @Query("SELECT * FROM deposito")
    fun getAll(): Flow<List<DepositoEntity>>

    @Query("""
        SELECT * 
        FROM deposito 
        WHERE idDeposito = :id
        LIMIT 1
        """
    )
    suspend fun find(id: Int): DepositoEntity?
}