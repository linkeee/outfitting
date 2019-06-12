package App.equation;

import java.util.*;

public class Test {
    public static void main(String[] args) throws MyException {
        String expression = "7.5*(-y)";
        List<String> list = ExpressionParser.wordAnalysis(expression);
        for (String str : list) {
            System.out.print(str+"  ");
        }
    }
}






































//    private static void isBracketLegal(String expression) throws MyException {
//        int sumIndexOfBracket = 0;
//        List<Integer> indexOfBracket = new ArrayList<>();
//        for (int i = 0; i < expression.length(); i ++) {
//
//            char ch = expression.charAt(i);
//            if (ch == "(".charAt(0)) {
//                indexOfBracket.add(i);// 获取字符串中所有括号的下标。
//                sumIndexOfBracket++;
//                if (i == 0) {
//                    isLR(expression.charAt(i+1));
//                } else {
//                    isLL(expression.charAt(i-1));
//                    isLR(expression.charAt(i+1));
//                }
//            } else if (ch == ")".charAt(0)) {
//                indexOfBracket.add(i);
//                sumIndexOfBracket--;
//                if (i < expression.length()-1) {
//                    isRL(expression.charAt(i-1));
//                    isRR(expression.charAt(i+1));
//                } else {
//                    isRL(expression.charAt(i-1));
//                }
//            }
//            if (!indexOfBracket.isEmpty()) {
//                if (expression.charAt(indexOfBracket.get(0)) == ')') {
//                    Validation.setIllegalInfo("')'不能先于'('出现!");
//                    throw new MyException(Validation.getIllegalInfo());
//                }
//            }
//            if (sumIndexOfBracket < 0) {
//                Validation.setIllegalInfo("括号顺序错误!");
//                throw new MyException(Validation.getIllegalInfo());
//            } else if (i == expression.length()-1 && sumIndexOfBracket != 0) {
//                Validation.setIllegalInfo("括号不成对!");
//                throw new MyException(Validation.getIllegalInfo());
//            }
//        }
//
//        for (Integer integer : indexOfBracket) {
//            System.out.println(integer);
//            if (integer == 0) {
//            }
//        }
//    }
//
//    private static void isLL(char ch) throws MyException {
//        if (isLetter(ch) || isDigit(ch) || isPoint(ch)) {
//            Validation.setIllegalInfo("'('的左边不能接字母、数字、小数点!");
//            throw new MyException(Validation.getIllegalInfo());
//        }
//    }
//
//    private static void isLR(char ch) throws MyException {
//        if (isPoint(ch) || ch == ')' || ch == '^' || ch == '*' || ch == '/') {
//            Validation.setIllegalInfo("'('的右边不能接')'、'^'、'*'、'/'!");
//            throw new MyException(Validation.getIllegalInfo());
//        }
//    }
//
//    private static void isRL(char ch) throws MyException {
//        if (isPoint(ch) || isOperator(ch)) {
//            Validation.setIllegalInfo("')'的左边不能接小数点和运算符!");
//            throw new MyException(Validation.getIllegalInfo());
//        }
//    }
//
//    private static void isRR(char ch) throws MyException {
//        if (isPoint(ch) || isLetter(ch) || isDigit(ch)) {
//            Validation.setIllegalInfo("')'的右边不能接字母、数字、小数点!");
//            throw new MyException(Validation.getIllegalInfo());
//        }
//    }
//
//    /**
//     * 判断是否是字母
//     *
//     * @param ch 需要判断的字符
//     * @return true代表是字母，false代表不是字母
//     */
//    private static boolean isLetter(char ch) {
//        if ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122)) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 判断是否是数字
//     *
//     * @param ch 需要判断的字符
//     * @return true代表是数字，false代表不是数字
//     */
//    private static boolean isDigit(char ch) {
//        if (ch >= 48 && ch <= 57) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 判断是否是小数点
//     *
//     * @param ch 需要判断的字符
//     * @return true代表是小数点，false代表不是
//     */
//    private static boolean isPoint(char ch) {
//        if (ch == 46) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 判断是否是运算符。
//     *
//     * @param ch 需要判断的字符
//     * @return true代表是运算符，false代表不是
//     */
//    private static boolean isOperator(char ch) {
//        List<String> list = new ArrayList<>(Arrays.asList("+", "-", "*", "/", "^"));
//        return list.contains(String.valueOf(ch));
//    }
//}



//            if (isBracket(ch)) {
//                int counter = 0;
//                char[] chars = expression.toCharArray();
//                if (chars[i] == "(".charAt(0)) {
//                    counter++;
//                } else if (chars[i] == ")".charAt(0)) {
//                    counter--;
//                }
//                if (counter < 0) {
//                    IllegalInfo.setIllegalInfo("括号不成对!");
//                    throw new MyException("fsegwdwaf括号不成对!");
//                }
//                if (i == chars.length - 1 && counter != 0) {
//                    IllegalInfo.setIllegalInfo("括号顺序有误!");
//                    System.out.println(IllegalInfo.getIllegalInfo());
//                    throw new MyException("fsegwdwaf括号顺序有误!");
//                }
//            }
