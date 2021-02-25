package com.bmanchi.contadoocuotas.View

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.bmanchi.contadoocuotas.ViewModel.CalcularViewModel
import com.bmanchi.contadoocuotas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val model: CalcularViewModel by viewModels()
        // TODO traer data del VM

        /** Si el text del editText es Empty, pone 0 en la variable a modificar */
        /** Igual creo que se puede modificar y achicar */
        binding.precioContado.doAfterTextChanged {

            if (it.toString().isEmpty()){
                model.precioContado = 0.00
            } else {
                model.precioContado = it.toString().toDouble()
            }
            model.calcularContado()
        }



        binding.descuentoContado.doAfterTextChanged {
            if (it.toString().isEmpty()){
                model.descuento = 0
            } else {
                model.descuento = it.toString().toInt()
            }
            model.calcularContado()
        }

        binding.precioCuotas.doAfterTextChanged {
            if (it.toString().isEmpty()){
                model.precioCuotas = 0.00
            } else {
                model.precioCuotas = it.toString().toDouble()
            }
            model.calcularCuotas()
        }

        binding.cuotas.doAfterTextChanged {
            if (it.toString().isEmpty()){
                model.cantidadCuotas = 0
            } else {
                model.cantidadCuotas = it.toString().toInt()
            }
            model.calcularCuotas()
        }

        binding.interes.doAfterTextChanged {
            if (it.toString().isEmpty()){
                model.interes = 0
            } else {
                model.interes = it.toString().toInt()
            }
            model.calcularCuotas()
        }

        binding.inflacionMensual.doAfterTextChanged {
            if (it.toString().isEmpty()){
                model.inflacion = 0.00
            } else {
                model.inflacion = it.toString().toDouble()
            }
            model.calcularCuotas()
        }
    }
    //TODO agregar tasa efectiva mensual y tasa de financiación real con botón +
}