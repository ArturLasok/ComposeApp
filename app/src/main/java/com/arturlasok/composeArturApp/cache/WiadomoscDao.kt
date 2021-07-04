package com.arturlasok.composeArturApp.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arturlasok.composeArturApp.cache.model.WiadomoscEntity
import com.arturlasok.composeArturApp.presentation.util.RECIPE_PAGINATION_PAGE_SIZE


@Dao
interface WiadomoscDao {

    @Insert
    suspend fun insertWiadomosc(wiadomosc: WiadomoscEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWiadomosci(wiadomosci: List<WiadomoscEntity>): LongArray

    @Query("SELECT * FROM wiadomosci WHERE id = :id")
    suspend fun getWiadomoscById(id: Int): WiadomoscEntity?

    @Query("DELETE FROM wiadomosci WHERE id IN (:ids)")
    suspend fun deleteWiadomosci(ids: List<Int>): Int

    @Query("DELETE FROM wiadomosci")
    suspend fun deleteAllWiadomosci()

    @Query("DELETE FROM wiadomosci WHERE id = :primaryKey")
    suspend fun deleteWiadomosc(primaryKey: Int): Int

    /**
     * Retrieve recipes for a particular page.
     * Ex: page = 2 retrieves recipes from 30-60.
     * Ex: page = 3 retrieves recipes from 60-90

    @Query(
        """
        SELECT * FROM wiadomosci
        WHERE tytul LIKE '%' || :query || '%'

        ORDER BY id DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
        """
    )
    suspend fun searchRecipes(
        query: String,
        page: Int,
        pageSize: Int = RECIPE_PAGINATION_PAGE_SIZE
    ): List<WiadomoscEntity>
    */
    /**
     * Same as 'searchRecipes' function, but no query.
     */
    @Query(
        """
        SELECT * FROM wiadomosci 
        ORDER BY id DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
    """
    )
    suspend fun getAllWiadomosci(
        page: Int,
        pageSize: Int = RECIPE_PAGINATION_PAGE_SIZE
    ): List<WiadomoscEntity>

    /**
     * Restore Recipes after process death

    @Query(
        """
        SELECT * FROM wiadomosci
        WHERE tytul LIKE '%' || :query || '%'

        ORDER BY id DESC LIMIT (:page * :pageSize)
        """
    )
    suspend fun restoreRecipes(
        query: String,
        page: Int,
        pageSize: Int = RECIPE_PAGINATION_PAGE_SIZE
    ): List<WiadomoscEntity>
    */
    /**
     * Same as 'restoreRecipes' function, but no query.

    @Query(
        """
        SELECT * FROM wiadomosci
        ORDER BY id DESC LIMIT (:page * :pageSize)
    """
    )
    suspend fun restoreAllWiadomosci(
        page: Int,
        pageSize: Int = RECIPE_PAGINATION_PAGE_SIZE
    ): List<WiadomoscEntity>
     */
}