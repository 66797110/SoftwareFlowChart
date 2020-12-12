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
     * ������ö��
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
     * ��ȡ�ַ�������Ӧ�������ö��
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
     * ��ȡ�ַ�������Ӧ�Ĳ�����ö��
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
     * ��1��: ��������ʽ�ַ����ֽ�Ϊ������ʽList
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
                System.out.println("���ʽ[" + str + "]�Ƿ�! ");
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
     * ��2��: ��������ʽListת��Ϊ�沨�����ʽList
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
                //������ֱ�������
                list.add(str);
            } else if (op != null) {
                if (Operator.PARENTHESIS_LEFT.equals(op)) {
                    //��������ջ
                    stack.push(str);
                } else if (Operator.PARENTHESIS_RIGHT.equals(op)) {
                    //������: ѭ����ջ���������ȡ����������У�ֱ��ȡ��������
                    while (true) {
                        if (stack.empty()) {
                            System.out.println("ȱ��������! ");
                            return null;
                        } else if (Operator.PARENTHESIS_LEFT.operator.equals(stack.peek())) {
                            stack.pop();
                            break;
                        } else {
                            list.add(stack.pop());
                        }
                    }
                } else {
                    //�������������
                    if (!stack.empty()) {
                        Operator top_op = getOperator(stack.peek());
                        //��ǰ��������ȼ�����ջ����������ȼ�������ջ��Ϊ������ʱ����ǰ�����ֱ����ջ
                        if(op.priority > top_op.priority
                                || Operator.PARENTHESIS_LEFT.equals(top_op)) {
                            stack.push(str);
                        }
                        //���򣬽�ջ���������ȡ����������У�Ȼ���Լ���ջ
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
                System.out.println("ȱ��������! ");
                return null;
            } else {
                list.add(stack.pop());
            }
        }
        //System.out.println(list);
        return list;
    }
   
    /**
     * ����������
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
            System.out.println("�������Ƿ�! ");
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
     * ��3��: �沨�����ʽ����
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
                //Ŀǰ����Զ�Ԫ�����
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
	public int priority(char a) {//�жϷ������ȼ�
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

	public char[] middleToBack(String exp) {//��׺���ʽת��׺���ʽ
		Stack<Character> stack = new Stack<Character>();//ջ������������ķ���
		char[] arr = exp.toCharArray();
		char[] brr = new char[arr.length * 2];//�����׺���ʽ���������ֺͷ���֮��ӿո����֣���˳��ȶ�Ϊarr��2��
		int count = 0;//���brr

		for (int i = 0; i < arr.length; i++) {
			if (i == arr.length - 1) {//��ֹԽ��
				brr[count++] = arr[i];
				brr[count++] = ' ';
			} else if ((arr[i] >= '0' && arr[i] <= '9') && !(arr[i + 1] >= '0' && arr[i + 1] <= '9')) {
				brr[count++] = arr[i];
				brr[count++] = ' ';
			} else if (arr[i] >= '0' && arr[i] <= '9') {
				brr[count++] = arr[i];
			} else {//����
				if (stack.isEmpty()) {
					stack.push(arr[i]);
				} else if (arr[i] == '(') {
					stack.push(arr[i]);
				} else if (arr[i] == ')') {
					while (stack.peek() != '(') {//'('��')'֮��ķ��ų�ջ
						brr[count++] = stack.peek();
						brr[count++] = ' ';
						stack.pop();
					}
					stack.pop();//��'('��ջ
				} else if (priority(arr[i]) >= priority(stack.peek())) {//������ȼ����ڻ����ջ��Ԫ�أ�ֱ����ջ
					stack.push(arr[i]);
				} else if (priority(arr[i]) < priority(stack.peek())) {//������ȼ�����ջ��Ԫ�أ����γ�ջ
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

	public double calculate(char[] brr) {//��׺���ʽ��ü�����
		Stack<Double> stack = new Stack<Double>();//ջ�����������������
		for (int i = 0; i < brr.length; ) {
			String number = "";
			if (brr[i] == ' '||brr[i]=='\0') {//�ո��Ϊ��
				i++;
				continue;
			}
			if (brr[i] >= '0' && brr[i] <= '9') {//������ջ
				while (brr[i] >= '0' && brr[i] <= '9') {
					number += brr[i];
					i++;
				}
				stack.push(Double.valueOf(number));//�ַ���ת������ջ
			} else if(brr[i]=='+'||brr[i]=='-'||brr[i]=='*'||brr[i]=='/') {//����
				double top1 = stack.peek();
				stack.pop();
				double top2 = stack.peek();
				stack.pop();
				double result = operate(top2,top1,brr[i]);//ע��top2��top1��λ��
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
