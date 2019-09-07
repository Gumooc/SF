package com.run.tools;

import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.ArrayList;

public class newbin {
    public static void main(String[] args) throws IOException, FileNotFoundException {

        File f=new File("E:\\model\\model.bin");
        FileInputStream fis=new FileInputStream(f);
        DataInputStream dis=new DataInputStream(fis);
        byte[]by=new byte[100];//要读取的位数
        dis.read(by);
        String st=new String(by,"ISO-8859-1");
        System.out.print(st);
    }
}
