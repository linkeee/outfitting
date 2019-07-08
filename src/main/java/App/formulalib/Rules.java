package App.formulalib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//读取变量对应规则的类，规则是左闭右开的
class Rules {
    private int ruleAmount;
    private int vID;
    private List<String[]> rulesList = new ArrayList();

    Rules(int varID){
        vID = varID;
        DataBase db = new DataBase();
        ruleAmount = rulesFilter(db.getRulesForm(varID));
        db.close();
    }

    int getFormulaID(int i) {
        if (i < ruleAmount) {
            return Integer.parseInt(rulesList.get(i)[0]);
        } else throw new IndexOutOfBoundsException("访问越界");
    }

    int getJudgeVarID(int i) {
        if (i < ruleAmount)
            return Integer.parseInt(rulesList.get(i)[1]);
        else throw new IndexOutOfBoundsException("访问越界");
    }

    double getUpperLimit(int i) {
        if (i < ruleAmount) {
            return Double.parseDouble(rulesList.get(i)[3]);
        }
        else throw new IndexOutOfBoundsException("访问越界");
    }

    double getLowerLimit(int i) {
        if (i < ruleAmount)
            return Double.parseDouble(rulesList.get(i)[2]);
        else throw new IndexOutOfBoundsException("访问越界");
    }

    /**
     * 获取指定index规则的状态：“0”-无效规则；“1”-上下限皆有；“2”-只有下限值；“3”-只有上限值；“4”-上下限相等（等号）
     *
     * @param i
     * @return
     */
    int getRuleState(int i) {
        if (i < ruleAmount)
            return Integer.parseInt(rulesList.get(i)[4]);
        else throw new IndexOutOfBoundsException("访问越界");
    }

    int amount() {
        return ruleAmount;
    }

    /**
     * 规则处理器，只存入有意义的规则
     *
     * @param rulesForm 从数据库中获取的规则表
     * @return 有效规则的条数
     */
    private int rulesFilter(List<String[]> rulesForm) //todo//tocheck
    {
        int amount = 0;
        int count = rulesForm.size();
        for (int i = 0; i < count; i++) {
            try {
                String[] rule = rulesForm.get(i);
                int judge = isRule(rule);
                if (judge == 0) {
                    continue;
                } else {
                    String[] tRule = Arrays.copyOf(rule, 5);//0:公式ID,1:判断的变量ID,2:下限值,3:上限值,4:状态标识
                    tRule[4] = String.valueOf(judge);
                    rulesList.add(tRule);
                    amount++;
                }
            } catch (LogicalException e) {
                Logger.getGlobal().log(Level.SEVERE, "规则表生成异常：", e);
            }
        }
        return amount;
    }

    /**
     * 判断规则条目的属性，“0”-无效规则；“1”-上下限皆有；“2”-只有下限值；“3”-只有上限值；“4”-上下限相等（等号）
     *
     * @param ru
     * @return int
     * @throws LogicalException
     */
    private int isRule(String[] ru) throws LogicalException //todo//tocheck
    {
        if (ru[1] == null) {
            return 0;//无效规则
        } else if (ru[2] == null) {
            if (ru[3] == null) {
                throw new LogicalException("规则定义错误，无效规则条目");
            } else {
                return 3;//只有上限值
            }
        } else if (ru[3] == null) {
            return 2;//只有下限值
        } else {
            if (ru[2] == null)
                return 4;//上下限相等
            else
                return 1;//上下限都有
        }
    }
}
