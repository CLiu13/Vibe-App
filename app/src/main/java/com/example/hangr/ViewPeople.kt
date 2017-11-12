package com.example.hangr

import android.os.Bundle
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.view_people.*
import org.jetbrains.anko.*

/**
 * Created by Owner on 11/10/2017.
 */
class ViewPeople : AppCompatActivity() {

    var peopleList: MutableList<Person> = mutableListOf() //This list stores all of the database value entries.
    val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("people")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_people)

        //startActivity<MoreInformation>("id" to , "name" to "Test")

        toggleButton.setOnClickListener {
            if (toggleButton.isChecked) {
                toggleButton.isChecked = false

            }
        }

        ref.addValueEventListener(object: ValueEventListener {
            //Once you get into these functions, p0 is your new variable that you're working with.
            override fun onCancelled(p0: DatabaseError?) {
                alert("There was an error connecting to the database. Try checking your connection.")
            }

            override fun onDataChange(p0: DataSnapshot?) {
                //ref contains everything in the database node.
                peopleList.clear() //This clears the list so when new data comes up, it doesn't keep the old data in the list.

                if (p0!!.exists()) { //This means basically "if some data exists on the database."
                    for(p in p0.children) {
                        val person = p.getValue(Person::class.java) //The getValue method takes a Java class.
                        peopleList.add(person!!);
                    }

                    //This connects to the layout class.
                    val adapter = PeopleAdapter(this@ViewPeople, R.layout.people, peopleList)
                    listView.adapter = adapter
                }
            }
        })
    }
}