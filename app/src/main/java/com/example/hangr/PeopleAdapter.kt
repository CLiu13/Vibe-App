package com.example.hangr

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.content.ContextCompat.startActivity
import android.widget.*

/**
 * Created by Owner on 11/10/2017.
 */
class PeopleAdapter(val mCtx: Context, val layoutResId: Int, val peopleList: List<Person>) : ArrayAdapter<Person>(mCtx, layoutResId, peopleList) {
    /*
     * Context tells on which activity the stuff is going to happen.
     * layoutResId - Which xml file under layout do I need to use?
     * heroList has a type of List. Within its less and greater than signs, the class with the
       original Firebase uploading stuff goes in there.
     */

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx);
        val view : View = layoutInflater.inflate(layoutResId, null) //The view inflates the inflater.
        val textViewName = view.findViewById<TextView>(R.id.textViewName);
        val toggleButton = view.findViewById<ToggleButton>(R.id.toggleButton)
        val textViewOnline = view.findViewById<TextView>(R.id.textViewOnline)

        val people = peopleList[position] //This connects to the People class from the ArrayAdapter.
        textViewName.text = "${people.fbName}"; //This is the text that shows in the actual TextView from the database.
        if (people.fbOnline) {
            textViewOnline.text = "✔"
        } else {
            textViewOnline.text = "❌"
        }

//        val buttonUpdate = view.findViewById<Button>(R.id.buttonUpdate)

        textViewName.setOnClickListener() {

            val intent: Intent = Intent(mCtx, Chat::class.java)
            intent.putExtra("id", people.id); intent.putExtra("name", people.fbName);
            startActivity(mCtx, intent, null)

        }

//        buttonUpdate.setOnClickListener() {
//            showUpdateDialog(people) // This is your people variable that has the position of the list element.
//        }

        return view;
    }

    fun showUpdateDialog(people: Person) {
        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.layout_update_people, null)

        val editName = view.findViewById<EditText>(R.id.editName)
        val editEmail = view.findViewById<EditText>(R.id.editEmail)

        editName.setText(people.fbName)
        editEmail.setText(people.fbEmail)

        builder.setView(view)

        builder.setPositiveButton("Update") { p0, p1 ->
            val dbPerson = FirebaseDatabase.getInstance().getReference("people")

            val name = editName.text.toString().trim();
            val email = editEmail.text.toString().trim();
            val online = true;

            if (name.isEmpty()) {
                editName.error = "Please enter a name."
                editName.requestFocus() //I guess this puts the cursor back in this field if the error arises.
                return@setPositiveButton
            } else if (email.isEmpty()){
                editEmail.error = "Please enter an email."
                editEmail.requestFocus()
                return@setPositiveButton
            } else {
                val person = Person(people.id, name, email, online)
                dbPerson.child(person.id).setValue(person) //This basically says query the database, and set the values of the existing id to these new ones.
            }
        }

        builder.setNegativeButton("No") { p0, p1 ->
        }

        val alert = builder.create()
        alert.show()
    }
}