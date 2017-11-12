package com.example.hangr

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.pawegio.kandroid.startActivity
import kotlinx.android.synthetic.main.create_account.*
import org.jetbrains.anko.toast

/**
 * Created by Owner on 11/12/2017.
 */
class CreateAccount : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account)

        createSubmit.setOnClickListener {
            savePerson()
            startActivity<ViewPeople>()
        }
    }

    fun savePerson() {
        val fbName = createUsername.text.toString().trim()
        val fbEmail = createPassword.text.toString().trim()
        val fbOnline = true

        if (fbName.isEmpty()) {
            createUsername.error = "Please enter a name."
            return
        } else if (fbEmail.isEmpty()) {
            createPassword.error = "Please enter a password."
        } else {

            val ref = FirebaseDatabase.getInstance().getReference("people")
            val personId = ref.push().key

            val person = Person(personId, fbName, fbEmail, fbOnline)
            ref.child(personId).setValue(person).addOnCompleteListener{
                toast("The text was submitted to the Firebase!")
            }
        }
    }
    }