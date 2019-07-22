package App.formulalib;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class Consoler {

    private final ObservableList<String> queryType = FXCollections.observableArrayList("待求变量名", "公式ID");
    private final ObservableList<Equation> equationList = FXCollections.observableArrayList();
    private final ObservableList<Vari> variableList = FXCollections.observableArrayList();
    boolean isAmended = false;
    //默认equationID为null
    Integer equationID;
    @FXML
    private Button submitFormula;
    @FXML
    private Button exitWindow;
    @FXML
    private TextField tfStringNewVariable;
    @FXML
    private TextField tfStringNewAlgebraic;
    @FXML
    private TextField tfStringNewFormulaRemark;
    @FXML
    private CheckBox cbNewIsRuled;
    @FXML
    private TextField tfStringNewRestrictedVariable;
    @FXML
    private TextField tfDoubleNewLowerBound;
    @FXML
    private TextField tfDoubleNewUpperBound;
    @FXML
    private TextField tfStringNewRuleDescription;
    @FXML
    private ComboBox choiceQueryType;
    @FXML
    private TextField tfStringQuery;
    @FXML
    private Button btQueryFormula;
    @FXML
    private TableView<Equation> tvTableFormula;
    @FXML
    private TableColumn tcTableFormulaCol1, tcTableFormulaCol2, tcTableFormulaCol3, tcTableFormulaCol4, tcTableFormulaCol5, tcTableFormulaCol6, tcFormulaLastColumn;
    @FXML
    private Button btEditFormula;
    @FXML
    private Button btDeleteFormula;
    @FXML
    private TextField tfStringQueryVariable;
    @FXML
    private TextField tfStringQueryDescription;
    @FXML
    private Button btQueryVariable;
    @FXML
    private CheckBox cbIsCalculated;
    @FXML
    private TableView<Vari> tvTableVariable;
    @FXML
    private TableColumn tcTableVariableCol1, tcTableVariableCol2, tcTableVariableCol3, tcTableVariableCol4, tcTableVariableCol5, tcTableVariableCol6;
    @FXML
    private Button btNewVariable;
    @FXML
    private Button btEditVariable;
    @FXML
    private Button btDeleteVariable;
    @FXML
    private Button btCheckAlteration;

    @FXML
    private void initialize() throws LogicalException {
//        exitWindow.setOnAction(event -> buttonActionExitSystem());
        choiceQueryType.setValue(queryType.get(0));
        choiceQueryType.setItems(queryType);
        tcFormulaLastColumn.prefWidthProperty().bind(tvTableFormula.widthProperty().subtract(842));//列宽绑定
        checkboxActivateRuleArea();
        bindTableColumns();

        cbNewIsRuled.selectedProperty().addListener(ov -> checkboxActivateRuleArea());//将Observable ov绑定在selectedProperty上,使用checkboxActivateRuleArea()重写invalidated()方法
        submitFormula.setOnAction(event -> buttonActionSubmitFormula());
        btQueryFormula.setOnAction(event -> buttonActionQueryFormula());
        btEditFormula.setOnAction(event -> buttonActionEditFormula());
        btDeleteFormula.setOnAction(event -> buttonActionDeleteFormula());
        btQueryVariable.setOnAction(event -> buttonActionQueryVariable());
        btNewVariable.setOnAction(event -> buttonActionNewVariable());
        btEditVariable.setOnAction(event -> buttonActionEditVariable());
        btDeleteVariable.setOnAction(event -> buttonActionDeleteVariable());
        btCheckAlteration.setOnAction(event -> buttonActionCheckAlteration());

        DataBase db = new DataBase();
        List<Equation> list = new ArrayList<>();
        for (Vari vari : db.getAllVariable()) {
            list.addAll(db.getFormulaList(vari));
        }
        tvTableFormula.setItems(FXCollections.observableArrayList(list));

        resetVariableForm();
    }

//    private void buttonActionExitSystem() {
//        if (AlertWindows.newConfirmWindows("确认退出", "确认退出公式库管理系统？", "退出前请确保已对改动应用检查")) {
//            Stage stage = (Stage) exitWindow.getScene().getWindow();
//            stage.close();
//            //Platform.exit();
//        } else
//            return;
//    }

    /**
     * 提交公式的按钮动作
     */
    private void buttonActionSubmitFormula() {
        try {
            DataBase db = new DataBase();
            String NewVariable = Checker.removeSpace(tfStringNewVariable.getText());//异常处理？
            if (!Checker.isVarSpellCorrected(NewVariable)) {
                throw new LogicalException("变量名错误", "输入的待求变量名有误");
            }
            //将变量String转换为变量ID，throw变量不存在的异常（LogicalException）
            if (db.getVar(NewVariable) == null) {
                throw new LogicalException("没有定义这样的变量:" + NewVariable);
            }
            int NewVariableID = db.getVar(NewVariable).getVariableID();
            String NewAlgebraic = tfStringNewAlgebraic.getText();
            String NewFormulaRemark = tfStringNewFormulaRemark.getText();
            boolean isRuled = cbNewIsRuled.isSelected();
            //对输入的代数式进行检查，并对输入代数式格式化
            NewAlgebraic = Checker.formulaFormatChecker(NewAlgebraic);
            Checker checker = new Checker();
            List<String> undefinedVariableList = checker.illegalVarList(NewAlgebraic);
            if (!undefinedVariableList.isEmpty()) {
                AlertWindows alert = new AlertWindows(undefinedVariableList);
                db.close();
                return;
            }

            if (isRuled) {
                double NewLowerBound = 0.666, NewUpperBound = 0.666;//缺省值为0.666，无意义
                int stateBound = 0;//规则的状态：“0”-无效规则；“1”-上下限皆有；“2”-只有下限值；“3”-只有上限值；“4”-上下限相等（等号）
                String NewRestrictedVariable = Checker.removeSpace(tfStringNewRestrictedVariable.getText());
                if (!Checker.isVarSpellCorrected(NewRestrictedVariable)) {
                    throw new LogicalException("变量名错误", "输入的规则变量名有误");
                }

                //判断规则的类型
                if (tfDoubleNewLowerBound.getLength() == 0) {
                    if (tfDoubleNewUpperBound.getLength() == 0) {
                        stateBound = 0;
                    } else {
                        stateBound = 3;
                        NewUpperBound = Double.parseDouble(tfDoubleNewUpperBound.getText());
                    }
                } else {
                    if (tfDoubleNewUpperBound.getLength() == 0) {
                        stateBound = 2;
                        NewLowerBound = Double.parseDouble(tfDoubleNewLowerBound.getText());
                    } else {
                        stateBound = 1;
                        NewLowerBound = Double.parseDouble(tfDoubleNewLowerBound.getText());
                        NewUpperBound = Double.parseDouble(tfDoubleNewUpperBound.getText());
                        if (NewLowerBound > NewUpperBound) {
                            AlertWindows alert = new AlertWindows("数值错误", "下限值不能大于上限值");
                            db.close();
                            return;
                        }
                        if (NewLowerBound == NewUpperBound)
                            stateBound = 4;
                    }
                }
                String NewRuleDescription = tfStringNewRuleDescription.getText();
                //将变量String转换为变量ID，throw变量不存在的异常（LogicalException）
                if (db.getVar(NewRestrictedVariable) == null) {
                    throw new LogicalException("没有定义这样的变量:" + NewRestrictedVariable);
                }
                int NewRestrictedVariableID = db.getVar(NewRestrictedVariable).getVariableID();

                //带Rule的公式储存
                if (isAmended) {
                    String confirmInformation = "公式ID：" + equationID + "\n更改后的公式：" + NewVariable + "=" + NewAlgebraic + "\n公式备注：" + NewFormulaRemark;
                    switch (stateBound) {
                        case 1:
                            confirmInformation = confirmInformation.concat("\n规则：" + NewLowerBound + "≤" + NewRestrictedVariable + "≤" + NewUpperBound + "\n规则备注:" + NewRuleDescription);
                            break;
                        case 2:
                            confirmInformation = confirmInformation.concat("\n规则：" + NewRestrictedVariable + "≥" + NewLowerBound + "\n规则备注:" + NewRuleDescription);
                            break;
                        case 3:
                            confirmInformation = confirmInformation.concat("\n规则：" + NewRestrictedVariable + "≤" + NewUpperBound + "\n规则备注:" + NewRuleDescription);
                            break;
                        case 4:
                            confirmInformation = confirmInformation.concat("\n规则：" + NewRestrictedVariable + "=" + NewLowerBound + "\n规则备注:" + NewRuleDescription);
                            break;
                    }
                    if (AlertWindows.newConfirmWindows("确认更新操作", "是否将更改后的公式添加到公式库中？", confirmInformation)) {
                        boolean success = db.updateFormula(equationID, NewVariableID, NewAlgebraic, NewFormulaRemark, stateBound, NewRestrictedVariableID, NewLowerBound, NewUpperBound, NewRuleDescription, "TBD");
                        if (success) {
                            isAmended = false;
                            equationID = null;
                            AlertWindows sc = new AlertWindows("成功更新公式", "成功更改后的公式更新到公式库中！", confirmInformation);
                            clearSubmitFormula();
                        } else {
                            AlertWindows fa = new AlertWindows("未成功更新公式", "将更改后的公式更新到公式库失败！");
                        }
                    }
                    db.close();
                    return;
                } else {
                    String confirmInformation = NewVariable + "=" + NewAlgebraic + "\n公式备注：" + NewFormulaRemark;
                    switch (stateBound) {
                        case 1:
                            confirmInformation = confirmInformation.concat("\n规则：" + NewLowerBound + "≤" + NewRestrictedVariable + "≤" + NewUpperBound + "\n规则备注:" + NewRuleDescription);
                            break;
                        case 2:
                            confirmInformation = confirmInformation.concat("\n规则：" + NewRestrictedVariable + "≥" + NewLowerBound + "\n规则备注:" + NewRuleDescription);
                            break;
                        case 3:
                            confirmInformation = confirmInformation.concat("\n规则：" + NewRestrictedVariable + "≤" + NewUpperBound + "\n规则备注:" + NewRuleDescription);
                            break;
                        case 4:
                            confirmInformation = confirmInformation.concat("\n规则：" + NewRestrictedVariable + "=" + NewLowerBound + "\n规则备注:" + NewRuleDescription);
                            break;
                    }
                    if (AlertWindows.newConfirmWindows("确认添加操作", "是否将新公式添加到公式库中？", confirmInformation)) {
                        boolean success = db.addFormula(NewVariableID, NewAlgebraic, NewFormulaRemark, stateBound, NewRestrictedVariableID, NewLowerBound, NewUpperBound, NewRuleDescription, "TBD");
                        if (success) {
                            AlertWindows sc = new AlertWindows("成功添加公式", "成功将公式添加到公式库中！", confirmInformation);
                            clearSubmitFormula();
                        } else {
                            AlertWindows fa = new AlertWindows("未成功添加公式", "将公式添加到公式库中失败！");
                        }
                    }
                    db.close();
                    return;
                }
            }

            //无规则的公式写入
            if (isAmended) {
                String confirmInformation = "公式ID：" + equationID + "\n更改后的公式：" + NewVariable + "=" + NewAlgebraic + "\n公式备注：" + NewFormulaRemark;
                if (AlertWindows.newConfirmWindows("确认更新操作", "是否将更改后的公式添加到公式库中？", confirmInformation)) {
                    boolean success = db.updateFormula(equationID, NewVariableID, NewAlgebraic, NewFormulaRemark);
                    if (success) {
                        isAmended = false;
                        equationID = null;
                        AlertWindows sc = new AlertWindows("成功更新公式", "成功更改后的公式更新到公式库中！", confirmInformation);
                        clearSubmitFormula();
                    } else {
                        AlertWindows fa = new AlertWindows("未成功更新公式", "将更改后的公式更新到公式库失败！");
                    }
                }
                db.close();
                return;
            } else {
                String confirmInformation = NewVariable + "=" + NewAlgebraic + "\n公式备注：" + NewFormulaRemark;
                if (AlertWindows.newConfirmWindows("确认添加操作", "是否将新公式添加到公式库中？", confirmInformation)) {
                    boolean success = db.addFormula(NewVariableID, NewAlgebraic, NewFormulaRemark);
                    if (success) {
                        AlertWindows sc = new AlertWindows("成功添加公式", "成功将公式添加到公式库中！", confirmInformation);
                        clearSubmitFormula();
                    } else {
                        AlertWindows fa = new AlertWindows("未成功添加公式", "将公式添加到公式库中失败！");
                    }
                }
                db.close();
                return;
            }
        } catch (NumberFormatException e) {
            AlertWindows NewAlert = new AlertWindows(e, "输入内容格式错误", "请输入正确的数值格式");
        } catch (LogicalException e) {
            AlertWindows NewAlert = new AlertWindows(e.getTitle(), e.getContent());
        } catch (IllegalArgumentException e) {
            AlertWindows NewAlert = new AlertWindows("不存在的变量", e.getMessage(), "输入的变量名在变量库中无记录，请检查输入");
        }
    }

    /**
     * Checkbox cbNewIsRuled控制规则输入区域的处理器
     */
    private void checkboxActivateRuleArea() {
        if (cbNewIsRuled.isSelected()) {
            tfStringNewRestrictedVariable.setDisable(false);
            tfStringNewRestrictedVariable.setPromptText("请输入变量名");
            tfDoubleNewLowerBound.setDisable(false);
            tfDoubleNewLowerBound.setPromptText("输入数值");
            tfDoubleNewUpperBound.setDisable(false);
            tfDoubleNewUpperBound.setPromptText("输入数值");
            tfStringNewRuleDescription.setDisable(false);
            tfStringNewRuleDescription.setPromptText("在这里输入不能用数值表示的规则文本");
        } else {
            tfStringNewRestrictedVariable.setDisable(true);
            tfStringNewRestrictedVariable.setPromptText("");
            tfDoubleNewLowerBound.setDisable(true);
            tfDoubleNewLowerBound.setPromptText("");
            tfDoubleNewUpperBound.setDisable(true);
            tfDoubleNewUpperBound.setPromptText("");
            tfStringNewRuleDescription.setDisable(true);
            tfStringNewRuleDescription.setPromptText("");
        }
    }

    /**
     * 公式查询按钮的交互动作
     */
    private void buttonActionQueryFormula() {
        try {
            DataBase db = new DataBase();
            int indexSelectedType = choiceQueryType.getSelectionModel().getSelectedIndex();//0:"待求变量名", 1:"公式ID"
            String NewQuery = tfStringQuery.getText();
            switch (indexSelectedType) {
                case 0: {
                    String QueryVariable = Checker.removeSpace(NewQuery);
                    if (Checker.isVarSpellCorrected(QueryVariable)) {
                        if (db.isVarExist(QueryVariable)) {
                            List<Equation> equations = db.getFormulaList(QueryVariable);
                            if (equations.isEmpty()) {
                                db.close();
                                throw new LogicalException("无符合条件的公式", "输入的查询变量不存在对应的公式");
                            } else {
                                equationList.clear();
                                for (int i = 0, j = equations.size(); i < j; i++) {
                                    equationList.add(equations.get(i));
                                }
                            }
                        } else {
                            db.close();
                            throw new LogicalException("变量未定义", "输入的查询变量不存在");
                        }
                    } else {
                        db.close();
                        throw new LogicalException("变量名错误", "输入的变量名有误");
                    }
                }
                break;
                case 1: {
                    int QueryEquationID = Integer.parseInt(Checker.removeSpace(NewQuery));
                    {
                        Equation equation = db.getFormula(QueryEquationID);
                        if (equation == null) {
                            db.close();
                            throw new LogicalException("无符合条件的公式", "不存在与输入公式ID对应的公式");
                        } else {
                            equationList.clear();
                            equationList.add(equation);
                        }
                    }
                }
                break;
                default:
            }
            db.close();
        } catch (LogicalException e) {
            AlertWindows NewAlert = new AlertWindows(e.getTitle(), e.getContent());
        }

    }

    /**
     * 修改公式按钮的交互动作
     */
    private void buttonActionEditFormula() {
        Equation equation = tvTableFormula.getSelectionModel().getSelectedItem();
        if (equation != null) {
            if (!AlertWindows.newConfirmWindows("修改公式", "确认对下列公式进行修改？\n（修改后请重新执行查询以更新表格）", equation.toString()))
                return;
            setSubmitFormula(equation);
            isAmended = true;
            equationID = equation.getEquationID();
        } else return;
    }

    /**
     * 删除公式按钮交互
     */
    private void buttonActionDeleteFormula() {
        Equation equation = tvTableFormula.getSelectionModel().getSelectedItem();
        if (equation != null) {
            if (AlertWindows.newConfirmWindows("删除公式", "确认删除下列公式？", equation.toString())) {
                DataBase db = new DataBase();
                if (db.deleteFormula(equation)) {
                    db.close();
                    AlertWindows alert = new AlertWindows("删除公式", "公式删除成功");
                    equationList.remove(equation);
                } else {
                    db.close();
                    AlertWindows alert = new AlertWindows("删除公式", "公式删除失败");
                }
            } else return;
        }
    }

    /**
     * 查询变量按钮交互，查询条件皆可为空
     */
    private void buttonActionQueryVariable() {
        try {
            DataBase db = new DataBase();
            String queryVariable = Checker.removeSpace(tfStringQueryVariable.getText());//异常处理？
            String queryDescription = Checker.removeSpace(tfStringQueryDescription.getText());
            boolean isCalculated = cbIsCalculated.isSelected();
            if (!queryVariable.isEmpty()) {
                if (!Checker.isVarSpellCorrected(queryVariable)) {
                    db.close();
                    throw new LogicalException("变量名错误", "输入的查询变量名拼写有误");
                }
            }
            List<Vari> variList = db.getVariableList(queryVariable, queryDescription, isCalculated);
            if (variList.isEmpty()) {
                db.close();
                throw new LogicalException("无符合条件的变量", "变量库中不存在符合输入条件的已定义变量");
            } else {
                variableList.clear();
                for (int i = 0, j = variList.size(); i < j; i++) {
                    variableList.add(variList.get(i));
                }
                db.close();
            }
        } catch (LogicalException e) {
            AlertWindows NewAlert = new AlertWindows(e.getTitle(), e.getContent());
        } finally {
            cbIsCalculated.setSelected(false);
        }
    }

    /**
     * 按钮动作，启动新的“新变量定义窗口”对象
     */
    private void buttonActionNewVariable() {
        NewVarPane newVarPane = new NewVarPane();
        newVarPane.show();
        resetVariableForm();
    }

    private void buttonActionEditVariable(){
        Vari vari=tvTableVariable.getSelectionModel().getSelectedItem();
        if (vari!=null) {
            VarEditor varEditor=new VarEditor();
            Vari editedVar = varEditor.show(vari);
            if (editedVar!=null)
            {
                DataBase db =new DataBase();
                db.updateVariable(editedVar);
                db.close();
                resetVariableForm();
            }
            else return;
        }else return;
    }

    /**
     * 按钮动作，删除变量定义，不能删除已有公式关联的待求变量
     */
    private void buttonActionDeleteVariable() {
        Vari var = tvTableVariable.getSelectionModel().getSelectedItem();
        if (var != null) {
            if (AlertWindows.newConfirmWindows("删除变量", "确认删除下列变量的定义？", var.toString())) {
                DataBase db = new DataBase();
                if (!db.getAllAlgebraic(var).isEmpty()) {
                    db.close();
                    AlertWindows alert = new AlertWindows("删除变量", "变量删除失败，不能删除已有公式关联的待求变量！");
                    return;
                }
                if (db.deleteVariable(var)) {
                    db.close();
                    AlertWindows alert = new AlertWindows("删除变量", "变量删除成功");
                    variableList.remove(var);
                } else {
                    db.close();
                    AlertWindows alert = new AlertWindows("删除变量", "变量删除失败");
                }
            } else return;
        }
    }

    /**
     * 验证公式库有效性
     */
    private void buttonActionCheckAlteration() {
        try {
            DataBase db = new DataBase();
            db.repairVariableAssociation();
            db.close();
            Checker checker = new Checker();
            checker.isDerivationCorrected();
            checker.checkRules();
            AlertWindows alert = new AlertWindows("公式库有效性", "检查完毕，没有发现问题");
        } catch (LogicalException e) {
            if (e.getVari() == null) {
                AlertWindows alert = new AlertWindows("公式库有效性", e.getTitle(), e.getContent());
            } else {
                AlertWindows alert = new AlertWindows("公式库有效性", e.getTitle(), e.getVari().getVarString() + "：" + e.getContent());
            }
        }
    }

    /**
     * 初始化填充变量列表
     */
    private void resetVariableForm() {
        DataBase db = new DataBase();
        String queryVariable = Checker.removeSpace(tfStringQueryVariable.getText());//异常处理？
        String queryDescription = Checker.removeSpace(tfStringQueryDescription.getText());
        boolean isCalculated = cbIsCalculated.isSelected();
        if (!queryVariable.isEmpty()) {
            if (!Checker.isVarSpellCorrected(queryVariable)) {
                db.close();
                return;
            }
        }
        List<Vari> variList = db.getVariableList(queryVariable, queryDescription, isCalculated);
        if (variList.isEmpty()) {
            db.close();
        } else {
            variableList.clear();
            for (int i = 0, j = variList.size(); i < j; i++) {
                variableList.add(variList.get(i));
            }
            db.close();
        }
    }

    /**
     * 清空“公式编辑”的窗口输入内容
     */
    private void clearSubmitFormula() {
        tfStringNewVariable.clear();
        tfStringNewAlgebraic.clear();
        tfStringNewFormulaRemark.clear();
        cbNewIsRuled.setSelected(false);
        tfStringNewRestrictedVariable.clear();
        tfDoubleNewLowerBound.clear();
        tfDoubleNewUpperBound.clear();
        tfStringNewRuleDescription.clear();
    }


    /**
     * 将传入Equation填写到公式编辑区域（带非空检查）
     *
     * @param targetFormula Equation
     */
    private void setSubmitFormula(@NotNull Equation targetFormula) {
        tfStringNewVariable.setText(targetFormula.getEquationVarStr());
        tfStringNewAlgebraic.setText(targetFormula.getEquationRight());
        tfStringNewFormulaRemark.setText(targetFormula.getDescription());
        if (targetFormula.getRule().isEmpty())
            cbNewIsRuled.setSelected(false);
        else
            cbNewIsRuled.setSelected(true);
        if (targetFormula.getRestrictedVar() != null)
            tfStringNewRestrictedVariable.setText(targetFormula.getRestrictedVar().getVarString());
        if (targetFormula.getLowerBound() != null)
            tfDoubleNewLowerBound.setText(targetFormula.getLowerBound().toString());
        if (targetFormula.getUpperBound() != null)
            tfDoubleNewUpperBound.setText(targetFormula.getUpperBound().toString());
        if (targetFormula.getUpperBound() != null)
            tfStringNewRuleDescription.setText(targetFormula.getRuleDescription());
    }

    /**
     * 设定各TableColumn的属性并和实体类Label名绑定
     */
    private void bindTableColumns() {
        tcTableFormulaCol1.setCellValueFactory(new PropertyValueFactory<>("equationID"));
        tcTableFormulaCol1.impl_setReorderable(false);
        tcTableFormulaCol2.setCellValueFactory(new PropertyValueFactory<>("equationVarStr"));
        tcTableFormulaCol3.setCellValueFactory(new PropertyValueFactory<>("equationVarID"));
        tcTableFormulaCol4.setCellValueFactory(new PropertyValueFactory<>("equationRight"));
        tcTableFormulaCol5.setCellValueFactory(new PropertyValueFactory<>("isRuled"));
        tcTableFormulaCol6.setCellValueFactory(new PropertyValueFactory<>("rule"));
        tcFormulaLastColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        tvTableFormula.setItems(equationList);
        tcTableVariableCol1.setCellValueFactory(new PropertyValueFactory<>("variableID"));
        tcTableVariableCol2.setCellValueFactory(new PropertyValueFactory<>("varString"));
        tcTableVariableCol3.setCellValueFactory(new PropertyValueFactory<>("varType"));
        tcTableVariableCol4.setCellValueFactory(new PropertyValueFactory<>("variableDescription"));
        tcTableVariableCol5.setCellValueFactory(new PropertyValueFactory<>("varDevice"));
        tcTableVariableCol6.setCellValueFactory(new PropertyValueFactory<>("varScope"));
        tvTableVariable.setItems(variableList);
    }
}

