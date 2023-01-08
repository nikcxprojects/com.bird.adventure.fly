package com.bird.adventure.fly

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bird.adventure.fly.databinding.FragmentMainBinding
import com.bird.adventure.fly.databinding.FragmentRecordsBinding


class FragmentRecords : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private lateinit var binding: FragmentRecordsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecordsBinding.inflate(inflater,container,false)
        binding.back.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.popBackStack()
        }
        binding.list.adapter = MyAdapter(requireContext())
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentRecords().apply {
            }
    }
}