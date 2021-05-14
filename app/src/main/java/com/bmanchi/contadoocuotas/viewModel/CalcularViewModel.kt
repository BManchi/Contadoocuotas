package com.bmanchi.contadoocuotas.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlin.math.pow

private const val TAG = "calcularViewModel"

class CalcularViewModel : ViewModel() {

    var precioContado = 0.0
    var descuento: Int = 0
    private var finalContado = MutableLiveData<Double>()
    val finalContadoLiveData: LiveData<String>
        get() = Transformations.map(finalContado) { it.toString() }

    var precioCuotas = 0.0
    var cantidadCuotas = 0
    var interes = 0
    var finalCuotas = 0.0
    private var finalDescontado = MutableLiveData<Double>()
    val finalDescontadoLiveData: LiveData<String>
        get() = Transformations.map(finalDescontado) { it.toString() }

    var inflacion = 0.0

    var mejorAlternativa = MutableLiveData<String>()


    fun calcularContado() {
        if (precioContado > 0 && descuento > 0) {
            finalContado.value = String.format(
                "%.2f",
                precioContado.times((1 - (descuento.toDouble().div(100))))
                )
                .toDouble()
            Log.d(TAG, "calularContado() finalContado = ${finalContado.value}")

            calcularMejor()
        } else {
            finalContado.value = precioContado
            Log.d(TAG, "calularContado() finalContado = ${finalContado.value}")

            calcularMejor()
        }
    }

    fun calcularCuotas() {
        Log.d(TAG, "calularCuotas()")


        if (precioCuotas > 0 && cantidadCuotas > 0) {
            var cuotaDescontada = 0.00
            finalCuotas = 0.00
            for (i in 1..cantidadCuotas) {
                cuotaDescontada = ((precioCuotas*(1+interes.toDouble().div(100))).div(cantidadCuotas).div((1 + inflacion.div(100)).pow(i)))
                finalCuotas += cuotaDescontada
                Log.d(TAG, "agrega $cuotaDescontada a nuevo final descontado correcto ${finalDescontado.value}")
            }
            finalDescontado.value = String.format("%.2f", finalCuotas).toDouble()
        }
        calcularMejor()
    }

    private fun calcularMejor() {
        Log.d(TAG, "calularMejor()")
        if (finalContado.value != null && finalDescontado.value != null) {
            if (finalContado.value!! > finalDescontado.value!!) {
                Log.d(
                    TAG,
                    "${finalContado.value} > ${finalDescontado.value} mejor alternativa ${mejorAlternativa.value}"
                )
                mejorAlternativa.value = "financiado"
            } else if (finalDescontado.value!! > finalContado.value!!) {
                Log.d(
                    TAG,
                    "${finalContado.value} < ${finalDescontado.value} mejor alternativa ${mejorAlternativa.value}"
                )
                mejorAlternativa.value = "contado"
            }
        }
    }
}