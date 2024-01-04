package lat.pam.mootsadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import lat.pam.formmoots.databinding.ActivityUploadBinding

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val name = binding.uploadName.text.toString()
            val phone = binding.uploadPhone.text.toString()
            val detailorder = binding.uploadDetailOrder.text.toString()
            val status = binding.uploadStatus.text.toString()

            databaseReference = FirebaseDatabase.getInstance().getReference("Users")
            val users = UserData(name,phone,detailorder,status)
            databaseReference.child(phone).setValue(users).addOnSuccessListener {
                binding.uploadName.text.clear()
                binding.uploadPhone.text.clear()
                binding.uploadDetailOrder.text.clear()
                binding.uploadStatus.text.clear()

                Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
                val intent = Intent(this@UploadActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}