package com.example.noteapp.ui.fragments.singin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentSinginBinding
import com.example.noteapp.utils.PreferenceHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth


class SingInFragment : Fragment() {

    private lateinit var binding: FragmentSinginBinding
    private lateinit var sharedPreferences: PreferenceHelper
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val singInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                        firebaseAuthWithGoogle(account.idToken)
                }catch (e: ApiException){
                    updateUi(null)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(requireContext())
        binding = FragmentSinginBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_id_client))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkRegistrationStatus()
            setupListeners()
    }

    private fun setupListeners() = with(binding) {
        btnSing.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            singInLauncher.launch(signInIntent)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()){ task->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    sharedPreferences.setRegistered(true)
                    updateUi(user)
                }else{
                    updateUi(null)
                }

            }
    }

    private fun updateUi(user: FirebaseUser?) {
        if (user != null){
            findNavController().navigate(R.id.noteFragment)
        }else{
            Toast.makeText(requireContext(), "аунтефикация не удалась", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkRegistrationStatus() {
        if (sharedPreferences.isRegistered()){
            findNavController().navigate(R.id.noteFragment)
        }
    }
}