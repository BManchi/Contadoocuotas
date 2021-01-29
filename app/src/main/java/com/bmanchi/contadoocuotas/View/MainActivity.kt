package com.bmanchi.contadoocuotas.View

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.bmanchi.contadoocuotas.databinding.ActivityMainBinding
import kotlin.math.pow


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var precioContado: Double? = 0.0
    var descuento: Int? = 0
    var finalContado:Double? = null

    var precioCuotas:Double? = 0.0
    var cantidadCuotas:Int? = 0
    var interes:Int? = 0
    var finalCuotas:Double? = 0.0
    var finalDescontado: Double? = null

    var inflacion:Double? = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        /** Si el text del editText es Empty, pone 0 en la variable a modificar */
        /** Igual creo que se puede modificar y achicar */
        binding.precioContado.doAfterTextChanged {

            if (it.toString().isEmpty()){
                precioContado = 0.00
            } else {
                precioContado = it.toString().toDouble()
            }
            calcularContado()

//            precioContado = binding.precioContado.text.toString().toDouble()
//            calcularContado()
        }

        binding.descuentoContado.doAfterTextChanged {
            if (it.toString().isEmpty()){
                descuento = 0
            } else {
                descuento = it.toString().toInt()
            }
            calcularContado()

//            descuento = binding.descuentoContado.text.toString().toInt()
//            calcularContado()
        }

        binding.precioCuotas.doAfterTextChanged {
            if (it.toString().isEmpty()){
                precioCuotas = 0.00
            } else {
                precioCuotas = it.toString().toDouble()
            }
            calcularCuotas()

//            precioCuotas = binding.precioCuotas.text.toString().toDouble()
//            calcularCuotas()
        }

        binding.cuotas.doAfterTextChanged {
            if (it.toString().isEmpty()){
                cantidadCuotas = 0
            } else {
                cantidadCuotas = it.toString().toInt()
            }
            calcularCuotas()

//            cantidadCuotas = binding.cuotas.text.toString().toInt()
//            calcularCuotas()
        }

        binding.interes.doAfterTextChanged {
            if (it.toString().isEmpty()){
                interes = 0
            } else {
                interes = it.toString().toInt()
            }
            calcularCuotas()

//            interes = binding.interes.text.toString().toInt()
//            calcularCuotas()
        }

        binding.inflacionMensual.doAfterTextChanged {
            if (it.toString().isEmpty()){
                inflacion = 0.00
            } else {
                inflacion = it.toString().toDouble()
            }
            calcularCuotas()

//            inflacion = binding.inflacionMensual.text.toString().toDouble()
//            calcularCuotas()
        }
    }


    fun calcularContado(){
        if (precioContado!! > 0 && descuento!! > 0) {
            finalContado = precioContado?.times((1 - (descuento?.toDouble()?.div(100)!!)))
            binding.finalContado.text = finalContado.toString()

            calcularMejor()
        } else {
            finalContado = precioContado
            binding.finalContado.text = finalContado.toString()

            calcularMejor()
        }
    }

    fun calcularCuotas(){ //TODO agregar EditText en precio final financiado, lo que calcularía el interés implícito
        if (precioCuotas!! > 0 && interes!! > 0 && cantidadCuotas!! > 0) {
            finalCuotas = String.format("%.2f", precioCuotas?.times((1 + (interes?.toDouble()?.div(100)!!)))).toDouble()
            binding.finalCuotas.text = finalCuotas.toString()


            finalDescontado = String.format("%.2f", finalCuotas?.div((1+ inflacion!!.toDouble().div(100)).pow(cantidadCuotas!!))).toDouble()
            binding.finalDescontado.text = finalDescontado.toString()

            calcularMejor()
        } //TODO agregar else interés cero (implicito en precio)
    }

    fun calcularMejor() {
        if (finalContado != null && finalDescontado != null){
            if (finalContado!! > finalDescontado!!) {
                binding.contadoLayout.setBackgroundColor(Color.parseColor("#D3D2D2"))
                binding.cuotasLayout.setBackgroundColor(Color.parseColor("#C6EFD2"))
            }
            else if (finalDescontado!! > finalContado!!){
                binding.contadoLayout.setBackgroundColor(Color.parseColor("#C6EFD2"))
                binding.cuotasLayout.setBackgroundColor(Color.parseColor("#D3D2D2"))
            }
        }
    }
    //TODO agregar tasa efectiva mensual y tasa de financiación real con botón +

    //todo: toda la logica debiera estar en en viewModel (Hacer MVVM). Lo podemos ver en la semana
}