package App.formulalib;

import App.dataModel.ParamAndValueData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Calculate {
    private Interpreter interpreter;

    /**
     * 如果List<ParamAndValueData> valueDataList发生变化，请注意检查同步
     *
     * @param valueDataList
     */
    public Calculate(List<ParamAndValueData> valueDataList) {
        interpreter = new Interpreter(valueDataList);
    }

    /**
     * 计算入口
     *
     * @param varStr String
     * @return double
     * @throws LogicalException
     */
    public double varToValue(@NotNull String varStr) throws LogicalException {
        return interpreter.varToValue(varStr);
    }

    /**
     * 计算入口
     *
     * @param var Vari
     * @return double
     * @throws LogicalException
     */
    public double varToValue(@NotNull Vari var) throws LogicalException {
        return varToValue(var.getVarString());
    }
}
