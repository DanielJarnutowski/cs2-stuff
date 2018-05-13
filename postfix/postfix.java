//Daniel Jarnutowski
//naruto016@live.com


import java.util.*;
import java.util.Stack;

public class postfix 
{	
    public static int priority = -1;
    public static int pcounter = 0;

    public static int priorityCheck (String token)
    {
        priority = -1;
        pcounter =0;// keeps track of parenthesis sets so nested parenthesis can be used

        if (token.equals("*") || token.equals("/")) 
        {
            return priority = 2 + 3*pcounter ;
        }
        else if (token.equals("+") || token.equals("-"))
        {
            return priority = 1 + 3*pcounter;
        }
        else if (token.equals("(")) 
        {
            return priority = 3;

        }
        else if (token.equals(")") || token.equals("#")) 
        {
            return priority = 0;

        }
        else 
        {
            return priority = 4;// if letter or number is found 
        }
    }

    public static LLQueue infixtopostfix(String infix)
    {
        String[] inf =  infix.split("\\s");
        LLQueue Infix = new LLQueueADT();
        LLStack operators = new LLStackADT();
        LLQueue postfix = new LLQueueADT();

        operators.push("#");// push number sign on stack to be used later
        String o;
        String i;

        for (int a=0; a<inf.length; a++) // scan string of infix
        {
            Infix.enqueue(inf[a]);// put strings into infix queue
            i = Infix.front();
            o = operators.onTop();

            if (priorityCheck(i) == 4) 
            {
                postfix.enqueue(i);

            }
            else if (priorityCheck(i) == 2) //if item is / or  * push them on operators stack
            {
                operators.push(i);

            }
            else if(priorityCheck(i) == 1) // if item is a + or -
            {

                while (o.equals("*")  || o.equals("/")|| o.equals("+") || o.equals("-")) //if there are any opertors on stack  
                // enqueue those onto postfix first
                {
                    o = operators.pop();
                    postfix.enqueue(o);

                    o = operators.onTop();
                }
                operators.push(i);// push operators on operators stack

            }
            else if(priorityCheck(i) == 3 ) // if left parenthesis is encountered push onto stack
            {
                pcounter++;
                operators.push(i);

            }
            else if(priorityCheck(i) == 0   ) // if right parenthesis encountered
            {
                pcounter--;
                while (!o.equals("(")  ) {// pops all items in between parenethesis

                    o = operators.pop();
                    postfix.enqueue(o);

                    o = operators.onTop();
                }
                o = operators.pop();

                while (!o.equals("#") && pcounter>0  )  
                {

                    o = operators.pop();
                    postfix.enqueue(o);

                    o = operators.onTop();
                }

                operators.pop();

            }
            Infix.dequeue();// removes items from infix 
        }
        while (!Infix.empty() )
        {
            o=operators.pop();

            postfix.enqueue(o);

        }

        return postfix;// returns postfix expression
    }

    static int evaluatePostfix(String exp)
    {

        Stack<Integer> stack=new Stack<>();

        for(int i=0;i<exp.length();i++)
        {
            char c=exp.charAt(i);// sets string to characters for easier use

            // If the scanned character is an operand (number here),
            // push it to the stack.
            if(Character.isDigit(c))
                stack.push(c - '0');

            //  If the scanned character is an operator, pop two
            // elements from stack apply the operator
            else
            {
                int value1 = stack.pop();
                int value2 = stack.pop();//  sets the items in stack to integer to be used with operators

                switch(c)// operators handeling for stack
                {
                    case '+':
                    stack.push(value2+value1);
                    break;

                    case '-':
                    stack.push(value2- value1);
                    break;

                    case '/':
                    stack.push(value2/value1);
                    break;

                    case '*':
                    stack.push(value2*value1);
                    break;
                }
            }
        }
        return stack.pop();   // return the asnwer thats left on the stack. 
    }

    public static void main (String[] args) 
    {

        String a = " ( ( ( A * B )  / C  + D )";
        String answer = new String("");

        LLQueue postfixform = postfix.infixtopostfix(a);
        int size = postfixform.size();
        for(int i = 0; i<size; i++)// scan post fix queue to create string to respresent its contents 
        {

            answer = answer + postfixform.dequeue();
        }
        System.out.print("The infix expression of:" + a + "converted to postfix is:" + answer);
        System.out.println();
        //System.out.println("The postfix evaluates to the answer:" + evaluatePostfix(answer));
    }

}

