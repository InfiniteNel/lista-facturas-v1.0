package com.jroslar.listafacturasv01.domain

import com.jroslar.listafacturasv01.data.FacturasRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before


class GetFacturasLocalUseCaseTest {

    @RelaxedMockK
    private lateinit var facturasRepository: FacturasRepository

    lateinit var getFacturasLocalUseCase: GetFacturasLocalUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getFacturasLocalUseCase = GetFacturasLocalUseCase()
    }
}