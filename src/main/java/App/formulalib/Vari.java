package App.formulalib;

public class Vari {
    private final int varID;//变量ID
    private final boolean isCalculatedVar;//是否为待算变量
    private final String varString;
    private final String varDescription;//可能为null请注意检查
    private final String varDevice;//可能为null请注意检查 outfitting_name

    Vari(int ID, String str, boolean isCalculated, String description, String device) {
        varID = ID;
        isCalculatedVar = isCalculated;
        varString = str;
        varDescription = description;
        varDevice = device;
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
        if (varDescription == null)
            return "The Variable:ID=" + varID + " ;String=" + varString + " ;IsCalculated=" + isCalculatedVar;
        else
            return "The Variable:ID=" + varID + " ;String=" + varString + " ;IsCalculated=" + isCalculatedVar + " ;Description=" + varDescription;
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

    public String getVariableDescription() {
        return varDescription;
    }

    public String getVarDevice() {
        return varDevice;
    }
}
