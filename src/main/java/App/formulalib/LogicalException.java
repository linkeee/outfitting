package App.formulalib;

public class LogicalException extends Exception {
    static final long serialVersionUID = -3387516993124229948L;
    private final String title;
    private final String content;
    private final transient Vari var;

    LogicalException(Vari var, String message) {
        title = "未定义的变量";
        this.var = var;
        content = message;
    }

    LogicalException(String message) {
        super("逻辑错误:" + message);
        title = "逻辑错误";
        content = message;
        var = null;
    }

    LogicalException(String title, String message) {
        this.title = title;
        content = message;
        var = null;
    }

    LogicalException(Vari var) {
        super("变量" + var.getVarString() + "的推导公式错误，请检查");
        title = "公式推导错误";
        content = "变量没有对应公式或变量的推导公式错误，请检查";
        this.var = var;
    }

    String getTitle() {
        return title;
    }

    String getContent() {
        return content;
    }

    Vari getVari() {
        return var;
    }

}
