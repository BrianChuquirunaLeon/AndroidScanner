package com.example.scanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.scanner.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnScanner.setOnClickListener{ initScanner() }

    }

    private fun initScanner() {

        val integrator = IntentIntegrator(this)
        //Definiremos que cosas queremos escanear, ya que esta ZXING es capaz de escanear varias cosas, como por ejemplo
        //codigo QR, codigo de barras,etc. por lo que si tenemos una APP que necesita escanear codigos de Barra, no quisieramos
        //que por equivocacion escanee un codigo QR
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Mi primer codigo de Barras")
        //Con setTorchEnabled(true) activamos el Flash de la camara
        integrator.setTorchEnabled(true)
        //Con setBeepEnabled(true) el celular hara un sonido cada que scannea un codigo de barras
        integrator.setBeepEnabled(true)
        //Lanza una activity y se queda escuchando si hay una respuesta, ademas al hacer esto ya se lanza el scanner y se gestionan
        // los permisos de la camara automaticamente sin que nostros hagamos nada.
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result:IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result != null){
            //El resultado fue bien, volvi a la main activity xq queria, pero no escanee nada
            if(result.contents == null){
                Toast.makeText(this,"Cancelado",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"El valor escaneado es ${result.contents}",Toast.LENGTH_SHORT).show()
            }

        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }

    }


}