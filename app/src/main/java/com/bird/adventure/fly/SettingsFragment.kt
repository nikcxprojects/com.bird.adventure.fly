package com.bird.adventure.fly

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import com.bird.adventure.fly.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        val prefs = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        binding.soundOn.isEnabled = prefs.getBoolean("sound",false)
        binding.soundOff.isEnabled = !prefs.getBoolean("sound",false)
        binding.vibrOn.isEnabled = prefs.getBoolean("vibration",false)
        binding.vibrOff.isEnabled = !prefs.getBoolean("vibration",false)
        binding.soundOn.setOnClickListener {
            prefs.edit().putBoolean("sound",false).apply()
            binding.soundOn.isEnabled = false
            binding.soundOff.isEnabled = true
        }
        binding.soundOff.setOnClickListener {
            prefs.edit().putBoolean("sound",true).apply()
            binding.soundOn.isEnabled = true
            binding.soundOff.isEnabled = false
        }
        binding.vibrOn.setOnClickListener {
            prefs.edit().putBoolean("vibration",false).apply()
            binding.vibrOn.isEnabled = false
            binding.vibrOff.isEnabled = true
        }
        binding.vibrOff.setOnClickListener {
            prefs.edit().putBoolean("vibration",true).apply()
            binding.vibrOn.isEnabled = true
            binding.vibrOff.isEnabled = false
        }
        binding.dark.isEnabled = prefs.getBoolean("dark",false)
        binding.light.isEnabled = !prefs.getBoolean("dark",false)
        binding.dark.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            (requireActivity() as MainActivity).light()
            prefs.edit().putBoolean("dark",false).apply()
            binding.dark.isEnabled = false
            binding.light.isEnabled = true
        }
        binding.light.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            (requireActivity() as MainActivity).dark()
            prefs.edit().putBoolean("dark",true).apply()
            binding.dark.isEnabled = true
            binding.light.isEnabled = false
        }
        binding.back.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.popBackStack()
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {

            }
    }
}