package App.formulalib;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {
    private static final String JDBC_DRIVER = "org.sqlite.JDBC";
//    private static final String DB_URL = "jdbc:sqlite:/";
//    private final String rootPath;
    public Connection conn;

    public DataBase() {
//        rootPath = this.getClass().getResource("/").getPath();
        try {
            conn = getConnection();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "数据库对象初始化失败", e);
        }

    }

    /**
     * SQLite的JDBC会在数据库文件不存在时自动新建空的db文件，我们使用getResource("/").getPath()获取项目根目录拼接formulalib.db的地址，因此只要做好前置过滤，手动写建立表结构的函数即可
     *
     * @return Connection
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws IOException
     */
    private Connection getConnection() throws IllegalAccessException, InstantiationException, IOException {
        try {
//            Class.forName(JDBC_DRIVER).newInstance();
//            Logger.getGlobal().log(Level.INFO, "数据库驱动加载成功");
//            Connection newConn = DriverManager.getConnection(DB_URL.concat(rootPath).concat("App/formulalib/formulalib.db"));

            Class.forName(JDBC_DRIVER);
            Connection newConn = DriverManager.getConnection("jdbc:sqlite:src/main/java/App/db/formula.db");
            Logger.getGlobal().log(Level.INFO, "数据库连接成功");
            return newConn;
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库连接失败", e);
            return null;
        } catch (ClassNotFoundException e) {
            Logger.getGlobal().log(Level.SEVERE, "数据库驱动不存在", e);
            return null;
        }
    }

    /**
     * 所有新建的Database对象都要显式地调用close()注销数据库连接
     *
     * @throws SQLException
     */
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "未成功断开与数据库的连接", e);
        }
    }

    /**
     * 建立一个新的空数据库（包含表结构）
     */
    void createTables() {
        try {
            String sql1 = "CREATE TABLE IF NOT EXISTS variable ([variableID] INTEGER PRIMARY KEY ASC AUTOINCREMENT NOT NULL UNIQUE,[variable_string] TEXT NOT NULL,[variable_type] BOOLEAN NOT NULL DEFAULT (-1),[variable_description] TEXT NULL DEFAULT NULL,[variable_device] TEXT NULL DEFAULT NULL,[isDeleted] BOOLEAN NOT NULL DEFAULT '0', [variable_scope] TEXT NULL DEFAULT NULL,[accuracyDigit] INTEGER NOT NULL DEFAULT '2',[varUnit] TEXT NOT NULL)";
            String sql2 = "CREATE TABLE IF NOT EXISTS formula ([equationID] INTEGER PRIMARY KEY ASC AUTOINCREMENT NOT NULL UNIQUE,[equation_right]TEXT NOT NULL,[equation_VarID]INTEGER NOT NULL,[description]TEXT NULL DEFAULT NULL,[restrictedVarID]INTEGER NULL DEFAULT NULL,[lowerBound]REAL NULL DEFAULT NULL,[upperBound]REAL NULL DEFAULT NULL,[rule_more]TEXT NULL DEFAULT NULL,[rule_description]TEXT NULL DEFAULT NULL,[isDeleted]BOOLEAN NOT NULL DEFAULT '0');";
            Statement stat = conn.createStatement();
            stat.addBatch("PRAGMA encoding = \"UTF-8\";");
            stat.addBatch(sql1);
            stat.addBatch(sql2);
            int[] report = stat.executeBatch();
            stat.close();
            Logger.getGlobal().info("数据库创建成功");
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库创建失败", e);
        }
    }

    /**
     * 查询变量ID对应的第一条公式的ID
     *
     * @param varID 变量ID
     * @return FirstFormulaID 变量ID对应的第一条公式的ID
     * @throws IllegalArgumentException
     */
    int getFirstFormulaID(int varID) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rSet = stmt.executeQuery("select equationID FROM formula WHERE isDeleted=0 and equation_VarID=".concat(String.valueOf(varID).concat(" limit 1")));
            while (rSet.next()) {
                return rSet.getInt(1);
            }
            rSet.close();
            stmt.close();
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
        }
        throw new IllegalArgumentException("该变量ID没有对应的公式");
    }

    /**
     * 返回varID对应的所有公式
     * 非静态方法，需要初始化对象
     *
     * @param var Vari 变量对象
     * @return List<String> 包含所有公式
     * @throws IllegalArgumentException the illegal argument exception
     */
    List<String> getAllAlgebraic(@NotNull Vari var) {
        List<String> a = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rSet = stmt.executeQuery("SELECT equation_right FROM formula WHERE isDeleted=0 and equation_VarID=".concat(String.valueOf(var.getVariableID())));
            while (rSet.next()) {
                a.add(rSet.getString(1));
            }
            rSet.close();
            stmt.close();
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
        }
        return a;
    }

    /**
     * 从字符串查询变量，返回一个Vari对象，如果没有这样的公式库中没有这样的变量，则返回null
     *
     * @param varStr 变量字符串
     * @return Vari
     * @throws LogicalException
     */
    Vari getVar(@NotNull String varStr) throws LogicalException {
        try {
            String sql = "SELECT variableID,variable_type,variable_description,variable_device, variable_scope,accuracyDigit,varUnit FROM variable WHERE isDeleted=0 and variable_string= ?";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, varStr);
            ResultSet rSet = stat.executeQuery();
            if (rSet.next()) {
                Vari a = new Vari(rSet.getInt(1), varStr, rSet.getBoolean(2), rSet.getString(3), rSet.getString(4), rSet.getString(5), rSet.getInt(6), rSet.getString(7));
                rSet.close();
                stat.close();
                return a;
            } else {
                rSet.close();
                stat.close();
                return null;
            }
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
        }
        throw new LogicalException("没有定义这样的变量:" + varStr);
    }

    /**
     * 从变量ID查询变量，返回一个Vari对象，如果没有这样的公式库中没有这样的变量，则抛出异常
     *
     * @param varID 变量字符串
     * @return Vari
     * @throws LogicalException
     */
    Vari getVar(int varID) throws LogicalException {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT variableID,variable_string,variable_type,variable_description,variable_device,variable_scope,accuracyDigit,varUnit FROM variable WHERE isDeleted=0 and variableID=".concat(String.valueOf(varID));
            ResultSet rSet = stmt.executeQuery(sql);
            if (rSet.next()) {
                String vardescription = null;
                String vardevice = null;
                if (rSet.getObject(4) != null)
                    vardescription = rSet.getString(4);
                if (rSet.getObject(5) != null)
                    vardevice = rSet.getString(5);
                Vari a = new Vari(varID, rSet.getString(2), rSet.getBoolean(3), vardescription, vardevice, rSet.getString(6), rSet.getInt(7), rSet.getString(8));
                rSet.close();
                stmt.close();
                return a;
            } else {
                rSet.close();
                stmt.close();
                throw new LogicalException("没有定义这样的变量:VarID=" + varID);
            }
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
        }
        throw new LogicalException("没有定义这样的变量:VarID=" + varID);
    }

    /**
     * 查询变量在数据库中存在与否
     *
     * @param strVar
     * @return 该变量在数据库中是否存在
     */
    boolean isVarExist(String strVar) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT variableID FROM variable WHERE isDeleted=0 and variable_string=\"" + strVar + "\"";
            ResultSet rSet = stmt.executeQuery(sql);
            boolean is_Exist = rSet.next();
            rSet.close();
            stmt.close();
            return is_Exist;
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
            throw new IllegalArgumentException("传入变量的字符串非法，请检查");
        }
    }

    /**
     * 从数据库获取指定ID的公式
     *
     * @param FormulaID
     * @return String 公式右侧
     * @throws IllegalArgumentException
     */
    String getAlgebraic(int FormulaID) throws IllegalArgumentException {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT equation_right FROM formula WHERE isDeleted=0 and equationID=".concat(String.valueOf(FormulaID));
            ResultSet rSet = stmt.executeQuery(sql);
            if (rSet.next()) {
                String str = rSet.getString(1);
                rSet.close();
                stmt.close();
                return str;
            } else {
                rSet.close();
                stmt.close();
                throw new IllegalArgumentException("没有这样的公式");
            }
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
        }
        throw new IllegalArgumentException("没有这样的公式");
    }

    /**
     * 通过FormulaID查询完整的公式
     *
     * @param FormulaID
     * @return Equation 返回一条公式或null，请注意检查返回对象为null的情况
     */
    Equation getFormula(int FormulaID) throws LogicalException {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT equationID,equation_right,equation_VarID,description,restrictedVarID,lowerBound,upperBound,rule_more,rule_description FROM formula WHERE isDeleted=0 and equationID=" + FormulaID;
            ResultSet rSet = stmt.executeQuery(sql);
            while (rSet.next()) {
                Vari restrictedVar = null;
                Double lowerBound = null;
                Double upperBound = null;
                String ruleMore = null;
                String ruleDescription = null;
                if (rSet.getObject(5) != null) {
                    restrictedVar = getVar(rSet.getInt(5));
                }
                if (rSet.getObject(6) != null) {
                    lowerBound = rSet.getDouble(6);
                }
                if (rSet.getObject(7) != null) {
                    upperBound = rSet.getDouble(7);
                }
                if (rSet.getObject(9) != null) {
                    ruleDescription = rSet.getString(9);
                }
                if (rSet.getObject(8) != null) {
                    ruleMore = rSet.getString(8);
                }
                Equation equation = new Equation(rSet.getInt(1), rSet.getString(2), getVar(rSet.getInt(3)), rSet.getString(4), restrictedVar, lowerBound, upperBound, ruleDescription, ruleMore);
                rSet.close();
                stmt.close();
                return equation;
            }
            rSet.close();
            stmt.close();
            return null;
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
            return null;
        }
    }

    /**
     * 从数据库中获取对应变量的包含所有规则的表
     *
     * @param varID
     * @return List<String [ ]>
     */
    List<String[]> getRulesForm(int varID) throws IllegalArgumentException {
        List<String[]> rulesForm = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT equationID,restrictedVarID,lowerBound,upperBound FROM formula WHERE isDeleted=0 and equation_VarID=".concat(String.valueOf(varID));
            ResultSet rSet = stmt.executeQuery(sql);
            while (rSet.next()) {
                String equationID = rSet.getString(1);
                String restrictedVarID = rSet.getString(2);
                String lowerBound = rSet.getString(3);
                String upperBound = rSet.getString(4);
                String[] rule = {equationID, restrictedVarID, lowerBound, upperBound};
                rulesForm.add(rule);
            }
            rSet.close();
            stmt.close();
            return rulesForm;
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
        }
        throw new IllegalArgumentException("该变量ID没有对应的公式");
    }

    /**
     * 根据varID查询所有符合规则的公式
     * List以链表形式储存Equation
     *
     * @param varID int
     * @return List<Equation> 可能返回空的List，需做处理
     */
    List<Equation> getFormulaList(int varID) throws LogicalException {
        List<Equation> a = new LinkedList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT equationID,equation_right,equation_VarID,description,restrictedVarID,lowerBound,upperBound,rule_more,rule_description FROM formula WHERE isDeleted=0 and equation_VarID=" + varID;
            ResultSet rSet = stmt.executeQuery(sql);
            while (rSet.next()) {
                Vari restrictedVar = null;
                Double lowerBound = null, upperBound = null;
                String ruleMore = null, ruleDescription = null;
                if (rSet.getObject(5) != null) {
                    restrictedVar = getVar(rSet.getInt(5));
                }
                if (rSet.getObject(6) != null) {
                    lowerBound = rSet.getDouble(6);
                }
                if (rSet.getObject(7) != null) {
                    upperBound = rSet.getDouble(7);
                }
                if (rSet.getObject(9) != null) {
                    ruleDescription = rSet.getString(9);
                }
                if (rSet.getObject(8) != null) {
                    ruleMore = rSet.getString(8);
                }
                Equation anEquation = new Equation(rSet.getInt(1), rSet.getString(2), getVar(rSet.getInt(3)), rSet.getString(4), restrictedVar, lowerBound, upperBound, ruleDescription, ruleMore);
                a.add(anEquation);
            }
            rSet.close();
            stmt.close();
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
        }
        return a;
    }

    /**
     * 根据varID查询所有符合规则的公式
     * List以链表形式储存Equation
     *
     * @param var Vari
     * @return List<Equation> 可能返回空的List，需做处理
     */
    public List<Equation> getFormulaList(Vari var) throws LogicalException {
        return getFormulaList(var.getVariableID());
    }

    /**
     * 根据varID查询所有符合规则的公式
     * List以链表形式储存Equation
     *
     * @param strVar String
     * @return List<Equation> 可能返回空的List，需做处理
     */
    List<Equation> getFormulaList(String strVar) throws LogicalException {
        if (getVar(strVar) == null) {
            throw new LogicalException("没有定义这样的变量:" + strVar);
        }
        return getFormulaList(getVar(strVar));
    }

    /**
     * 根据输入信息查询符合输入条件的变量，函数未使用PreparedStatement
     *
     * @param strVar         忽略大小写，不能有空格，不能为null，如果没有这一项请传入""
     * @param varDescription 不能有空格,不能为null，如果没有这一项请传入""，模糊匹配，写得越少越好
     * @param isAknown       是否为已知变量，默认是false，则查询时不会添加该条件
     * @return List<Vari>符合条件的所有变量
     */
    List<Vari> getVariableList(@NotNull String strVar, @NotNull String varDescription, boolean isAknown) {
        List<Vari> varList = new LinkedList<>();
        String varString = strVar.toLowerCase();
        try {
            String sql = "SELECT variableID,variable_string,variable_type,variable_description,variable_device,variable_scope,accuracyDigit,varUnit FROM variable WHERE isDeleted=0";
            if (!varString.isEmpty()) {
                sql = sql + " and lower(variable_string)='" + varString + "'";
            }
            if (!varDescription.isEmpty()) {
                sql = sql + " and variable_description LIKE '%" + varDescription + "%'";
            }
            if (isAknown) {
                sql = sql + " and variable_type=0";
            }
            Statement stmt = conn.createStatement();
            ResultSet rSet = stmt.executeQuery(sql);
            while (rSet.next()) {
                int variableID = rSet.getInt(1);
                String variableString = rSet.getString(2);
                boolean variableType = rSet.getBoolean(3);
                String variableDescription = null;
                String variableDevice = null;
                if (rSet.getObject(4) != null) {
                    variableDescription = rSet.getString(4);
                }
                if (rSet.getObject(5) != null) {
                    variableDevice = rSet.getString(5);
                }
                Vari var = new Vari(variableID, variableString, variableType, variableDescription, variableDevice, rSet.getString(6), rSet.getInt(7), rSet.getString(8));
                varList.add(var);
            }
            rSet.close();
            stmt.close();
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
        }
        return varList;
    }

    /**
     * 获取变量库中所有非删除状态变量的列表
     *
     * @return List<Vari>可能为空，请注意检查
     */
    public List<Vari> getAllVariable() {
        List<Vari> varList = new ArrayList<>();
        try {
            String sql = "SELECT variableID,variable_string,variable_type,variable_description,variable_device,variable_scope,accuracyDigit,varUnit FROM variable WHERE isDeleted=0";
            Statement stat = conn.createStatement();
            ResultSet rSet = stat.executeQuery(sql);
            while (rSet.next()) {
                int variableID = rSet.getInt(1);
                String variableString = rSet.getString(2);
                boolean variableType = rSet.getBoolean(3);
                String varDescription = null;
                String variableDevice = null;
                if (rSet.getObject(4) != null) {
                    varDescription = rSet.getString(4);
                }
                if (rSet.getObject(5) != null) {
                    variableDevice = rSet.getString(5);
                }
                Vari var = new Vari(variableID, variableString, variableType, varDescription, variableDevice, rSet.getString(6), rSet.getInt(7), rSet.getString(8));
                varList.add(var);
            }
            rSet.close();
            stat.close();
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
        }
        return varList;
    }

    /**
     * 获取所有变量类型为“已知变量”的变量对象
     *
     * @return List<Vari>
     */
    public List<Vari> getAllKnownVariable() {
        List<Vari> varList = new ArrayList<>();
        try {
            String sql = "SELECT variableID,variable_string,variable_type,variable_description,variable_device,variable_scope,accuracyDigit,varUnit FROM variable WHERE isDeleted=0 and variable_type=0";
            Statement stat = conn.createStatement();
            ResultSet rSet = stat.executeQuery(sql);
            while (rSet.next()) {
                int variableID = rSet.getInt(1);
                String variableString = rSet.getString(2);
                boolean variableType = rSet.getBoolean(3);
                String varDescription = null;
                String variableDevice = null;
                if (rSet.getObject(4) != null) {
                    varDescription = rSet.getString(4);
                }
                if (rSet.getObject(5) != null) {
                    variableDevice = rSet.getString(5);
                }
                Vari var = new Vari(variableID, variableString, variableType, varDescription, variableDevice, rSet.getString(6), rSet.getInt(7), rSet.getString(8));
                varList.add(var);
            }
            rSet.close();
            stat.close();
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
        }
        return varList;
    }

    boolean addFormula(int equationVarID, String equationRight) {
        return addFormula(equationVarID, equationRight, "");
    }

    boolean addFormula(int equationVarID, String equationRight, String description) {

        try {
            //测试预编译版本
            String sql = "INSERT INTO formula (equation_right,equation_VarID,description) VALUES (?,?,?)";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, equationRight);
            stat.setInt(2, equationVarID);
            if (description.isEmpty())
                stat.setNull(3, Types.NULL);
            else
                stat.setString(3, description);
            int line = stat.executeUpdate();
            Logger.getGlobal().log(Level.INFO, "更新记录数" + line);
            stat.close();
            return true;
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
            return false;
        }
    }

    /**
     * 向数据库中添加公式
     *
     * @param equationVarID   不能为空
     * @param equationRight   不能为空
     * @param description     可以为空
     * @param ruleType        不能为空 //todo 考虑是否要过滤这个输入
     * @param restrictedVarID 有效规则时不能为空
     * @param lowerBound      有效规则时不能为空
     * @param upperBound      有效规则时不能为空
     * @param ruleDescription 可以为空
     * @param ruleMore        可以为空
     * @return Boolean 添加成功返回true，失败返回false
     */
    boolean addFormula(int equationVarID, String equationRight, String description, int ruleType, int restrictedVarID, double lowerBound, double upperBound, String ruleDescription, String ruleMore) {
        try {
            String sql = "INSERT INTO formula (equation_VarID,equation_right, description, restrictedVarID, lowerBound, upperBound, rule_description, rule_more) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement stat = conn.prepareStatement(sql);

            stat.setInt(1, equationVarID);
            stat.setString(2, equationRight);
            if (description.isEmpty())
                stat.setNull(3, Types.NULL);
            else
                stat.setString(3, description);

            //规则的状态：“0”-无效规则；“1”-上下限皆有；“2”-只有下限值；“3”-只有上限值；“4”-上下限相等（等号）
            switch (ruleType) {
                case 1:
                case 4: {
                    stat.setInt(4, restrictedVarID);
                    stat.setDouble(5, lowerBound);
                    stat.setDouble(6, upperBound);
                    if (ruleDescription.isEmpty())
                        stat.setNull(7, Types.NULL);
                    else
                        stat.setString(7, ruleDescription);
                    if (ruleMore.isEmpty())
                        stat.setNull(8, Types.NULL);
                    else
                        stat.setString(8, ruleMore);
                    break;
                }
                case 2: {
                    stat.setInt(4, restrictedVarID);
                    stat.setDouble(5, lowerBound);
                    stat.setNull(6, Types.NULL);
                    if (ruleDescription.isEmpty())
                        stat.setNull(7, Types.NULL);
                    else
                        stat.setString(7, ruleDescription);
                    if (ruleMore.isEmpty())
                        stat.setNull(8, Types.NULL);
                    else
                        stat.setString(8, ruleMore);
                    break;
                }
                case 3: {
                    stat.setInt(4, restrictedVarID);
                    stat.setNull(5, Types.NULL);
                    stat.setDouble(6, upperBound);
                    if (ruleDescription.isEmpty())
                        stat.setNull(7, Types.NULL);
                    else
                        stat.setString(7, ruleDescription);
                    if (ruleMore.isEmpty())
                        stat.setNull(8, Types.NULL);
                    else
                        stat.setString(8, ruleMore);
                    break;
                }
                default: {
                    stat.setNull(4, Types.NULL);
                    stat.setNull(5, Types.NULL);
                    stat.setNull(6, Types.NULL);
                    stat.setNull(7, Types.NULL);
                    stat.setNull(8, Types.NULL);
                    break;
                }
            }
            int line = stat.executeUpdate();
            Logger.getGlobal().info("更新记录数" + line);
            stat.close();
            return true;
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
            return false;
        }
    }

    boolean updateVariable(@NotNull Vari inVar) {
        try {
            String sql = "UPDATE variable SET [variable_description]=?,[variable_device]=?,[variable_scope]=?,[accuracyDigit]=?,[varUnit]=? WHERE variableID = ? and isDeleted=0";
            PreparedStatement stat = conn.prepareStatement(sql);
            if (inVar.getVariableDescription().isEmpty())
                stat.setNull(1, Types.NULL);
            else
                stat.setString(1, inVar.getVariableDescription());
            if (inVar.getVarDevice().isEmpty())
                stat.setNull(2, Types.NULL);
            else
                stat.setString(2, inVar.getVarDevice());
            stat.setString(3, inVar.getVarScope());
            stat.setInt(4, inVar.getAccuracyDigit());
            stat.setString(5, inVar.getVarUnit());
            stat.setInt(6, inVar.getVariableID());
            int line = stat.executeUpdate();
            Logger.getGlobal().info("更新记录数" + line);
            return true;
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
            return false;
        }
    }

    /**
     * 更新无规则公式的方法，全部覆盖，可将有规则地变成无规则的
     *
     * @param equationID
     * @param equationVarID
     * @param equationRight
     * @param description
     * @return boolean
     */
    boolean updateFormula(int equationID, int equationVarID, String equationRight, String description) {
        //因为ruleType=0，所以666的值并不会影响数据写入
        return updateFormula(equationID, equationVarID, equationRight, description, 0, 666, 666, 666, "", "");
    }

    /**
     * 更新数据库中的公式
     *
     * @param equationID      不能为空
     * @param equationVarID   不能为空
     * @param equationRight   不能为空
     * @param description     可以为空
     * @param ruleType        不能为空，当此参数为0时，后续参数全部作废，全部向数据库中写入NULL //todo 考虑是否要过滤这个输入
     * @param restrictedVarID 有效规则时不能为空
     * @param lowerBound      有效规则时不能为空
     * @param upperBound      有效规则时不能为空
     * @param ruleDescription 可以为空
     * @param ruleMore        可以为空
     * @return boolean
     */
    boolean updateFormula(int equationID, int equationVarID, String equationRight, String description, int ruleType, int restrictedVarID, double lowerBound, double upperBound, String ruleDescription, String ruleMore) {
        try {
            String sql = "UPDATE formula SET equation_VarID=?,equation_right=?, description=?, restrictedVarID=?, lowerBound=?, upperBound=?, rule_description=?, rule_more=? WHERE equationID = ? and isDeleted=0";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setInt(1, equationVarID);
            stat.setString(2, equationRight);
            if (description.isEmpty())
                stat.setNull(3, Types.NULL);
            else
                stat.setString(3, description);

            //规则的状态：“0”-无效规则；“1”-上下限皆有；“2”-只有下限值；“3”-只有上限值；“4”-上下限相等（等号）
            switch (ruleType) {
                case 1:
                case 4: {
                    stat.setInt(4, restrictedVarID);
                    stat.setDouble(5, lowerBound);
                    stat.setDouble(6, upperBound);
                    if (ruleDescription.isEmpty())
                        stat.setNull(7, Types.NULL);
                    else
                        stat.setString(7, ruleDescription);
                    if (ruleMore.isEmpty())
                        stat.setNull(8, Types.NULL);
                    else
                        stat.setString(8, ruleMore);
                    break;
                }
                case 2: {
                    stat.setInt(4, restrictedVarID);
                    stat.setDouble(5, lowerBound);
                    stat.setNull(6, Types.NULL);
                    if (ruleDescription.isEmpty())
                        stat.setNull(7, Types.NULL);
                    else
                        stat.setString(7, ruleDescription);
                    if (ruleMore.isEmpty())
                        stat.setNull(8, Types.NULL);
                    else
                        stat.setString(8, ruleMore);
                    break;
                }
                case 3: {
                    stat.setInt(4, restrictedVarID);
                    stat.setNull(5, Types.NULL);
                    stat.setDouble(6, upperBound);
                    if (ruleDescription.isEmpty())
                        stat.setNull(7, Types.NULL);
                    else
                        stat.setString(7, ruleDescription);
                    if (ruleMore.isEmpty())
                        stat.setNull(8, Types.NULL);
                    else
                        stat.setString(8, ruleMore);
                    break;
                }
                default: {
                    stat.setNull(4, Types.NULL);
                    stat.setNull(5, Types.NULL);
                    stat.setNull(6, Types.NULL);
                    stat.setNull(7, Types.NULL);
                    stat.setNull(8, Types.NULL);
                    break;
                }
            }
            stat.setInt(9, equationID);
            int line = stat.executeUpdate();
            Logger.getGlobal().info("更新记录数" + line);
            return true;
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
            return false;
        }
    }

    /**
     * 在数据库中对公式标记删除
     *
     * @param equationID
     * @return boolean 操作成功与否
     */
    boolean deleteFormula(int equationID) {
        try {
            String sql = "UPDATE formula SET isDeleted=? WHERE isDeleted=0 and equationID = ?";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setBoolean(1, true);
            stat.setInt(2, equationID);
            int line = stat.executeUpdate();
            Logger.getGlobal().info("更新记录数" + line);
            stat.close();
            return true;
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
            return false;
        }
    }

    /**
     * 在数据库中对公式标记删除
     *
     * @param equation
     * @return boolean 操作成功与否
     */
    boolean deleteFormula(@NotNull Equation equation) {
        return deleteFormula(equation.getEquationID());
    }

    /**
     * 在数据库中对变量定义标记删除
     *
     * @param varID int
     * @return 成功/失败
     */
    boolean deleteVariable(int varID) {
        try {
            String sql = "UPDATE variable SET isDeleted=? WHERE isDeleted=0 and variableID = ?";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setBoolean(1, true);
            stat.setInt(2, varID);
            int line = stat.executeUpdate();
            Logger.getGlobal().info("更新记录数" + line);
            stat.close();
            return true;
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
            return false;
        }
    }

    /**
     * 在数据库中对变量定义标记删除
     *
     * @param var Vari
     * @return 成功/失败
     */
    boolean deleteVariable(@NotNull Vari var) {
        return deleteVariable(var.getVariableID());
    }

    /**
     * 添加新的变量定义，并返回新定义变量的对象
     *
     * @param varString      String应是处理过的非空字符串
     * @param isCalculated   boolean
     * @param varDescription String 可以为空但不能为null
     * @param varDevice      String 可以为空但不能为null
     * @return
     */
    Vari addVariable(@NotNull String varString, boolean isCalculated, @NotNull String varDescription, @NotNull String varDevice, String varScope, int accuracyDigit, String varUnit) throws LogicalException {
        try {
            //测试预编译版本
            String sql = "INSERT INTO variable (variable_string,variable_type,variable_description,variable_device, variable_scope,accuracyDigit,varUnit) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, varString);
            stat.setBoolean(2, isCalculated);
            if (varDescription.isEmpty())
                stat.setNull(3, Types.NULL);
            else
                stat.setString(3, varDescription);
            if (varDevice.isEmpty())
                stat.setNull(4, Types.NULL);
            else
                stat.setString(4, varDevice);
            if (varScope.isEmpty())
                stat.setNull(5, Types.NULL);
            else
                stat.setString(5, varScope);
            stat.setInt(6, accuracyDigit);
            stat.setString(7, varUnit);
            int line = stat.executeUpdate();
            Logger.getGlobal().log(Level.INFO, "更新记录数" + line);
            stat.close();
            return getVar(varString);
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
            return null;
        }
    }

    void repairVariableAssociation() throws LogicalException {
        List<Integer> validList = new ArrayList<>();
        List<Integer> invalidList = new ArrayList<>();
        try {
            String sql = "SELECT variableID,variable_string FROM variable WHERE isDeleted=1";
            Statement stat = conn.createStatement();
            ResultSet rSet = stat.executeQuery(sql);
            while (rSet.next()) {
                int variableID = rSet.getInt(1);
                String variableString = rSet.getString(2);
                if (!variableString.isEmpty()) {
                    Vari validVar = getVar(variableString);
                    //如果变量库中不存在新的变量定义，则跳过替换步骤
                    if (validVar == null)
                        continue;
                    else {
                        validList.add(validVar.getVariableID());
                        invalidList.add(variableID);
                    }
                }
            }
            rSet.close();
            stat.close();
            String sql2 = "UPDATE formula SET equation_VarID=? WHERE isDeleted=0 and equation_VarID = ?";
            String sql3 = "UPDATE formula SET restrictedVarID=? WHERE isDeleted=0 and restrictedVarID = ?";
            PreparedStatement stat2 = conn.prepareStatement(sql2);
            PreparedStatement stat3 = conn.prepareStatement(sql3);
            int count = 0;
            for (int i = 0, amount = invalidList.size(); i < amount; ++i) {
                int valid = validList.get(i);
                int invalid = invalidList.get(i);
                stat2.setInt(1, valid);
                stat2.setInt(2, invalid);
                count += stat2.executeUpdate();
                stat3.setInt(1, valid);
                stat3.setInt(2, invalid);
                count += stat3.executeUpdate();
            }
            stat2.close();
            stat3.close();
            Logger.getGlobal().log(Level.INFO, "更新记录数：" + count);
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "数据库错误", e);
        }
    }

    String getTopPath(Class cls)
    {
        String path=cls.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        int firstIndex = path.lastIndexOf(System.getProperty("path.separator"))+1;
        int lastIndex = path.lastIndexOf(File.separator)+1;
        path=path.substring(firstIndex,lastIndex);
        return path;
    }
}
