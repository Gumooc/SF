package com.run.tools;

import com.baidu.aip.speech.AipSpeech;
import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import net.sf.json.JSONObject;
import org.json.JSONArray;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private static final String MODEL_PATH = "D:\\HanLP\\data\\model\\sen\\classification-sen-model.ser";
    public static final String CORPUS_FOLDER =  "D:\\HanLP\\data\\dictionary\\sen\\ChnSentiCorp情感分析酒店评论";
    public static void asr(AipSpeech client) throws Exception {
        // 对本地语音文件进行识别
        //String path = "E:\\1-1\\1-1.pcm_69640-84499_A.pcm";
        //org.json.JSONObject asrRes = client.asr(path, "pcm", 16000, null);
        //System.out.println(asrRes);
        ArrayList<String> tmp=getAllFileName("E:\\1-1");
        ArrayList<pcmfile> pcmfiles=new ArrayList<>();
        String Path="E:\\";
        String MusicPath="D:\\index\\handle";
        String BgmPath="E:\\BGM";
        Integer positive=0;
        Integer negative=0;
        IClassifier classifier = new NaiveBayesClassifier(trainOrLoadModel());
        for (int i=0;i<tmp.size();i++)
        {
            String str=tmp.get(i);
            Double starttime=0.0;
            Double endtime=0.0;
            Boolean flag=true;
            String filename=str.substring(0,str.length()-4);
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
            System.out.println(classifier.classify(parse));
            System.out.println(classifier.classify(parse).equals("正面"));
            if (classifier.classify(parse).equals("正面")) positive++;
            else negative++;
            //System.out.println(classifier.classify(parse));
                for (int k = 0; k < dictc.size(); k++) {
                    if (docVectorModel.similarity(parse, dictc.get(k)) > max) {
                        max = docVectorModel.similarity(parse, dictc.get(k));
                        sug = dictc.get(k);
                    }
                }

            if (flag==true) {
                System.out.println(res + "has bgm" +sug+max);
            }
            pcmfile tmpfile=new pcmfile();
            if (max>0.7) {
                    File file = new File(filename+"mf.mp3");
                    if (!file.exists()){
                        ffmpegx.mofidyvoice(MusicPath+"\\"+sug+"\\"+sug+".mp3",MusicPath+"\\"+sug+"\\"+sug+"mf.mp3",-30-ffmpegx.processFLV(MusicPath+"\\"+sug+"\\"+sug+".mp3"));
                    }
                ffmpegx.addsoundpcm(Path + "1-1" + "\\" + filename+".pcm", MusicPath+"\\"+sug+"\\"+sug+"mf", Path + "1-1" + "\\" + filename+"-f.pcm");
                tmpfile.setName(filename+"-f");
                tmpfile.setStarttime(starttime);
            }
            else {
                tmpfile.setName(filename);
                tmpfile.setStarttime(starttime);
            }
            pcmfiles.add(tmpfile);
        }
        System.out.println(positive.toString()+"000"+negative.toString());
        Collections.sort(pcmfiles,new pcmfileComparetor());
        for (int i=0;i<pcmfiles.size();i++)
        {
            byte[] ret=getBytes(Path+"1-1\\"+pcmfiles.get(i).getName().toString()+".pcm");
            getFile(ret,Path,"1-1"+"-f.pcm");
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
        ffmpegx.mergepcm(Path+"1-1"+"-f.pcm",bgm,Path+"1-1"+"-f-v.pcm");
        // 对语音二进制数据进行识别
        //byte[] data = Util.readFileByBytes(path);     //readFileByBytes仅为获取二进制数据示例
        //org.json.JSONObject asrRes2 = client.asr(data, "pcm", 16000, null);
        //System.out.println(asrRes2);

    }
    public static void main(String[] args) throws Exception {
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
    /*public static Boolean parse_file(String str,Double st,Double en,Boolean fl)
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
    }*/
    public static void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// �ж��ļ�Ŀ¼�Ƿ����
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
        IClassifier classifier = new NaiveBayesClassifier(); // 创建分类器，更高级的功能请参考IClassifier的接口定义
        classifier.train(CORPUS_FOLDER);                     // 训练后的模型支持持久化，下次就不必训练了
        model = (NaiveBayesModel) classifier.getModel();
        IOUtil.saveObjectTo(model, MODEL_PATH);
        return model;
    }
    public static String selectbgm(Integer label) throws Exception {
        String Path="E:\\";
        String Filename="BGM";
        Filename=Filename+"\\"+label.toString();
        System.out.println(Path+Filename);
        File file=new File(Path+Filename);
        File [] files=file.listFiles();
        int size=files.length;
        int random=(int)(Math.random()*size+1);
        System.out.println(files[random].getPath());
        return files[random].getPath();
    }
}
