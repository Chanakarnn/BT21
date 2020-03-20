package layout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.facebook.R
import com.example.facebook.detail
import org.json.JSONArray
import pl.droidsonroids.gif.GifImageView


class MyRecyclerAdapter(context: Context, fm: FragmentManager, val dataSource: JSONArray) : RecyclerView.Adapter<MyRecyclerAdapter.Holder>() {
    private val thiscontext : Context = context
    private val fragment: FragmentManager = fm

    class Holder (view : View) : RecyclerView.ViewHolder(view) {
        private val View = view;

        lateinit var layout : LinearLayout
        lateinit var titleTextView: TextView
        lateinit var detailTextView: TextView
        lateinit var image: GifImageView

        fun Holder(){

            layout = View.findViewById<View>(R.id.recyview_layout) as LinearLayout
            titleTextView = View.findViewById<View>(R.id.tv_name) as TextView
            detailTextView = View.findViewById<View>(R.id.tv_description) as TextView
            image = View.findViewById<View>(R.id.imgV) as GifImageView

        }
    }


    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.recy_layout, parent, false))
    }


    override fun getItemCount(): Int {
        return dataSource.length()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.Holder()

        holder.titleTextView.setText( dataSource.getJSONObject(position).getString("title").toString() )
        holder.detailTextView.setText( dataSource.getJSONObject(position).getString("servings").toString() )

        Glide.with(thiscontext)
            .load(dataSource.getJSONObject(position).getString("image").toString())
            .into(holder.image)

        holder.layout.setOnClickListener{

            Toast.makeText(thiscontext,holder.titleTextView.text.toString(),Toast.LENGTH_SHORT).show()
            val image:String = dataSource.getJSONObject(position).getString("image").toString()
            val title:String = dataSource.getJSONObject(position).getString("title").toString()
            val description:String = dataSource.getJSONObject(position).getString("description").toString()

            val detail = detail().newInstance(image,title,description)
            val transaction : FragmentTransaction = fragment!!.beginTransaction()
            transaction.replace(com.example.facebook.R.id.contentContainer,detail,"detail")
            transaction.addToBackStack("detail")
            transaction.commit()
        }


    }



}
