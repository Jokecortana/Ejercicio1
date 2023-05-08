package com.example.myapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val imageResources = arrayOf(R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5, R.drawable.c6, R.drawable.c7
        , R.drawable.c8, R.drawable.c9, R.drawable.c10, R.drawable.c11, R.drawable.c12, R.drawable.c13, R.drawable.c14, R.drawable.c15)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        val bundle = intent.extras





        // Referencia de text views



        if(bundle!=null){
            val nombre = bundle.getString("nombre", getString(R.string.vacio))
            val apellido = bundle.getString("apellido", getString(R.string.vacio))
            val cuenta = bundle.getInt("cuenta", 0)
            val edad2= bundle.getInt("eddad", 0)
            val Zod = bundle.getString("signoZ",getString(R.string.error))
            val ZodChino = bundle.getString("signoCh", getString(R.string.error))
            val carrera = bundle.getString("carrera", getString(R.string.error))
            val imageResource = bundle.getInt("selectedImageResource", 0)
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
            val imageView = findViewById<ImageView>(R.id.iCarreras)
            imageView.setImageResource(imageResources[imageResource-1])
            var usuario: User? = null

            usuario = if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable<User>("usuarios", User::class.java)


            }else{
                bundle.getParcelable<User>("usuarios")
            }

            if(usuario!=null) {
                Toast.makeText(
                    this,
                    getString(R.string.DatosCorrectos),
                    Toast.LENGTH_LONG
                ).show()

                etNom2.text = "${usuario.nombre}"
                etApe2.text = "${usuario.apellido}"
                nCta2.text = "${usuario.numCta}"
                etmail2.text = email
                etEdad.text = getString(R.string.edad, edad2.toString())
                sZodiac.text=  Zod
                sChZodiaco.text=ZodChino
                etcarrera.text=carrera


            }
            }


        }










    }


