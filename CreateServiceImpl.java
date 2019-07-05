package com.run.service.Impl;

import com.baidu.aip.nlp.AipNlp;
import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.run.service.CreateService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

@Service
public class CreateServiceImpl implements CreateService {
    @Override
    public JSONObject createoriginbook(MultipartFile txt) {
        AipSpeech aipSpeech = new AipSpeech("16683764","drYw1Ut4GQRAeZSC2FMqbNEg", "36Oz5GXBqYWIMC6QfG9GG4AGrVqO0CUI");
        AipNlp client = new AipNlp("16683764","drYw1Ut4GQRAeZSC2FMqbNEg", "36Oz5GXBqYWIMC6QfG9GG4AGrVqO0CUI");
        byte bb[] = null;
        try {
            bb = txt.getBytes();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String text = "";
        System.out.println("start");
        text = new String(bb);
        System.out.println(text);
        String tmp="";
        for (int i=0;i<text.length();i++)
        {
            char c=text.charAt(i);
            tmp=tmp+c;
            if (String.valueOf(c).equals("。")||tmp.length()>=256)
            {
                 // 传入可选参数调用接口
                System.out.println(tmp);
                HashMap<String, Object> options = new HashMap<String, Object>();
                org.json.JSONObject res= client.lexer(tmp,options);
                System.out.println(res.toString());
                tmp="";
            }
        }
        HashMap<String, Object> options = new HashMap<String, Object>();
        org.json.JSONObject res= client.lexer(tmp,options);
        System.out.println(res.toString());
        return new JSONObject();
    }
    @Override
    public JSONObject createsoundeffect(MultipartFile text) {
        return new JSONObject();
    }
    @Override
    public JSONObject emotionanalyse(MultipartFile text) {
        return new JSONObject();
    }
    @Override
    public JSONObject wordanalyse(MultipartFile text){
        return new JSONObject();
    }
    @Override
    public void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file,true);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
