package pers.sfc.execute;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Calculator {
    public Double calculator(String str) {
        // ��ֵУ��
        if (null == str || "".equals(str)) {
            return null;
        }
        // ����У��
        if (str.length() > MyUtils.FORMAT_MAX_LENGTH) {
            System.out.println("���ʽ������");
            return null;
        }
        // Ԥ����
        str = str.replaceAll(" ", "");// ȥ�ո�
        if ('-' == str.charAt(0)) {// ��ͷΪ��������-1����Ϊ0-1
            str = 0 + str;
        }
        // У���ʽ
        if (!MyUtils.checkFormat(str)) {
            System.out.println("���ʽ����");
            return null;
        }
        // ������ʽ����Ϊ��׼���ʽ
        str = MyUtils.change2StandardFormat(str);
        // ����ַ�������
        String[] nums = str.split("[^.0-9]");
        List<Double> numLst = new ArrayList<Double>();
        for (int i = 0; i < nums.length; i++) {
            if (!"".equals(nums[i])) {
                numLst.add(Double.parseDouble(nums[i]));
            }
        }
        String symStr = str.replaceAll("[.0-9]", "");
        return doCalculate(symStr, numLst);
    }

    public Double doCalculate(String symStr, List<Double> numLst) {
        LinkedList<Character> symStack = new LinkedList<>();// ����ջ
        LinkedList<Double> numStack = new LinkedList<>();// ����ջ
        int i = 0;// numLst�ı�־λ
        int j = 0;// symStr�ı�־λ
        char sym;// ����
        double num1, num2;// ����ǰ��������
        while (symStack.isEmpty() || !(symStack.getLast() == '=' && symStr.charAt(j) == '=')) {// ���磺
                                                                                                // =8=
                                                                                                // ���˳�ѭ�������Ϊ8
            if (symStack.isEmpty()) {
                symStack.add('=');
                numStack.add(numLst.get(i++));
            }
            if (MyUtils.symLvMap.get(symStr.charAt(j)) > MyUtils.symLvMap.get(symStack.getLast())) {// �ȽϷ������ȼ�������ǰ�������ȼ�����ǰһ����ѹջ
                if (symStr.charAt(j) == '(') {
                    symStack.add(symStr.charAt(j++));
                    continue;
                }
                numStack.add(numLst.get(i++));
                symStack.add(symStr.charAt(j++));
            } else {// ��ǰ�������ȼ�С�ڵ���ǰһ�� ���ŵ����ȼ�
                if (symStr.charAt(j) == ')' && symStack.getLast() == '(') {// ������֮��û�з��ţ��򡰣�����ջ
                    j++;
                    symStack.removeLast();
                    continue;
                }
                if (symStack.getLast() == '(') {// ������ֱ��ѹջ
                    numStack.add(numLst.get(i++));
                    symStack.add(symStr.charAt(j++));
                    continue;
                }
                num2 = numStack.removeLast();
                num1 = numStack.removeLast();
                sym = symStack.removeLast();
                switch (sym) {
                case '+':
                    numStack.add(MyUtils.plus(num1, num2));
                    break;
                case '-':
                    numStack.add(MyUtils.reduce(num1, num2));
                    break;
                case '*':
                    numStack.add(MyUtils.multiply(num1, num2));
                    break;
                case '/':
                    if (num2 == 0) {// ����Ϊ0
                        System.out.println("���ڳ���Ϊ0");
                        return null;
                    }
                    numStack.add(MyUtils.divide(num1, num2));
                    break;
                }
            }
        }
        return numStack.removeLast();
    }
    private static class MyUtils {
        public static final int FORMAT_MAX_LENGTH = 500;// ���ʽ��󳤶�����
        public static final Map<Character, Integer> symLvMap = new HashMap<Character, Integer>();// �������ȼ�map
        static {
            symLvMap.put('=', 0);
            symLvMap.put('-', 1);
            symLvMap.put('+', 1);
            symLvMap.put('*', 2);
            symLvMap.put('/', 2);
            symLvMap.put('(', 3);
            symLvMap.put(')', 1);
            // symLvMap.put('^', 3);
            // symLvMap.put('%', 3);
        }

        public static boolean checkFormat(String str) {
            // У���Ƿ��ԡ�=����β
            if ('=' != str.charAt(str.length() - 1)) {
                return false;
            }
            // У�鿪ͷ�Ƿ�Ϊ���ֻ��ߡ�(��
            if (!(isCharNum(str.charAt(0)) || str.charAt(0) == '(')) {
                return false;
            }
            char c;
            // У��
            for (int i = 1; i < str.length() - 1; i++) {
                c = str.charAt(i);
                if (!isCorrectChar(c)) {// �ַ����Ϸ�
                    return false;
                }
                if (!(isCharNum(c))) {
                    if (c == '-' || c == '+' || c == '*' || c == '/') {
                        if (c == '-' && str.charAt(i - 1) == '(') {// 1*(-2+3)�����
                            continue;
                        }
                        if (!(isCharNum(str.charAt(i - 1)) || str.charAt(i - 1) == ')')) {// ������ǰһ���������ֻ��ߡ�����ʱ
                            return false;
                        }
                    }
                    if (c == '.') {
                        if (!isCharNum(str.charAt(i - 1)) || !isCharNum(str.charAt(i + 1))) {// У�顰.����ǰ���Ƿ�λ����
                            return false;
                        }
                    }
                }
            }
            return isBracketCouple(str);// У�������Ƿ����
        }

        public static String change2StandardFormat(String str) {
            StringBuilder sb = new StringBuilder();
            char c;
            for (int i = 0; i < str.length(); i++) {
                c = str.charAt(i);
                if (i != 0 && c == '(' && (isCharNum(str.charAt(i - 1)) || str.charAt(i - 1) == ')')) {
                    sb.append("*(");
                    continue;
                }
                if (c == '-' && str.charAt(i - 1) == '(') {
                    sb.append("0-");
                    continue;
                }
                sb.append(c);
            }
            return sb.toString();
        }

        public static boolean isBracketCouple(String str) {
            LinkedList<Character> stack = new LinkedList<>();
            for (char c : str.toCharArray()) {
                if (c == '(') {
                    stack.add(c);
                } else if (c == ')') {
                    if (stack.isEmpty()) {
                        return false;
                    }
                    stack.removeLast();
                }
            }
            if (stack.isEmpty()) {
                return true;
            } else {
                return false;
            }

        }

        public static boolean isCorrectChar(Character c) {
            if (('0' <= c && c <= '9') || c == '-' || c == '+' || c == '*' || c == '/' || c == '(' || c == ')'
                    || c == '.') {
                return true;
            }
            return false;
        }

        public static boolean isCharNum(Character c) {
            if (c >= '0' && c <= '9') {
                return true;
            }
            return false;

        }

        public static double plus(double num1, double num2) {
            return num1 + num2;
        }

        public static double reduce(double num1, double num2) {
            return num1 - num2;
        }

        public static double multiply(double num1, double num2) {
            return num1 * num2;

        }

        public static double divide(double num1, double num2) {
            return num1 / num2;
        }
    }
}

/*
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
 
    private enum Operand {
        ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"),
        SEVEN("7"), EIGHT("8"), NINE("9"), ZERO("0"), POINT(".");
        private String operand;
        private Operand(String operand) {
            this.operand = operand;
        }
    }
    private Operator getOperator(String str) {
        for (Operator op : Operator.values()) {
            if (str.equals(op.operator)) {
                return op;
            }
        }
        return null;
    }

    private Operand getOperand(String str) {
        for (Operand op : Operand.values()) {
            if (str.equals(op.operand)) {
                return op;
            }
        }
        return null;
    }   

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
}
*/