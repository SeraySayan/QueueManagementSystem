package com.example.queuemanagement



import ClassFiles.*
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.example.queuemanagement.databinding.ActivityCustomerActiveQueueBinding
import com.google.firebase.*
import java.sql.Timestamp


class CustomerActiveQueue : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerActiveQueueBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerActiveQueueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myData= Data() // TODO :: Db ye bağla
        myData.func1()
        myData.func2()
        var myQIndex=0
        var totalTimePrevious= myData.myQueues[0].tickets?.last()?.total_waited_time
        for(i in 0..myData.myQueues.size)
        {
            if (totalTimePrevious != null) {
                if(totalTimePrevious > myData.myQueues[i].tickets?.last()?.total_waited_time!!)
                {
                    totalTimePrevious= myData.myQueues[i].tickets?.last()?.total_waited_time
                    myQIndex=i
                }
            }
            else{
                totalTimePrevious=0
            }
        }

        val currentTimestamp = Timestamp(System.currentTimeMillis())
        val emp = myData.myQueues[myQIndex].servedEmp
        val preID= myData.myQueues[myQIndex].tickets?.last()?.id
        val id = 4 // TODO: düzelt
        val processTime = intent.getIntExtra("processTime",0)
        val processType = intent.getStringExtra("processType")
        val totalTime=5 // TODO: bak buraya
        val ticket=
            id?.let {
                processType?.let { it1 ->
                    Ticket() // TODO: düzelt
                }
            }

        if (ticket != null) {
            myData.myQueues[myQIndex].tickets?.add(myData.myQueues[myQIndex].tickets!!.size,ticket)
        }

        binding.LeaveQueueButton.setOnClickListener {

            // TODO: implement leave queue

            val intent = Intent(this, CustomerMenuActivity::class.java)
            startActivity(intent)

        }
        val layout = findViewById<LinearLayout>(R.id.root)

        // Create TextView programmatically.
        val textView = TextView(this)

        // setting height and width
        textView.layoutParams= LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        // setting text
        binding.queueNum.setText(id)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40f)
        textView.setTextColor(Color.BLACK)
        // onClick the text a message will be displayed
        textView.setOnClickListener()
        {
            Toast.makeText(this, id!!,
                Toast.LENGTH_LONG).show()
        }

        // Add TextView to LinearLayout
        layout ?.addView(textView)

        val textView2 = TextView(this)

        // setting height and width
        textView2.layoutParams= LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        // setting text
        binding.remainingTime.setText(totalTime)
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40f)
        textView2.setTextColor(Color.BLACK)
        // onClick the text a message will be displayed
        textView2.setOnClickListener()
        {
            Toast.makeText(this, totalTime,
                Toast.LENGTH_LONG).show()
        }

        // Add TextView to LinearLayout
        layout ?.addView(textView2)

        val textView3 = TextView(this)

        // setting height and width
        textView3.layoutParams= LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        // setting text
        id?.let { textView.setText(it) }
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40f)
        textView3.setTextColor(Color.BLACK)
        // onClick the text a message will be displayed
        textView3.setOnClickListener()
        {
            //Toast.makeText(this, myData.myQueues[myQIndex].tickets?.first()!!.id!!, Toast.LENGTH_LONG).show()
            // TODO: düzelt

        }

        // Add TextView to LinearLayout
        layout ?.addView(textView3)
    }
}


