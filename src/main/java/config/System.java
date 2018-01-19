package config;

/**
 * 减少更改权限的系统配置
 * Created by liuhu on 2018/1/17.
 */
public class System implements ConfigBase {
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
