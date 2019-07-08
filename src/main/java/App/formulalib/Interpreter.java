package App.formulalib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Interpreter {
    private DataBase db = new DataBase();

    //搜索变量字符串(至少有一位)
    private static String findvar(String str) {
        int lengthOfvar = 1;
        int lengthOfstr = str.length();
        for (; lengthOfvar < lengthOfstr; lengthOfvar++) {
            switch (str.charAt(lengthOfvar)) {
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                case '(':
                case ')': {
                    return str.substring(0, lengthOfvar);
                }
            }
        }
        if (lengthOfvar == lengthOfstr) {
            return str;
        } else {
            throw new IllegalArgumentException("非法字符串，找不到变量");
        }

    }

    /**
     * 解释器主入口
     *
     * @param inVar
     * @return double 最终计算结果
     */
    double varToValue(String inVar) throws LogicalException {
        String numLine = varToexpression(varFormat(inVar));
        Calculator a = new Calculator();
        return a.Calcu(numLine);
    }

    //变量文本格式化器
    private String varFormat(String inVar) throws IllegalArgumentException {
        //匹配输入字符串前后的控制符并删除
        String spaceEx = "^(\\s*)|(\\s*)$";
        final Pattern spacePattern = Pattern.compile(spaceEx);
        Matcher spaceRemover = spacePattern.matcher(inVar);
        inVar = spaceRemover.replaceAll("");
        //变量名合法性检查
        String regEx = "^[A-Za-z][A-Za-z0-9]{0,2}";//正则表达式:只允许大小写字母开头，由大小写字母与0-9数字构成的1-3位字符串
        final Pattern varPattern = Pattern.compile(regEx);
        Matcher varChecker = varPattern.matcher(inVar);
        if (varChecker.matches()) {
            return inVar;
        } else throw new IllegalArgumentException("查询变量名非法，请检查输入");
    }

    //计算判断变量的实际值
    private double getJudgeValue(int varID) throws LogicalException {
        Calculator a = new Calculator();
        return a.Calcu(toExpression(db.getVar(varID)));
    }

    /**
     * 规则判断，判断适用哪一条规则
     * 不允许有多条适用规则
     *
     * @param varID 待判断的变量ID
     * @return 应该采用代数式的ID
     * @throws IllegalArgumentException
     */
    private int ruleJudgment(int varID) throws IllegalArgumentException, LogicalException {
        Rules judge = new Rules(varID);
        int amount = judge.amount();
        //如果变量对应公式没有规则，视为有且仅有一条公式，即返回该公式的ID
        if (amount == 0) {
            return db.getFirstFormulaID(varID);
        } else {
            for (int i = 0; i < amount; i++) {
                double judgeValue = getJudgeValue(judge.getJudgeVarID(i));
                switch (judge.getRuleState(i)) {
                    case 1: {
                        if (judgeValue >= judge.getLowerLimit(i) && judgeValue < judge.getUpperLimit(i))
                            return judge.getFormulaID(i);
                    }
                    break;
                    case 2: {
                        if (judgeValue >= judge.getLowerLimit(i)) return judge.getFormulaID(i);
                    }
                    break;
                    case 3: {
                        if (judgeValue < judge.getUpperLimit(i)) return judge.getFormulaID(i);
                    }
                    break;
                    case 4: {
                        if (judgeValue == judge.getLowerLimit(i)) return judge.getFormulaID(i);
                    }
                }
            }
        }
        throw new IllegalArgumentException("公式库中没有该变量的计算公式");
    }

    //将字符变量展开为只包含数的代数式
    String varToexpression(String var) throws LogicalException {
        return toExpression(db.getVar(var));
    }

    //将字符ID展开为只包含数的代数式
    private String toExpression(Vari Var) throws LogicalException {
        if (Var.getIsCalculated()) {
            String strFormula = db.getAlgebraic(ruleJudgment(Var.getVariableID()));
            StringBuilder varExpress = new StringBuilder("(");
            varExpress.append(formulaAnalyzer(strFormula)).append(")");
            return varExpress.toString();
        } else {
            return Double.toString(LibraryManager.getValue(Var));
        }
    }

    //公式解析
    private String formulaAnalyzer(String strFormula) throws LogicalException {
        int lengthOfFormula = strFormula.length();
        int lengthOfFrag = 0;
        StringBuffer processedFormula = new StringBuffer();
        for (int i = 0; i < lengthOfFormula; i++) {
            //文本过滤器
            switch (strFormula.charAt(i)) {
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                case '(':
                case ')':
                case '.':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9': {
                    lengthOfFrag++;
                    break;
                }
                case 'S': {
                    if (strFormula.substring(i, i + 3).equals("SIN")) {
                        lengthOfFrag = lengthOfFrag + 3;
                        i = i + 2;//TODO
                        break;
                    }
                }
                case 'C': {
                    if (strFormula.substring(i, i + 3).equals("COS")) {
                        lengthOfFrag = lengthOfFrag + 3;
                        i = i + 2;//TODO
                        break;
                    }
                }
                case 'T': {
                    if (strFormula.substring(i, i + 3).equals("TAN")) {
                        lengthOfFrag = lengthOfFrag + 3;
                        i = i + 2;//TODO
                        break;
                    }
                }
                default: {
                    if (i - lengthOfFrag >= 0) {
                        processedFormula.append(strFormula, i - lengthOfFrag, i);
                        String varNew = findvar(strFormula.substring(i));
                        processedFormula.append(varToexpression(varNew));
                        i = i + varNew.length() - 1;
                        lengthOfFrag = 0;
                    } else throw new LogicalException("出现未知错误");
                }
            }
        }
        if (lengthOfFrag != 0) {
            processedFormula.append(strFormula, lengthOfFormula - lengthOfFrag, lengthOfFormula);
        }
        return processedFormula.toString();
    }
}
