package config;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.*;
import java.lang.System;
import java.util.*;

/**
 * 测试配置在debug版本生效
 * Created by liuhu on 2018/1/17.
 */
public class Test implements ConfigBase {
    /**
     * <文件名，类型>
     */
    private static HashMap<String, String> fileType;
    /**
     * <文件名，map数据<主键value，map数据<configkey，configvalue>>>
     */
    private static HashMap<String, HashMap<String,HashMap<String,String>>> mapData;
    /**
     * <文件名，list数据>
     */
    private static HashMap<String, List<String>> listData;

    public boolean load(String filePath, String name) {

        File file = new File("src/main/"+filePath);

        if( file!=null &&file.isDirectory() )
        {
            File[] files = file.listFiles((dir, fileName) -> {
                if(fileName.endsWith(".xml"))
                {
                    return true;
                }
                return false;
            });

            SAXReader saxReader = new SAXReader();
            fileType = new HashMap<>();
            mapData = new HashMap<>();

            for(File file1:files)
            {
                try {
                    Document document = saxReader.read(file1);
                    Element root = document.getRootElement();
                    String type = root.element("type").getTextTrim();
                    String primary = root.elementTextTrim("primarykey");

                    fileType.put(file1.getName(), type);

                    if(type.equals("map"))
                    {
                        Iterator elementIterator = root.elementIterator("data");

                        HashMap<String,HashMap<String,String>> data = new HashMap<>();
                        while (elementIterator.hasNext())
                        {
                            Element element = (Element) elementIterator.next();
                            String primarykey = element.attribute(primary).getValue();
                            HashMap dataCore = new HashMap<String,String>();
                            Iterator it = element.attributeIterator();

                            while (it.hasNext()) {
                                Attribute attribute = (Attribute) it.next();
                                dataCore.put(attribute.getName(), attribute.getValue());
                            }
                            data.put(primarykey, dataCore);
                        }
                        mapData.put(file1.getName(), data);
                    }

                    if(type.equals("list"))
                    {
                        listData = new HashMap<>();
                        if (root.elements("data").size() != 1) {
                            System.out.printf("配置类型与解析类型不一致，文件名为：" + file1.getName());
                            continue;
                        }
                        String[] list = root.element("data").getTextTrim().split(" ");
                        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(list));
                        listData.put(file1.getName(), arrayList);
                    }


                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }


        }
        else
        {
            System.out.printf("配置文件路径错误！");
            return false;
        }
        System.out.printf(mapData.toString()+"\n"+listData.toString());
        return true;
    }

    private String getMapValue(String fileName, String pirKey, String key)
    {
        if(!mapData.containsKey(fileName))
        {
            System.out.printf("获取配置文件不存在，文件名为："+fileName);
            return null;
        }
        if(!mapData.get(fileName).containsKey(pirKey))
        {
            System.out.printf(fileName + "文件中，要索引的主键值为" + pirKey + "的数据行不存在!");
            return null;
        }
        if(!mapData.get(fileName).get(pirKey).containsKey(key))
        {
            System.out.printf(fileName + "文件中，索引的主键为" + pirKey + "的行中，不存在键值" + key);
            return null;
        }

        return mapData.get(fileName).get(pirKey).get(key);
    }

    public String getMapValueAsString(String fileName, String pirKey, String key) {
        return this.getMapValue(fileName,pirKey,key);
    }

    private String getListValue(String fileName, int index) {
        if(!listData.containsKey(fileName))
        {
            System.out.printf("获取配置文件不存在，文件名为："+fileName);
            return null;
        }

        if(listData.get(fileName).size() >= index)
        {
            System.out.printf(fileName+"文件中，要索引的主键值为"+index+"的数据不存在，数据越界!");
            return null;
        }

        return listData.get(fileName).get(index);
    }

    public String getListValueAsString(String fileName, int index) {
        return this.getListValue(fileName, index);
    }
}
