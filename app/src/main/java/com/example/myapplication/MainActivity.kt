package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.parcelize.Parcelize
import java.text.FieldPosition
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
        val etScheduleDate = findViewById<EditText>(R.id.etFecha)
        val selectedCalendar = Calendar.getInstance()
        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val day = selectedCalendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener{datePicker, y, m, d -> etScheduleDate.setText("$y-$m-$d") }

        DatePickerDialog(this, listener, year, month, day).show()

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
        /*paquete
        bundle.putString("nombre", user.nombre)
        bundle.putString("nombre", user.apellido)
        bundle.putInt("nombre", user.numCta)
*/
        bundle.putString("mail", email)

        //pasando objeto parcelable
        bundle.putParcelable("usuarios", user)


        intent.putExtras(bundle)

        startActivity(intent)

        //Toast.makeText(this, "nu nombre es ${user.nombre} ${user.apellido} y su numero de cuenta es: ${user.numCta}", Toast.LENGTH_LONG).show()
    }

}

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,}")
    return emailRegex.matches(email)
}