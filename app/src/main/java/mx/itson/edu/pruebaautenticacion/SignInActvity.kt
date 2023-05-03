package mx.itson.edu.pruebaautenticacion


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mx.itson.edu.pruebaautenticacion.databinding.ActivitySignInBinding

class SignInActvity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        /* var email: EditText=findViewById<EditText?>(R.id.emailEditText)
         var psw: EditText=findViewById(R.id.passwordEditText)
         var btnL: Button = findViewById(R.id.signInAppCompatButton)
         btnL.setOnClickListener{
             SignIn(email.text.toString(), psw.text.toString())
         }*/
        binding.signInAppCompatButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val psw = binding.passwordEditText.text.toString()
            when {
                email.isEmpty() || psw.isEmpty() -> {
                    Toast.makeText(baseContext, "Mail o contraseña incorrecta", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    SignIn(email, psw)
                }
            }
        }

    }

    private fun SignIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    /*Toast.makeText(
                        baseContext, "Acceso concedido",
                        Toast.LENGTH_SHORT
                    ).show()*/
                    reload()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //updateUI(null)
                }
            }
    }

    private fun reload() {
        val intent = Intent(this, Bienvenido::class.java)
        this.startActivity(intent)
    }

    private fun updateUI(user: FirebaseUser?) {

    }
}