package com.run.tools;

import com.baidu.aip.speech.AipSpeech;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import net.sf.json.JSONObject;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class asrapi {
    private static  WordVectorModel wordVectorModel;
    static {
        try {
            wordVectorModel = new WordVectorModel("E:\\download\\word2vec_c");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static List<String> dictc= dict.init();
    private static DocVectorModel docVectorModel=new DocVectorModel(wordVectorModel);
    private static deom2 ffmpegx;
    public static void asr(AipSpeech client)
    {
        // 对本地语音文件进行识别
        //String path = "E:\\1-1\\1-1.pcm_69640-84499_A.pcm";
        //org.json.JSONObject asrRes = client.asr(path, "pcm", 16000, null);
        //System.out.println(asrRes);
        ArrayList<String> tmp=getAllFileName("E:\\1-1");
        for (int i=0;i<tmp.size();i++)
        {
            String str=tmp.get(i);
            Double starttime=0.0;
            Double endtime=0.0;
            Boolean flag=true;
            Integer delim1=str.indexOf("_");
            Integer delim2=str.indexOf("-",delim1);
            if (delim1==-1||delim2==-1) break;
            String tmp1=str.substring(delim1+1,delim2);
            starttime=Double.valueOf(tmp1);
            delim1=str.indexOf("_",delim2);
            if (delim1==-1||delim2==-1) break;
            tmp1=str.substring(delim2+1,delim1);
            endtime=Double.valueOf(tmp1);
            delim2=str.indexOf(".",delim1);
            tmp1=str.substring(delim1+1,delim2);
            if (delim1==-1||delim2==-1) break;
            if (tmp1=="A") flag=true;
            System.out.println(tmp1+flag);
            org.json.JSONObject asrRes = client.asr("E:\\1-1\\"+str, "pcm", 16000, null);
            while (!asrRes.getString("err_msg").equals("success.")) {
                asrRes = client.asr("E:\\1-1\\" + str, "pcm", 16000, null);
                System.out.println(asrRes.toString());
            }
            double max=0;
            String sug="";
            JSONArray res=asrRes.getJSONArray("result");
            List<Object> strres=res.toList();
            String parse=strres.get(0).toString();
            System.out.println(parse);
                for (int k = 0; k < dictc.size(); k++) {
                    if (docVectorModel.similarity(parse, dictc.get(k)) > max) {
                        max = docVectorModel.similarity(parse, dictc.get(k));
                        sug = dictc.get(k);
                    }
                }

            if (flag==true) {
                System.out.println(res + "has bgm" +sug+max);
            }
            List<String> sound=new ArrayList<>();
            if (max>0.7)
            sound.add(sug);
            ffmpegx.addsoundeffects(str,sound,"E:\\);
        }
        // 对语音二进制数据进行识别
        //byte[] data = Util.readFileByBytes(path);     //readFileByBytes仅为获取二进制数据示例
        //org.json.JSONObject asrRes2 = client.asr(data, "pcm", 16000, null);
        //System.out.println(asrRes2);

    }
    public static void main(String[] args)
    {
        AipSpeech aipSpeech = new AipSpeech("16720362","drYw1Ut4GQRAeZSC2FMqbNEg", "36Oz5GXBqYWIMC6QfG9GG4AGrVqO0CUI");
        asr(aipSpeech);
    }
    public static ArrayList<String> getAllFileName(String path)
    {
        ArrayList<String> res=new ArrayList<>();
        File file = new File(path);
        File [] files = file.listFiles();
        String [] names = file.list();
        if(names != null)
            res.addAll(Arrays.asList(names));
        return res;
    }
    public static Boolean parse_file(String str,Double st,Double en,Boolean fl)
    {
        Integer delim1=str.indexOf("_");
        Integer delim2=str.indexOf("-",delim1);
        if (delim1==-1||delim2==-1) return false;
        String tmp1=str.substring(delim1+1,delim2);
        System.out.println(tmp1+delim1+delim2);
        st=Double.valueOf(tmp1);
        delim1=str.indexOf("_",delim2);
        if (delim1==-1||delim2==-1) return false;
        tmp1=str.substring(delim2+1,delim1);
        System.out.println(tmp1+delim1+delim2);
        en=Double.valueOf(tmp1);
        delim2=str.indexOf(".",delim1);
        tmp1=str.substring(delim1+1,delim2-1);
        if (delim1==-1||delim2==-1) return false;
        fl=Boolean.valueOf(tmp1);
        return true;
    }
}
