package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract.CommonDataKinds.Email
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import java.util.*
import kotlin.coroutines.coroutineContext


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
    private var edadB = 0
    var signo = ""
    var signochino = ""
    private var selectedOption: String = ""
    private var numCtaB = 0
    private var emailB = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Asignar el método de validación al botón "Continuar"

        //Spiner
        ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item
                )
                binding.spinner.adapter = adapter
            }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?, position: Int, id: Long
            ) {
                selectedOption = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

    }

//Date picker

    //validacion de campos
    private fun camposValidos(): Boolean {
        val nombreval = binding.etNombre.text.toString()
        val apellidoval = binding.etApellido.text.toString()
        val numCtaval = binding.etNumCta.text.toString()

        return nombreval.isNotEmpty() && apellidoval.isNotEmpty() && numCtaval.isNotEmpty()
    }
    //fin de validacion de campos


    fun onClickScheduleDate(v: View) {
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
            val fechaActual = Calendar.getInstance()

            if (fechaNacimiento.before(fechaActual)) {
                etScheduleDate.setText("$d/$m/$y")
                if (esFechaValida(fechaNacimiento)) {
                    // Calcular la edad
                    val edad = obtenerEdad(fechaNacimiento.timeInMillis)

                    // Comprobar 18 años
                    if (edad >= 18) {
                        Toast.makeText(v.context, getString(R.string.correcto), Toast.LENGTH_SHORT)
                            .show()
                        edadB = edad
                        val signoZodiacal = obtenerSignoZodiacal(fechaNacimiento)
                        signo = signoZodiacal
                        val signoZodiacalChino = obtenerSignoZodiacalChino(fechaNacimiento)
                        signochino = signoZodiacalChino
                    } else {
                        Toast.makeText(
                            v.context,
                            getString(R.string.mayor),
                            Toast.LENGTH_SHORT
                        ).show()
                        etEdd.text = getString(R.string.vacio)
                    }
                } else {
                    // La fecha seleccionada no es válida
                    Toast.makeText(
                        v.context, getString(R.string.mayor),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                // La fecha seleccionada es posterior a la fecha actual
                Toast.makeText(
                    v.context,
                    getString(R.string.fechamayor),
                    Toast.LENGTH_SHORT
                ).show()
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


    //signo zodiacal
    fun obtenerSignoZodiacal(fechaNacimiento: Calendar): String {
        val mes = fechaNacimiento.get(Calendar.MONTH)
        val dia = fechaNacimiento.get(Calendar.DAY_OF_MONTH)
        when (mes) {
            Calendar.JANUARY -> {
                if (dia <= 19) return getString(R.string.capricornio)
                else return getString(R.string.acuario)
            }
            Calendar.FEBRUARY -> {
                if (dia <= 18) return getString(R.string.acuario)
                else return getString(R.string.piscis)
            }
            Calendar.MARCH -> {
                if (dia <= 20) return getString(R.string.piscis)
                else return getString(R.string.aries)
            }
            Calendar.APRIL -> {
                if (dia <= 19) return getString(R.string.aries)
                else return getString(R.string.tauro)
            }
            Calendar.MAY -> {
                if (dia <= 20) return getString(R.string.tauro)
                else return getString(R.string.geminis)
            }
            Calendar.JUNE -> {
                if (dia <= 20) return getString(R.string.geminis)
                else return getString(R.string.cancer)
            }
            Calendar.JULY -> {
                if (dia <= 22) return getString(R.string.cancer)
                else return getString(R.string.leo)
            }
            Calendar.AUGUST -> {
                if (dia <= 22) return getString(R.string.leo)
                else return getString(R.string.virgo)
            }
            Calendar.SEPTEMBER -> {
                if (dia <= 22) return getString(R.string.virgo)
                else return getString(R.string.libra)
            }
            Calendar.OCTOBER -> {
                if (dia <= 22) return getString(R.string.libra)
                else return getString(R.string.escorpio)
            }
            Calendar.NOVEMBER -> {
                if (dia <= 21) return getString(R.string.escorpio)
                else return getString(R.string.sagitario)
            }
            Calendar.DECEMBER -> {
                if (dia <= 21) return getString(R.string.sagitario)
                else return getString(R.string.capricornio)
            }
            else -> return getString(R.string.vacio)
        }
    }


    //fin signo zodiacal

    //signo zodiacal chino
    private fun obtenerSignoZodiacalChino(fechaNacimiento: Calendar): String {
        val año = fechaNacimiento.get(Calendar.YEAR)
        val signosZodiacalesChinos = resources.getStringArray(R.array.signos_zodiacales_chinos)
        return signosZodiacalesChinos[(año - 1900) % 12]
    }
    //fin signo zodiacal chino

    fun click(view: View?) {
        val nombre = binding.etNombre.text.toString()
        val apellido = binding.etApellido.text.toString()
        val numCta = binding.etNumCta.text.toString()
        val email = binding.etMail.text.toString()

        if (nombre.isEmpty() || apellido.isEmpty() || numCta.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, getString(R.string.errorgeneral), Toast.LENGTH_LONG).show()
            return
        }

        if (isValidEmail(email)) {
            Toast.makeText(this, getString(R.string.correovalido), Toast.LENGTH_SHORT).show()
            emailB = 0

            if (numCta.length != 9) {
                numCtaB = 10
                Toast.makeText(this, getString(R.string.errorcta), Toast.LENGTH_SHORT).show()
                return
            }

            val intent = Intent(this, MainActivity2::class.java)

            val user = User(nombre, apellido, numCta.toInt())

            val bundle = Bundle()

            bundle.putString("mail", email)

            bundle.putParcelable("usuarios", user)
            bundle.putInt("eddad", edadB)
            bundle.putString("signoZ", signo)
            bundle.putString("carrera", selectedOption)

            bundle.putString("signoCh", signochino)

            intent.putExtras(bundle)

            startActivity(intent)

        } else {
            Toast.makeText(this, getString(R.string.errorcorreo), Toast.LENGTH_SHORT).show()
            emailB = 1
        }
    }
}
    private fun isValidEmail(mail: CharSequence) =
        (!TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches())
