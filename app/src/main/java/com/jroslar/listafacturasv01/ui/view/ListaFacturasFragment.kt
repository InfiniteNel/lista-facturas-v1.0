package com.jroslar.listafacturasv01.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jroslar.listafacturasv01.R
import com.jroslar.listafacturasv01.data.adapter.ListaFacturasAdapter
import com.jroslar.listafacturasv01.data.model.FacturaModel
import com.jroslar.listafacturasv01.data.model.FacturasModel
import com.jroslar.listafacturasv01.databinding.FragmentListaFacturasBinding
import com.jroslar.listafacturasv01.ui.viewmodel.ListaFacturasViewModel
import com.jroslar.listafacturasv01.ui.viewmodel.ListaFacturasViewModelFactory

class ListaFacturasFragment : Fragment(), ListaFacturasAdapter.OnManageFactura {

    private var _binding: FragmentListaFacturasBinding? = null
    private val binding get() = _binding!!
    private val adapter = ListaFacturasAdapter(this)
    private val viewModel: ListaFacturasViewModel by viewModels {ListaFacturasViewModelFactory(requireContext())}

    companion object {
        val MAX_IMPORTE: String = "maximo_importe"
        val DATA_FILTER: String = "data_filter"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filtrarFacturas -> {
                if (viewModel._state.value == ListaFacturasViewModel.ListaFacturasResult.DATA) {
                    val bundle = Bundle()
                    bundle.putFloat(MAX_IMPORTE, viewModel._maxValueImporte.value?: 0F)
                    findNavController().navigate(R.id.action_ListaFacturasFragment_to_filtrarFacturasFragment, bundle)
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListaFacturasBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intiAdapter()

        viewModel._data.observe(viewLifecycleOwner, Observer {
            adapter.listaFacturas = it
            adapter.notifyDataSetChanged()
        })

        /*viewModel._isLoading.observe(viewLifecycleOwner, Observer {
            binding.loading.isVisible = it
        })*/

        viewModel._state.observe(viewLifecycleOwner, Observer {
            when(it) {
                ListaFacturasViewModel.ListaFacturasResult.LOADING -> binding.loading.isVisible = true
                ListaFacturasViewModel.ListaFacturasResult.DATA -> {
                    binding.loading.isVisible = false
                    binding.tvTitleNoData.isVisible = false
                }
                ListaFacturasViewModel.ListaFacturasResult.NO_DATA -> {
                    binding.loading.isVisible = false
                    binding.tvTitleNoData.isVisible = true
                }
                else -> {}
            }
        })

        setFragmentResultListener(DATA_FILTER) { reqKey, bundle ->
            if (reqKey == DATA_FILTER) {
                val value: FacturasModel = bundle.getParcelable(DATA_FILTER)!!
                viewModel._data.value = value.facturas
            }
        }
    }

    private fun intiAdapter() {
        binding.rvListaFacturas.layoutManager = LinearLayoutManager(context)
        binding.rvListaFacturas.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickFactura(factura: FacturaModel) {
        findNavController().navigate(R.id.popUpFragment)
    }
}