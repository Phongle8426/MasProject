package com.example.masapp.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

/*
///
/// Project: MasApp
/// Created by pc on 12/17/2021.
///
*/
class DialogConfirm : DialogFragment() {
    var title: String = ""
    var onAccept: () -> Unit = {}
    var onDecline: () -> Unit = {}

    fun setMessage(mess: String): DialogConfirm{
        this.title = mess
        return this
    }

    companion object {
        var instance: DialogConfirm? = null
        fun getInstanceDialog() = instance?:DialogConfirm().also { instance = it }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(activity)
            .setMessage(title)
            .setNegativeButton("Hủy", DialogInterface.OnClickListener{ dialog, id ->
                onDecline()
                dialog.dismiss()
            })
            .setPositiveButton("Chấp nhận", DialogInterface.OnClickListener{ dialog, id ->
                onAccept()
                dialog.dismiss()
            })
            return dialog.create()
        }
}