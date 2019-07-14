package com.run.tools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FFMPEG 锟斤拷锟斤拷夭锟斤拷锟�
 *
 * @author Administrator
 */
public class deom2{
        //Windows锟斤拷 ffmpeg.exe锟斤拷路锟斤拷
        private static String ffmpegEXE = "/usr/local/ffmpeg/bin/ffmpeg";
        //Linux锟斤拷mac锟斤拷  ffmpeg锟斤拷路锟斤拷
        //private static String ffmpegEXE = "/developer/ffmpeg-4.0/bin/ffmpeg";
        public static void main(String[] args) {
            //String m1 = "E:\\123.mp3";
            //String m2 = "D:\\index\\handle\\澶ц嚜鐒禱\澶ц嚜鐒秏f.mp3";
            //String m3 = "E:\\FINAL.mp3";
                try {
                    //addsoundeffect(m1,m2,m3,10000.0,10000.0);
                   // System.out.println(processFLV(m2));
                    //String finalPath= "D:\\index\\handle\\澶ц嚜鐒禱\澶ц嚜鐒�";
                    //System.out.println(-30.0-processFLV(finalPath+".mp3"));
                    //ystem.out.println(processFLV("E:\\123-1.mp3"));
                    /*List<String> list=new ArrayList<>();
                    String s1="D:\\index\\handle\\澶ц嚜鐒禱\澶ц嚜鐒秏f.mp3";
                    String s2="D:\\index\\handle\\澶ф氮鏉ヨ\\澶ф氮鏉ヨmf.mp3";
                    list.add(s1);
                    list.add(s2);
                    List<Double> time=new ArrayList<>();
                    time.add(10.0*1000);
                    time.add(20.0*1000);
                    addsoundeffects("E:\\123-0.mp3",list,"E:\\FINAL.mp3",time);*/
                    //mofidyvoice("E:\\123-f.mp3","E:\\123-f''.mp3",-11-processFLV("E:\\123-f.mp3"));
                    //merge("E:\\123-f''.mp3","E:\\BGM\\2\\鍑虹窘鑹桨 - Silent express.mp3","E:\\123-f.mp3"+"-f-v.mp3");
                    //mofidyvoice("E:\\123-2.mp3","E:\\123-22.mp3",-16-processFLV("E:\\123-2.mp3"));
                    //mofidyvoice("D:\\index\\handle\\澶ф氮鏉ヨ\\澶ф氮鏉ヨmf.mp3","D:\\index\\handle\\澶ф氮鏉ヨ\\澶ф氮鏉ヨmf.mp3",-30.0-ffmpegx.processFLV(finalPath));
                    //System.out.println(processFLV("D:\\index\\handle\\澶ф氮鏉ヨ\\澶ф氮鏉ヨmf.mp3"));
                    //utmusic("D:\\index\\handle\\澶忔棩铦夐福\\澶忔棩铦夐福mf.mp3","D:\\index\\handle\\澶忔棩铦夐福\\澶忔棩铦夐福10mf.mp3",0.0,10.0);
                } catch (Exception e) {
           e.printStackTrace();
            }
        }
        // ffmpeg -i 0.mp3  -i 00.mp3 -filter_complex amix=inputs=2:duration=first:dropout_transition=2 -f mp3 000.mp3
        public static void merge(String music1, String music2, String dest)
                throws Exception {
            List<String> command = new ArrayList<String>();
            Double time=processFLT(music1);
            String tmpx="";
            if (music2.indexOf("mf.mp3")==-1)
            {
                tmpx=music2.replace(".mp3","mf.mp3");
                File file=new File(tmpx);
                if (!file.exists())
                {
                    mofidyvoice(music2,tmpx,-32-processFLV(music2));
                }
                music2=tmpx;
            }
            //String str = new String(music2.getBytes("gbk"),"utf-8");
            String str = music2;
            command.add(ffmpegEXE);
            command.add("-i");
            command.add(music1);
            command.add("-i");
            command.add(str);
            command.add("-filter_complex");
            command.add("[1:a]aloop=loop=-1:size=2e+09[out];[out][0:a]amix=inputs=2:duration=first:dropout_transition=2");
            command.add("-f");
            command.add("mp3");
            command.add("-ss");
            command.add("0");
            command.add("-t");
            command.add(time.toString());
            command.add("-y");
            command.add(dest);
            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = null;
            builder.redirectErrorStream(true);
            try {
                process = builder.start();
                StringBuffer sbf = new StringBuffer();
                String line = null;
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((line = br.readLine()) != null) {
                    sbf.append(line);
                    sbf.append(" ");
                    System.out.println(line);
                }
                String resultInfo = sbf.toString();
                System.out.println(resultInfo);
            }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 使锟斤拷锟斤拷锟街凤拷式锟斤拷锟斤拷瞬锟斤拷锟斤拷锟斤拷锟斤拷锟紺PU锟斤拷锟节达拷锟较低筹拷锟皆达拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟揭拷锟斤拷锟斤拷锟斤拷写锟斤拷锟�
            InputStream errorStream = process.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = br.readLine()) != null) {
            }
            if (br != null) {
                br.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (errorStream != null) {
                errorStream.close();
            }
        }
        public static Double processFLV(String inputPath) throws Exception{
            List<String> command = new ArrayList<String>();
            //inputPath=new String(inputPath.getBytes("GBK"),"UTF-8");
            command.add(ffmpegEXE);
            command.add("-i");
            command.add(inputPath);
            command.add("-filter_complex");
            command.add("volumedetect");
            command.add("-c:v");
            command.add("copy");
            command.add("-f");
            command.add("null");
            command.add("/dev/null");
            Double voice=0.0;
            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = null;
            builder.redirectErrorStream(true);
            try {
                process = builder.start();
                StringBuffer sbf = new StringBuffer();
                String line = null;
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((line = br.readLine()) != null) {
                    sbf.append(line);
                    sbf.append(" ");
                    if (line.indexOf("mean_volume:")!=-1)
                    {
                        int start=line.indexOf("mean_volume:");
                        int end=line.indexOf("dB");
                        String tmp=line.substring(start+13,end-1);
                        voice=Double.valueOf(tmp.toString());
                    }
                }
            }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 使锟斤拷锟斤拷锟街凤拷式锟斤拷锟斤拷瞬锟斤拷锟斤拷锟斤拷锟斤拷锟紺PU锟斤拷锟节达拷锟较低筹拷锟皆达拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟揭拷锟斤拷锟斤拷锟斤拷写锟斤拷锟�
            InputStream errorStream = process.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = br.readLine()) != null) {
            }
            if (br != null) {
                br.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (errorStream != null) {
                errorStream.close();
            }
            return voice;
        }
        public static Double processFLT(String inputPath) throws Exception{
            List<String> commands = new ArrayList<>();
            //inputPath=new String(inputPath.getBytes("GBK"),"UTF-8");
            commands.add(ffmpegEXE);
            commands.add("-i");
            commands.add(inputPath);
            Double time=0.0;
            try {
                ProcessBuilder builder = new ProcessBuilder();
                builder.command(commands);
                Process p = builder.start();
                //锟斤拷锟斤拷锟斤拷锟斤拷锟叫讹拷取锟斤拷频锟斤拷息
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line);
                }
                br.close();
                //锟斤拷锟斤拷频锟斤拷息锟叫斤拷锟斤拷时锟斤拷
                String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
                Pattern pattern = Pattern.compile(regexDuration);
                Matcher m = pattern.matcher(stringBuilder.toString());
                if (m.find()) {
                    time = getTimelen(m.group(1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return time;
        }

        public static void mofidyvoice(String inputPath, String outputPath, Double modification)
                throws Exception {
            List<String> command = new ArrayList<String>();
            //inputPath=new String(inputPath.getBytes("GBK"),"UTF-8");
            //outputPath=new String(outputPath.getBytes("GBK"),"UTF-8");
            command.add(ffmpegEXE);
            command.add("-i");
            command.add(inputPath);
            command.add("-af");
            command.add("volume="+modification.toString()+"dB");
            command.add(outputPath);
            command.add("-y");
            System.out.println(modification.toString());
            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = null;
            builder.redirectErrorStream(true);
            try {
                process = builder.start();
                StringBuffer sbf = new StringBuffer();
                String line = null;
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((line = br.readLine()) != null) {
                    sbf.append(line);
                    sbf.append(" ");
                    System.out.println(line);
                }
                String resultInfo = sbf.toString();
                System.out.println(resultInfo);
            }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 使锟斤拷锟斤拷锟街凤拷式锟斤拷锟斤拷瞬锟斤拷锟斤拷锟斤拷锟斤拷锟紺PU锟斤拷锟节达拷锟较低筹拷锟皆达拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟揭拷锟斤拷锟斤拷锟斤拷写锟斤拷锟�
            InputStream errorStream = process.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = br.readLine()) != null) {
            }
            if (br != null) {
                br.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (errorStream != null) {
                errorStream.close();
            }
        }
        public static Double getTimelen(String timelen) {
            Double min = 0.0;
            String strs[] = timelen.split(":");
            if (strs[0].compareTo("0") > 0) {
                // 锟斤拷
                min += Double.valueOf(strs[0]) * 60 * 60;
            }
            if (strs[1].compareTo("0") > 0) {
                min += Double.valueOf(strs[1]) * 60;
            }
            if (strs[2].compareTo("0") > 0) {
                min += Double.valueOf(strs[2]);
            }
            return min;
        }
    /*public static void addsoundeffect(String music1, String music2,String dest,Double lasttime,Double starttime)
            throws Exception {
        List<String> command = new ArrayList<String>();
        Double time=processFLT(music1);
        command.add("-i");
        command.add(tmpx);
        command.add(ffmpegEXE);
        command.add("-i");
        command.add(music1);
        command.add("-i");
        command.add(music2);
        command.add("-filter_complex");
        String delay="\"[1]adelay="+starttime.toString()+"|"+starttime.toString()+"[del1];"+"[0][del1]"+"amix=inputs=2:duration=first:dropout_transition=2\"";
        command.add(delay);
        command.add("-f");
        command.add("mp3");
        command.add("-ss");
        command.add("0");
        command.add("-t");
        command.add(time.toString());
        command.add("-y");
        command.add(dest);
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        builder.redirectErrorStream(true);
        try {
            process = builder.start();
            StringBuffer sbf = new StringBuffer();
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = br.readLine()) != null) {
                sbf.append(line);
                sbf.append(" ");
                System.out.println(line);
            }
            String resultInfo = sbf.toString();
            System.out.println(resultInfo);
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使锟斤拷锟斤拷锟街凤拷式锟斤拷锟斤拷瞬锟斤拷锟斤拷锟斤拷锟斤拷锟紺PU锟斤拷锟节达拷锟较低筹拷锟皆达拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟揭拷锟斤拷锟斤拷锟斤拷写锟斤拷锟�
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }*/
    public static void addsoundeffects(String music1, List<String> musicset,String dest,List<Double> lasttime)
            throws Exception {
        List<String> command = new ArrayList<String>();
        Double time=processFLT(music1);
        command.add(ffmpegEXE);
        command.add("-i");
        command.add(music1);
        for (int i=0;i<musicset.size();i++) {
            String tmp=musicset.get(i);
            Double last=processFLT(tmp);
            String tmpx=tmp;
            if  (last>10){
                tmpx=tmp.replace(".mp3","");
                tmpx=tmpx+"10.mp3";
                System.out.println(tmpx);
                File file =new File(tmpx);
                if (!file.exists())
                    cutmusic(tmp,tmpx,0.0,10.0);
            }
            command.add("-i");
            //tmpx=new String(tmpx.getBytes("GBK"),"UTF-8");
            command.add(tmpx);
        }
        command.add("-filter_complex");
        String delay="";
        for (Integer i=0;i<lasttime.size();i++) {
            Integer tmp=i+1;
            delay += "[" + String.valueOf(tmp)+ "]adelay=" +lasttime.get(i).toString()+"|"+lasttime.get(i).toString()+"[del"+String.valueOf(tmp)+"];";
        }
        delay+="[0]";
        for (Integer i=0;i<lasttime.size();i++) {
            Integer tmp=i+1;
            delay += "[del"+String.valueOf(tmp)+"]";
        }
        Integer tmp=musicset.size()+1;
        delay+="amix=inputs="+tmp.toString()+":duration=first:dropout_transition=2";
        command.add(delay);
        command.add("-f");
        command.add("mp3");
        command.add("-ss");
        command.add("0");
        command.add("-t");
        command.add(time.toString());
        command.add("-y");
        command.add(dest);
        System.out.println(command.toString());
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        builder.redirectErrorStream(true);
        try {
            process = builder.start();
            StringBuffer sbf = new StringBuffer();
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = br.readLine()) != null) {
                sbf.append(line);
                sbf.append(" ");
                System.out.println(line);
            }
            String resultInfo = sbf.toString();
            System.out.println(resultInfo);
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使锟斤拷锟斤拷锟街凤拷式锟斤拷锟斤拷瞬锟斤拷锟斤拷锟斤拷锟斤拷锟紺PU锟斤拷锟节达拷锟较低筹拷锟皆达拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟揭拷锟斤拷锟斤拷锟斤拷写锟斤拷锟�
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }
    public static void cutmusic(String music1, String dest,Double starttime,Double endtime)
            throws Exception {
        List<String> command = new ArrayList<String>();
        command.add(ffmpegEXE);
        command.add("-i");
        command.add(music1);
        command.add("-vn");
        command.add("-acodec");
        command.add("copy");
        command.add("-ss");
        command.add(starttime.toString());
        command.add("-t");
        command.add(endtime.toString());
        command.add(dest);
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        builder.redirectErrorStream(true);
        try {
            process = builder.start();
            StringBuffer sbf = new StringBuffer();
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = br.readLine()) != null) {
                sbf.append(line);
                sbf.append(" ");
                System.out.println(line);
            }
            String resultInfo = sbf.toString();
            System.out.println(resultInfo);
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使锟斤拷锟斤拷锟街凤拷式锟斤拷锟斤拷瞬锟斤拷锟斤拷锟斤拷锟斤拷锟紺PU锟斤拷锟节达拷锟较低筹拷锟皆达拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟揭拷锟斤拷锟斤拷锟斤拷写锟斤拷锟�
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }
    public static String selectbgm(String txt) throws Exception {
        String Path="E:\\";
        String Filename="BGM";
        Integer random=(int)(Math.random()*3+1);
        Filename=Filename+"\\"+random.toString();
        System.out.println(Path+Filename);
        File file=new File(Path+Filename);
        File [] files=file.listFiles();
        int size=files.length;
        random=(int)(Math.random()*size+1);
        return files[random].getPath();
    }
}