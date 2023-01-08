package com.bird.adventure.fly

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bird.adventure.fly.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater,container,false)
        val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
        binding.imageButton2.setOnClickListener {
            navController.navigate(R.id.fragmentRecords)
        }
        binding.imageButton3.setOnClickListener {
            navController.navigate(R.id.fragmentBirds)
        }
        binding.imageButton.setOnClickListener {
            navController.navigate(R.id.settingsFragment)
        }
        binding.button.setOnClickListener {
            navController.navigate(R.id.gameFragment)
        }
        binding.imageView2.setImageResource(requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE).getInt("id",R.mipmap.bird1_foreground))
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}