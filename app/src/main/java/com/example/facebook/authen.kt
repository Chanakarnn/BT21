package com.example.facebook


import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.facebook.*
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.recy_layout.*
import layout.MyRecyclerAdapter

/**
 * A simple [Fragment] subclass.
 */
class authen : Fragment() {

    var user : FirebaseUser? = null
    lateinit var facebookSignInButton : LoginButton
    var callbackManager : CallbackManager? = null
    // Firebase Auth Object.
    var firebaseAuth: FirebaseAuth? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_authen, container, false)

        val btn_login: Button = view.findViewById(R.id.btn_login);

        callbackManager = CallbackManager.Factory.create()
        facebookSignInButton  = view.findViewById(R.id.login_button) as LoginButton
        firebaseAuth = FirebaseAuth.getInstance()
        facebookSignInButton.setReadPermissions("email")

        // If using in a fragment
        facebookSignInButton.setFragment(this)

        val token: AccessToken?
        token = AccessToken.getCurrentAccessToken()

        if (token != null) { //Means user is not logged in
            LoginManager.getInstance().logOut()
        }

        // Callback registration
        facebookSignInButton.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) { // App code

                handleFacebookAccessToken(loginResult!!.accessToken)

            }
            override fun onCancel() { // App code
            }
            override fun onError(exception: FacebookException) { // App code
            }
        })

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) { // App code

                    handleFacebookAccessToken(loginResult!!.accessToken)

                }

                override fun onCancel() { // App code
                }

                override fun onError(exception: FacebookException) { // App code
                }
            })
        btn_login.setOnClickListener {
            val username: String = view.findViewById<EditText>(R.id.inputusername).text.toString();
            val password: String = view.findViewById<EditText>(R.id.inputpassword).text.toString();

            if (username.isEmpty()) {
                Toast.makeText(context, "Please Enter Your Username", Toast.LENGTH_LONG).show()
            } else if (password.isEmpty()) {
                Toast.makeText(context, "Please Enter Your Password", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context,"Hello!! " + username, Toast.LENGTH_LONG).show()

                val Recycler_view = Recycler_view()
                val fm = fragmentManager
                val transaction : FragmentTransaction = fm!!.beginTransaction()
                transaction.replace(R.id.contentContainer, Recycler_view,"Recycler_view")
                transaction.addToBackStack("Recycler_view")
                transaction.commit()
            }

        }
        // Inflate the layout for this fragment
        return view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext())
        AppEventsLogger.activateApp(activity!!.baseContext)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token : AccessToken) {

        Log.d(TAG, "handleFacebookAccessToken:" + token)
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth!!.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
//                Log.d(ContentValues.TAG, "signInWithCredential:success") // edit
//
//                user = firebaseAuth!!.currentUser
//
//                //val profile = profile().newInstance(user!!.photoUrl.toString(),user!!.displayName.toString())
//                val Recycler_view = Recycler_view()
//                val fm = fragmentManager
//                val transaction : FragmentTransaction = fm!!.beginTransaction()
//                transaction.replace(R.id.contentContainer, Recycler_view,"Recycler_view")
//                transaction.addToBackStack("Recycler_view")
//                transaction.commit()

                Log.d(TAG, "signInWithCredential:success")

                user = firebaseAuth!!.currentUser

                val profile = profile().newInstance(user!!.photoUrl.toString(),user!!.displayName.toString())
                val fm = fragmentManager
                val transaction : FragmentTransaction = fm!!.beginTransaction()
                transaction.replace(R.id.contentContainer, profile,"fragment_profile")
                transaction.addToBackStack("fragment_profile")
                transaction.commit()



            } else {
                // If sign in fails, display a message to the user.
                Log.w(ContentValues.TAG, "signInWithCredential:failure", task.getException())
                Toast.makeText(activity!!.baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
