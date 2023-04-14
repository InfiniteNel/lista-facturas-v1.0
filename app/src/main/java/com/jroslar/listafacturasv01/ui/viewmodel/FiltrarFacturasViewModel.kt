package com.jroslar.listafacturasv01.ui.viewmodel

import android.content.Context
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jroslar.listafacturasv01.data.model.FacturaModel
import com.jroslar.listafacturasv01.domain.GetFacturasLocalUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
        _state.value = _state.value?.filterNot { it.descEstado.equals(value) }
    }

    fun filterListByImporte(value: Int) {
        _state.value = _state.value?.filter { it.importeOrdenacion < value }
    }

    fun filterlistByFechaDesde(value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val df: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            _state.value = _state.value?.filter { LocalDate.parse(it.fecha, df).isAfter(LocalDate.parse(castDate(value), df))}
        }
    }

    fun filterlistByFechaHasta(value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val df: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            _state.value = _state.value?.filter { LocalDate.parse(it.fecha, df).isBefore(LocalDate.parse(castDate(value), df))}
        }
    }

    private fun castDate(value:String):String {
        var list = value.split("/").toMutableList()
        if (list[0].toInt() < 10) list[0] = "0${list[0]}"
        if (list[1].toInt() < 10) list[1] = "0${list[1]}"
        return "${list[0]}/${list[1]}/${list[2]}"
    }
}
class FiltrarFacturasViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FiltrarFacturasViewModel(context) as T
    }
}