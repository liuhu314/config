package config;

/**
 * Created by liuhu on 2018/1/17.
 */

/**
 * 配置文件路径
 * 分类名称
 */
public interface ConfigBase {
    /**
     * 加载配置文件
     * @param filePath
     * @param name
     * @return
     */
    boolean load(String filePath,String name);

//    /**
//     *
//     * @param fileName
//     * @param perKey
//     * @param key
//     * @return
//     */
//    String getMapValueAsString(String fileName,String perKey,String key);
//
//    /**
//     *
//     * @param fileName
//     * @param index
//     * @return
//     */
//    String getListValueAsString(String fileName,int index);

}
