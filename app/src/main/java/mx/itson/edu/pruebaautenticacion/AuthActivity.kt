package mx.itson.edu.pruebaautenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        //elementos
        val signUpButton:Button=findViewById(R.id.signUpButton)
        val signInButton:Button=findViewById(R.id.signInButton)
        val emailET:EditText=findViewById(R.id.emailET)
        val pswET:EditText=findViewById(R.id.pswET)

        // Analytics Event
        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString ("message", "Integración de Firebase completa")
        analytics.logEvent("InitScreen", bundle)

        //setup
        setup()
    }

    private fun setup(){
        title="Autenticacion"
        //elementos
        val signUpButton:Button=findViewById(R.id.signUpButton)
        val signInButton:Button=findViewById(R.id.signInButton)
        val emailET:EditText=findViewById(R.id.emailET)
        val pswET:EditText=findViewById(R.id.pswET)
        signUpButton.setOnClickListener{
            if(emailET.text.isNotEmpty() && pswET.text.isNotEmpty() ){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailET.text.toString(),
                    pswET.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful){
                            showHome(it.result?.user?.email?:"")
                        }else{
                            showAlert()
                        }
                    }
            }
        }

        signInButton.setOnClickListener{
            if(emailET.text.isNotEmpty() && pswET.text.isNotEmpty() ){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailET.text.toString(),
                    pswET.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        showHome(it.result?.user?.email?:"")
                    }else{
                        showAlert()
                    }
                }
            }
        }

    }

    private fun showAlert () {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton( "Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email:String){
        val homeIntent= Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(homeIntent)
    }

}