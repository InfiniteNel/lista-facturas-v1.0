package com.jroslar.listafacturasv01.domain

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jroslar.listafacturasv01.data.FacturasRepository
import com.jroslar.listafacturasv01.data.model.DescEstado
import com.jroslar.listafacturasv01.data.model.FacturaModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest= Config.NONE)
class GetFacturasLocalUseCaseTest {

    @RelaxedMockK
    private lateinit var facturasRepository: FacturasRepository

    lateinit var getFacturasLocalUseCase: GetFacturasLocalUseCase

    lateinit var context: Context

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getFacturasLocalUseCase = GetFacturasLocalUseCase()
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `when database is empty then return emptylist`() = runBlocking {
        //Give
        coEvery { facturasRepository.getAllFacturasLocal(context) } returns emptyList()

        //When
        val response = getFacturasLocalUseCase(context, facturasRepository)

        //Then
        assert(response.isEmpty())
    }
    @Test
    fun `when database is not empty then return list facturas`() = runBlocking {
        //Give
        val myList = listOf(FacturaModel(0, DescEstado.pedientedepago.descEstado, 30.3F, "20/12/2018"))
        coEvery { facturasRepository.getAllFacturasLocal(context) } returns myList

        //When
        val response = getFacturasLocalUseCase(context, facturasRepository)

        //Then
        assert(myList == response)
    }
}