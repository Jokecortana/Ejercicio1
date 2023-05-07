package com.example.myapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        val bundle = intent.extras





        // Referencia de text views



        if(bundle!=null){
            val nombre = bundle.getString("nombre", "")
            val apellido = bundle.getString("apellido", "")
            val cuenta = bundle.getInt("cuenta", 0)
            val edad2= bundle.getInt("eddad", 0)
            val Zod = bundle.getString("signoZ","error")
            val ZodChino = bundle.getString("signoCh", "error")
            val carrera = bundle.getString("carrera", "error")

            //email
            val email = bundle.getString("mail", "error")


            val bundledad = intent.getBundleExtra("bundle1")

            //Text VIew referencia
            val etEdad = findViewById<TextView>(R.id.etEdd)
            val etmail2 = findViewById<TextView>(R.id.etMail2)
            val etNom2 = findViewById<TextView>(R.id.etNombre2)
            val etApe2 = findViewById<TextView>(R.id.etApellid2)
            val nCta2 = findViewById<TextView>(R.id.etNumCta2)
            val sZodiac = findViewById<TextView>(R.id.tvZodiac)
            val sChZodiaco =findViewById<TextView>(R.id.tvZchino)
            val etcarrera = findViewById<TextView>(R.id.tvrCarrera)

            var usuario: User? = null

            usuario = if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable<User>("usuarios", User::class.java)


            }else{
                bundle.getParcelable<User>("usuarios")
            }

            if(usuario!=null) {
                Toast.makeText(
                    this,
                    "Datos recibidos correctamente",
                    Toast.LENGTH_LONG
                ).show()

                etNom2.text = "${usuario.nombre}"
                etApe2.text = "${usuario.apellido}"
                nCta2.text = "${usuario.numCta}"
                etmail2.text = email
                etEdad.text = "${edad2.toString()} años"
                sZodiac.text=  Zod
                sChZodiaco.text=ZodChino
                etcarrera.text=carrera
            }
            }


        }










    }


