package com.jroslar.listafacturasv01.domain

import android.content.Context
import androidx.test.core.app.ApplicationProvider
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
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
class GetFacturasFromApiUseCaseTest {

    @RelaxedMockK
    private lateinit var facturasRepository: FacturasRepository

    lateinit var getFacturasFromApiUseCase: GetFacturasFromApiUseCase

    lateinit var context: Context

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getFacturasFromApiUseCase = GetFacturasFromApiUseCase()
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `when the api doesnt return anything then get database data`() = runBlocking {
        //Give
        coEvery { facturasRepository.getAllFacturasFromApi(true) } returns emptyList()

        //When
        getFacturasFromApiUseCase(context, facturasRepository, true)

        //Then
        coVerify(exactly = 1) { facturasRepository.getAllFacturasLocal(context) }
    }
    @Test
    fun `when the api return something then get api data`() = runBlocking {
        //Give
        val myList = listOf(FacturaModel(0, DescEstado.pedientedepago.descEstado, 30.3F, "20/12/2018"))
        coEvery { facturasRepository.getAllFacturasFromApi(true) } returns myList

        //When
        val response = getFacturasFromApiUseCase(context, facturasRepository, true)

        //Then
        coVerify(exactly = 1) { facturasRepository.clearFacturas(context) }
        coVerify(exactly = 1) { facturasRepository.insertFacturas(context, any()) }
        coVerify(exactly = 0) { facturasRepository.getAllFacturasLocal(context) }
        assert(myList == response)
    }
}