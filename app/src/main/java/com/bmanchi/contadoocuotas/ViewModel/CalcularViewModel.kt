package com.bmanchi.contadoocuotas.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlin.math.pow

private const val TAG = "calcularViewModel"

class CalcularViewModel: ViewModel() {

    //todo br1: Todos estos objetos son realmente nulleables? porque si le das valor 0 al instancearlos
    //todo br1: y nunca los pasas a null, debielas sacarle el signo de pregunta
    var precioContado: Double? = 0.0
    var descuento: Int? = 0
    var finalContado = MutableLiveData<Double>()
    val finalContadoLiveData : LiveData<String>
        get() = Transformations.map(finalContado) {it.toString()}

    var precioCuotas:Double? = 0.0
    var cantidadCuotas:Int? = 0
    var interes:Int? = 0
    var finalCuotas:Double? = 0.0
    var finalDescontado = MutableLiveData<Double>()
    val finalDescontadoLiveData : LiveData<String>
        get() = Transformations.map(finalDescontado) {it.toString()}

    var inflacion:Double? = 0.0

    var mejorAlternativa = MutableLiveData<String>()


    fun calcularContado(){
        if (precioContado!! > 0 && descuento!! > 0) {
            finalContado.value = precioContado?.times((1 - (descuento?.toDouble()?.div(100)!!)))

            Log.d(TAG, "calularContado() finalContado = ${finalContado.value}")


            calcularMejor()
        } else {
            finalContado.value = precioContado

            Log.d(TAG, "calularContado() finalContado = ${finalContado.value}")

            calcularMejor()
        }
    }

    fun calcularCuotas(){
        Log.d(TAG, "calularCuotas()")
        if (precioCuotas!! > 0 && cantidadCuotas!! > 0) {
            if (interes!! == 0){
                finalCuotas = precioCuotas
                Log.d(TAG, "finalCuotas = $finalCuotas")
                finalDescontado.value = String.format("%.2f",
                    finalCuotas?.div((1+ inflacion!!.toDouble().div(100)).pow(cantidadCuotas!!))).toDouble()
            }

            if (interes!! > 0){
                finalCuotas = String.format("%.2f",
                    precioCuotas?.times((1 + (interes?.toDouble()?.div(100)!!)))).toDouble()
                Log.d(TAG, "finalCuotas = $finalCuotas")
                finalDescontado.value = String.format("%.2f",
                    finalCuotas?.div((1+ inflacion!!.toDouble().div(100)).pow(cantidadCuotas!!))).toDouble()
                Log.d(TAG, "finalDescontado = ${finalDescontado.value}")
            }
        }
        calcularMejor()
    }

    fun calcularMejor() {
        Log.d(TAG, "calularMejor()")
        if (finalContado.value != null && finalDescontado.value != null){
            if (finalContado.value!! > finalDescontado.value!!) {
                Log.d(TAG, "${finalContado.value} > ${finalDescontado.value} mejor alternativa $mejorAlternativa")
                mejorAlternativa.value = "financiado"
            }
            else if (finalDescontado.value!! > finalContado.value!!){
                Log.d(TAG, "${finalContado.value} < ${finalDescontado.value} mejor alternativa $mejorAlternativa")
                mejorAlternativa.value = "contado"
            }
        }
    }
}