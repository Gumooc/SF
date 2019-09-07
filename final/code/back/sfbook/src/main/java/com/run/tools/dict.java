package com.run.tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dict {
    public static List<String> dict=new ArrayList<String>();
    public static List<String> init(){
        List<String> res=new ArrayList<String>();
        String path = "/usr/music/index/RenWuPeiYin.txt";
        File newfile = new File(path);
        List<String> list = new ArrayList<String>();
        list = getFileContext(path);
        for (int i=0;i<list.size();i++)
        res.add(list.get(i));
        path = "/usr/music/index/RiChangShengHuo.txt";
        newfile = new File(path);
        list = new ArrayList<String>();
        list = getFileContext(path);
        for (int i=0;i<list.size();i++)
            res.add(list.get(i));
        path = "/usr/music/index/TianQiYinXiao.txt";
        newfile = new File(path);
        list = new ArrayList<String>();
        list = getFileContext(path);
        for (int i=0;i<list.size();i++)
            res.add(list.get(i));
        return res;
    }
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        init();
        //list=init("D:\\BGM\\TianQiYinXiao.txt");
        dict=init();
        for (int i=0;i<dict.size();i++)
            System.out.println(dict.get(i));
        //    System.out.println(list.get(i));
        //for (int i=0;i<list.size();i++)
        //    dict.add(list.get(i));
    }
    public static List<String> getFileContext( String path ) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        List<String> list = new ArrayList<String>();
        String str = "";
        try {
            Charset charset = Charset.forName("GBK");
        fileReader = new FileReader(path);
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path),"GBK"));
            //bufferedReader = new BufferedReader(fileReader);
        while( (str = bufferedReader.readLine()) != null ) {
        if( str.trim().length() > 2 ) {
        }
        String regexp = "\'";
        str=str.substring(2,str.length()-2);
        //str.replace("","");
        str = str.replaceAll(regexp, "");
        //regexp =" ";
        //str = str.replaceAll(regexp,"");
        //System.out.println(str);
        list= new ArrayList(Arrays.asList(str.split(",")));
        List<String> ret=new ArrayList<>();
        for (int i=0;i<list.size();i++)
        {
        	String tmp=list.get(i);
        	ret.add(tmp.substring(1,tmp.length()));
        }
        return ret;
    }
    } catch ( Exception e ) {
        e.printStackTrace();
        } finally {
    try {
    if (bufferedReader != null) {
    bufferedReader.close();
    }
    } catch (Exception e2) {
    e2.printStackTrace();
    }
    try {
    if (fileReader != null) {
    fileReader.close();
    }
    } catch (Exception e2) {
    e2.printStackTrace();
    }
    }
    return list;
    }
}
