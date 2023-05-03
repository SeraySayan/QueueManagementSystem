package com.example.queuemanagement
import java.time.Duration
import ClassFiles.Branchs
import ClassFiles.Customer
import ClassFiles.Ticket
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import java.time.Instant

class CustomerTicketHistoryAdapter(private val ticketList:ArrayList<Ticket>, //val user_uid:String //probably no need
                                   private val context: Context): RecyclerView.Adapter<CustomerTicketHistoryAdapter.CustomerViewHolder>() {

    class CustomerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val tvName: TextView = itemView.findViewById(R.id.textView1)
        val tvSurname: TextView = itemView.findViewById(R.id.textView2)
        val tvProcessType: TextView =itemView.findViewById(R.id.textView3)
        val tvWaitTime: TextView =itemView.findViewById(R.id.textView7)
        val tvProcessTime : TextView = itemView.findViewById(R.id.textView8)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            println("zaaaaaaaaaaaaaaaaaaaaa\n")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_ticket_history,parent,false)
        return CustomerViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.tvName.text = "Branch: ${ticketList[position].branch_name}"
        holder.tvSurname.text = "Process Type: ${ticketList[position].processType}"
        holder.tvProcessType.text = "Employee: ${ticketList[position].served_employee}"
        holder.tvWaitTime.text = "Waited time: ${ticketList[position].total_waited_time}"
        holder.tvProcessTime.text = "Process Time: ${ticketList[position].total_process_time}"
        //holder.tvWaitTime.text = "Waited time: za"
        // holder.tvID.text =branchlist[position].id.toString()
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }




    fun formatDuration(duration: Duration): String {
        val minutes = duration.toMinutes()
        val seconds = duration.minusMinutes(minutes).seconds
        val builder = StringBuilder()
        if (minutes > 0) {
            builder.append("$minutes" + "m ")
        }
        builder.append("$seconds" + "s")
        return builder.toString()
    }


    fun formatTimestamp(timestamp: Timestamp): String {
        val instant = Instant.ofEpochSecond(timestamp.seconds)
        val duration = Duration.between(instant, Instant.now())
        return formatDuration(duration)
    }


}