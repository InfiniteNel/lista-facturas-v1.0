package com.jroslar.listafacturasv01.ui.viewmodel

import android.content.Context
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jroslar.listafacturasv01.core.Extensions.Companion.castStringToDate
import com.jroslar.listafacturasv01.data.model.FacturaModel
import com.jroslar.listafacturasv01.domain.GetFacturasLocalUseCase
import kotlinx.coroutines.launch

class FiltrarFacturasViewModel(val context: Context): ViewModel() {
    var _state: MutableLiveData<List<FacturaModel>> = MutableLiveData()

    private val getFacturasLocalUseCase = GetFacturasLocalUseCase()

    fun getList() {
        viewModelScope.launch {
            val data: List<FacturaModel> = getFacturasLocalUseCase(context)
            if (!data.isNullOrEmpty()) {
                _state.value = data
            } else {
                _state.value = emptyList()
            }
        }
    }

    fun filterListByCheckBox(value: String) {
        _state.value = _state.value?.filterNot { it.descEstado == value }
    }

    fun filterListByImporte(value: Int) {
        _state.value = _state.value?.filter { it.importeOrdenacion < value }
    }

    fun filterlistByFechaDesde(value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _state.value = _state.value?.filter { it.fecha.castStringToDate().isAfter(value.castStringToDate())}
        }
    }

    fun filterlistByFechaHasta(value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _state.value = _state.value?.filter { it.fecha.castStringToDate().isBefore(value.castStringToDate())}
        }
    }
}

class FiltrarFacturasViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FiltrarFacturasViewModel(context) as T
    }
}