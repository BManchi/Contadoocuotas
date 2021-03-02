package com.bmanchi.contadoocuotas.view

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.bmanchi.contadoocuotas.viewModel.CalcularViewModel
import com.bmanchi.contadoocuotas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val model: CalcularViewModel by viewModels()

        binding.precioContado.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                model.precioContado = it.toString().toDouble()
            }
            model.calcularContado()
        }

        binding.descuentoContado.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                model.descuento = it.toString().toInt()
            }
            model.calcularContado()
        }

        model.finalContadoLiveData.observe(this, { text ->
            binding.finalContado.text = text
        })

        binding.precioCuotas.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                model.precioCuotas = it.toString().toDouble()
            }
            model.calcularCuotas()
        }

        binding.cuotas.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                model.cantidadCuotas = it.toString().toInt()
            }
            model.calcularCuotas()
        }

        binding.interes.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                model.interes = it.toString().toInt()
            }
            model.calcularCuotas()
        }

        binding.finalCuotas.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                model.finalCuotas = it.toString().toDouble()
            }
            model.calcularCuotas()
        }

        model.finalDescontadoLiveData.observe(this, { text ->
            binding.finalDescontado.text = text
        })

        binding.inflacionMensual.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                model.inflacion = it.toString().toDouble()
            }
            model.calcularCuotas()
        }

        model.mejorAlternativa.observe(this, { text ->
            if (text == "financiado") {
                binding.contadoLayout.setBackgroundColor(Color.parseColor("#D3D2D2"))
                binding.cuotasLayout.setBackgroundColor(Color.parseColor("#C6EFD2"))
            }
            if (text == "contado") {
                binding.contadoLayout.setBackgroundColor(Color.parseColor("#C6EFD2"))
                binding.cuotasLayout.setBackgroundColor(Color.parseColor("#D3D2D2"))
            }
        })
    }
    //TODO agregar tasa efectiva mensual y tasa de financiación real con botón +
    //TODO agregar mas altertativas de financiación para comparar entre sí
}