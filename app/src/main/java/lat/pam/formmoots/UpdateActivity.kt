package lat.pam.mootsadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import lat.pam.formmoots.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.updateButton.setOnClickListener {
            val referencePhone = binding.referencePhone.text.toString()
            val updateName = binding.updateName.text.toString()
            val updateDetailOrder = binding.updateDetailOrder.text.toString()
            val updateStatus = binding.updateStatus.text.toString()
            updateData(referencePhone,updateName,updateDetailOrder,updateStatus)
        }
    }
    private fun updateData(phone: String, name: String, detailorder: String, status: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val user = mapOf<String,String>(
            "name" to name,
            "detailorder" to detailorder,
            "status" to status
        )
        databaseReference.child(phone).updateChildren(user).addOnSuccessListener {
            binding.referencePhone.text.clear()
            binding.updateName.text.clear()
            binding.updateDetailOrder.text.clear()
            binding.updateStatus.text.clear()
            Toast.makeText(this,"Successfully Updated",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@UpdateActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to Update",Toast.LENGTH_SHORT).show()
        }
    }
}