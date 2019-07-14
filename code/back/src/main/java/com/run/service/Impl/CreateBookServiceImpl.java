package com.run.service.Impl;

import com.baidu.aip.nlp.AipNlp;
import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.hankcs.hanlp.suggest.Suggester;
import com.run.tools.deom2;
import com.run.tools.dict;
import com.run.service.CreateBookService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CreateBookServiceImpl implements CreateBookService {
    private deom2 ffmpegx;
    private static List<String> dictc= dict.init();
    
    @Override
    public JSONObject bysound(JSONObject info, MultipartFile sound) {
    	return null;
    }
    
    
    @Override
    public JSONObject bytext(JSONObject info, MultipartFile txt) throws Exception{
        AipSpeech aipSpeech = new AipSpeech("16720362","drYw1Ut4GQRAeZSC2FMqbNEg", "36Oz5GXBqYWIMC6QfG9GG4AGrVqO0CUI");
        AipNlp client = new AipNlp("16720362","drYw1Ut4GQRAeZSC2FMqbNEg", "36Oz5GXBqYWIMC6QfG9GG4AGrVqO0CUI");
        byte bb[] = null;
        try {
            bb = txt.getBytes();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Integer bid=info.getInt("bid");
        Integer chapter=info.getInt("chapter");
        String spd=info.getString("spd");
        String pit=info.getString("pit");
        String vol=info.getString("vol");
        String per=info.getString("per");
        String text = "";
        System.out.println("start");
        text = new String(bb,"GBK");
        String tmp="";
        Double time=0.0;
        Double pretime=0.0;
        String Path="/staticsrc/audio/";
        String Filename=bid.toString()+'_'+chapter.toString();
        Integer Part=0;
        String MusicPath="/usr/music/handle";
        Suggester suggester = new Suggester();
        for (int i=0;i<dictc.size();i++)
            suggester.addSentence(dictc.get(i));
        String sug="";
        Double start=0.0;
        for (int i=0;i<text.length();i++)
        {
            List<String> sound=new ArrayList<>();
            List<Double> soundtime=new ArrayList<>();
            char c=text.charAt(i);
            tmp=tmp+c;
            if (tmp.length()>=100) {
                HashMap<String, Object> options = new HashMap<String, Object>();
                options.put("spd", spd);
                options.put("pit", pit);
                options.put("per", per);
                TtsResponse ttsResponse = aipSpeech.synthesis(tmp, "zh", 1, options);
                byte[] aa = ttsResponse.getData();
                getFile(aa, Path, Filename + "-" + Part + ".mp3");
                time = ffmpegx.processFLT(Path + Filename + "-" + Part + ".mp3");
                System.out.println(time);
                int num = 0;
                String tmpx = "";
                for (int j = 0; j < tmp.length(); j++) {
                    char cx = tmp.charAt(j);
                    tmpx = tmpx + cx;
                    if (cx == '。') {
                        boolean flag = true;
                        sug = suggester.suggest(tmpx, 1).get(0);
                        int tot = 0;
                        for (int k = 0; k < sug.length(); k++) {
                            if (tmpx.indexOf(sug.charAt(k)) != -1)
                                tot++;
                        }
                        if (flag) {
                            sound.add(MusicPath+"/"+sug+"/"+sug);
                            soundtime.add(start);
                            start =time/ 100 * j * 1000;
                        }
                        tmpx = "";
                    }
                }
                createsoundeffect(sound, soundtime, Path + Filename + "-"+Part);
                tmp="";
                Part = Part+1;
            }
        }
        List<String> sound=new ArrayList<>();
        List<Double> soundtime=new ArrayList<>();
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("spd", spd);
        options.put("pit", pit);
        options.put("per", per);
        TtsResponse ttsResponse = aipSpeech.synthesis(tmp, "zh", 1, options);
        byte[] aa = ttsResponse.getData();
        getFile(aa, Path, Filename + "-" + Part + ".mp3");
        time = ffmpegx.processFLT(Path + Filename + "-" + Part + ".mp3");
        System.out.println(time);
        int num = 0;
        String tmpx = "";
        for (int j = 0; j < tmp.length(); j++) {
            char cx = tmp.charAt(j);
            tmpx = tmpx + cx;
            if (cx == '。') {
                boolean flag = true;
                sug = suggester.suggest(tmpx, 1).get(0);
                int tot = 0;
                for (int k = 0; k < sug.length(); k++) {
                    if (tmpx.indexOf(sug.charAt(k)) != -1)
                        tot++;
                }
                if (flag) {
                    sound.add(MusicPath+"/"+sug+"/"+sug);
                    soundtime.add(start);
                    start =time/ 100 * j * 1000;
                }
                tmpx = "";
            }
        }
        createsoundeffect(sound, soundtime, Path + Filename + "-"+Part);
        Part=Part+1;
        tmp="";
        for (Integer i=0;i<Part;i++)
        {
            byte[] ret=getBytes(Path+Filename+"-"+i.toString()+"-f.mp3");
            getFile(ret,Path,Filename+"-f.mp3");
        }
        for (Integer i=0;i<Part;i++)
        {
            File file = new File(Path+Filename+"-"+i.toString()+"-f.mp3");
            if (file.exists())
                file.delete();
            file = new File(Path+Filename+"-"+i.toString()+".mp3");
            if (file.exists())
                file.delete();
        }
        String finalname=Path+Filename;
        String bgmpath=selectbgm(text);
        ffmpegx.mofidyvoice(finalname+"-f.mp3",finalname+"-f'.mp3",-11-ffmpegx.processFLV(finalname+"-f.mp3"));
        ffmpegx.merge(finalname+"-f'.mp3",bgmpath,finalname+"-f-v.mp3");
        System.out.println("finish");
        File ffFile =new File(finalname+"-f-v.mp3");
        
        
        ffFile.setReadable(true);
        ffFile.setExecutable(true);
        String command = "chmod 755 -R " + finalname+"-f-v.mp3";
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        JSONObject feedback=new JSONObject();
        feedback.put("audiopath","/audio/"+Filename+"-f-v.mp3");
        return feedback;
    }
    public void createsoundeffect(List<String> sug,List<Double> time,String dest) throws Exception {
        for (int i=0;i<sug.size();i++) {
            File file = new File(sug.get(i)+"mf.mp3");
            if (!file.exists()){
                ffmpegx.mofidyvoice(sug.get(i)+".mp3",sug.get(i)+"mf.mp3",-30-ffmpegx.processFLV(sug.get(i)+".mp3"));
            }
        }
        List<String> mf=new ArrayList<>();
        for (int i=0;i<sug.size();i++) {
            String tmpx=sug.get(i)+"mf.mp3";
            mf.add(tmpx);
        }
        ffmpegx.addsoundeffects(dest+".mp3",mf,dest+"-f.mp3",time);
        //if(file.exists()&&file.isFile())
         //   file.delete();
    }
    public void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
                System.out.println("NOT FIND");
            }
            file = new File(filePath + "/" + fileName);
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
    public static String selectbgm(String txt) throws Exception {
        String Path="/usr/music/";
        String Filename="BGM";
        Integer random=(int)(Math.random()*3+1);
        Filename=Filename+"/"+random.toString();
        System.out.println(Path+Filename);
        File file=new File(Path+Filename);
        File [] files=file.listFiles();
        int size=files.length;
        random=(int)(Math.random()*size+1);
        System.out.println(files[random].getPath());
        return files[random].getPath();
    }
    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
