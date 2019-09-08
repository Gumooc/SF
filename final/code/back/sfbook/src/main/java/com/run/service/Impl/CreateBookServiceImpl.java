package com.run.service.Impl;

import com.baidu.aip.nlp.AipNlp;
import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.speech.restapi.jniTest.FileCutUtil;
import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.hankcs.hanlp.suggest.Suggester;
import com.run.tools.*;
import com.run.dao.BookDao;
import com.run.service.CreateBookService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;


@Service
public class CreateBookServiceImpl implements CreateBookService {
	
    private static deom2 ffmpegx;
    private static List<String> dictc= dict.init();
    private static  WordVectorModel wordVectorModel;
    private FileCutUtil filecut;
    private static final String MODEL_PATH = "/usr/HanLP/data/model/sen/classification-sen-model.ser";
    private static final String CORPUS_FOLDER =  "/usr/HanLP/data/dictionary/sen/ChnSentiCorp情感分析酒店评论";
    private static IClassifier classifier;
    {
        try {
            classifier = new NaiveBayesClassifier(trainOrLoadModel());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static {
        try {
            wordVectorModel = new WordVectorModel("/usr/HanLP/word2vec_c");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static DocVectorModel docVectorModel=new DocVectorModel(wordVectorModel);
    
    private static String getProperties(String key) throws IOException {
    	Properties prop = PropertiesLoaderUtils.loadAllProperties("bookfile.properties");
    	return prop.getProperty(key);
    }
    
    @Override
    public JSONObject bysound(HttpSession session, JSONObject info, MultipartFile sound) throws Exception {
        byte bb[] = null;
        System.out.println("start");/*
        String Path="/staticsrc/audio/";
        String MusicPath="/usr/music/handle/";
        String BgmPath="/usr/music/BGM/";
        */
        String Path = getProperties("Path");
        String MusicPath= getProperties("MusicPath");
        String BgmPath = getProperties("BgmPath");
        
        
        String originfilename=sound.getOriginalFilename();
        String format=originfilename.substring(originfilename.length()-3);
        System.out.println(format);
        if (!format.equals("mp3"))
        {
            JSONObject feedback=new JSONObject();
            feedback.put("error","wrong format");
            return feedback;
        }
        Integer bid=info.getInt("bid");
        Integer chapter=info.getInt("chapter");
        Path=Path+bid.toString()+"_"+chapter.toString()+"/";
        File tmpfile=new File(Path);
        if (tmpfile.exists()) tmpfile.delete();
        tmpfile.mkdir();
        String command = "chmod 755 -R " + Path.toString();
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(originfilename);
        try {
            bb = sound.getBytes();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (session.getAttribute("createlist")==null){
            JSONArray  createlist=new JSONArray();
            session.setAttribute("createlist",createlist);
        }
        
        JSONObject newbook=new JSONObject();
        JSONArray  createlist= (JSONArray) session.getAttribute("createlist");
        newbook.put("bid",bid);
       
        newbook.put("chapter",chapter);
        createlist.add(newbook);
        for (int i=0;i<createlist.size();i++)
        {
        	JSONObject tmp=createlist.getJSONObject(i);
        	if (tmp.getInt("bid")==bid&&tmp.getInt("chapter")==chapter)
        	{
        		newbook=tmp;
        		break;
        	}
        }
        newbook.put("status","正在上传mp3");
        newbook.put("start",0);
        newbook.put("end",1);
        String filename=bid.toString()+'_'+chapter.toString();
        getFile(bb,Path,filename+".mp3");
        newbook.put("start",1);
        ffmpegx.denoise(Path+filename+".mp3",Path+filename+"de.mp3");
        String pre=filename;
        filename=filename+"de";
        newbook.put("status","转换pcm文件");
        newbook.put("start",0);
        newbook.put("end",1);
        ffmpegx.changetopcm(Path+filename+".mp3");
        newbook.put("start",1);
        //System.out.println("OK?"+Path+filename);
        //System.out.println(Path+filename);
        newbook.put("status","分片pcm文件");
        newbook.put("start",0);
        newbook.put("end",1);
        filecut.cutpcmfile(Path,filename);
        newbook.put("start",1);
        ArrayList<String> allfilename=getAllFileName(Path+filename);
        AipSpeech aipSpeech = new AipSpeech("16720362","drYw1Ut4GQRAeZSC2FMqbNEg", "36Oz5GXBqYWIMC6QfG9GG4AGrVqO0CUI");
        newbook.put("status","语音识别");
        newbook.put("start",0);
        newbook.put("end",allfilename.size());
        asr(aipSpeech,Path,filename,MusicPath,BgmPath,newbook,pre);
        System.out.println("FINE");
        
        File ffFile =new File(Path+pre+"-f-v.mp3");
        ffFile.setReadable(true);
        ffFile.setExecutable(true);
        
        String command1 = "chmod 755 -R " + Path+pre+"-f-v.mp3";
        try {
            Runtime.getRuntime().exec(command1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        JSONObject feedback=new JSONObject();
        feedback.put("audiopath",Path+pre+"-f-v.mp3");
        return feedback;
    }
    
    /* create book by text*/
    @Override
    public JSONObject bytext(HttpSession session, JSONObject info, MultipartFile txt) throws Exception{
        AipSpeech aipSpeech = new AipSpeech("16720362","drYw1Ut4GQRAeZSC2FMqbNEg", "36Oz5GXBqYWIMC6QfG9GG4AGrVqO0CUI");
        AipNlp client = new AipNlp("16720362","drYw1Ut4GQRAeZSC2FMqbNEg", "36Oz5GXBqYWIMC6QfG9GG4AGrVqO0CUI");
        byte bb[] = null;
        try {
            bb = txt.getBytes();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String originfilename=txt.getOriginalFilename();
        String format=originfilename.substring(originfilename.length()-3);
        if (!format.equals("txt"))
        {
            JSONObject feedback=new JSONObject();
            feedback.put("error","wrong format");
            return feedback;
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
        /*
        String Path="/staticsrc/audio/";
        String MusicPath="/usr/music/handle/";
        */
        String Path = getProperties("Path");
        String MusicPath = getProperties("MusicPath");
        
        String Filename=bid.toString()+'_'+chapter.toString();
        Path=Path+bid.toString()+"_"+chapter.toString()+"/";
        File tmpfile=new File(Path);
        if (tmpfile.exists()) tmpfile.delete();
        tmpfile.mkdir();
        String command = "chmod 755 -R " + Path.toString();
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer Part=0;
        ArrayList<Boolean> modifyflag=new ArrayList<>();
        
        if (session.getAttribute("createlist")==null){
            JSONArray  createlist=new JSONArray();
            session.setAttribute("createlist",createlist);
        }
        JSONArray  createlist= (JSONArray) session.getAttribute("createlist");
        //JSONArray createlist = new JSONArray();
        JSONObject newbook=new JSONObject();
        newbook.put("bid",bid);
        
        newbook.put("chapter",chapter);
        newbook.put("status","处理文本");
        newbook.put("start",0);
        newbook.put("end",text.length());
        createlist.add(newbook);
        for (int i=0;i<createlist.size();i++)
        {
        	JSONObject tmpjs=createlist.getJSONObject(i);
        	if (tmpjs.getInt("bid")==bid&&tmpjs.getInt("chapter")==chapter)
        	{
        		newbook=tmpjs;
        		break;
        	}
        }
        String sug="";
        Double start=0.0;
        Integer positive=0;
        Integer negative=0;
        for (int i=0;i<text.length();i++)
        {
            List<String> sound=new ArrayList<>();
            List<Double> soundtime=new ArrayList<>();
            char c=text.charAt(i);
            tmp=tmp+c;
            start=0.0;
            if (tmp.length()>=100&&c=='。') {
                HashMap<String, Object> options = new HashMap<String, Object>();
                options.put("spd", spd);
                options.put("pit", pit);
                options.put("per", per);
                TtsResponse ttsResponse = aipSpeech.synthesis(tmp, "zh", 1, options);
                while (ttsResponse.getResult()!=null) {
                    ttsResponse = aipSpeech.synthesis(tmp, "zh", 1, options);
                }
                byte[] aa = ttsResponse.getData();
                getFile(aa, Path, Filename + "-" + Part + ".mp3");
                time = ffmpegx.processFLT(Path + Filename + "-" + Part + ".mp3");
                System.out.println(time);
                int num = 0;
                String tmpx = "";
                System.out.println(tmp.length());
                for (int j = 0; j < tmp.length(); j++) {
                    char cx = tmp.charAt(j);
                    tmpx = tmpx + cx;
                    if (cx == '。') {
                        if (classifier.classify(tmpx).equals("正面")) positive++;
                        else negative++;
                        sug=findsuitable(tmpx);
                        System.out.println(sug+tmpx);
                        if (sug!=null) {
                            sound.add(MusicPath  + sug + "/" + sug);
                            soundtime.add(start);
                        }
                        start = time / tmp.length() * j * 1000;
                        tmpx = "";
                    }
                }
                System.out.println("finish123");
                newbook.put("start",i);
                System.out.println("finishNEW");
                if (sound!=null&&!sound.isEmpty()){
                    System.out.println(sound);
                    createsoundeffect(sound, soundtime, Path + Filename + "-"+Part);
                    modifyflag.add(true);
                }
                else
                    modifyflag.add(false);
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
            start=0.0;
            String tmpx = "";
            for (int j = 0; j < tmp.length(); j++) {
                char cx = tmp.charAt(j);
                tmpx = tmpx + cx;
                Boolean flag = true;
                if (cx == '。') {
                    double max = 0;
                    sug=findsuitable(tmpx);
                    System.out.println(sug);
                    System.out.println(tmpx);
                    if (sug!=null) {
                        sound.add(MusicPath + sug + "/" + sug);
                        soundtime.add(start);
                        start = time / tmp.length() * j * 1000;
                    }
                }
            }
            if (sound!=null&&!sound.isEmpty()){
            createsoundeffect(sound, soundtime, Path + Filename + "-"+Part);
            modifyflag.add(true);
            }
            else
            modifyflag.add(false);
            Part = Part+1;
        newbook.put("status","合成mp3");
        newbook.put("start",0);
        newbook.put("end",Part);
        for (Integer i=0;i<Part;i++)
        {
            System.out.println(modifyflag.get(i));
            if (modifyflag.get(i)) {
                byte[] ret = getBytes(Path + Filename + "-" + i.toString() + "-f.mp3");
                getFile(ret, Path, Filename + "-f.mp3");
            }
            else {
                byte[] ret = getBytes(Path + Filename + "-" + i.toString() + ".mp3");
                getFile(ret, Path, Filename + "-f.mp3");
            }
            session.setAttribute("start",i);
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
        newbook.put("status","添加背景音乐");
        newbook.put("start",0);
        newbook.put("end",1);
        String finalname=Path+Filename;
        Integer flag=0;
        Double percent=((positive-negative))*1.0/(positive+negative);
        System.out.println(percent);
        if (Math.abs(percent)<=0.1) flag=3;
        else
        if (percent>0) flag=2;
        else flag=1;
        System.out.println(flag);
        String bgmpath=selectbgm(flag);
        ffmpegx.mofidyvoice(finalname+"-f.mp3",finalname+"-f'.mp3",-11-ffmpegx.processFLV(finalname+"-f.mp3"));
        ffmpegx.merge(finalname+"-f'.mp3",bgmpath,finalname+"-f-v.mp3");
        newbook.put("start",1);
        System.out.println("finish");
        newbook.put("status","制作完成");
        newbook.put("start",1);
        newbook.put("end",1);

        File ffFile =new File(finalname+"-f-v.mp3");
        ffFile.setReadable(true);
        ffFile.setExecutable(true);
        String command2 = "chmod 755 -R " + finalname+"-f-v.mp3";
        try {
            Runtime.getRuntime().exec(command2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        JSONObject feedback=new JSONObject();
        feedback.put("audiopath",finalname+"-f-v.mp3");
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
    
    public static void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// 
                dir.mkdirs();
                System.out.println("NOT FIND");
            }
            file = new File(filePath  + fileName);
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
    public static String selectbgm(Integer flag) throws Exception {
    	/*
        String BGMPath="/usr/music/";
        String Filename="BGM";
        */
    	String BGMPath =getProperties("BGMPath");
    	String Filename = getProperties("Filename");
        Filename=Filename+"/"+flag.toString();
        System.out.println(BGMPath+Filename);
        File file=new File(BGMPath+Filename);
        File [] files=file.listFiles();
        int size=files.length;
        Integer random=(int)(Math.random()*size+1);
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
    public static void asr(AipSpeech client, String Path, String musicfile, String MusicPath, String BgmPath,JSONObject newbook,String prev) throws Exception {
        // 
        //String path = "E:\\1-1\\1-1.pcm_69640-84499_A.pcm";
        //org.json.JSONObject asrRes = client.asr(path, "pcm", 16000, null);
        //System.out.println(asrRes);
        ArrayList<String> tmp=getAllFileName(Path+musicfile);
        ArrayList<pcmfile> pcmfiles=new ArrayList<>();
        Integer positive=0;
        Integer negative=0;
        for (int i=0;i<tmp.size();i++)
        {
            String str=tmp.get(i);
            Double starttime=0.0;
            Double endtime=0.0;
            Boolean flag=true;
            String filename=str.substring(0,str.length()-4);
            Integer delim1=str.indexOf("_");
            delim1=str.indexOf("_",delim1+1);
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
            System.out.println("asrRes(1): "+Path+musicfile+"/"+str);
            
            org.json.JSONObject asrRes = client.asr(Path+musicfile+"/"+str, "pcm", 16000, null);
            while (!asrRes.getString("err_msg").equals("success.")) {
                asrRes = client.asr(Path+musicfile+"/" + str, "pcm", 16000, null);
                System.out.println(asrRes.toString());
            }
            
            JSONObject asrResJs = new JSONObject();
            asrResJs = JSONObject.fromObject(asrRes.toString());
            JSONArray res=asrResJs.getJSONArray("result");
            
            System.out.println("result: "+res);
            List<Object> strres=JSONArray.toList(res);
            
            
            String parse=strres.get(0).toString();
            if (classifier.classify(parse).equals("正面")) positive++;
            else negative++;
            //System.out.println(classifier.classify(parse));
            String sug=findsuitable(parse);
            pcmfile tmpfile=new pcmfile();
            if (sug!=null) {
                File file = new File(filename+"mf.mp3");
                if (!file.exists()){
                    ffmpegx.mofidyvoice(MusicPath+"/"+sug+"/"+sug+".mp3",MusicPath+"/"+sug+"/"+sug+"mf.mp3",-30-ffmpegx.processFLV(MusicPath+"/"+sug+"/"+sug+".mp3"));
                }
                ffmpegx.addsoundpcm(Path + musicfile + "/" + filename+".pcm", MusicPath+"/"+sug+"/"+sug+"mf", Path + musicfile + "/" + filename+"-f.pcm");
                tmpfile.setName(filename+"-f");
                tmpfile.setStarttime(starttime);
            }
            else {
                tmpfile.setName(filename);
                tmpfile.setStarttime(starttime);
            }
            pcmfiles.add(tmpfile);
            Integer now=Integer.parseInt(String.valueOf(newbook.get("start")));
            now=now+1;
            newbook.put("start",now);
        }
        //System.out.println(positive.toString()+"000"+negative.toString());
        Collections.sort(pcmfiles,new pcmfileComparetor());
        newbook.put("status","正在合并pcm文件");
        newbook.put("start",0);
        newbook.put("end",pcmfiles.size());
        for (int i=0;i<pcmfiles.size();i++)
        {
            byte[] ret=getBytes(Path+musicfile+"/"+pcmfiles.get(i).getName()+".pcm");
            getFile(ret,Path,musicfile +"-f.pcm");
            newbook.put("start",i);
        }
        Double percent=((positive-negative))*1.0/(positive+negative);
        Integer flag=0;
        System.out.println(percent);
        if (Math.abs(percent)<=0.1) flag=3;
        else
        if (percent>0) flag=2;
        else flag=1;
        System.out.println(flag);
        String bgm=selectbgm(flag);
        System.out.println(bgm);
        newbook.put("status","添加背景音乐");
        newbook.put("start",0);
        newbook.put("end",1);
        ffmpegx.mergepcm(Path+musicfile+"-f.pcm",bgm,Path+prev+"-f-v.pcm");
        newbook.put("start",1);
        newbook.put("status","合成最终文件");
        newbook.put("start",0);
        newbook.put("end",1);
        ffmpegx.changetomp3(Path+prev+"-f-v.pcm");
        newbook.put("start",1);
        newbook.put("status","制作完成");
        newbook.put("start",1);
        newbook.put("end",1);
    }
    private static NaiveBayesModel trainOrLoadModel() throws IOException {
        NaiveBayesModel model = (NaiveBayesModel) IOUtil.readObjectFrom(MODEL_PATH);
        if (model != null) return model;
        File corpusFolder = new File(CORPUS_FOLDER);
        System.out.println(corpusFolder.isDirectory());
        if (!corpusFolder.exists() || !corpusFolder.isDirectory()) {
            System.err.println("没有文本分类语料，请阅读IClassifier.train(java.lang.String)中定义的语料格式与语料下载：" +
                    "https://github.com/hankcs/HanLP/wiki/%E6%96%87%E6%9C%AC%E5%88%86%E7%B1%BB%E4%B8%8E%E6%83%85%E6%84%9F%E5%88%86%E6%9E%90");
            System.exit(1);
        }
        IClassifier classifier = new NaiveBayesClassifier(); // 鍒涘缓鍒嗙被鍣紝鏇撮珮绾х殑鍔熻兘璇峰弬鑰僆Classifier鐨勬帴鍙ｅ畾涔�
        classifier.train(CORPUS_FOLDER);                     // 璁粌鍚庣殑妯″瀷鏀寔鎸佷箙鍖栵紝涓嬫灏变笉蹇呰缁冧簡
        model = (NaiveBayesModel) classifier.getModel();
        IOUtil.saveObjectTo(model, MODEL_PATH);
        return model;
    }
    private static String findsuitable(String tmpx){
        double max=0;
        String sug="";
        for (int k=0;k<dictc.size();k++)
            if (docVectorModel.similarity(tmpx,dictc.get(k))>max)
            {
                max=docVectorModel.similarity(tmpx,dictc.get(k));
                sug=dictc.get(k);
            }
        if (max>0.7) {
            System.out.println(tmpx + "has bgm" +sug+max);
            return sug;
        }
        return null;
    }
}
