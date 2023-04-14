package com.jroslar.listafacturasv01.core

enum class DescEstado(val descEstado: String) {
    pedientedepago("Pendiente de pago"),
    pagada("Pagada"),
    anuladas("Anuladas"),
    cuotafija("Cuota Fija"),
    plandepago("Plan de pago")
}