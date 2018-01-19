package config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.*;
import java.lang.System;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 配置文件起点
 * Created by liuhu on 2018/1/17.
 */
public class Root {
    /**
     * 启动配置
     */
    private static Map configPath = new HashMap<String,String>();
    private static Map classPath = new HashMap<String, String>();

    public static void boot()
    {
        Document document;
        SAXReader saxReader= new SAXReader();

        try {
//            String rootPath = Root.class.getClassLoader().getResource("root.xml").getPath();
            document = saxReader.read(new File("src/main/resources/root.xml"));
            Element rootElement = document.getRootElement();
            Iterator it = rootElement.element("models").elementIterator("model");
//          Iterator it = rootElement.element("models").elementIterator();


            while (it.hasNext())
            {
                Element model = (Element) it.next();
                /*
                if(!model.getName().equals("model"))
                {
                    continue;
                }
                */
                String path = model.element("path").getTextTrim();
                String name = model.element("name").getTextTrim();
                String className = model.element("class").getTextTrim();

                configPath.put(name,path);
                classPath.put(name,className);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Iterator iterator = configPath.keySet().iterator();
        while (iterator.hasNext())
        {
            String name = (String) iterator.next();
            String path = (String) configPath.get(name);
            String className = (String) classPath.get(name);
            try {
                ConfigBase configBase = (ConfigBase) Class.forName(className).newInstance();

                if(configBase == null)
                {
                    continue;
                }

                if(!configBase.load(path, name))
                {
                    System.out.printf("配置文件加载错误，程序无法正常启动!\n");
                }

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return;
    }

}
