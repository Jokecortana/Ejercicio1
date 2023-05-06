package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import java.util.*


data class User (var nombre: String?, var apellido: String?, var numCta: Int): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(apellido)
        parcel.writeInt(numCta)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

//MAIN ACTIVITY
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var edadB = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Spiner
        ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item).also { adapter -> adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter= adapter
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?,
            view: View?, position: Int, id: Long) {
                Log.d("LOGTAG", "Su carrera es: $position")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }


        }



    }

//Date picker

    fun onClickScheduleDate(v:View) {
        val intent = Intent(this, MainActivity2::class.java)
        val etScheduleDate = findViewById<EditText>(R.id.etFecha)
        val etEdd = findViewById<TextView>(R.id.etEdd)
        val selectedCalendar = Calendar.getInstance()
        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val day = selectedCalendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
            // Obtener la fecha de nacimiento del usuario
            val fechaNacimiento = Calendar.getInstance().apply {
                set(Calendar.YEAR, y)
                set(Calendar.MONTH, m)
                set(Calendar.DAY_OF_MONTH, d)

            }

            etScheduleDate.setText("$y, $m-$d")
            if (esFechaValida(fechaNacimiento)) {
            // Calcular la edad del usuario
            var edad = obtenerEdad(fechaNacimiento.timeInMillis)

            // Comprobar si el usuario tiene más de 18 años
            if (edad >= 18) {
                Toast.makeText(v.context, "Correcto", Toast.LENGTH_SHORT).show()
                edadB=edad
            } else {
                Toast.makeText(v.context, "Debes ser mayor de 18 años para continuar.", Toast.LENGTH_SHORT).show()
                etEdd.text = ""

            }


            } else {
                // La fecha seleccionada no es válida, mostrar un mensaje y permitir seleccionar una nueva fecha
                Toast.makeText(v.context, "Debe ser mayor de 18 años para usar la aplicacion", Toast.LENGTH_SHORT).show()
            }




        }


        DatePickerDialog(this, listener, year, month, day).show()


    }


    // Función para obtener la edad a partir de una fecha de nacimiento en milisegundos
    private fun obtenerEdad(fechaNacimiento: Long): Int {
        val hoy = Calendar.getInstance().timeInMillis
        val diff = hoy - fechaNacimiento
        val edadInMillis = diff
        return ((edadInMillis / 1000) / 60 / 60 / 24 / 365).toInt()
    }

    private fun esFechaValida(fecha: Calendar): Boolean {
        val hoy = Calendar.getInstance()
        return fecha < hoy
    }


    fun click(view: View?) {



        val intent = Intent(this, MainActivity2::class.java)
        //nombre EDIT TEXT
        val nombreT = findViewById<EditText>(R.id.etNombre)

        //apellido
        val apellidoT = findViewById<EditText>(R.id.etApellido)

        //numero de cuenta
        val numCtaT = findViewById<EditText>(R.id.etNumCta)

        //correo edit text
        val emaileT = findViewById<EditText>(R.id.etMail)


        //obtencion de datos ingresados

        val nombre = nombreT.text.toString()
        val apellido= apellidoT.text.toString()
        val numCta = numCtaT.text.toString().toInt()

        val email = emaileT.text.toString()

        //validacion email
        if (isValidEmail(email)) {
            // Realizar alguna operación con el correo electrónico
        } else {
            // Mostrar un mensaje de error al usuario
            Toast.makeText(this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show()
        }






        //objeto
        val user = User(nombre,apellido,numCta)



        val bundle = Bundle()

        bundle.putString("mail", email)

        //pasando objeto parcelable
        bundle.putParcelable("usuarios", user)
        bundle.putInt("eddad", edadB)

        intent.putExtras(bundle)

        startActivity(intent)

        //Toast.makeText(this, "nu nombre es ${user.nombre} ${user.apellido} y su numero de cuenta es: ${user.numCta}", Toast.LENGTH_LONG).show()












    }

}

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,}")
    return emailRegex.matches(email)
}