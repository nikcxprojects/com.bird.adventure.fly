package com.bird.adventure.fly

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bird.adventure.fly.databinding.FragmentBirdsBinding

class FragmentBirds : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var binding: FragmentBirdsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentBirdsBinding.inflate(inflater,container,false)
        binding.back2.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.popBackStack()
        }
        val prefs = requireActivity().getSharedPreferences("prefs",Context.MODE_PRIVATE)
        inv()
        binding.imageView4.setOnClickListener {
            prefs.edit().putInt("bird",1)
                .putInt("id",R.mipmap.bird1_foreground)
                .apply()
            inv()
        }
        binding.imageView5.setOnClickListener {
            prefs.edit().putInt("bird",2)
                .putInt("id",R.mipmap.bird3_foreground)
                .apply()
            inv()
        }
        binding.imageView6.setOnClickListener {
            prefs.edit().putInt("bird",3)
                .putInt("id",R.mipmap.bird2_foreground)
                .apply()
            inv()
        }
        return binding.root
    }
    private fun inv() {
        val prefs = requireActivity().getSharedPreferences("prefs",Context.MODE_PRIVATE)
        binding.imageView4.background = requireActivity().getDrawable(R.drawable.focus)
        binding.imageView5.background = requireActivity().getDrawable(R.drawable.focus)
        binding.imageView6.background = requireActivity().getDrawable(R.drawable.focus)
        when(prefs.getInt("bird",1)) {
            1 -> {
                binding.imageView4.background = requireActivity().getDrawable(R.drawable.bird_bg)
            }
            2 -> binding.imageView5.background = requireActivity().getDrawable(R.drawable.bird_bg)
            3 -> binding.imageView6.background = requireActivity().getDrawable(R.drawable.bird_bg)
        }
    }

    companion object {

        @JvmStatic fun newInstance(param1: String, param2: String) =
                FragmentBirds().apply {

                }
    }
}