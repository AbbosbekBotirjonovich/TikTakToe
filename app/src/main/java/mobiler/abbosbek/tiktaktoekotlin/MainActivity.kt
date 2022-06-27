package mobiler.abbosbek.tiktaktoekotlin

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import androidx.core.view.forEach
import mobiler.abbosbek.tiktaktoekotlin.databinding.ActivityMainBinding
import mobiler.abbosbek.tiktaktoekotlin.databinding.WinDialogLayoutBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var isXTurn = true
    val chars = Array(3){CharArray(3){'*'} }
    var xCount = 0
    var oCount = 0
    var drawCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.turnTitleTv.text = if (isXTurn){
            getString(R.string.xTurnPlayer)
        }else{
            getString(R.string.oTurnPlayer)
        }
        setOnClickListener()
    }

    private fun setOnClickListener(){
        binding.firstBtn.setOnClickListener {
            btnClickListener(it as Button,1)
        }
        binding.secondBtn.setOnClickListener {
            btnClickListener(it as Button,2)
        }
        binding.thirdBtn.setOnClickListener {
            btnClickListener(it as Button,3)
        }
        binding.fourthBtn.setOnClickListener {
            btnClickListener(it as Button,4)
        }
        binding.fifthBtn.setOnClickListener {
            btnClickListener(it as Button,5)
        }
        binding.sexBtn.setOnClickListener {
            btnClickListener(it as Button,6)
        }
        binding.sevenBtn.setOnClickListener {
            btnClickListener(it as Button,7)
        }
        binding.eightBtn.setOnClickListener {
            btnClickListener(it as Button,8)
        }
        binding.nineBtn.setOnClickListener {
            btnClickListener(it as Button,9)
        }
    }

    private fun btnClickListener(button: Button,buttonNumber : Int){
        if (button.text.isNotBlank()) return
        button.text = if (isXTurn) {
            button.setTextColor(Color.RED)
            chars[(buttonNumber-1)/3][(buttonNumber-1)%3] = 'X'
            "X"
        } else {
            button.setTextColor(Color.GREEN)
            chars[(buttonNumber-1)/3][(buttonNumber-1)%3] = '0'
            "0"
        }
        isXTurn = !isXTurn
        binding.turnTitleTv.text = if (isXTurn){
            getString(R.string.xTurnPlayer)
        }else{
            getString(R.string.oTurnPlayer)
        }
        setOnClickListener()
        checkWin()
    }

    private fun checkWin(){
        for (array in chars){
            if (array[0] != '*' && array[0]==array[1] && array[1]==array[2]){
                // win array[0]
                showWinDialog(array[0])
                return
            }
        }

        for (i in 0 until 3){
            if (chars[0][i] != '*' && chars[0][i]==chars[1][i] && chars[1][i]==chars[2][i]){
                showWinDialog(chars[0][i])
                return
            }
        }
        if (chars[0][0]!='*' && chars[0][0] == chars[1][1] && chars[1][1]==chars[2][2]){
            showWinDialog(char = chars[0][0])
            return
        }
        if (chars[2][0]!='*' && chars[2][0] == chars[1][1] && chars[0][2]==chars[1][1]){
            showWinDialog(char = chars[0][0])
            return
        }

        var count = 0
        for (i in chars.indices){
            for (j in 0 until 3){
                if (chars[i][j] != '*'){
                    count++
                }
            }
        }
        if (count==9){
            showWinDialog(char = '*')
        }
    }

    private fun showWinDialog(char : Char){
        val dialogLayoutBinding = WinDialogLayoutBinding.inflate(LayoutInflater.from(this))

        dialogLayoutBinding.titleTv.text = if (char =='X'){
            xCount++
            binding.xCountTv.text = "$xCount"
            "X player won"
        }else if (char == '0'){
            oCount++
            binding.oCountTv.text = "$oCount"
            "0 player won"
        }else{
            binding.drawCountTv.text = "$drawCount"
            drawCount++
            "This game is draw"
        }
        val dialog = AlertDialog
            .Builder(this,R.style.RoundedDialog)
            .setCancelable(false)
            .setView(dialogLayoutBinding.root)
            .create()
        dialog.show()
        dialogLayoutBinding.button.setOnClickListener {
            resetGame()
            dialog.dismiss()
        }
    }
    private fun resetGame(){
        for (i in 0 until 3){
            for (j in 0 until 3) {
                chars[i][j] = '*'
            }
        }

        binding.btnTableLayout.children.forEach {
            (it as TableRow).children.forEach {b->
                (b as Button).text = ""
            }
        }
    }
}


