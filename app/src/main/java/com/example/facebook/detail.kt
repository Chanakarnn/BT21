package com.example.facebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager

/**
 * A simple [Fragment] subclass.
 */
class detail : Fragment() {

    var image: String? = null
    var title: String? = null
    var description: String? = null

    fun newInstance(image: String, title: String, description: String): detail {
        val fragment = detail()
        val bundle = Bundle()
        bundle.putString("image", image);
        bundle.putString("title", title);
        bundle.putString("description", description);
        fragment.setArguments(bundle)
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            this.image = bundle.getString("image").toString()
            this.title = bundle.getString("title").toString()
            this.description = bundle.getString("description").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_detail, container, false)
        val image_view: ImageView = view.findViewById(R.id.imgV)
        val title_view: TextView = view.findViewById(R.id.tv_name)
        val description_view: TextView = view.findViewById(R.id.tv_description)
        title_view.text = this.title
        description_view.text = this.description
        Glide.with(activity!!.baseContext).load(image).into(image_view)

        val login_button8 = view.findViewById(R.id.login_button8) as Button
        login_button8.setOnClickListener{

            LoginManager.getInstance().logOut()
            activity!!.supportFragmentManager.popBackStack()

        }
        return view
    }
}