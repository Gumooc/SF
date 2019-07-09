package com.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FFMPEG 的相关操作
 *
 * @author Administrator
 */
public class deom2{
        //Windows下 ffmpeg.exe的路径
        private static String ffmpegEXE = "D:\\ffmpeg\\bin\\ffmpeg.exe";
        //Linux与mac下  ffmpeg的路径
        //private static String ffmpegEXE = "/developer/ffmpeg-4.0/bin/ffmpeg";
        public static void main(String[] args) {
            String m1 = "D:\\testdemo\\origintext.mp3";
            String m2 = "D:\\testdemo\\output4.mp3";
            String m3 = "D:\\testdemo\\end.mp3";
                try {
                    merge(m1,m2,m3);
                } catch (Exception e) {
           e.printStackTrace();
            }
        }
        // ffmpeg -i 0.mp3  -i 00.mp3 -filter_complex amix=inputs=2:duration=first:dropout_transition=2 -f mp3 000.mp3
        public static void merge(String music1, String music2, String dest)
                throws Exception {
            List<String> command = new ArrayList<String>();
            Double time=processFLT(music1);
            command.add(ffmpegEXE);
            command.add("-i");
            command.add(music1);
            command.add("-i");
            command.add(music2);
            command.add("-filter_complex");
            command.add("[1:a]aloop=loop=-1:size=2e+09[out];[out][0:a]amix=inputs=2:duration=first:dropout_transition=2");
            command.add("-f");
            command.add("mp3");
            command.add("-ss");
            command.add("0");
            command.add("-t");
            command.add(time.toString());
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
            // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
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
        private static Double processFLV(String inputPath) throws Exception{
            List<String> command = new ArrayList<String>();
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
            // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
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
        private static Double processFLT(String inputPath) throws Exception{
            List<String> commands = new ArrayList<>();
            commands.add(ffmpegEXE);
            commands.add("-i");
            commands.add(inputPath);
            Double time=0.0;
            try {
                ProcessBuilder builder = new ProcessBuilder();
                builder.command(commands);
                Process p = builder.start();
                //从输入流中读取视频信息
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line);
                }
                br.close();
                //从视频信息中解析时长
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
            command.add(ffmpegEXE);
            command.add("-i");
            command.add(inputPath);
            command.add("-af");
            command.add("volume="+modification.toString());
            command.add(outputPath);
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
            // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
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
        private static Double getTimelen(String timelen) {
            Double min = 0.0;
            String strs[] = timelen.split(":");
            if (strs[0].compareTo("0") > 0) {
                // 秒
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
}