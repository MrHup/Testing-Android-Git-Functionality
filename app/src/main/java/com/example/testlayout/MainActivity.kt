package com.example.testlayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*


// ------ stack implementation
class StackWithList{
    val elements: MutableList<Any> = mutableListOf()

    fun isEmpty() = elements.isEmpty()

    fun size() = elements.size

    fun push(item: Any) = elements.add(item)

    fun pop() : Any? {
        val item = elements.lastOrNull()
        if (!isEmpty()){
            elements.removeAt(elements.size -1)
        }
        return item
    }
    fun peek() : Any? = elements.lastOrNull()

    fun clear(){
        while(!isEmpty()) elements.removeAt(elements.size-1)
    }

    override fun toString(): String = elements.toString()
}
//--------------
var stack  = StackWithList()


fun sum(a:Long){
    stack.pop()
    var b = stack.peek().toString().toLong()
    b+=a
    stack.pop()
    stack.push(b)
}

fun subtract(a:Long){
    stack.pop()
    var b = stack.peek().toString().toLong()
    b-=a
    stack.pop()
    stack.push(b)
}

class MainActivity : AppCompatActivity() {

    fun addToStack(button: Button){
        if (button.text.toString() != "+" && button.text.toString() !="-"){
            var num :Long= button.text.toString().toLong()
            if(!stack.isEmpty()){
                if(stack.peek().toString()!="+" && stack.peek().toString()!="-"){
                    var n : Long = stack.peek().toString().toLong()
                    n=n*10+num
                    stack.pop()
                    stack.push(n)

                }
                else {
                    stack.push(num)
                }
            }
            else{
                stack.push(num)
            }
            output.text = (stack.peek().toString())
        }
        else if(!stack.isEmpty()){

            if(stack.peek().toString()=="+" || stack.peek().toString()=="-"){
                stack.pop()
                stack.push(button.text.toString())
            }
            else{
                var aux = stack.peek().toString().toLong()
                stack.pop()
                if(!stack.isEmpty()){
                    if(stack.peek().toString()=="+") { // sum
                        sum(aux)
                    }
                    else if(stack.peek().toString()=="-"){ // subtract
                        subtract(aux)
                    }
                    output.text = (stack.peek().toString())
                    stack.push(button.text.toString())
                }
                else {
                    stack.push(aux.toLong())
                    stack.push(button.text.toString())
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val b_list = arrayOf(b1,b2,b3,b4,b5,b6,b7,b8,b9,bplus,bminus)
        for (i in b_list){
            i.setOnClickListener{addToStack(i)}
        }


        undo.setOnClickListener{
            if(!stack.isEmpty()) stack.pop()
            output.text = stack.peek().toString()
        }

        clear.setOnClickListener{
            while(!stack.isEmpty()){
                stack.pop()
            }
            output.text = ""
        }

        beq.setOnClickListener{
            if((!stack.isEmpty()) && stack.peek().toString()!="+" && stack.peek().toString()!="-") {
                var a = stack.peek().toString().toLong()
                stack.pop()
                if (stack.peek().toString() == "+") {
                    sum(a)
                } else if (stack.peek().toString() == "-") {
                    subtract(a)
                }
            }
            output.text = stack.peek().toString()
            stack.clear()
        }

    }
}
