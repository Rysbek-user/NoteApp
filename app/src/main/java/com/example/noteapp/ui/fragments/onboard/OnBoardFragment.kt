package com.example.noteapp.ui.fragments.onboard

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardBinding
import com.example.noteapp.ui.adapters.OnBoardViewpagerAdapter
import com.example.noteapp.utils.PreferenceHelper

class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding
    private lateinit var sharedPreferences: PreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(requireContext())
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (sharedPreferences.isOnBoardShown) {
            findNavController().navigate(R.id.action_onBoardFragment_to_noteFragment)
        }else{
            initialize()
            setupListeners()
        }
    }

    private fun initialize() {
        binding.viewpager2.adapter = OnBoardViewpagerAdapter(this)
        binding.dots1.attachTo(binding.viewpager2)
    }

    private fun setupListeners() = with(binding.viewpager2) {
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) {
                    binding.txtSkip.visibility = View.GONE
                    binding.btnStart.visibility = View.VISIBLE
                } else {
                    binding.txtSkip.visibility = View.VISIBLE
                    binding.btnStart.visibility = View.GONE
                    binding.txtSkip.setOnClickListener {
                        if (currentItem < 3) {
                            setCurrentItem(currentItem + 2, true)
                        }
                    }
                }
            }
        })
        binding.btnStart.setOnClickListener{
            sharedPreferences.isOnBoardShown = true
            findNavController().navigate(R.id.action_onBoardFragment_to_noteFragment)
            }
        }
    }

