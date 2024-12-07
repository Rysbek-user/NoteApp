package com.example.noteapp.ui.fragments.onboard

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardPagingBinding

class OnBoardPagingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardPagingBinding

    companion object{
        const val ARG_ONBOARD_POSITION = "OnBoardPosition"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentOnBoardPagingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() = with(binding) {
        when(requireArguments().getInt(ARG_ONBOARD_POSITION)){
            0 -> {
                elipse1.setBackgroundResource(R.drawable.elipse)
                val newColor = ContextCompat.getColor(requireContext(), R.color.orange)
                val drawable = GradientDrawable()
                drawable.shape = GradientDrawable.OVAL
                drawable.setColor(newColor)
                elipse1.background = drawable

                txtTitle.text = "Удобство"
                animationView.setAnimation(R.raw.nubnur)
                txtBody.text = "Создавайте заметки в два клика! Записывайте мысли, идеи и важные задачи мгновенно."
            }
            1 -> {
                elipse2.setBackgroundResource(R.drawable.elipse)
                val newColor = ContextCompat.getColor(requireContext(), R.color.orange)
                val drawable = GradientDrawable()
                drawable.shape = GradientDrawable.OVAL
                drawable.setColor(newColor)
                elipse2.background = drawable

                txtTitle.text = "Организация"
                animationView.setAnimation(R.raw.nurnur)
                txtBody.text = "Организуйте заметки по папкам и тегам. Легко находите нужную информацию в любое время."
            }
            2 -> {
                elipse3.setBackgroundResource(R.drawable.elipse)
                val newColor = ContextCompat.getColor(requireContext(), R.color.orange)
                val drawable = GradientDrawable()
                drawable.shape = GradientDrawable.OVAL
                drawable.setColor(newColor)
                elipse3.background = drawable

                txtTitle.text = "Синхронизация"
                animationView.setAnimation(R.raw.nur)
                txtBody.text = "Синхронизация на всех устройствах. Доступ к записям в любое время и в любом месте."
            }
            else -> {
                txtTitle.text = "Ошибка"
                animationView.setAnimation(R.raw.nur)
                txtBody.text = "Что-то пошло не так."
                }
        }
    }
}