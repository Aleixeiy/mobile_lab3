package com.example.cat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HelpFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_lelp, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HelpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments.takeIf { it!!.containsKey("number") }?.apply {
            var number = getInt("number")
            if (number == 1)
            {
                val helpPic = view.findViewById<ImageView>(R.id.help)
                helpPic?.setImageResource(R.drawable.help1)
                val helpText = view.findViewById<TextView>(R.id.helpText)
                helpText.text = "На главном окне можно выбрать свое настроение и аослушать музыку."
            }
            if (number == 2)
            {
                val helpPic = view.findViewById<ImageView>(R.id.help)
                helpPic?.setImageResource(R.drawable.help2)
                val helpText = view.findViewById<TextView>(R.id.helpText)
                helpText.text = "На экране профиля вы можете поставить аватарку и рассчитать свой ИМТ."
            }
            if (number == 3)
            {
                val helpPic = view.findViewById<ImageView>(R.id.help)
                helpPic?.setImageResource(R.drawable.help3)
                val helpText = view.findViewById<TextView>(R.id.helpText)
                helpText.text = "Нажав на кнопку 'гороскоп', можно узнать прогноз на сегодня."
            }
        }
    }
}