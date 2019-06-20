package App.utile;

import App.dataModel.ParamAndValueData;
import App.dataModel.VersionData;

import java.util.List;

public class DataModelUtil {

    /**
     * 根据输入属性，返回ParamAndValueData。
     *
     * @param proj_id
     * @param version_name
     * @param param_id
     * @param outfitting_name
     * @param param_name
     * @param param_type
     * @param param_description
     * @param param_value
     * @return
     */
    public static ParamAndValueData getParamAndValueData(String proj_id, String version_name, String param_id, String outfitting_name, String param_name, String param_type, String param_description, String param_value) {
        ParamAndValueData paramAndValueData = new ParamAndValueData();
        paramAndValueData.setProj_id(proj_id);
        paramAndValueData.setVersion_name(version_name);
        paramAndValueData.setParam_id(param_id);
        paramAndValueData.setOutfitting_name(outfitting_name);
        paramAndValueData.setParam_name(param_name);
        paramAndValueData.setParam_type(param_type);
        paramAndValueData.setParam_description(param_description);
        paramAndValueData.setParam_value(param_value);

        return paramAndValueData;
    }

    public static VersionData getVersionData(String proj_id, String version_name, String version_description, List<ParamAndValueData> param_value_list) {
        VersionData versionData = new VersionData();
        versionData.setVersion_id(null);
        versionData.setProj_id(proj_id);
        versionData.setVersion_name(version_name);
        versionData.setVersion_description(version_description);
        versionData.setParam_value_list(param_value_list);

        return versionData;
    }
}
