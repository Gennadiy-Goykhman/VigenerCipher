package com.example.vigenerci

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import com.example.vigenerci.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    public lateinit var binding: ActivityMainBinding
    private lateinit var cipherScrambler: VigenerCipherScrambler
    //Использование 95 символов ASCII

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cipherScrambler= VigenerCipherScrambler.getInstance()
        cipherScrambler.setContext(this)
        binding.modeGroup.check(binding.decodeMode.id)
        cipherScrambler.setMode(2131296397)
        binding.executeButton.setOnClickListener {
            binding.cipherText.text=cipherScrambler.execute()
            cipherScrambler.resetCipher()
        }
        binding.opentext.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cipherScrambler.setOpenText(binding.opentext.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                cipherScrambler.setOpenText(binding.opentext.text.toString())
            }

        })
        binding.key.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cipherScrambler.setKey(binding.key.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                cipherScrambler.setKey(binding.key.text.toString())
            }

        })
        with(binding.gammaSpinner){
            adapter= ArrayAdapter.createFromResource(context,R.array.gamma_array,android.R.layout.simple_list_item_1)
        }
        binding.gammaSpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
              cipherScrambler.setGammaMode(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                cipherScrambler.setGammaMode(0)
            }

        }
        binding.modeGroup.setOnCheckedChangeListener(object :RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                cipherScrambler.setMode(p1)
            }

        })

    }
}