package com.bird.adventure.fly

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bird.adventure.fly.databinding.DialogViewBinding
import com.bird.adventure.fly.databinding.EndDialogBinding
import com.bird.adventure.fly.databinding.FragmentGameBinding
import java.util.*


class GameFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var binding: FragmentGameBinding
    private var dialog: EndDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(inflater,container,false)
        binding.imageButton4.setOnClickListener {
            binding.game.togglePause()
            val fragment = MyDialog(c = { binding.game.togglePause() })
            fragment.show(parentFragmentManager,"TAG")
            //fragment.dialog!!.setCanceledOnTouchOutside(false)
            //fragment.dialog!!.setCancelable(false)
        }
        Thread {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    while(isVisible && !isDetached) {
                        if(binding.game.count<=0 && dialog==null) {
                            dialog = EndDialog(c = {
                                val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
                                navController.popBackStack()
                                                   }, "${binding.game.score}")
                            //dialog!!.dialog!!.setCanceledOnTouchOutside(false)
                           // dialog!!.dialog!!.setCancelable(false)
                            dialog!!.show(parentFragmentManager,"TAG")
                        }
                    }
                }
            }, 0, 20)
        }.start()
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {

            }
    }
    class MyDialog(c: ()-> Unit): DialogFragment() {

        val call = c

        @SuppressLint("DialogFragmentCallbacksDetector")
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            var builder = AlertDialog.Builder(context)
                .setCustomTitle(null)

            val view = DialogViewBinding.inflate(layoutInflater,null)
            view.button2.setOnClickListener {
                dismiss()
                call()
            }
            view.back2.setOnClickListener {
                val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
                navController.popBackStack()
                dismiss()
            }
            view.refresh.setOnClickListener {
                val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
                navController.popBackStack()
                navController.navigate(R.id.gameFragment)
                dismiss()
            }
            builder = builder.setCancelable(false)
            builder = builder.setView(view.root)
            builder = builder.setOnDismissListener { call() }
            isCancelable = false;
            val dialog =  builder.create()
            //dialog.setOnDismissListener { call() }
            //dialog.setOnCancelListener { call() }
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            return dialog
        }
    }
    class EndDialog(c: ()-> Unit, private val score: String): DialogFragment() {

        val call = c

        @SuppressLint("DialogFragmentCallbacksDetector")
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            var builder = AlertDialog.Builder(context)
                .setCustomTitle(null)

            val view = EndDialogBinding.inflate(layoutInflater,null)
            view.back2.setOnClickListener {
                val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
                navController.popBackStack()
                dismiss()
            }
            view.refresh.setOnClickListener {
                val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
                navController.popBackStack()
                navController.navigate(R.id.gameFragment)
                dismiss()
            }
            view.imageView11.setImageResource(requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE).getInt("id",R.mipmap.bird1_foreground))
            view.textView7.text = score
            builder = builder.setView(view.root)
            builder = builder.setCancelable(false)
            builder = builder.setOnDismissListener { call() }
            isCancelable = false
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            //dialog?.setOnDismissListener { call() }
            //dialog?.setOnCancelListener { call() }
            val dialog =  builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            return dialog
        }
    }
}