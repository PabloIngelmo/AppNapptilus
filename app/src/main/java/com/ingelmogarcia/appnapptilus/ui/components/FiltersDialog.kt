package com.ingelmogarcia.appnapptilus.ui.components

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.ingelmogarcia.appnapptilus.R
import com.ingelmogarcia.appnapptilus.databinding.FiltersDialogBinding


class FiltersDialog(
    private val onSubmitClickListener: (SpinnerOptionsSelected) -> Unit,
): DialogFragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding : FiltersDialogBinding
    var spinnerGenderItems = arrayOf("-","Male", "Female")
    var spinnerProfessionItems = arrayOf("-","Developer", "Metalworker")
    private var genderSpinerOption = "-"
    private var professionSpinerOption = "-"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FiltersDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(activity)
        builder.setView(binding.root)

        binding.buttonAddFilters.setOnClickListener {
            onSubmitClickListener.invoke(SpinnerOptionsSelected(genderSpinerOption,professionSpinerOption))
            dismiss()
        }


        if (binding.spinnerGender != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item_style1, spinnerGenderItems)
            binding.spinnerGender.adapter = adapter
            binding.spinnerGender.tag = "spinnerGender"
            binding.spinnerGender.onItemSelectedListener = this
        }


        if (binding.spinnerGender != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item_style1, spinnerProfessionItems)
            binding.spinnerProfession.adapter = adapter
            binding.spinnerProfession.tag = "spinnerProfession"
            binding.spinnerProfession.onItemSelectedListener = this
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if(p0!!.tag == binding.spinnerGender.tag){
            genderSpinerOption = spinnerGenderItems.get(p2)
        }
        if(p0!!.tag == binding.spinnerProfession.tag){
            professionSpinerOption = spinnerProfessionItems.get(p2)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}




data class SpinnerOptionsSelected(
    val optionSp1:String,
    val optionSp2:String
)