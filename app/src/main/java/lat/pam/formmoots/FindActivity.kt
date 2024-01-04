package lat.pam.mootsadmin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import lat.pam.formmoots.databinding.ActivityFindBinding

class FindActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFindBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            val searchPhone = binding.searchPhone.text.toString().trim()
            if (searchPhone.isNotEmpty()) {
                readData(searchPhone)
            } else {
                Toast.makeText(this, "Please enter the phone number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(phone: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(phone).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snapshot = task.result
                if (snapshot != null && snapshot.exists()) {
                    val name = snapshot.child("name").value.toString()
                    val detailorder = snapshot.child("detailorder").value.toString()
                    val status = snapshot.child("status").value.toString()

                    Toast.makeText(this, "Results Found", Toast.LENGTH_SHORT).show()
                    binding.searchPhone.text.clear()
                    binding.readName.text = name
                    binding.readdetailorder.text = detailorder
                    binding.readStatus.text = status

                    // Menunda selama 5 detik sebelum kembali ke MainActivity
                    Handler(Looper.getMainLooper()).postDelayed({
                        val intent = Intent(this@FindActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }, 5000)
                } else {
                    Toast.makeText(this, "Phone number does not exist", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
