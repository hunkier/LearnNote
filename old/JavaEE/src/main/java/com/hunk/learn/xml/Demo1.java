package com.hunk.learn.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;


import java.io.File;
import java.util.Iterator;

/**
 * Created by dell on 2015/7/29.
 */
public class Demo1 {
    private static final String path = "./src/com/hunk/learn/xml/contact.xml";
    /**
     * 得到文档信息
     * @throws Exception
     */
    @Test
    public void Test1() throws Exception{
        SAXReader read = new SAXReader() ;
        Document doc = read.read(new File(path));
        System.out.println(doc);
    }

    /**
     * 得到借点信息
     * @throws Exception
     */
    @Test
    public void Test2() throws Exception{
        SAXReader read = new SAXReader() ;
        Document doc = read.read(new File(path));
//        System.out.println(doc);
        Iterator<Node> it = doc.nodeIterator();
        while (it.hasNext()){
            Node node = it.next();
            String name = node.getName();
//            System.out.println(name);
            if(node instanceof Element){
                Element elem = (Element) node;
                Iterator<Node> it2 = elem.nodeIterator();
                while (it2.hasNext()){
                    Node n2 = it2.next();
                    System.out.println(n2.getName());
                }
            }
        }
    }

    /**
     * 遍历xml文档所有节点信息
     * @throws Exception
     */
    @Test
    public void Test3() throws Exception{
        SAXReader read = new SAXReader() ;
        Document doc = read.read(new File(path));
        Element rootElem = doc.getRootElement();
        getChindNotes(rootElem);
    }

    /**
     * 获取 传入的标签下的所以子节点
     * @param elem
     */
    private void getChindNotes(Element elem){
        System.out.println(elem.getName());
        Iterator<Node> it = elem.nodeIterator();
        while (it.hasNext()){
            Node node = it.next();
            String name = node.getName();
//            System.out.println(name);
            if(node instanceof Element){
                Element el = (Element) node;
                getChindNotes(el);
            }
        }
    }
}
