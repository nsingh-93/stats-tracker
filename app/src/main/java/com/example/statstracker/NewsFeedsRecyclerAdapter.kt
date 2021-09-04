package com.example.statstracker

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*


class NewsFeedRecyclerAdapter internal constructor(
    listNews: List<News>,
    context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listNews: List<News>
    private val context: Context
    private var width: Int
    private var timestampWidth = 0
    private val hourDateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    //private SimpleDateFormat hour24DateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private val dayDateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
    override fun getItemViewType(position: Int): Int {
        var showDayDivider = false
        if (position < listNews.size - 1) {
            val lastTimestamp: Long =
                (listNews[position + 1]).getTime().toLong()
            val currentTimestamp: Long =
                (listNews[position]).getTime().toLong()
            val lastDay = dayDateFormat.format(lastTimestamp)
            val currentDay = dayDateFormat.format(currentTimestamp)
            if (lastDay != null && lastDay != currentDay) {
                showDayDivider = true
            }
        } else {
            showDayDivider = true
        }
        return if (listNews[position].getFolderName().equals("sent")) {
            if (showDayDivider) VIEW_TYPE_USER_DAYBREAK else VIEW_TYPE_USER_DEFAULT
        } else {
            if (showDayDivider) VIEW_TYPE_OTHER_DAYBREAK else VIEW_TYPE_OTHER_DEFAULT
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_USER_DEFAULT -> {
            }
            else -> {
                val defaultView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_user_default, parent, false)
                UserDefaultHolder(defaultView)
            }
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message: News = listNews[position]
        val userName: String = message.getDisplayName()
        val folder: String = message.getFolderName()
        val timestamp: String = message.getTime()
        val date = Date(timestamp.toLong())
        val messageDate = hourDateFormat.format(date)
        val currentDay = dayDateFormat.format(date)
        val messageText: String = message.getMsg()

        //boolean showDayDivider = false;
        var nameVisibility = View.VISIBLE
        var timestampVisibility = View.VISIBLE

/*        if (position > 0){
            long lastTimestamp = Long.parseLong(Objects.requireNonNull(listMessages.get(position - 1)).getTime());
            String lastDay = dayDateFormat.format(lastTimestamp);

            if (lastDay != null && !lastDay.equals(currentDay)) {
                showDayDivider = true;
            }
        } else {
            showDayDivider = true;
        }*/if (position > 0) {
            val nextUserName: String =
                Objects.requireNonNull<Any>(listNews[position - 1]).getDisplayName()
            val nextTimestamp: Long =
                Objects.requireNonNull<Any>(listNews[position - 1]).getTime().toLong()
            val nextDay = dayDateFormat.format(nextTimestamp)
            if (folder == "inbox") {
                if (nextUserName != null && nextUserName == userName) {
                    nameVisibility = View.GONE
                    if (nextTimestamp - Objects.requireNonNull(timestamp).toLong() < 120000
                        && nextDay == currentDay
                    ) {
                        timestampVisibility = View.INVISIBLE
                    } else if (nextDay == currentDay) {
                        nameVisibility = View.INVISIBLE
                    }
                    if (nextTimestamp - Objects.requireNonNull(timestamp).toLong() < 120000) {
                        timestampVisibility = View.INVISIBLE
                    } else {
                        nameVisibility = View.INVISIBLE
                    }
                }
            } else {
                val nextFolderName: String = Objects.requireNonNull<Any>(
                    listNews[position - 1]
                ).getFolderName()
                if (nextFolderName != null && nextFolderName == folder) {
                    nameVisibility = View.GONE
                    if (nextTimestamp - Objects.requireNonNull(timestamp).toLong() < 120000
                        && nextDay == currentDay
                    ) {
                        timestampVisibility = View.INVISIBLE
                    } else if (nextDay == currentDay) {
                        nameVisibility = View.INVISIBLE
                    }
                } else {
                    nameVisibility = View.INVISIBLE
                }
            }
        }
        when (holder.itemViewType) {
            VIEW_TYPE_USER_DEFAULT -> (holder as UserDefaultHolder).bind(
                message,
                messageDate,
                messageText,
                timestampVisibility,
                nameVisibility
            )
        }
    }

    //ViewHolders
    private inner class UserDefaultHolder internal constructor(v: View) :
        RecyclerView.ViewHolder(v) {
        var photoImageView: ImageView
        var messageTextView: TextView
        var authorTextView: TextView
        var txtTimestamp: TextView
        var dayTimestamp: TextView
        var textBubble: LinearLayout
        var dayTimestampLayout: ConstraintLayout
        fun bind(
            message: Sms,
            messageDate: String?,
            messageText: String?,
            timestampVisibility: Int,
            nameVisibility: Int
        ) {
            txtTimestamp.text = messageDate
            messageTextView.text = messageText
            setInitialMaxWidth(txtTimestamp, messageTextView)
            authorTextView.visibility = nameVisibility

            //Set the max width of the text bubbles based on the screen width and the size of the timestamp. There's probably
            // a much more efficient way to do this! Checking the timeStampWidth == 0 helps but a more efficient method is required.
            if (timestampWidth == 0) {
                val vto = txtTimestamp.viewTreeObserver
                vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        timestampWidth =
                            txtTimestamp.width + txtTimestamp.paddingEnd + txtTimestamp.paddingStart
                        messageTextView.maxWidth = width - timestampWidth
                        txtTimestamp.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            } else {
                messageTextView.maxWidth = width - timestampWidth
            }
            txtTimestamp.visibility = timestampVisibility
            selectListItem(message, messageTextView, textBubble)
        }

        init {
            photoImageView = v.findViewById(R.id.photoImageView)
            messageTextView = v.findViewById(R.id.messageTextView)
            authorTextView = v.findViewById(R.id.nameTextView)
            txtTimestamp = v.findViewById(R.id.txtTimestamp)
            textBubble = v.findViewById(R.id.textBubble)
            dayTimestamp = v.findViewById(R.id.dayTimestamp)
            dayTimestampLayout = v.findViewById(R.id.dayTimestampLayout)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return listNews.size
    }

    private fun selectListItem(message: Sms, tv: TextView, textBubble: LinearLayout) {
        val isUser: Boolean = message.getFolderName().equals("sent")
        if (!message.isSelected()) {
            if (isUser) {
                textBubble.background = context.getDrawable(R.drawable.text_bubble_user)
            } else {
                textBubble.background = context.getDrawable(R.drawable.text_bubble_other)
            }
            tv.setTextColor(Color.parseColor("#000000"))
        } else {
            if (isUser) {
                textBubble.background = context.getDrawable(R.drawable.text_bubble_user_selected)
            } else {
                textBubble.background = context.getDrawable(R.drawable.text_bubble_other_selected)
            }
            tv.setTextColor(context.resources.getColor(R.color.colorTitle))
        }
    }

    private fun setInitialMaxWidth(txtTimestamp: TextView, messageTextView: TextView) {
        //Set the max width of the text bubbles based on the screen width and the size of the timestamp. There's probably
        // a much more efficient way to do this! Checking the timeStampWidth == 0 helps but a more efficient method is required.
        if (timestampWidth == 0) {
            val vto = txtTimestamp.viewTreeObserver
            vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    timestampWidth =
                        txtTimestamp.width + txtTimestamp.paddingEnd + txtTimestamp.paddingStart
                    messageTextView.maxWidth = width - timestampWidth
                    txtTimestamp.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        } else {
            messageTextView.maxWidth = width - timestampWidth
        }
    }

    fun setMaxWidth(width: Int) {
        this.width = width
        notifyDataSetChanged()
    }

    companion object {
        //View Types
        private const val VIEW_TYPE_USER_DEFAULT = 0
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    init {
        this.listNews = listNews
        this.context = context
    }
}