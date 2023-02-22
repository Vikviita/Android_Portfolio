package com.vikvita.test


import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vikvita.test.databinding.ItemUsersBinding
import com.vikvita.test.models.User

interface UserActionListener{

    fun onUserMove(user:User,moveBy:Int)
    fun onUserDelete(user:User)
    fun onUserDetail(user:User)

}

class userDiffCallback(
    val oldListe:List<User>,
    val newList:List<User>):DiffUtil.Callback(){

    override fun getOldListSize()=oldListe.size

    override fun getNewListSize()=newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
     val oldUser=oldListe[oldItemPosition]

     val newUser=newList[newItemPosition]

        return oldUser.id==newUser.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser=oldListe[oldItemPosition]
        val newUser=newList[newItemPosition]
        return oldUser== newUser
    }

}


class UsersAdapter(private val userActionListener: UserActionListener):
    RecyclerView.Adapter<UsersAdapter.MyViewHolder>(),
    View.OnClickListener {



    var users:List<User> = emptyList()
    set(newValue) {
        val diffResult=DiffUtil.calculateDiff(userDiffCallback(field,newValue))
        field=newValue
        diffResult.dispatchUpdatesTo(this)
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=ItemUsersBinding.inflate(inflater,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int =users.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user=users[position]
        with(holder.binding){
          userNameTextView.text=user.name
          userCompanyTextView.text=if(user.company.isNotBlank()) user.company else "Untitled"

            moreImageView.setOnClickListener(this@UsersAdapter)
            root.setOnClickListener(this@UsersAdapter)

            moreImageView.tag=user
            holder.itemView.tag=user

            if(user.photo.isNotBlank()){
                Glide.with(photoImageView.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.baseline_account_circle_24)
                    .error(R.drawable.baseline_account_circle_24)
                    .into(photoImageView)
            }
            else{
                photoImageView.setImageResource(R.drawable.baseline_account_circle_24)
            }
        }
    }

    class MyViewHolder(val binding: ItemUsersBinding):RecyclerView.ViewHolder(binding.root)

private fun showPopUpMenu(view: View){
    val user=view.tag as User
val popupMenu=PopupMenu(view.context,view)
    val position=users.indexOfFirst { it.id==user.id }
    popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE,"Move UP").apply {
        isEnabled=position>0
    }
    popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE,"Move Down").apply {
        isEnabled=position< users.size-1
    }
    popupMenu.menu.add(0, ID_DELETE, Menu.NONE,"Delete")


popupMenu.setOnMenuItemClickListener {
    when(it.itemId){
        ID_MOVE_UP-> userActionListener.onUserMove(user,-1)

        ID_DELETE-> userActionListener.onUserDelete(user)

        ID_MOVE_DOWN-> userActionListener.onUserMove(user,1)


    }
    return@setOnMenuItemClickListener true
}
    popupMenu.show()
}


    override fun onClick(view: View) {
        val user=view.tag as User
        when(view.id){
            R.id.moreImageView->{
                showPopUpMenu(view)
            }
            else->{
                userActionListener.onUserDetail(user)
            }
        }
    }


    companion object{
private const val ID_DELETE=0
private const val ID_MOVE_DOWN=1
private const val ID_MOVE_UP=2


    }
}