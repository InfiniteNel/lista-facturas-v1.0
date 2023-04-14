package com.jroslar.listafacturasv01.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jroslar.listafacturasv01.databinding.PopupDetallesFacturasBinding


class PopUpFragment : DialogFragment() {

    private var _binding: PopupDetallesFacturasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = PopupDetallesFacturasBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vtCerrarPopUp.setOnClickListener {
            dismiss()
        }
    }
}