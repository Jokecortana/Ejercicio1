package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.parcelize.Parcelize



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


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun click(view: View) {

        val intent = Intent(this, MainActivity2::class.java)
        //nombre
        val nombreT = findViewById<EditText>(R.id.etNombre)

        //apellido
        val apellidoT = findViewById<EditText>(R.id.etApellido)

        //numero de cuenta
        val numCtaT = findViewById<EditText>(R.id.etNumCta)


        val nombre = nombreT.text.toString()
        val apellido= apellidoT.text.toString()
        val numCta = numCtaT.text.toString().toInt()

        val user = User(nombre,apellido,numCta)



        val bundle = Bundle()
        /*paquete
        bundle.putString("nombre", user.nombre)
        bundle.putString("nombre", user.apellido)
        bundle.putInt("nombre", user.numCta)
*/

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