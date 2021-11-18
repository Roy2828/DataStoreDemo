package com.roy.datastoredemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.asLiveData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var userManager: StudentManager
    var age ="0"
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userManager = StudentManager(dataStore)
        buttonSave()
        observeData()
    }


    private fun observeData() {
      /*  GlobalScope.launch {
            //flow
            userManager.studentAgeFlow.collect {

            }

        }*/

        GlobalScope.launch {
            var str = userManager.readAgeString()
            Log.e("aaa",str)
        }


        userManager.studentAgeFlow.asLiveData().observe(this, {
            if (it != null) {  //数据只有发生改变才可以回调  如果重复的值存取 是不会走的
                age = it
               Toast.makeText(this@MainActivity,"$age",0).show()
            }
        })

        userManager.studentNameFlow.asLiveData().observe(this, {
            if (it != null) {
                name = it
              Toast.makeText(this@MainActivity,name,0).show()
            }

        })


    }

    private fun buttonSave() {

        btn_save.setOnClickListener {
            name = et_fname.text.toString()
            age = et_age.text.toString()
            GlobalScope.launch {
                userManager.storeUser(age, name)
            }
        }


    }
}