package com.example.myapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.callback.UserDiffCallBack
import com.example.myapplication.data.User
import com.example.myapplication.module.GlideApp
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.view.*

class UserItemAdapter(private val context: Context) :
    ListAdapter<User, UserItemAdapter.CustomViewHolder>(UserDiffCallBack) {

    var selectItem: SelectItem? = null

    interface SelectItem {
        fun selectItem(position: Int, type: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        if (currentList.size > position) {
            holder.bindView(context, position, getItem(position), selectItem)
        }
    }

    class CustomViewHolder(override val containerView: View?) :
        RecyclerView.ViewHolder(containerView!!), LayoutContainer {

        fun bindView(
            context: Context,
            position: Int,
            user: User,
            selectItem: SelectItem?
        ) {

            with(itemView) {

                context.let {

                    clItem.tag = position
                    clItem.setOnClickListener {
                        val pos = it.tag.toString().toInt()
                        selectItem?.selectItem(pos, "select")
                    }

                    val name = user.name
                    tvName.text = name

                    val url = user.htmlUrl
                    tvUrl.text = url

                    GlideApp.with(context).load(user.avatarUrl)
                        .placeholder(R.drawable.img_profile_default)
                        .error(R.drawable.img_profile_default)
                        .fallback(R.drawable.img_profile_default)
                        .into(ivPhoto)
                }
            }
        }
    }

    fun updateUserList(newList: List<User>) = submitList(newList)

    fun getUser(position: Int): User = currentList[position]
}