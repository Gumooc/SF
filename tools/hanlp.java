package com.run.tools;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.suggest.Suggester;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class hanlp {
    private static List<String> dictc=dict.init();
    public static void main(String[] args) throws IOException {
        File file= new File("C:\\Users\\hasee\\Desktop\\server\\杨绛《我们仨》+.txt");
        String str = "";
        try {
            Charset charset=Charset.forName("GBK");
            FileReader fileReader = new FileReader(file,charset);
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
        DocVectorModel docVectorModel = new DocVectorModel(new WordVectorModel("D:\\HanLP\\data\\dictionary\\CoreNatureDictionary.ngram.txt"));
        for (int i=0;i<str.length();i++) {
            char c=str.charAt(i);
            tmp=tmp+c;
            int max=100000;
            String answer="";
            if (c=='。') {
                /*System.out.println(tmp);
                MySimHash hash1 = new MySimHash(tmp, 64);
                    for (int k=0;k<dictc.size();k++) {
                        MySimHash hash2 = new MySimHash(dictc.get(k), 64);
                        if (hash1.hammingDistance(hash2)<max)
                        {
                            max=hash1.hammingDistance(hash2);
                            answer=dictc.get(k);
                        }
                        //if (CoreSynonymDictionary.similarity(list1.get(j),dictc.get(k))>0)
                        //System.out.println(dictc.get(k) + hash1.hammingDistance(hash2));
                    }
                    //if (max<13)
                    System.out.println(tmp+answer+max);
                    tmp="";*/
                String sug=suggester.suggest(tmp,1).get(0);
                System.out.println(tmp+sug);
                System.out.println(docVectorModel.similarity(tmp,sug));
                tmp="";
            }
        }
        System.out.println(CoreSynonymDictionary.similarity("雷神","水"));
    }
    public static List<Term> segment(String text) {
        System.out.println(text);
        System.out.println(HanLP.segment(text));
        return HanLP.segment(text);
    }
}
