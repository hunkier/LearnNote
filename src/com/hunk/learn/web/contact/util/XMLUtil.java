package com.hunk.learn.web.contact.util;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * xml的工具类
 * Created by hunk on 2015/8/3.
 */
public class XMLUtil {
    public final static  String FILE_PATH = XMLUtil.class.getResource("/").getPath() +"com/hunk/learn/web/contact/contact.xml";

    /**
     * 读取xml文档方法
     * @return
     */
    public static Document getDocument(){
        try {
            Document doc = new SAXReader().read(new File(FILE_PATH));
            return  doc;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 写出到xml文档中
     * @param doc
     */
    public static void write(Document doc){
        try {
            FileOutputStream out = new FileOutputStream(FILE_PATH);
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(out,format);
            writer.write(doc);
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
