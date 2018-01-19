package config;

/**
 * 预留配置
 * Created by liuhu on 2018/1/17.
 */
public class Other implements ConfigBase {
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
