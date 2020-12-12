package pers.sfc.execute;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Calculator {
	private enum Operator {
        ADD("+", 10), SUBTRACT("-", 10), MULTIPLY("*", 20), DIVIDE("/", 20),
        PARENTHESIS_LEFT("(", 100), PARENTHESIS_RIGHT(")", 100);
        private String operator;
        private int priority;
        private Operator(String operator, int priority) {
            this.operator = operator;
            this.priority = priority;
        }
    }
    /**
     * 操作数枚举
     * @author Jinjichao
     *
     */
    private enum Operand {
        ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"),
        SEVEN("7"), EIGHT("8"), NINE("9"), ZERO("0"), POINT(".");
        private String operand;
        private Operand(String operand) {
            this.operand = operand;
        }
    }
   
    /**
     * 获取字符串所对应的运算符枚举
     * @param str
     * @return
     */
    private Operator getOperator(String str) {
        for (Operator op : Operator.values()) {
            if (str.equals(op.operator)) {
                return op;
            }
        }
        return null;
    }
    /**
     * 获取字符串所对应的操作数枚举
     * @param str
     * @return
     */
    private Operand getOperand(String str) {
        for (Operand op : Operand.values()) {
            if (str.equals(op.operand)) {
                return op;
            }
        }
        return null;
    }   
    /**
     * 第1步: 将运算表达式字符串分解为运算表达式List
     *
     * @param exp
     * @return
     */
    private List<String> resolveExpr(String exp) {
        List<String> list = new LinkedList<String>();
        String temp = "";
        exp = exp.replace(" ", "");
        for (int i = 0; i < exp.length(); i++) {
            String str = exp.substring(i, i + 1);
            Operator op = getOperator(str);
            Operand od = getOperand(str);
            if (op != null) {
                if (!temp.isEmpty()) {
                    list.add(temp);
                    temp = "";
                }
                list.add(str);
            } else if (od != null) {
                temp += str;
            } else {
                System.out.println("表达式[" + str + "]非法! ");
                return null;
            }
        }
        if (!temp.isEmpty()) {
            list.add(temp);
        }
        //System.out.println(list);
        return list;
    }
    /**
     * 第2步: 将运算表达式List转换为逆波兰表达式List
     * @param expList
     * @return
     */
    private List<String> dealExpr(List<String> expList) {
        if(expList == null) {
            return null;
        }
        
        List<String> list = new LinkedList<String>();
        Stack<String> stack = new Stack<String>();
        for (String str : expList) {
            Operator op = getOperator(str.substring(0, 1));
            Operand od = getOperand(str.substring(0, 1));
            if (od != null) {
                //操作数直接入队列
                list.add(str);
            } else if (op != null) {
                if (Operator.PARENTHESIS_LEFT.equals(op)) {
                    //左括号入栈
                    stack.push(str);
                } else if (Operator.PARENTHESIS_RIGHT.equals(op)) {
                    //右括号: 循环将栈顶的运算符取出并存入队列，直到取出左括号
                    while (true) {
                        if (stack.empty()) {
                            System.out.println("缺少左括号! ");
                            return null;
                        } else if (Operator.PARENTHESIS_LEFT.operator.equals(stack.peek())) {
                            stack.pop();
                            break;
                        } else {
                            list.add(stack.pop());
                        }
                    }
                } else {
                    //非括号类运算符
                    if (!stack.empty()) {
                        Operator top_op = getOperator(stack.peek());
                        //当前运算符优先级大于栈顶运算符优先级，或者栈顶为左括号时，当前运算符直接入栈
                        if(op.priority > top_op.priority
                                || Operator.PARENTHESIS_LEFT.equals(top_op)) {
                            stack.push(str);
                        }
                        //否则，将栈顶的运算符取出并存入队列，然后将自己入栈
                        else {
                            list.add(stack.pop());
                            stack.push(str);
                        }                        
                    } else {
                        stack.push(str);
                    }
                }
            }
        }
        while(!stack.empty()) {
            String str = stack.peek();
            if(Operator.PARENTHESIS_LEFT.operator.equals(str)) {
                System.out.println("缺少右括号! ");
                return null;
            } else {
                list.add(stack.pop());
            }
        }
        //System.out.println(list);
        return list;
    }
   
    /**
     * 操作数运算
     * @param x
     * @param y
     * @param op
     * @return
     */
    private String operation(String x, String y, Operator op) {
        double a = 0.0;
        double b = 0.0;
        try {
            a = Double.parseDouble(x);
            b = Double.parseDouble(y);
        } catch (NumberFormatException e) {
            System.out.println("操作数非法! ");
            e.printStackTrace();
        }
        
        switch (op) {
        case ADD:
            return String.valueOf(a + b);
        case SUBTRACT:
            return String.valueOf(a - b);
        case MULTIPLY:
            return String.valueOf(a * b);
        case DIVIDE:
            return String.valueOf(a / b);
        default:
            return null;
        }
    }
   
    /**
     * 第3步: 逆波兰表达式运算
     * @param exp
     * @return
     */
    public double calculator(String exp) {
        List<String> expList = dealExpr(resolveExpr(exp));
        if(expList == null) {
            return Double.NaN;
        }
        Stack<String> stack = new Stack<String>();
        for(String str : expList) {
            Operator op = getOperator(str.substring(0, 1));
            Operand od = getOperand(str.substring(0, 1));
            if(od != null) {
                stack.push(str);
            } else if (op != null) {
                //目前仅针对二元运算符
                String x = "";
                String y = "";
                if(!stack.empty()) {
                    y = stack.pop();
                }
                if(!stack.empty()) {
                    x = stack.pop();
                }               
                if(!x.isEmpty() && !x.isEmpty()) {
                    String result = operation(x, y, op);
                    if(result == null) {
                        return Double.NaN;
                    }
                    stack.push(result);
                } else {
                    return Double.NaN;
                }
            }
        }
        return Double.parseDouble(stack.pop());
    }   
	/*
	public int priority(char a) {//判断符号优先级
		switch (a) {
		case '+':
		case '-':
		case '(':
			return 1;
		case '*':
		case '/':
			return 2;
		}
		return 0;
	}

	public char[] middleToBack(String exp) {//中缀表达式转后缀表达式
		Stack<Character> stack = new Stack<Character>();//栈用来进出运算的符号
		char[] arr = exp.toCharArray();
		char[] brr = new char[arr.length * 2];//保存后缀表达式，需在数字和符号之间加空格区分，因此长度定为arr的2倍
		int count = 0;//标记brr

		for (int i = 0; i < arr.length; i++) {
			if (i == arr.length - 1) {//防止越界
				brr[count++] = arr[i];
				brr[count++] = ' ';
			} else if ((arr[i] >= '0' && arr[i] <= '9') && !(arr[i + 1] >= '0' && arr[i + 1] <= '9')) {
				brr[count++] = arr[i];
				brr[count++] = ' ';
			} else if (arr[i] >= '0' && arr[i] <= '9') {
				brr[count++] = arr[i];
			} else {//符号
				if (stack.isEmpty()) {
					stack.push(arr[i]);
				} else if (arr[i] == '(') {
					stack.push(arr[i]);
				} else if (arr[i] == ')') {
					while (stack.peek() != '(') {//'('与')'之间的符号出栈
						brr[count++] = stack.peek();
						brr[count++] = ' ';
						stack.pop();
					}
					stack.pop();//将'('出栈
				} else if (priority(arr[i]) >= priority(stack.peek())) {//如果优先级高于或等于栈顶元素，直接入栈
					stack.push(arr[i]);
				} else if (priority(arr[i]) < priority(stack.peek())) {//如果优先级低于栈顶元素，依次出栈
					while (!stack.isEmpty()) {
						brr[count++] = stack.peek();
						brr[count++] = ' ';
						stack.pop();
					}
					stack.push(arr[i]);
				}
			}
		}
		while (!stack.empty()) {
			brr[count++] = stack.peek();
			brr[count++] = ' ';
			stack.pop();
		}
		return brr;
	}

	public double calculate(char[] brr) {//后缀表达式求得计算结果
		Stack<Double> stack = new Stack<Double>();//栈用来进出运算的数字
		for (int i = 0; i < brr.length; ) {
			String number = "";
			if (brr[i] == ' '||brr[i]=='\0') {//空格或为空
				i++;
				continue;
			}
			if (brr[i] >= '0' && brr[i] <= '9') {//数字入栈
				while (brr[i] >= '0' && brr[i] <= '9') {
					number += brr[i];
					i++;
				}
				stack.push(Double.valueOf(number));//字符型转整型入栈
			} else if(brr[i]=='+'||brr[i]=='-'||brr[i]=='*'||brr[i]=='/') {//符号
				double top1 = stack.peek();
				stack.pop();
				double top2 = stack.peek();
				stack.pop();
				double result = operate(top2,top1,brr[i]);//注意top2和top1的位置
				stack.push(result);
				i++;
			}
		}
		return stack.peek();
	}

	public double operate(double item1, double item2, char operator) {
		double result = 0;
		switch (operator) {
		case '+':
			result = item1 + item2;
			break;
		case '-':
			result = item1 - item2;
			break;
		case '*':
			result = item1 * item2;
			break;
		case '/':
			result = item1 / item2;
			break;
		}
		return result;
	}
	
	public double calculator(String exp) {
        char[] brr = middleToBack(exp);
        //return Double.parseDouble(calculate(brr));
        return calculate(brr);
    }
	*/
}
