package com.jroslar.listafacturasv01.ui.view

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.SeekBar
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jroslar.listafacturasv01.R
import com.jroslar.listafacturasv01.core.DescEstado
import com.jroslar.listafacturasv01.core.Extensions.Companion.castStringToDate
import com.jroslar.listafacturasv01.data.model.FacturasModel
import com.jroslar.listafacturasv01.databinding.FragmentFiltrarFacturasBinding
import com.jroslar.listafacturasv01.ui.view.ListaFacturasFragment.Companion.DATA_FILTER
import com.jroslar.listafacturasv01.ui.view.ListaFacturasFragment.Companion.MAX_IMPORTE
import com.jroslar.listafacturasv01.ui.viewmodel.FiltrarFacturasViewModel
import com.jroslar.listafacturasv01.ui.viewmodel.FiltrarFacturasViewModelFactory
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.floor


class FiltrarFacturasFragment : Fragment() {

    private var _binding: FragmentFiltrarFacturasBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FiltrarFacturasViewModel by viewModels { FiltrarFacturasViewModelFactory(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filtrar, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cerrarFiltrarFacturas -> {
                findNavController().navigateUp()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFiltrarFacturasBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getList()

        val bundle = arguments
        if (bundle!=null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val maxImporte = floor(bundle.getFloat(MAX_IMPORTE).toDouble()).toInt() + 1
            binding.sbImporte.max = maxImporte
            binding.sbImporte.progress = maxImporte
            binding.tvMaxImporte.text = "${maxImporte}€"

            binding.sbImporte.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seek: SeekBar,
                                               progress: Int, fromUser: Boolean) {
                    binding.tvRankImporte.text = "${binding.sbImporte.min}€  -  ${binding.sbImporte.progress}€"
                }

                override fun onStartTrackingTouch(seek: SeekBar) {
                    //
                }

                override fun onStopTrackingTouch(seek: SeekBar) {
                    //
                }
            })
        }

        binding.btFechaDesde.setOnClickListener {
            showDatePicker(binding.btFechaDesde)
        }

        binding.btFechaHasta.setOnClickListener {
            showDatePicker(binding.btFechaHasta)
        }

        binding.btEliminarFiltro.setOnClickListener {
            eliminarFiltros()
        }

        binding.btAplicarFiltro.setOnClickListener{
            comprobarCheckBoxs()
            comprobarFechas()
            viewModel.filterListByImporte(binding.sbImporte.progress)

            var bundle = Bundle()
            bundle.putParcelable(DATA_FILTER, FacturasModel(viewModel._state.value!!.size, viewModel._state.value!!))
            setFragmentResult(DATA_FILTER, bundle)
            findNavController().navigateUp()
        }
    }

    private fun showDatePicker(button: Button) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpdFecha = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val newdf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("es"))
                button.text = "$dayOfMonth/${monthOfYear+1}/$year".castStringToDate().format(newdf)
            }
        }, year, month, day)
        dpdFecha.show()
    }

    private fun comprobarFechas() {
        var text = binding.btFechaDesde.text
        if (text != null && text.isNotEmpty()) viewModel.filterlistByFechaDesde(text.toString())
        text = binding.btFechaHasta.text
        if (text != null && text.isNotEmpty()) viewModel.filterlistByFechaHasta(text.toString())
    }

    private fun comprobarCheckBoxs() {
        if (!binding.chAnuladas.isChecked) viewModel.filterListByCheckBox(DescEstado.anuladas.descEstado)
        if (!binding.chCuotaFija.isChecked) viewModel.filterListByCheckBox(DescEstado.cuotafija.descEstado)
        if (!binding.chPedientesDePago.isChecked) viewModel.filterListByCheckBox(DescEstado.pedientedepago.descEstado)
        if (!binding.chPagado.isChecked) viewModel.filterListByCheckBox(DescEstado.pagada.descEstado)
        if (!binding.chPlanDePago.isChecked) viewModel.filterListByCheckBox(DescEstado.plandepago.descEstado)
    }

    private fun eliminarFiltros() {
        binding.chAnuladas.isChecked = false
        binding.chCuotaFija.isChecked = false
        binding.chPedientesDePago.isChecked = false
        binding.chPagado.isChecked = false
        binding.chPlanDePago.isChecked = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.sbImporte.progress = binding.sbImporte.max
        }

        binding.btFechaDesde.text = null
        binding.btFechaHasta.text = null
    }
}