package App.formulalib;

public class Vari {
    private final static String varType0String = "已知";
    private final static String varType1String = "待求";

    private final int varID;//变量ID
    private final boolean isCalculatedVar;//是否为待算变量
    private final String varString;//变量名
    private final String varDescription;//可能为null请注意检查，变量描述
    private final String varDevice;//可能为null请注意检查，舾装件名称
    private final String varScope;

    Vari(int ID, String str, boolean isCalculated, String description, String device, String scope) {
        varID = ID;
        isCalculatedVar = isCalculated;
        varString = str;
        varDescription = description;
        varDevice = device;
        varScope = scope;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vari) {
            return varID == ((Vari) obj).varID;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + varID;
        result = result * 31 + Boolean.hashCode(isCalculatedVar);
        result = result * 31 + (varString == null ? 0 : varString.hashCode());
        result = result * 31 + (varDescription == null ? 0 : varDescription.hashCode());
        result = result * 31 + (varDevice == null ? 0 : varDevice.hashCode());
        return result;
    }


    @Override
    public String toString() {
        if (varString == null)//防止空的对象
            return "";
        else {
            if (varDescription == null)
                return "The Variable:ID=" + varID + " ;String=" + varString + " ;IsCalculated=" + isCalculatedVar;
            else
                return "The Variable:ID=" + varID + " ;String=" + varString + " ;IsCalculated=" + isCalculatedVar + " ;Description=" + varDescription;
        }
    }

    public int getVariableID() {
        return varID;
    }

    public String getVarString() {
        return varString;
    }

    public boolean getIsCalculated() {
        return isCalculatedVar;
    }

    public String getVarType() {
        if (isCalculatedVar) {
            return varType1String;
        } else {
            return varType0String;
        }
    }

    /**
     * 返回变量描述String
     *
     * @return String VariableDescription 可能为null请注意检查
     */
    public String getVariableDescription() {
        return varDescription;
    }

    /**
     * 返回设备说明String
     *
     * @return String VariableDevice 可能为null请注意检查
     */
    public String getVarDevice() {
        return varDevice;
    }

    public String getVarScope() {
        return varScope;
    }
}
