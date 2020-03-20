package com.example.facebook


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager

/**
 * A simple [Fragment] subclass.
 */
class profile : Fragment() {

    var PhotoURL : String = ""
    var Name : String = ""

    fun newInstance(url: String,name : String): profile {

        val profile = profile()
        val bundle = Bundle()
        bundle.putString("PhotoURL", url)
        bundle.putString("Name", name)
        profile.setArguments(bundle)

        return profile
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val ivProfilePicture = view.findViewById(R.id.iv_profile) as ImageView
        val tvName = view.findViewById(R.id.tv_name) as TextView
        val login_button2 = view.findViewById(R.id.login_button2) as Button
        val btn_charecter = view.findViewById(R.id.btn_charecter) as Button
        val btn_Chart = view.findViewById(R.id.btn_Chart) as Button

        Glide.with(activity!!.baseContext)
            .load(PhotoURL)
            .into(ivProfilePicture)

        tvName.setText(Name)

        login_button2.setOnClickListener{

            LoginManager.getInstance().logOut()
            activity!!.supportFragmentManager.popBackStack()
        }
        btn_Chart.setOnClickListener{
            val MainChart = MainChart()
            val fm = fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.contentContainer, MainChart,"MainChart")
            transaction.addToBackStack("MainChart")
            transaction.commit()
        }
        btn_charecter.setOnClickListener{
            val Recycler_view = Recycler_view()
            val fm = fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.contentContainer, Recycler_view,"Recycler_view")
            transaction.addToBackStack("Recycler_view")
            transaction.commit()
        }
        // Inflate the layout for this fragment
        return view

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            PhotoURL = bundle.getString("PhotoURL").toString()
            Name = bundle.getString("Name").toString()

        }

    }

}

