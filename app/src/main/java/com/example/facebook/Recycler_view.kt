package com.example.facebook

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.login.LoginManager
import layout.MyRecyclerAdapter
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class Recycler_view : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        // Inflate the layout for this fragment
        val jsonString : String = loadJsonFromAsset("recipes.json", activity!!.baseContext).toString()
        val json = JSONObject(jsonString)
        val jsonArray = json.getJSONArray("recipes")

        val recyclerView: RecyclerView = view.findViewById(R.id.recyLayout)

        val fm = fragmentManager
        val transaction : FragmentTransaction = fm!!.beginTransaction()

        //ตั้งค่า Layout
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity!!.baseContext)
        recyclerView.layoutManager = layoutManager

        //ตั้งค่า Adapter
        val adapter = MyRecyclerAdapter(activity!!.baseContext,activity!!.supportFragmentManager,jsonArray)
        recyclerView.adapter = adapter
        val login_button3 = view.findViewById(R.id.login_button3) as Button
        login_button3.setOnClickListener{

            LoginManager.getInstance().logOut()
            activity!!.supportFragmentManager.popBackStack()

        }

        return view
    }
    private fun loadJsonFromAsset(filename: String, context: Context): String? {
        val json: String?

        try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: java.io.IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }

}
