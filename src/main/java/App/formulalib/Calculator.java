package App.formulalib;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

class Calculator {
    /**
     * 计算入口
     * 三角函数按角度制
     *
     * @param input
     * @return
     */
    double Calcu(String input) throws LogicalException {
        //计算多字节运算符表达式
        ArrayList<Double> factors = new ArrayList();
        ArrayList<Integer> operators = new ArrayList();
        StringBuffer transform_Algebraic = CompliOpera(input, "SIN");
        transform_Algebraic = CompliOpera(transform_Algebraic.toString(), "COS");
        transform_Algebraic = CompliOpera(transform_Algebraic.toString(), "TAN");
        transform_Algebraic = CompliOpera(transform_Algebraic.toString(), "SQRT");
        transform_Algebraic = BracketCompu(transform_Algebraic.toString());
        int lengthOfalgebraic = transform_Algebraic.length();
        //在计算之前，必须确保代数式只包含四则运算和乘方，开头必须是数
        int lengthOfnum = 0;
        for (int i = 0; i < lengthOfalgebraic; i++) {
            //公式分解，+-*/^分别对应int 1、2、3、4、5
            switch (transform_Algebraic.charAt(i)) {
                case '+': {
                    factors.add(NumberStr2Dou(transform_Algebraic.substring(i - lengthOfnum, i)));
                    operators.add(1);
                    lengthOfnum = 0;
                    break;
                }
                case '-': {
                    //负数判断
                    if (i == 0) {
                        lengthOfnum++;
                        break;
                    }
                    switch (transform_Algebraic.charAt(i - 1)) {
                        case '+':
                        case '-':
                        case '*':
                        case '/':
                        case '^':
                            lengthOfnum++;
                            break;
                        default: {
                            factors.add(NumberStr2Dou(transform_Algebraic.substring(i - lengthOfnum, i)));
                            operators.add(2);
                            lengthOfnum = 0;
                        }
                        break;
                    }
                    break;
                }
                case '*': {
                    factors.add(NumberStr2Dou(transform_Algebraic.substring(i - lengthOfnum, i)));
                    operators.add(3);
                    lengthOfnum = 0;
                    break;
                }
                case '/': {
                    factors.add(NumberStr2Dou(transform_Algebraic.substring(i - lengthOfnum, i)));
                    operators.add(4);
                    lengthOfnum = 0;
                    break;
                }
                case '^': {
                    factors.add(NumberStr2Dou(transform_Algebraic.substring(i - lengthOfnum, i)));
                    operators.add(5);
                    lengthOfnum = 0;
                    break;
                }
                default: {
                    lengthOfnum++;
                    break;
                }
            }
        }
        if (lengthOfnum != 0) {
            factors.add(NumberStr2Dou(transform_Algebraic.substring(transform_Algebraic.length() - lengthOfnum)));
            lengthOfnum = 0;
        }
        return Arithmetic(factors, operators);
    }

    /**
     * String转double的方法
     *
     * @param str
     * @return double
     */
    private double NumberStr2Dou(String str) {
        return Double.parseDouble(str);
    }

    /**
     * 多字符运算符解析
     *
     * @param so     输入字符串
     * @param Operat 要处理的多字符运算符
     * @return 处理过的字符串
     */
    private StringBuffer CompliOpera(String so, @NotNull String Operat) throws LogicalException {
        int num_Sym = Operat.length();
        StringBuffer No_fuction = new StringBuffer();
        StringBuffer Buffer = new StringBuffer(so);
        int length_so = so.length();
        //检查是否含有指定运算符，没有则直接返回
        if (Buffer.indexOf(Operat) == -1) {
            return Buffer;
        } else {
            for (int i = 0; i < length_so; i++) {
                int ind = Buffer.indexOf(Operat);
                //如果没有查到指定多字符运算符的存在，跳出循环
                if (ind == -1) {
                    break;
                }
                No_fuction.append(Buffer.substring(0, ind));
                Buffer.delete(0, ind + num_Sym);
                String con_Bracket = BracketAnalysis(Buffer.toString());
                double result_Bracket = Calcu(con_Bracket);
                double result = -666666;
                //多字符运算符的计算，包含sin、cos、tan、算术平方根，按角度值计算
                switch (Operat) {
                    case "SIN":
                        result = Math.sin(result_Bracket / 180 * Math.PI);
                        break;
                    case "COS":
                        result = Math.cos(result_Bracket / 180 * Math.PI);
                        break;
                    case "TAN":
                        result = Math.tan(result_Bracket / 180 * Math.PI);
                        break;
                    case "SQRT":
                        result = Math.sqrt(result_Bracket / 180 * Math.PI);
                        break;
                    default:
                        throw new LogicalException("有非法运算符进入运算器");
                }
                No_fuction.append(result);
                Buffer.delete(0, con_Bracket.length() + 2);//删除括号内内容及其前后方的“（）”

            }
            No_fuction.append(Buffer);
            return No_fuction;
        }
    }

