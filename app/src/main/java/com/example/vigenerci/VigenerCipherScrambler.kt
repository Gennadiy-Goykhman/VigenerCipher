package com.example.vigenerci

import android.content.Context
import android.util.Log
import android.widget.Toast

class VigenerCipherScrambler(private var context: Context?, private var openText: String, private var key: String, private var mode:String, private var cipherText: String) {
    private var gamma: String=""
    private var gamma_mode: Int=0

    private fun encode():String{
        gamma_calculating()
        for(i in 0..openText.length-1 ){
            cipherText+=(((openText.get(i).toByte().toInt()-32+gamma.get(i).toByte().toInt()-32)% ALPHABET_LEN)).toChar()
        }
        return cipherText
    }

    private fun decode():String{
        gamma_calculating()
        for(i in 0..openText.length-1 ){
            cipherText+=(((openText.get(i).toByte().toInt()+32-(gamma.get(i).toByte().toInt()-32))% ALPHABET_LEN)).toChar()
        }
        return cipherText
    }

    private fun gamma_calculating(){
        when(gamma_mode){
            0->{
                key_repeat()
            }
            1->{
                self_key_open_text()
            }
            2->{
                self_key_cipher_text()
            }
        }
        (context as MainActivity).binding.gamma.text=gamma
        Log.d("GAMMA_LOG",gamma)
    }

    private fun self_key_cipher_text() {
        gamma=""+key.get(0)
        for(i in 0..openText.length-2 ){
            gamma+=(((openText.get(i).toByte().toInt()-32+gamma.get(i).toByte().toInt()-32)% ALPHABET_LEN)).toChar()
        }
    }

    private fun self_key_open_text() {
        gamma=key.get(0).toString()
        for(i in 0..openText.length-2 ){
            gamma+=openText.get(i)
        }
    }

    private fun key_repeat(){
        gamma=""
        for(i in 0..openText.length-1 ){
            gamma+=key.get(i%key.length)
        }
    }

    public fun execute(): String{
        Log.d(TAG, openText+"\n"+key+"\n"+mode+"\n"+gamma_mode)
        if(openText==""||key==""||mode==""){
            Toast.makeText(context,"Не все поля были заполнены",Toast.LENGTH_LONG).show()
            return ""
        }
        when(mode){
            ENCODE_MODE->return encode()
            DECODE_MODE->return decode()
        }
        return ""
    }

    public fun resetCipher(){
        cipherText=""
    }

    public fun setOpenText(open_text: String){
        openText=open_text
    }
    public fun setCipherText(cipher_text: String){
        cipherText=cipher_text
    }
    public fun setContext(context_: Context?){
        context=context_;
    }
    public fun setKey(key_text:String){
        key=key_text
    }
    public fun setDefaulMode(pos:Int){
        when(pos){
            2131296430->{
                mode= ENCODE_MODE
            }
            2131296397->{
                mode= DECODE_MODE
            }
        }
    }
    public fun setMode(pos:Int){
        var bind=(context as MainActivity).binding
        when(pos){
            2131296430->{
                var temp=bind.titleOpentext.text.toString()
                bind.titleOpentext.text=bind.titileCiphertext.text
                bind.titileCiphertext.text=temp
                mode= ENCODE_MODE
            }
            2131296397->{
                var temp=bind.titleOpentext.text.toString()
                bind.titleOpentext.text=bind.titileCiphertext.text
                bind.titileCiphertext.text=temp
                mode= DECODE_MODE
            }
        }
    }
    public fun setGammaMode(gamma_pos:Int){
        gamma_mode=gamma_pos
    }
    companion object{
        var scrambler: VigenerCipherScrambler? = null
        public fun getInstance(): VigenerCipherScrambler{
            when(scrambler){
                null -> {
                    scrambler= newInstance()
                    return scrambler as VigenerCipherScrambler
                }
                else -> return scrambler as VigenerCipherScrambler
            }
        }

        private fun newInstance(): VigenerCipherScrambler{
            return VigenerCipherScrambler(null,"","","","")
        }
        private const val ENCODE_MODE="encode"
        private const val DECODE_MODE="decode"
        private val ALPHABET_LEN: Int=127
        private const val TAG=".CURRENT_CIPHER_DEBUG"
    }

}