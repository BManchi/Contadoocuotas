package com.bmanchi.contadoocuotas.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlin.math.pow

private const val TAG = "calcularViewModel"

class CalcularViewModel: ViewModel() {

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

    fun calcularContado(){
        if (precioContado!! > 0 && descuento!! > 0) {
            finalContado.value = precioContado?.times((1 - (descuento?.toDouble()?.div(100)!!)))
//            binding.finalContado.text = finalContado.toString()
            Log.d(TAG, "calularContado() finalContado = ${finalContado.value}")


            calcularMejor()
        } else {
            finalContado.value = precioContado
//            binding.finalContado.text = finalContado.toString()

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
//                binding.finalCuotas.text = finalCuotas.toString()
                finalDescontado.value = String.format("%.2f", finalCuotas?.div((1+ inflacion!!.toDouble().div(100)).pow(cantidadCuotas!!))).toDouble()
//                binding.finalDescontado.text = finalDescontado.toString()
            }

            if (interes!! > 0){
                finalCuotas = String.format("%.2f", precioCuotas?.times((1 + (interes?.toDouble()?.div(100)!!)))).toDouble()
//                binding.finalCuotas.text = finalCuotas.toString()
                Log.d(TAG, "finalCuotas = $finalCuotas")
                finalDescontado.value = String.format("%.2f", finalCuotas?.div((1+ inflacion!!.toDouble().div(100)).pow(cantidadCuotas!!))).toDouble()
//                binding.finalDescontado.text = finalDescontado.toString()
                Log.d(TAG, "finalDescontado = ${finalDescontado.value}")
            }
        }
        calcularMejor()
    }

    fun calcularMejor() {
        Log.d(TAG, "calularMejor()")
        if (finalContado.value != null && finalDescontado.value != null){
            if (finalContado.value!! > finalDescontado.value!!) {
                Log.d(TAG, "${finalContado.value} > ${finalDescontado.value} ")
//                binding.contadoLayout.setBackgroundColor(Color.parseColor("#D3D2D2"))
//                binding.cuotasLayout.setBackgroundColor(Color.parseColor("#C6EFD2"))
            }
            else if (finalDescontado.value!! > finalContado.value!!){
                Log.d(TAG, "${finalContado.value} < ${finalDescontado.value} ")
//                binding.contadoLayout.setBackgroundColor(Color.parseColor("#C6EFD2"))
//                binding.cuotasLayout.setBackgroundColor(Color.parseColor("#D3D2D2"))
            }
        }
    }
}