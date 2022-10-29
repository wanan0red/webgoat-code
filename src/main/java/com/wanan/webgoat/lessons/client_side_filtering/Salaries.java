package com.wanan.webgoat.lessons.client_side_filtering;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.annotation.PostConstruct;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class Salaries {
    @Value("${webgoat.user.directory}")
    private String webGoatHomeDirectory;
    @PostConstruct
    public void copyFiles(){
        ClassPathResource classPathResource = new ClassPathResource("lessons/employees.xml");
//        首先从classpath 中读取employees.xm文件
        File targetDirectory = new File(webGoatHomeDirectory, "/ClientSideFiltering");
//        新建一个目标问阿金
        if (!targetDirectory.exists()){
            targetDirectory.mkdir();
//            创建文件夹
        }
        try {
            FileCopyUtils.copy(classPathResource.getInputStream(),new FileOutputStream(new File(targetDirectory,"employees.xml")));
//            将文件复制进去
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("clientSideFiltering/salaries")
//    这里其实只要是get请求就会执行 不管有没有参数
    @ResponseBody
    public List<Map<String,Object>> invoke(){
        NodeList nodes = null;
        File d = new File(webGoatHomeDirectory,"ClientSideFiltering/employees.xml");
//        新建一个employees.xml对象
        XPathFactory factory = XPathFactory.newInstance();
//          获得一个xml解析对象
        XPath path = factory.newXPath();

        try (InputStream is = new FileInputStream(d)){
//            首先从创建一个文件读入流
            InputSource inputStream = new InputSource(is);
//            创建一个新的字节输入流
            StringBuffer sb = new StringBuffer();
//            这里的sb 其实就对应着xml 中的节点名称
            sb.append("/Employees/Employee/UserID | ");
            sb.append("/Employees/Employee/FirstName | ");
            sb.append("/Employees/Employee/LastName | ");
            sb.append("/Employees/Employee/SSN | ");
            sb.append("/Employees/Employee/Salary ");
            String expression = sb.toString();
            nodes = (NodeList) path.evaluate(expression,inputStream, XPathConstants.NODESET);
//            这里对应节点进行解析

        }catch (XPathExpressionException e){
            log.error("Unable to parse xml",e);
        }catch (IOException e){
            log.error("Unable to read employees.xml at location: '{}'",d);
        }
        int columns = 5;
        List json = new ArrayList();
        java.util.Map<String,Object> employeeJson = new HashMap<>();
        for (int i = 0; i < nodes.getLength(); i ++ ){
            if (i % columns == 0){
                employeeJson = new HashMap<>();
                json.add(employeeJson);
            }
            Node node = nodes.item(i);
            employeeJson.put(node.getNodeName(),node.getTextContent());
        }
//        将数据填入到map中
        return json;
    }

}
