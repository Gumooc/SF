package com.run;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class deom2 {
    public static void main(String[] args) {

            //合并文件
            //头一个file为amr文件
            try {
                log.info("Begin to merge video file " + videoFile.getAbsolutePath() + " into " + armFile.getAbsolutePath());
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                if(classLoader == null) {
                    classLoader = ClassLoader.getSystemClassLoader();
                }
                //注意FFmpeg路径
                String command =new File(classLoader.getResource("").toURI()).getParentFile() + "\\ffmpeg -i " + armFile.getAbsolutePath() + " -r 15 -i "
                        + videoFile.getAbsolutePath() + " -vf \"transpose=1\" -c:a copy -c:v libx264 " + videoFile.getParentFile() + "\\_" + videoFile.getName();
//			System.out.println(command);
                log.info("The command of ffmpeg is " + command);
                Process process =Runtime.getRuntime().exec(command);

                final InputStream in = process.getInputStream();
                final InputStream error = process.getErrorStream();

                new Thread(){
                    public void run() {
                        BufferedReader br = new BufferedReader(new InputStreamReader(error));

                        try {
                            while(br.readLine() != null) {
                                continue;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                br.close();
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                }.start();
                process.waitFor();
                process.destroy();
                log.info("Success to execute " + command);
                log.info("Success to merge " + videoFile.getAbsolutePath() + " into " + armFile.getAbsolutePath() + ", and success to create " +  videoFile.getParentFile() + "/_" + videoFile.getName());
            } catch (Exception e) {
                log.error("Exception occurs when merging video file", e);
                return false;
            }
            return true;

    }
}
