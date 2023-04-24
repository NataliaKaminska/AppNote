package com.example.appnote.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.appnote.ui.theme.BabyBlue
import com.example.appnote.ui.theme.BabyBlue2
import com.example.appnote.ui.theme.Ivory
import com.example.appnote.ui.theme.LightGreen
import com.example.appnote.ui.theme.Lilac
import com.example.appnote.ui.theme.RedOrange
import com.example.appnote.ui.theme.RedPink
import com.example.appnote.ui.theme.RoseWater
import com.example.appnote.ui.theme.Violet


@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
){

    companion object {
        val noteColors = listOf(RoseWater, Ivory, BabyBlue2, Lilac,RedPink)
    }

}

class InvalidNoteException(message: String): Exception(message)
