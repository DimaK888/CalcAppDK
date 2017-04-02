package dimak888.calcappdk;

import java.util.ArrayList;
import java.util.Stack;
/**
 * Created by user on 01.04.17.
 */

public class ArbeitenRPN{

    //MainActivity resultView;
    String err = "";

    final String operands = "^*+-/" ;
    double result;

    final static int PRECEDENCE_PLUS = 1;
    final static int PRECEDENCE_MINUS = 1;
    final static int PRECEDENCE_MULTIPLIY = 2;
    final static int PRECEDENCE_DIVIDE = 2;
    final static int PRECEDENCE_EXPONENT = 3;
    final static int PRECEDENCE_PARANTHESIS = 4;

    public int operatorToPrecedence(String op){
        if( op.equals("+"))
            return ArbeitenRPN.PRECEDENCE_PLUS;
        else if( op.equals("-"))
            return ArbeitenRPN.PRECEDENCE_MINUS;
        else if( op.equals("*"))
            return ArbeitenRPN.PRECEDENCE_MULTIPLIY ;
        else if( op.equals("^"))
            return ArbeitenRPN.PRECEDENCE_EXPONENT ;
        else if( op.equals("/") )
            return ArbeitenRPN.PRECEDENCE_DIVIDE ;
        else
            return ArbeitenRPN.PRECEDENCE_PARANTHESIS;
    }

    public boolean isOperand(String s, boolean allowParanethesis){
        s = s.trim();
        if (s.length() != 1)
            return false;
        if (allowParanethesis &&  (s.equals("(") || s.equals(")")))
            return true;
        else return operands.indexOf( s ) != -1;
    }

    public boolean isNumber(String s){
        String  master="-0123456789.";
        s = s.trim();

        for( int i = 0;i < s.length()  ;i++)
        {
            String lttr = s.substring(i, i+1);
            if(master.indexOf( lttr) == -1)
                return false;
        }
        return true;
    }

    public void parseRPN(String input){
        String rpnStr = input;
        String[] tokens = rpnStr.split("\\s+");
        Stack<Double> numberStack =new Stack<>();

        boolean  bAllowParenthesis = false;
        for( String token : tokens)
        {
            if(token.equals("-") == false && isNumber(token))
            {
                double d = Double.parseDouble(token) ;
                numberStack.push(d) ;
            }
            else if(isOperand(token, bAllowParenthesis))
            {
                if (numberStack.size() < 2)
                {
                    err = "Что за дичь, оператор " + token + " должен быть между двух операндов";
                    return;
                }
                double num1 = numberStack.pop();
                double num2 = numberStack.pop();
                double result = this.calculate(num2, num1, token);
                numberStack.push(result);
            }
            else if( token.trim().length() > 0 )
                err = token + " не валиден, используйте только знаки операций и числа";
        }
        result= numberStack.pop();
    }

    double getResult() {
        return result;
    }

    private Double calculate(double num1, double num2, String op) {
        if( op.equals("+"))
            return num1 + num2;
        else if( op.equals("-"))
            return num1 - num2;
        else if( op.equals("*"))
            return num1 * num2;
        else if( op.equals("^"))
            return Math.pow(num1 , num2);
        else if( op.equals("/") )
        {
            if(num2 ==0 )
                throw new ArithmeticException("Деление на 0");
            return num1 / num2;
        }
        else
        {
            err = op + " не поддерживаемый операнд";
            return null;
        }
    }

    public ArrayList<String> infixToPostfix(String input){

        ArrayList<String> output  = new ArrayList<>();
        input = input.replaceAll("\\s+","") ;

        Stack<String> operandStack = new Stack<>();

        for(int i = 0 ;i< input.length() ; i++)
        {
            String currentToken = input.substring(i,i+1);
            if(isOperand(currentToken, true))
            {
                if(operandStack.size() == 0)
                    operandStack.push( currentToken );
                else if(operandStack.size() > 0  && currentToken.equals(")"))
                {
                    while(operandStack.size() > 0  && operandStack.peek().equals("(") == false)
                    {
                        output.add(operandStack.pop()) ;
                    }
                    operandStack.pop(); // удаляем "("
                }

                else if(operandStack.size() > 0 )
                {
                    if((currentToken.equals("(") && operandStack.peek().equals("(")) || (currentToken.equals("(")== false && this.operatorToPrecedence(operandStack.peek()) >= this.operatorToPrecedence(currentToken)))
                    {
                        while (operandStack.size()> 0 && operandStack.peek().equals("(")== false &&  this.operatorToPrecedence(operandStack.peek()) >= this.operatorToPrecedence(currentToken))
                        {
                            output.add(operandStack.pop());
                        }
                        operandStack.push(currentToken) ;
                    }
                    else if( this.operatorToPrecedence(operandStack.peek()) < this.operatorToPrecedence(currentToken))
                    {
                        operandStack.push(currentToken) ;
                    }
                }
            }
            else if (isNumber(currentToken))
            {
                numberLoop : while(i+1 < input.length())
                {
                    String nxtLttr =  input.substring(i+1,i+2);
                    if(nxtLttr.equals("-") )
                        break numberLoop;

                    if(isNumber(nxtLttr))
                    {
                        currentToken +=nxtLttr;
                        i++;
                    }
                    else
                        break numberLoop;
                }
                try{
                    output.add(currentToken) ;
                }
                catch (NumberFormatException e){ err = currentToken + " не валидное число"; }
            }
        }
        while( operandStack.size() > 0 )
        {
            output.add( operandStack.pop() ) ;
        }
        return output;
    }
}