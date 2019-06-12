package App.equation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpressionParser {

    // 可变字符串，储存临时变量名
    private static StringBuffer tempVariable = new StringBuffer();

    // 可变字符串，储存临时数字
    private static StringBuffer tempDigit = new StringBuffer();

    // 遇到空格则为true
    private static boolean spaceFlag = false;

    // 遇到运算符则为true
    private static boolean operatorFlag = false;

    /**
     * 对字符串进行词法分析。
     * 若合法：分割成单个元素，存入数组并返回。
     * 若非法：报相应的错误。
     *
     * @param expression 需要分析的字符串，可以是‘变量名’或‘某个变量的代数式’。
     * @return 返回经过词法分析后的字符串队列。
     */
    public static List<String> wordAnalysis(String expression) throws MyException {

        if ("".equals(expression) || expression == null) {
            IllegalInfo.setIllegalInfo("输入不能为空!");
            throw new MyException(IllegalInfo.getIllegalInfo());
        }

        isBracketLegal(expression);

        //用来存放分析结果
        List<String> result = new ArrayList<>();

        for (int i = 0; i < expression.length(); i++) {

            char ch = expression.charAt(i);

            /**
             * 判断代数式是否以非法字符开头
             */
            if (i == 0 && (ch == '^' || ch == '*' || ch == '/' || ch == ' ' || ch == '.')) {
                String temp;
                if (ch == '^') {
                    temp = "^";
                } else {
                    if (ch == '*') {
                        temp = "*";
                    } else {
                        if (ch == '/') {
                            temp = "/";
                        } else {
                            if (ch == ' ') {
                                temp = "空格";
                            } else {
                                temp = ".";
                            }
                        }
                    }
                }
                IllegalInfo.setIllegalInfo("公式不能以" + temp + "开头!");
                throw new MyException(IllegalInfo.getIllegalInfo());
            }

            /**
             * 如果ch是字母，执行该if
             */
            if (isLetter(ch)) {
                operatorFlag = false;
                if (!tempVariable.toString().equals("")) {// 如果tempVariable不为空，执行该if
                    if (spaceFlag) {// 字母-空白符-字母，报错，程序停止
                        tempVariable.setLength(0);
                        tempDigit.setLength(0);
                        IllegalInfo.setIllegalInfo("字母之间不能有空格!");
                        throw new MyException(IllegalInfo.getIllegalInfo());
                    } else {// 字母-字母，正常，添加进去，然后跳过该次循环。
                        tempVariable.append(ch);
                        continue;
                    }
                }

                if (!tempDigit.toString().equals("")) {// 如果tempDigit不为空，执行该if
                    if (spaceFlag) {// 数字-空白符-字母
                        tempVariable.setLength(0);
                        tempDigit.setLength(0);
                        IllegalInfo.setIllegalInfo("数字与字母之间不能只有空格!");
                        throw new MyException(IllegalInfo.getIllegalInfo());
                    } else {// 数字-字母
                        tempVariable.setLength(0);
                        tempDigit.setLength(0);
                        IllegalInfo.setIllegalInfo("数字后不能直接接字母!");
                        throw new MyException(IllegalInfo.getIllegalInfo());
                    }
                }
                // 如果tempVariable和tempDigit都为空，则执行以下三句。
                tempVariable.append(ch);
                spaceFlag = false;
                continue;
            }

            /**
             * 是数字
             */
            if (isDigit(ch)) {
                operatorFlag = false;
                if (!tempVariable.toString().equals("")) {
                    // 字母-空白符-数字
                    if (spaceFlag) {
                        tempVariable.setLength(0);
                        tempDigit.setLength(0);
                        IllegalInfo.setIllegalInfo("字母与数字之间不能有空格!");
                        throw new MyException(IllegalInfo.getIllegalInfo());
                    }
                    // 字母-数字
                    else {
                        tempVariable.append(ch);
                        continue;
                    }
                }
                if (!tempDigit.toString().equals("")) {
                    // 数字-空白符-数字
                    if (spaceFlag) {
                        tempVariable.setLength(0);
                        tempDigit.setLength(0);
                        IllegalInfo.setIllegalInfo("数字之间不能有空格!");
                        throw new MyException(IllegalInfo.getIllegalInfo());
                    }
                    // 数字-数字
                    else {
                        tempDigit.append(ch);
                        continue;
                    }
                }
                tempDigit.append(ch);
                spaceFlag = false;
                continue;
            }

            // 是空白符，记录出现了空格符，然后继续循环
            if (isSpace(ch)) {
                spaceFlag = true;
                continue;
            }

            //是小数点
            if (isPoint(ch)) {
                if (operatorFlag) {
                    IllegalInfo.setIllegalInfo("运算符后不能跟小数点!");
                    throw new MyException(IllegalInfo.getIllegalInfo());
                }
                if (spaceFlag) {
                    tempVariable.setLength(0);
                    tempDigit.setLength(0);
                    IllegalInfo.setIllegalInfo("小数点前不能有空格!");
                    throw new MyException(IllegalInfo.getIllegalInfo());
                } else {
                    if (!tempVariable.toString().equals("")) {
                        tempVariable.setLength(0);
                        tempDigit.setLength(0);
                        IllegalInfo.setIllegalInfo("字母后不能接小数点!");
                        throw new MyException(IllegalInfo.getIllegalInfo());
                    }
                    if (!tempDigit.toString().equals("")) {
                        if (tempDigit.toString().contains(".")) {
                            tempVariable.setLength(0);
                            tempDigit.setLength(0);
                            IllegalInfo.setIllegalInfo("小数点不能连续出现!");
                            throw new MyException(IllegalInfo.getIllegalInfo());
                        }
                        tempDigit.append(ch);
                        continue;
                    }
                }
//                operatorFlag = false;//-----------------------------------------
            }

            //是运算符
            if (isOperator(ch)) {
                if (operatorFlag) {
                    IllegalInfo.setIllegalInfo("运算符不能相连!");
                    throw new MyException(IllegalInfo.getIllegalInfo());
                }
                spaceFlag = false;
                //变量寄存器中存有变量
                if (!tempVariable.toString().equals("")) {
                    result.add(tempVariable.toString());
                    //清空变量寄存器
                    tempVariable.setLength(0);
                }
                //数字寄存器中存有数字
                if (!tempDigit.toString().equals("")) {
                    if (tempDigit.toString().endsWith(".")) {
                        tempVariable.setLength(0);
                        tempDigit.setLength(0);
                        IllegalInfo.setIllegalInfo("数字不能以小数点结尾!");
                        throw new MyException(IllegalInfo.getIllegalInfo());
                    }
                    result.add(tempDigit.toString());
                    //清空变量寄存器
                    tempDigit.setLength(0);
                }
                result.add(String.valueOf(ch));
                operatorFlag = true;
                continue;
            }

            if (isBracket(ch)) {
                operatorFlag = false;
                if (!tempVariable.toString().equals("")) {
                    result.add(tempVariable.toString());
                    tempVariable.setLength(0);
                }
                if (!tempDigit.toString().equals("")) {
                    result.add(tempDigit.toString());
                    tempDigit.setLength(0);
                }
                result.add(String.valueOf(ch));
                continue;
            }

            tempVariable.setLength(0);
            tempDigit.setLength(0);
            IllegalInfo.setIllegalInfo("包含未知类型字符!");
            throw new MyException(IllegalInfo.getIllegalInfo());
        }

        //变量寄存器中存有变量
        if (!tempVariable.toString().equals("")) {
            result.add(tempVariable.toString());
            //清空变量寄存器
            tempVariable.setLength(0);
        }

        //数字寄存器中存有数字
        if (!tempDigit.toString().equals("")) {
            if (tempDigit.toString().endsWith(".")) {
                tempVariable.setLength(0);
                tempDigit.setLength(0);
                IllegalInfo.setIllegalInfo("数字不能以小数点结尾!");
                throw new MyException(IllegalInfo.getIllegalInfo());
            }
            result.add(tempDigit.toString());
            tempDigit.setLength(0);
        }
        return result;
    }

    private static void isBracketLegal(String expression) throws MyException {
        int sumIndexOfBracket = 0;
        List<Integer> indexOfBracket = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {

            char ch = expression.charAt(i);
            if (ch == "(".charAt(0)) {
                indexOfBracket.add(i);// 获取字符串中所有括号的下标。
                sumIndexOfBracket++;
                if (i == 0) {
                    isLR(expression.charAt(i + 1));
                } else {
                    isLL(expression.charAt(i - 1));
                    isLR(expression.charAt(i + 1));
                }
            } else if (ch == ")".charAt(0)) {
                indexOfBracket.add(i);
                sumIndexOfBracket--;
                if (i < expression.length() - 1) {
                    isRL(expression.charAt(i - 1));
                    isRR(expression.charAt(i + 1));
                } else {
                    isRL(expression.charAt(i - 1));
                }
            }
            if (!indexOfBracket.isEmpty()) {
                if (expression.charAt(indexOfBracket.get(0)) == ')') {
                    IllegalInfo.setIllegalInfo("')'不能先于'('出现!");
                    throw new MyException(IllegalInfo.getIllegalInfo());
                }
            }
            if (sumIndexOfBracket < 0) {
                IllegalInfo.setIllegalInfo("括号顺序错误!");
                throw new MyException(IllegalInfo.getIllegalInfo());
            } else if (i == expression.length() - 1 && sumIndexOfBracket != 0) {
                IllegalInfo.setIllegalInfo("括号不成对!");
                throw new MyException(IllegalInfo.getIllegalInfo());
            }
        }
    }

    /**
     * 判断左括号左边的一个字符是否合法。
     *
     * @param ch
     * @throws MyException
     */
    private static void isLL(char ch) throws MyException {
        if (isLetter(ch) || isDigit(ch) || isPoint(ch)) {
            IllegalInfo.setIllegalInfo("'('的左边不能接字母、数字、小数点!");
            throw new MyException(IllegalInfo.getIllegalInfo());
        }
    }

    /**
     * 判断左括号右边的一个字符是否合法。
     *
     * @param ch
     * @throws MyException
     */
    private static void isLR(char ch) throws MyException {
        if (isPoint(ch) || ch == ')' || ch == '^' || ch == '*' || ch == '/') {
            IllegalInfo.setIllegalInfo("'('的右边不能接')'、'^'、'*'、'/'!");
            throw new MyException(IllegalInfo.getIllegalInfo());
        }
    }

    /**
     * 判断右括号左边的一个字符是否合法。
     *
     * @param ch
     * @throws MyException
     */
    private static void isRL(char ch) throws MyException {
        if (isPoint(ch) || isOperator(ch)) {
            IllegalInfo.setIllegalInfo("')'的左边不能接小数点和运算符!");
            throw new MyException(IllegalInfo.getIllegalInfo());
        }
    }

    /**
     * 判断右括号右边的一个字符是否合法。
     *
     * @param ch
     * @throws MyException
     */
    private static void isRR(char ch) throws MyException {
        if (isPoint(ch) || isLetter(ch) || isDigit(ch)) {
            IllegalInfo.setIllegalInfo("')'的右边不能接字母、数字、小数点!");
            throw new MyException(IllegalInfo.getIllegalInfo());
        }
    }

    /**
     * 判断是否是字母
     *
     * @param ch 需要判断的字符
     * @return true代表是字母，false代表不是字母
     */
    private static boolean isLetter(char ch) {
        if ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是数字
     *
     * @param ch 需要判断的字符
     * @return true代表是数字，false代表不是数字
     */
    private static boolean isDigit(char ch) {
        if (ch >= 48 && ch <= 57) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是空格或换行
     *
     * @param ch 需要判断的字符
     * @return true代表是空白符，false代表不是
     */
    private static boolean isSpace(char ch) {
        if (ch == 32 || ch == 10) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是小数点
     *
     * @param ch 需要判断的字符
     * @return true代表是小数点，false代表不是
     */
    private static boolean isPoint(char ch) {
        if (ch == 46) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是运算符。
     *
     * @param ch 需要判断的字符
     * @return true代表是运算符，false代表不是
     */
    private static boolean isOperator(char ch) {
        List<String> list = new ArrayList<>(Arrays.asList("+", "-", "*", "/", "^"));
        return list.contains(String.valueOf(ch));
    }

    /**
     * 判断是否为括号。
     *
     * @param ch 需要判断的字符
     * @return true代表是括号，false代表不是。
     */
    private static boolean isBracket(char ch) {
        List<String> list = new ArrayList<>(Arrays.asList("(", ")"));
        return list.contains(String.valueOf(ch));
    }
}
