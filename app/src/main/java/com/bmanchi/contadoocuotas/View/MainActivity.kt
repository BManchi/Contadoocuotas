package com.bmanchi.contadoocuotas.View

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


        // TODO add null check
        binding.precioContado.doAfterTextChanged {
            precioContado = binding.precioContado.text.toString().toDouble()
            calcularContado()
        }

        binding.descuentoContado.doAfterTextChanged {
            descuento = binding.descuentoContado.text.toString().toInt()
            calcularContado()
        }

        binding.precioCuotas.doAfterTextChanged {
            precioCuotas = binding.precioCuotas.text.toString().toDouble()
            calcularCuotas()
        }

        binding.cuotas.doAfterTextChanged {
            cantidadCuotas = binding.cuotas.text.toString().toInt()
            calcularCuotas()
        }

        binding.interes.doAfterTextChanged {
            interes = binding.interes.text.toString().toInt()
            calcularCuotas()
        }

        binding.inflacionMensual.doAfterTextChanged {
            inflacion = binding.inflacionMensual.text.toString().toDouble()
            calcularCuotas()
        }
    }

    fun calcularContado(){
        if (precioContado!! > 0 && descuento!! > 0) {
            finalContado = precioContado?.times((1 - (descuento?.toDouble()?.div(100)!!)))
            binding.finalContado.text = finalContado.toString()

            calcularMejor()
        }
        else {
            Toast.makeText(this, "faltan datos", Toast.LENGTH_SHORT).show()
        }
    }

    fun calcularCuotas(){
        if (precioCuotas!! > 0 && interes!! > 0 && cantidadCuotas!! > 0) {
            finalCuotas = String.format("%.2f", precioCuotas?.times((1 + (interes?.toDouble()?.div(100)!!)))).toDouble()
            binding.finalCuotas.text = finalCuotas.toString()

            finalDescontado = String.format("%.2f", finalCuotas?.div((1+ inflacion!!.toDouble()?.div(100)).pow(cantidadCuotas!!))).toDouble()
            binding.finalDescontado.text = finalDescontado.toString()

            calcularMejor()
        }
        else {
            Toast.makeText(this, "faltan datos", Toast.LENGTH_SHORT).show()
        }
    }

    fun calcularMejor() {
        if (finalContado != null && finalDescontado != null){
            if (finalContado!! > finalDescontado!!) {
                binding.tableLayout1.setBackgroundColor(Color.parseColor("#D3D2D2"))
                binding.tableLayout2.setBackgroundColor(Color.parseColor("#C6EFD2"))
            }
            else if (finalDescontado!! > finalContado!!){
                binding.tableLayout1.setBackgroundColor(Color.parseColor("#C6EFD2"))
                binding.tableLayout2.setBackgroundColor(Color.parseColor("#D3D2D2"))
            }
        }
    }
    //TODO agregar tasa efectiva mensual y tasa de financiación real con botón +
}