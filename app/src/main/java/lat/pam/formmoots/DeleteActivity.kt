package lat.pam.mootsadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import lat.pam.formmoots.databinding.ActivityDeleteBinding

class DeleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeleteBinding
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.deleteButton.setOnClickListener {
            val phone = binding.deletePhone.text.toString()
            if (phone.isNotEmpty())
                deleteData(phone)
            else
                Toast.makeText(this, "Please enter the phone number", Toast.LENGTH_SHORT).show()
        }
    }
    private fun deleteData(phone: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(phone).removeValue().addOnSuccessListener {
            binding.deletePhone.text.clear()
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@DeleteActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Unable to delete", Toast.LENGTH_SHORT).show()
        }
    }
}