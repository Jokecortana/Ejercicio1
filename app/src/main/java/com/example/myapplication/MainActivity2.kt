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
        val nom= findViewById<TextView>(R.id.etNombre2)

        val ape= findViewById<TextView>(R.id.etApellid2)

        val cta= findViewById<TextView>(R.id.etNumCta2)



        if(bundle!=null){
            val nombre = bundle.getString("nombre", "")
            val apellido = bundle.getString("apellido", "")
            val cuenta = bundle.getInt("cuenta", 0)


            //Text VIew
            val etNom2 = findViewById<TextView>(R.id.etNombre2)
            val etApe2 = findViewById<TextView>(R.id.etApellid2)
            val nCta2 = findViewById<TextView>(R.id.etNumCta2)




            var usuario: User? = null

            usuario = if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable<User>("usuarios", User::class.java)


            }else{
                bundle.getParcelable<User>("usuarios")
            }

            if(usuario!=null){
                Toast.makeText(this, "El nombre recibido es: ${usuario.nombre}, cuenta ${usuario.numCta} y apellido ${usuario.apellido}", Toast.LENGTH_LONG).show()

                etNom2.text= "${usuario.nombre}"
                etApe2.text = "${usuario.apellido}"
                nCta2.text = "${usuario.numCta}"

            }


        }










    }


}