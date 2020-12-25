package pers.sfc.execute;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Calculator {
    public Double calculator(String str) {
        // 空值校验
        if (null == str || "".equals(str)) {
            return null;
        }
        // 长度校验
        if (str.length() > MyUtils.FORMAT_MAX_LENGTH) {
            System.out.println("表达式过长！");
            return null;
        }
        // 预处理
        str = str.replaceAll(" ", "");// 去空格
        if ('-' == str.charAt(0)) {// 开头为负数，如-1，改为0-1
            str = 0 + str;
        }
        // 校验格式
        if (!MyUtils.checkFormat(str)) {
            System.out.println("表达式错误！");
            return null;
        }
        // 处理表达式，改为标准表达式
        str = MyUtils.change2StandardFormat(str);
        // 拆分字符和数字
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
        LinkedList<Character> symStack = new LinkedList<>();// 符号栈
        LinkedList<Double> numStack = new LinkedList<>();// 数字栈
        int i = 0;// numLst的标志位
        int j = 0;// symStr的标志位
        char sym;// 符号
        double num1, num2;// 符号前后两个数
        while (symStack.isEmpty() || !(symStack.getLast() == '=' && symStr.charAt(j) == '=')) {// 形如：
                                                                                                // =8=
                                                                                                // 则退出循环，结果为8
            if (symStack.isEmpty()) {
                symStack.add('=');
                numStack.add(numLst.get(i++));
            }
            if (MyUtils.symLvMap.get(symStr.charAt(j)) > MyUtils.symLvMap.get(symStack.getLast())) {// 比较符号优先级，若当前符号优先级大于前一个则压栈
                if (symStr.charAt(j) == '(') {
                    symStack.add(symStr.charAt(j++));
                    continue;
                }
                numStack.add(numLst.get(i++));
                symStack.add(symStr.charAt(j++));
            } else {// 当前符号优先级小于等于前一个 符号的优先级
                if (symStr.charAt(j) == ')' && symStack.getLast() == '(') {// 若（）之间没有符号，则“（”出栈
                    j++;
                    symStack.removeLast();
                    continue;
                }
                if (symStack.getLast() == '(') {// “（”直接压栈
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
                    if (num2 == 0) {// 除数为0
                        System.out.println("存在除数为0");
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
        public static final int FORMAT_MAX_LENGTH = 500;// 表达式最大长度限制
        public static final Map<Character, Integer> symLvMap = new HashMap<Character, Integer>();// 符号优先级map
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
            // 校验是否以“=”结尾
            if ('=' != str.charAt(str.length() - 1)) {
                return false;
            }
            // 校验开头是否为数字或者“(”
            if (!(isCharNum(str.charAt(0)) || str.charAt(0) == '(')) {
                return false;
            }
            char c;
            // 校验
            for (int i = 1; i < str.length() - 1; i++) {
                c = str.charAt(i);
                if (!isCorrectChar(c)) {// 字符不合法
                    return false;
                }
                if (!(isCharNum(c))) {
                    if (c == '-' || c == '+' || c == '*' || c == '/') {
                        if (c == '-' && str.charAt(i - 1) == '(') {// 1*(-2+3)的情况
                            continue;
                        }
                        if (!(isCharNum(str.charAt(i - 1)) || str.charAt(i - 1) == ')')) {// 若符号前一个不是数字或者“）”时
                            return false;
                        }
                    }
                    if (c == '.') {
                        if (!isCharNum(str.charAt(i - 1)) || !isCharNum(str.charAt(i + 1))) {// 校验“.”的前后是否位数字
                            return false;
                        }
                    }
                }
            }
            return isBracketCouple(str);// 校验括号是否配对
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
}
*/