    /**
     * 括号优先级的解析与计算
     *
     * @param so 输入字符串
     * @return 不包含括号的字符串
     */
    private StringBuffer BracketCompu(String so) throws LogicalException {
        StringBuffer No_bracket = new StringBuffer();
        StringBuffer Buffer = new StringBuffer(so);
        int length_so = so.length();
        //检查是否含有左括号，没有则直接返回
        if (Buffer.indexOf("(") == -1) {
            return Buffer;
        } else {
            for (int i = 0; i < length_so; i++) {
                int ind = Buffer.indexOf("(");
                //如果没有查到左括号的存在，跳出循环
                if (ind == -1) {
                    break;
                }
                No_bracket.append(Buffer.substring(0, ind));
                Buffer.delete(0, ind);
                String con_Bracket = BracketAnalysis(Buffer.toString());
                double result = Calcu(con_Bracket);
                No_bracket.append(result);
                Buffer.delete(0, con_Bracket.length() + 2);//删除括号内容及其前后方的“（）”
            }
            No_bracket.append(Buffer);
            return No_bracket;
        }
    }

    /**
     * 取得传入字符串最外层括号对内部的字符串（传入字符串必须是"（"开头的）
     *
     * @param target 传入字符串
     * @return 括号对内的字符串
     */
    private String BracketAnalysis(@NotNull String target) throws LogicalException {
        int target_length = target.length();
        int output_length = 0;//比实际字符串长度多1
        for (int i = 0, j = 0; i < target_length; i++) {
            if (target.charAt(i) == '(') {
                j++;
            } else if (target.charAt(i) == ')') {
                j--;
                if (j == 0) {
                    output_length = i;
                    break;
                }
            }
             if(j<0) {
                throw new LogicalException("括号不成对");
            }
        }
        String output = target.substring(1, output_length);
        return output;
    }

    /**
     * 私有的计算方法，不可复用
     *
     * @param factors   因子队列
     * @param operators 运算符队列
     * @return 计算结果
     */
    private double Arithmetic(ArrayList<Double> factors, @NotNull ArrayList<Integer> operators) throws LogicalException {
        int counter = operators.size();
        for (int j = 0; j < counter; j++) {
            int i = operators.indexOf(5);
            if (i == -1) {
                break;
            }
            factors.set(i, Math.pow(factors.get(i), factors.get(i + 1)));
            operators.remove(i);
            factors.remove(i + 1);
        }
        //乘除同级，按顺序计算
        for (int k = 0; k < counter; k++) {
            int i = operators.indexOf(3), j = operators.indexOf(4);
            if (i == -1) {
                if (j == -1) {
                    break;
                } else {
                    factors.set(j, factors.get(j) / factors.get(j + 1));
                    operators.remove(j);
                    factors.remove(j + 1);
                }
            } else if (j == -1 || i < j) {
                factors.set(i, factors.get(i) * factors.get(i + 1));
                operators.remove(i);
                factors.remove(i + 1);
            } else {
                factors.set(j, factors.get(j) / factors.get(j + 1));
                operators.remove(j);
                factors.remove(j + 1);
            }
        }
        //加减同级，按顺序计算
        for (int k = 0; k < counter; k++) {
            int i = operators.indexOf(1), j = operators.indexOf(2);
            if (i == -1) {
                if (j == -1) {
                    break;
                } else {
                    factors.set(j, factors.get(j) - factors.get(j + 1));
                    operators.remove(j);
                    factors.remove(j + 1);
                }
            } else if (j == -1) {
                factors.set(i, factors.get(i) + factors.get(i + 1));
                operators.remove(i);
                factors.remove(i + 1);
            } else if (i < j) {
                factors.set(i, factors.get(i) + factors.get(i + 1));
                operators.remove(i);
                factors.remove(i + 1);
            } else {
                factors.set(j, factors.get(j) - factors.get(j + 1));
                operators.remove(j);
                factors.remove(j + 1);
            }
        }
        if (operators.isEmpty()) {
            return factors.get(0);
        } else {
            throw new LogicalException("计算错误");
        }
    }
}
