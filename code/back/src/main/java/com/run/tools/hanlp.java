package com.run.tools;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.Word2VecTrainer;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.suggest.Suggester;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class hanlp {
    private static List<String> dictc=dict.init();
    public static void main(String[] args) throws IOException {
        File file= new File("C:\\Users\\hasee\\Desktop\\server\\dd1.txt");
        String str = "";
        try {
            Charset charset=Charset.forName("GBK");
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file),charset);
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            str = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Suggester suggester = new Suggester();
        for (int i=0;i<dictc.size();i++)
            suggester.addSentence(dictc.get(i));
        String tmp="";
        //Word2VecTrainer trainerBuilder = new Word2VecTrainer();
        //WordVectorModel wordVectorModel = trainerBuilder.train("E:\\BaiduYunDownload\\hanlp-wiki-vec-zh\\hanlp-wiki-vec-zh.txt", "E:\\BaiduYunDownload\\hanlp-wiki-vec-zh\\msr-data.txt");
        //wordVectorModel.nearest("涓浗");
        WordVectorModel wordVectorModel=new WordVectorModel("E:\\download\\word2vec_c");
        DocVectorModel docVectorModel=new DocVectorModel(wordVectorModel);
        for (int i=0;i<str.length();i++) {
            char c=str.charAt(i);
            tmp=tmp+c;
            String answer="";
            if (c=='。') {
                String sug1="";
                String sug2="";
                System.out.println(tmp);
                    double max=0;
                    for (int k=0;k<dictc.size();k++)
                        if (docVectorModel.similarity(tmp,dictc.get(k))>max)
                        {
                            max=docVectorModel.similarity(tmp,dictc.get(k));
                            sug1=dictc.get(k);
                        }
                        //if (CoreSynonymDictionary.similarity(list1.get(j),dictc.get(k))>0)
                        //System.out.println(dictc.get(k) + hash1.hammingDistance(hash2));
                    //if (max<13)
                    //System.out.println(tmp+answer+max);
                sug2=suggester.suggest(tmp,1).get(0);
                //System.out.println(tmp+sug+wordVectorModel.similarity(tmp,sug));
                System.out.println(tmp+sug1+docVectorModel.similarity(tmp,sug1));
                System.out.println(tmp+sug2+docVectorModel.similarity(tmp,sug2));
                tmp="";
            }
        }
       // System.out.println(CoreSynonymDictionary.similarity("闆风","姘�"));
    }
    public static List<Term> segment(String text) {
        System.out.println(text);
        System.out.println(HanLP.segment(text));
        return HanLP.segment(text);
    }
}
