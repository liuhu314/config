package config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模块配置文件
 * Created by liuhu on 2018/1/17.
 */
public class Module implements ConfigBase {
    HashMap<String, String> fileType;
    HashMap<String, HashMap> mapData;
    HashMap<String, List> listData;


    public boolean load(String filePath, String name) {
        return true;
    }

    public String getMapValueAsString(String fileName, String priKey, String key) {
        return null;
    }

    public String getListValueAsString(String fileName, int index) {
        return null;
    }
}
