package com.baidu.speech.restapi.jniTest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileCutUtil {

	private static String vaddemoEXE = "/usr/baiduspeech/speech-vad-demo-master/vad-demo";
    /*static {
		System.out.println(System.getProperty("java.library.path"));
		System.loadLibrary("libgcc_s_seh-1");
    	System.loadLibrary("libstdc++-6");
    	System.loadLibrary("libwinpthread-1");
    	System.loadLibrary("libFileCutUtil");
    }

    private int mObject;
    public FileCutUtil(char[] filename,char[] output_filename_prefix,char[] output_dir) {  
//    	setFile(filename, output_filename_prefix);  
    }  
      
    public native int setFile(char[] filename,char[] output_filename_prefix,char[] output_dir);  
    protected native void finalize();  */
      
    public static void main(String[] args) throws IOException {
    	
    	/*
    	String file = "1-1.pcm";
    	String realpath = "E:\\"+file.substring(0, file.length()-4);
        File dir = new File(realpath);          
        if(!dir.exists()){  
            dir.mkdirs();  
        } 
    	
    	String url = "E:\\1-1.pcm";
    	String output = realpath;
    	
    	char[] filename = url.toCharArray();
    	char[] output_filename_prefix = file.toCharArray();
    	char[] output_dir = output.toCharArray();
    	
    	FileCutUtil fileCut = new FileCutUtil(filename, output_filename_prefix,output_dir);  
    	int res = fileCut.setFile(filename, output_filename_prefix,output_dir);

    	System.out.println("执行结束，res:"+res);*/
    	cutpcmfile("E:\\","2");
    }
	public static void cutpcmfile(String Path,String Filename) throws IOException {

		//
		System.out.println(Path+Filename);
		String file = Filename+".pcm";
		String realpath = Path+Filename.substring(0, file.length()-4);
		System.out.println(realpath);
		File dir = new File(realpath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String url = Path+Filename+".pcm";
		String output = realpath;
		System.out.println(url);
		//char[] filename = url.toCharArray();
		//char[] output_filename_prefix = file.toCharArray();
		//char[] output_dir = output.toCharArray();
		List<String> command = new ArrayList<String>();
		command.add(vaddemoEXE);
		command.add(url);
		command.add(file);
		command.add(output);
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
		while (true) {
			try {
				if (!((line = br.readLine()) != null)) break;
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		//FileCutUtil fileCut = new FileCutUtil(filename, output_filename_prefix,output_dir);
		//int res = fileCut.setFile(filename, output_filename_prefix,output_dir);
		//System.out.println("执行结束，res:"+res);
	}
}
