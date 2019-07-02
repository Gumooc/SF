package com.run.control;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.aip.nlp.AipNlp;
import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizerListener;

import net.sf.json.JSONObject;

@Controller
public class Creatbook {
	private void setRHeader(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials","true");
	}
	private SynthesizerListener mSynListener = new SynthesizerListener(){
		//会话结束回调接口，没有错误时，error为null
		public void onCompleted(SpeechError error) {}
		//缓冲进度回调
		//percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
		public void onBufferProgress(int percent, int beginPos, int endPos, String info) {}
		//开始播放
		public void onSpeakBegin() {}
		//暂停播放
		public void onSpeakPaused() {}
		//播放进度回调
		//percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
		public void onSpeakProgress(int percent, int beginPos, int endPos) {}
		//恢复播放回调接口
		public void onSpeakResumed() {}
		@Override
		public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4, Object arg5) {
			// TODO Auto-generated method stub
			
		}
	};
	
	@ResponseBody
	@RequestMapping("/xunfei")
	public void xunfei(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response){
		setRHeader(request, response);
		SpeechUtility.createUtility( SpeechConstant.APPID +"=5d19c8b1 ");
		SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer( );
		//2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
		mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
		mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
		//设置合成音频保存位置（可自定义保存位置），保存在“./tts_test.pcm”
		//如果不需要保存合成音频，注释该行代码
		mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./tts_test.pcm");
		//3.开始合成
		mTts.startSpeaking("语音合成测试程序", mSynListener);
		System.out.println("asd");
	}
	
	@ResponseBody
	@RequestMapping("/emotion")
	public JSONObject handleemotion(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response){
		setRHeader(request, response);
		System.out.println(liString);
		AipNlp client = new AipNlp("16683764","Ec37oetfvG54IuQeciRjDwWE", "dtAnXT5w9Hy99NElxrVzcBnmn554ZndL");
		String text = liString;

		HashMap<String, Object> options = new HashMap<String, Object>();
		    
		org.json.JSONObject res = client.sentimentClassify(text, options);
		System.out.println(res.toString(2));
		return JSONObject.fromObject(res.toString(2));
	}
	
	
	@ResponseBody
	@RequestMapping("/createbook")
	public JSONObject handleRequestall(@RequestParam(value="id") int id, @RequestParam(value="file", required = false) MultipartFile txt,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		setRHeader(request, response);
		AipSpeech aipSpeech = new AipSpeech("16683764","Ec37oetfvG54IuQeciRjDwWE", "dtAnXT5w9Hy99NElxrVzcBnmn554ZndL");
		byte bb[] = null;
		try {
			bb = txt.getBytes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String text = "";
		System.out.println("start");
		text = new String(bb);
		System.out.println(text);
		HashMap<String, Object> options = new HashMap<String, Object>();
	    options.put("spd", "3");
	    options.put("pit", "2");
	    options.put("per", "3");
		TtsResponse ttsResponse = aipSpeech.synthesis(text, "zh", 1, options);
		byte[] aa = ttsResponse.getData();
		getFile(aa, "E:/", "1.mp3");
		System.out.println(JSONObject.fromObject(ttsResponse));
		return new JSONObject();
	}
	
	
	public static void getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + "\\" + fileName);
			fos = new FileOutputStream(file);
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
}
