package App.formulalib;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Checker {
    /**
     * Checker内置数据库连接对象
     */
    private DataBase db = new DataBase();

    /**
     * 检查输入代数式的格式，合规则返回处理后的代数式
     *
     * @param newInput
     * @return String 处理后的代数式
     * @throws LogicalException
     */
    static String formulaFormatChecker(String newInput) throws LogicalException {
        String newFormula = fixComplexSymbol(removeSpaceCharacter(newInput));
        if (formulaSpellChecker(newFormula)) {
            if (isBracketSymmetric(newFormula)) {
                if (isSymbolCorrected(newFormula)) {
                    if (isCSymbolCorrected(newFormula, "SIN") && isCSymbolCorrected(newFormula, "COS") && isCSymbolCorrected(newFormula, "TAN")) {
                        return newFormula;
                    } else throw new LogicalException("代数式错误", "代数式中的含有非法函数！");
                } else throw new LogicalException("代数式错误", "代数式中运算符使用错误！");
            } else throw new LogicalException("代数式错误", "代数式中的括号使用错误！");
        } else throw new LogicalException("代数式错误", "代数式输入不正确！");
    }

    /**
     * 变量名拼写检查
     *
     * @param var 变量名字符串
     * @return Boolean
     */
    static boolean isVarSpellCorrected(String var) {
        String spellEx = "[A-Za-z]([\\w]|\\'){0,2}";//正则表达式,等价于"[A-Za-z][A-Za-z0-9]{0,2}":只允许大小写字母开头，由大小写字母与0-9数字构成的1-3位字符串
        Pattern varPattern = Pattern.compile(spellEx);
        Matcher varChecker = varPattern.matcher(var);
        Pattern cSymbol = Pattern.compile("(?i)(sin|cos|tan)");//匹配字符串中的sin、cos、tan，不区分大小写
        Matcher cSymbolChecker = cSymbol.matcher(var);
        return varChecker.matches() && !cSymbolChecker.matches();
    }

    /**
     * 去除变量字符串前后的空白字符
     *
     * @param str 输入字符串
     * @return
     */
    static String removeSpace(String str) {
        String spaceEx = "^(\\s*)|(\\s*)$";
        final Pattern spacePattern = Pattern.compile(spaceEx);
        Matcher spaceRemover = spacePattern.matcher(str);
        return spaceRemover.replaceAll("");
    }

    /**
     * 去除公式字符串内的空白字符
     *
     * @param sFormula
     * @return String 去掉空白字符后的字符串
     */
    static String removeSpaceCharacter(String sFormula) {
        final Pattern spacePattern = Pattern.compile("\\s*");
        Matcher spaceRemover = spacePattern.matcher(sFormula);
        return spaceRemover.replaceAll("");
    }

    /**
     * 公式字符串拼写检查
     *
     * @param formula 输入字符串
     * @return Boolean
     * todo 对带'变量的识别
     */
    private static boolean formulaSpellChecker(String formula) {
        String spellEx = "^[\\w(-][\\w+\\-*/^.()']*[\\w)]$";//正则表达式，公式中只能包含大小写字母、+-*/^()，且只能以\w、（-开头，\w、)结尾
        final Pattern formulaSpell = Pattern.compile(spellEx);
        Matcher spellChecker = formulaSpell.matcher(formula);
        return spellChecker.matches();
    }

    /**
     * 公式字符串中单字节运算符合法性检查
     * 可能有访问越界的bug，慢慢修吧
     *
     * @param formula 要检查式子的字符串
     * @return Boolean
     */
    static boolean isSymbolCorrected(String formula) {
        final Pattern okSym = Pattern.compile("[\\w]");
        int formulaLength = formula.length();
        //+_*/^后可跟-、(、\w，(后可跟-、\w，)后可跟+-*/^
        for (int i = 0; i < formulaLength; i++) {
            char a = formula.charAt(i);
            if (a == '+' || a == '-' || a == '*' || a == '/' || a == '^') {
                if (i == formulaLength - 1)
                    return false;
                else {
                    switch (formula.charAt(i + 1)) {
                        case '-': {
                            if (i == formulaLength - 2)
                                return false;
                            else {
                                Matcher m = okSym.matcher(formula.subSequence(i + 2, i + 3));
                                if (m.matches())
                                    i++;
                                else return false;
                            }
                        }
                        break;
                        case '(':
                            break;
                        default: {
                            Matcher m = okSym.matcher(formula.subSequence(i + 1, i + 2));
                            if (!m.matches()) return false;
                        }
                        break;
                    }
                }
            } else if (a == '(') {
                if (i == formulaLength - 1)
                    return false;
                else {
                    if (formula.charAt(i + 1) == '-') i++;
                    Matcher m = okSym.matcher(formula.subSequence(i + 1, i + 2));
                    if (m.matches()) i++;
                    else return false;
                }
            } else if (a == ')') {
                if (i == formulaLength - 1)
                    continue;
                else {
                    switch (formula.charAt(i + 1)) {
                        case '+':
                        case '-':
                        case '*':
                        case '/':
                        case '^':
                            break;
                        default:
                            return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 公式字符串中多字符运算符合法性检查,前面可以跟四则运算符、乘方、(，后面只能跟(
     *
     * @param formula     检查对象字符串
     * @param childString 多字符运算符字符串
     * @return Boolean
     */
    private static boolean isCSymbolCorrected(String formula, String childString) {
        int formulaLength = formula.length();
        int childLength = childString.length();
        for (int i = 0; i < formulaLength; i++) {
            i = formula.indexOf(childString, i);
            if (i == -1) break;
            else {
                if (i == 0) {
                    if (formula.charAt(i + childLength) == '(') continue;
                    else return false;
                } else {
                    if (formula.charAt(i - 1) == '+' || formula.charAt(i - 1) == '-' || formula.charAt(i - 1) == '*' || formula.charAt(i - 1) == '/' || formula.charAt(i - 1) == '^' || formula.charAt(i - 1) == '(') {
                        if (formula.charAt(i + childLength) == '(') continue;
                        else return false;
                    } else return false;
                }
            }
        }
        return true;
    }

    /**
     * 公式字符串中括号成对性检查
     *
     * @param str 要检查式子的字符串
     * @return Boolean
     * @throws IllegalArgumentException
     */
    private static boolean isBracketSymmetric(String str) throws IllegalArgumentException {
        int strLength = str.length();
        int i = str.indexOf('(');
        int count = 0;
        if (i == -1) {
            if (str.indexOf(')') == -1)
                return true;
            else throw new IllegalArgumentException("存在多余的“)”字符，请检查公式");
        }
        for (; i < strLength; i++) {
            if (str.charAt(i) == '(') {
                count++;
            } else if (str.charAt(i) == ')') {
                count--;
                if (count < 0) return false;
            }
        }
        return count == 0;
    }

    /**
     * 多字符运算符处理，去除所有的SIN\COS\TAN
     *
     * @param sFormula 原代数式字符串
     * @return String 去除SIN\COS\TAN后的字符串
     */
    static String removeComplexSymbol(String sFormula) {
        final Pattern symPattern = Pattern.compile("(?i)(sin)|(cos)|(cos)");
        Matcher symMatcher = symPattern.matcher(sFormula);
        return symMatcher.replaceAll("");
    }

    /**
     * 变量分解,不分正负
     *
     * @param formula 代数式字符串
     * @return 变量List
     */
    static List<String> varSplit(String formula) throws LogicalException {
        String[] a = formula.split("\\+|-|\\*|/|\\^|\\(|\\)");
        List<String> varList = new ArrayList<>();
        for (int i = 0, j = a.length; i < j; i++) {
            if (!a[i].equals("")) {
                if ((a[i].charAt(0) >= 'A' && a[i].charAt(0) <= 'Z') || (a[i].charAt(0) >= 'a' && a[i].charAt(0) <= 'z'))//todo//可能出错
                {
                    varList.add(a[i]);
                } else {
                    if (!isNumberCorrected(a[i]))
                        throw new LogicalException("数的拼写错误");//检查数的拼写
                }
            }
        }
        return varList;
    }

    /**
     * 数的拼写检查（忽略正负），整数1-9开头，小数的整数部分0-9开头，小数部分不含小数点
     *
     * @param num 数
     * @return Boolean
     */
    static boolean isNumberCorrected(String num) {
        if (num.indexOf('.') == -1) {
            if (num.charAt(0) > '0' && num.charAt(0) <= '9') {
                try {
                    Integer.valueOf(num);
                    return true;
                } catch (NumberFormatException e) {
                    Logger.getGlobal().log(Level.WARNING, "不合法的整数表达", e);
                    return false;
                }
            } else {
                Logger.getGlobal().log(Level.WARNING, "不合法的整数表达");
                return false;
            }
        } else {
            if (num.indexOf('.') == 1) {
                if (num.charAt(0) < '0' || num.charAt(0) > '9') {
                    Logger.getGlobal().warning("不合法的小数表达:" + num);
                    return false;
                } else {
                    try {
                        Double.valueOf(num);
                        return true;
                    } catch (NumberFormatException e) {
                        Logger.getGlobal().log(Level.WARNING, " 不是一个数", e);
                        return false;
                    }
                }
            } else {
                if (num.charAt(0) < '1' || num.charAt(0) > '9') {
                    Logger.getGlobal().warning("不合法的小数表达:" + num);
                    return false;
                }
                try {
                    Double.valueOf(num);
                    return true;
                } catch (NumberFormatException e) {
                    Logger.getGlobal().log(Level.WARNING, " 不是一个数", e);
                    return false;
                }
            }
        }
    }

    /**
     * 统计字符串中子串的个数
     *
     * @param targetString
     * @param childString
     * @return
     */
    static int countString(String targetString, String childString) {
        int formulaLength = targetString.length();
        int count = 0;
        for (int i = 0; i < formulaLength; i++) {
            i = targetString.indexOf(childString, i);
            if (i == -1) break;
            else {
                count++;
            }
        }
        return count;
    }

    /**
     * 多字符运算符处理，返回SIN\COS\TAN全部换成大写
     *
     * @param sFormula 输入字符串
     * @return String SIN\COS\TAN全部大写的式子
     */
    private static String fixComplexSymbol(String sFormula) {
        final Pattern sinPattern = Pattern.compile("(?i)(sin)");
        final Pattern cosPattern = Pattern.compile("(?i)(cos)");
        final Pattern tanPattern = Pattern.compile("(?i)(tan)");
        Matcher sinMatcher = sinPattern.matcher(sFormula);
        String str11 = sinMatcher.replaceAll("");
        String str21 = sinMatcher.replaceAll("SIN");
        Matcher cos1 = cosPattern.matcher(str11);
        Matcher cos2 = cosPattern.matcher(str21);
        String str12 = cos1.replaceAll("");
        String str22 = cos2.replaceAll("COS");
        Matcher tan1 = tanPattern.matcher(str12);
        Matcher tan2 = tanPattern.matcher(str22);
        String str13 = tan1.replaceAll("");
        String str23 = tan2.replaceAll("TAN");
        return str23;
    }

    /**
     * 公式中变量的存在性检查
     *
     * @param formula
     * @return 返回数据库中没有记录的变量
     */
    List<String> illegalVarList(String formula) throws LogicalException {
        List<String> varStrList = varSplit(removeComplexSymbol(formula));
        List<String> illegalVarList = new ArrayList<>();
        int list_size = varStrList.size();
        for (int i = 0; i < list_size; i++) {
            if (!db.isVarExist(varStrList.get(i)))
                illegalVarList.add(varStrList.get(i));
        }
        return illegalVarList;
    }

    //变量递推关系检查//tocheck

    /**
     * 变量递推关系检查，遇到错误即停止运行，返回LogicalException异常，全部分支检查完毕返回true
     *
     * @return 只返回true，错误抛出异常终止运行
     * @throws LogicalException
     */
    boolean isDerivationCorrected() throws LogicalException {
        List<Vari> variList = db.getAllVariable();
        int length = variList.size();
        for (int i = 0; i < length; i++) {
            if (variList.get(i).getIsCalculated()) {
                List<Integer> route = new ArrayList<>();
                route.add(variList.get(i).getVariableID());
                isBranchCorrected(variList.get(i), route);
            }
        }
        return true;
    }

    /**
     * 树状图分析者，以传入的变量对象为起点，寻找是否有不可通过公式库中已存公式推导出来的待求变量
     *
     * @param variable Vari 应为待求变量
     * @param route    记录已经历的待求变量的路径，防止出现公式链的死循环
     * @return
     * @throws LogicalException 两种：出现了死循环、变量缺乏对应的推导公式
     */
    private boolean isBranchCorrected(Vari variable, List<Integer> route) throws LogicalException {
        List<Vari> VariList = getVariList(variable);
        for (int i = 0, amount = VariList.size(); i < amount; i++) {
            Vari var = VariList.get(i);
            if (var.getIsCalculated()) {
                if (route.contains(var.getVariableID())) {
                    throw new LogicalException(var);
                } else {
                    List<Integer> newRoute = new ArrayList<>(route);
                    newRoute.add(var.getVariableID());
                    isBranchCorrected(var, newRoute);
                }
            }
        }
        return true;
    }

    /**
     * 获得下层推导所需的变量集合，无重复元素
     *
     * @param var Vari 应为待求变量
     * @return List<Vari> 包含了所有推导这个变量所需的下层变量的集合
     */
    private List<Vari> getVariList(Vari var) throws LogicalException {
        List<String> formulaList = db.getAllAlgebraic(var);
        if (formulaList.isEmpty()) {
            throw new LogicalException(var, "该变量为待求变量，但缺少对应的推导公式");
        }
        int formulaAmount = formulaList.size();
        List<String> varStrList = new ArrayList<>();
        List<Vari> varList = new ArrayList<>();
        for (int i = 0; i < formulaAmount; i++) {
            varStrList.addAll(varSplit(removeComplexSymbol(formulaList.get(i))));
        }
        HashSet<String> varSet = new HashSet<>(varStrList);
        varStrList.clear();
        varStrList.addAll(varSet);
        for (int i = 0, varAmount = varStrList.size(); i < varAmount; i++) {
            Vari varToAdd = db.getVar(varStrList.get(i));
            if (varToAdd == null) {
                throw new LogicalException("没有定义这样的变量:" + varStrList.get(i));
            } else {
                varList.add(varToAdd);
            }
        }
        return varList;
    }

    /**
     * 单个变量的规则覆盖区域检查 tocheck
     *
     * @param var Vari
     * @return true-该变量规则覆盖区域重叠，false-该变量规则覆盖区域正常
     */
    private boolean isRedundant(Vari var) {
        Rules varA = new Rules(var.getVariableID());
        int rulesAmount = varA.amount();
        for (int i = 0; i < rulesAmount; i++) {
            for (int j = i + 1; j < rulesAmount; j++) {
                if (varA.getJudgeVarID(i) == varA.getJudgeVarID(j)) {
                    if (!((varA.getUpperLimit(i) <= varA.getLowerLimit(j)) || (varA.getUpperLimit(j) <= varA.getLowerLimit(i))))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * 库中记录的规则覆盖区域检查
     *
     * @return true 检查通过
     * @throws LogicalException Vari与message
     */
    void checkRules() throws LogicalException {
        List<Vari> variList = db.getAllVariable();
        int length = variList.size();
        for (int i = 0; i < length; i++) {
            if (variList.get(i).getIsCalculated()) {
                if (isRedundant(variList.get(i))) {
                    throw new LogicalException(variList.get(i), "变量对应公式的规则覆盖区域重叠，请检查");
                }
            }
        }
    }
}
