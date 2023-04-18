package com.jroslar.listafacturasv01.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jroslar.listafacturasv01.data.model.FacturaModel
import com.jroslar.listafacturasv01.domain.GetFacturasFromApiUseCase
import kotlinx.coroutines.launch

class ListaFacturasViewModel(val context: Context): ViewModel() {
    var _data: MutableLiveData<List<FacturaModel>> = MutableLiveData()
    var _state: MutableLiveData<ListaFacturasResult> = MutableLiveData()
    var _maxValueImporte: MutableLiveData<Float> = MutableLiveData()

    private val getFacturasUseCase = GetFacturasFromApiUseCase()

    init {
        viewModelScope.launch {
            _state.postValue(ListaFacturasResult.LOADING)
            val data: List<FacturaModel> = getFacturasUseCase(context)
            if (!data.isNullOrEmpty()) {
                _data.postValue(data)
                _state.postValue(ListaFacturasResult.DATA)
                _maxValueImporte.postValue(data.sortedBy { it.importeOrdenacion }[data.size - 1].importeOrdenacion)
            } else {
                _state.postValue(ListaFacturasResult.API_NO_DATA)
                _maxValueImporte.postValue(0F)
            }
        }
    }

    fun getList(data: List<FacturaModel>) {
        if (!data.isNullOrEmpty()) {
            _data.postValue(data)
            _state.postValue(ListaFacturasResult.DATA)
        } else {
            _data.postValue(emptyList())
            _state.postValue(ListaFacturasResult.NO_DATA)
        }
    }

    enum class ListaFacturasResult {
        LOADING,
        API_NO_DATA,
        NO_DATA,
        DATA
    }
}
class ListaFacturasViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListaFacturasViewModel(context) as T
    }
}