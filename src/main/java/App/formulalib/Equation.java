package App.formulalib;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Equation {
    private final SimpleIntegerProperty equationID;
    private final SimpleStringProperty equationRight;
    private final SimpleIntegerProperty equationVarID;
    private final SimpleStringProperty equationVarStr;
    private final SimpleStringProperty description;
    private final SimpleBooleanProperty isRuled;
    private final SimpleStringProperty rule;
    //以下成员对象可能为空，请检查
    private final Vari restrictedVar;
    private final Double lowerBound;
    private final Double upperBound;
    private final String ruleDescription;
    private final String ruleMore;


    Equation(int equationID, String equationRight, Vari equationVar, String description, Vari restrictedVar, Double lowerBound, Double upperBound, String ruleDescription, String ruleMore) {
        this.equationID = new SimpleIntegerProperty(equationID);
        this.equationVarID = new SimpleIntegerProperty(equationVar.getVariableID());
        this.equationVarStr = new SimpleStringProperty(equationVar.getVarString());
        this.equationRight = new SimpleStringProperty(equationRight);
        this.description = new SimpleStringProperty(description);
        String rule = "";
        if (restrictedVar != null) {
            rule = restrictedVar.getVarString();
        }
        if (lowerBound != null) {
            rule = lowerBound + "≤" + rule;
        }
        if (upperBound != null) {
            rule = rule + "≤" + upperBound;
        }
        if (ruleDescription != null) {
            rule = rule + ruleDescription;
        }
        if (ruleMore != null) {
            rule = rule + ruleMore;
        }
        if (rule.isEmpty()) {
            isRuled = new SimpleBooleanProperty(false);
        } else {
            isRuled = new SimpleBooleanProperty(true);
        }
        this.rule = new SimpleStringProperty(rule);
        this.restrictedVar = restrictedVar;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.ruleDescription = ruleDescription;
        this.ruleMore = ruleMore;
    }

    @Override
    public String toString() {
        return "EquationID=" + equationID.getValue() + "\nEquationVarID=" + equationVarID.getValue() + "\nEquation:" + equationVarStr.getValue() + "=" + equationRight.getValue() + "\nDescription=" + description.getValue() + "\nRule:" + rule.getValue();
    }

    public int getEquationID() {
        return equationID.get();
    }

    public String getEquationVarStr() {
        return equationVarStr.getValueSafe();
    }

    public int getEquationVarID() {
        return equationVarID.get();
    }

    public String getEquationRight() {
        return equationRight.getValueSafe();
    }

    public boolean getIsRuled() {
        return isRuled.get();
    }

    public String getRule() {
        return rule.getValueSafe();
    }

    public String getDescription() {
        return description.getValueSafe();
    }

    public Vari getRestrictedVar() {
        return restrictedVar;
    }

    public Double getLowerBound() {
        return lowerBound;
    }

    public Double getUpperBound() {
        return upperBound;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public String getRuleMore() {
        return ruleMore;
    }
}
