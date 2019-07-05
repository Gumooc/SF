package com.run.control;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.run.service.Impl.CreateServiceImpl;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	CreateServiceImpl createService;
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
	public JSONObject handleemotion(@RequestParam(value="id") int id, @RequestParam(value="file", required = false) MultipartFile txt,HttpServletRequest request, HttpServletResponse response){
		setRHeader(request, response);
		System.out.println("prestart");
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
		String tmp="";
		AipNlp client = new AipNlp("16683764","Ec37oetfvG54IuQeciRjDwWE", "dtAnXT5w9Hy99NElxrVzcBnmn554ZndL");
		HashMap<String, Object> options = new HashMap<String, Object>();
		for (int i=0;i<text.length();i++)
		{
			char c=text.charAt(i);
			tmp=tmp+c;
			if (tmp.length()>=1000)
			{
				System.out.println(tmp);
				org.json.JSONObject res = client.sentimentClassify(tmp, options);
				System.out.println(res.toString(2));
				tmp="";
			}
		}
		org.json.JSONObject res = client.sentimentClassify(tmp, options);
		System.out.println(res.toString(2));
		//System.out.println(res.toString(2));
		return JSONObject.fromObject(res.toString(2));
	}
	@ResponseBody
	@RequestMapping("/createbook")
	public JSONObject handleRequestall(@RequestParam(value="id") int id, @RequestParam(value="file", required = false) MultipartFile txt,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		setRHeader(request, response);
		System.out.println("OK?");
		JSONObject res=createService.createoriginbook(txt);
		return res;
		/*AipSpeech aipSpeech = new AipSpeech("16683764","Ec37oetfvG54IuQeciRjDwWE", "dtAnXT5w9Hy99NElxrVzcBnmn554ZndL");
		AipNlp client = new AipNlp("16683764","Ec37oetfvG54IuQeciRjDwWE", "dtAnXT5w9Hy99NElxrVzcBnmn554ZndL");
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
		String tmp="";
		for (int i=0;i<text.length();i++)
		{
			char c=text.charAt(i);
			tmp=tmp+c;
			if (tmp.length()>=1000)
			{
				System.out.println(tmp);
				HashMap<String, Object> options = new HashMap<String, Object>();
				org.json.JSONObject res = client.sentimentClassify(tmp, options);
				System.out.println(res.toString());
				double confidence=res.getJSONArray("items").getJSONObject(0).getDouble("confidence");
				double positive=res.getJSONArray("items").getJSONObject(0).getDouble("positive_prob");
				double negative=res.getJSONArray("items").getJSONObject(0).getDouble("negative_prob");
				options.put("spd", 3+1.5*(confidence*positive-(1-confidence)*negative));
				options.put("pit", 3+1.5*(confidence*positive-(1-confidence)*negative));
				options.put("per", "3");
				TtsResponse ttsResponse = aipSpeech.synthesis(tmp, "zh", 1, options);
				System.out.println(ttsResponse.toString());
				byte[] aa = ttsResponse.getData();
				getFile(aa, "E:/", "123.mp3");
				System.out.println(JSONObject.fromObject(ttsResponse));
				tmp="";
			}
		}
		HashMap<String, Object> options = new HashMap<String, Object>();
		org.json.JSONObject res = client.sentimentClassify(tmp, options);
		System.out.println(res.toString());
		double confidence=res.getJSONArray("items").getJSONObject(0).getDouble("confidence");
		double positive=res.getJSONArray("items").getJSONObject(0).getDouble("positive_prob");
		double negative=res.getJSONArray("items").getJSONObject(0).getDouble("negative_prob");
		System.out.println(confidence);
		System.out.println(positive);
		System.out.println(negative);
		options.put("spd", 3+1.5*(confidence*positive-(1-confidence)*negative));
		options.put("pit", 3+1.5*(confidence*positive-(1-confidence)*negative));
		options.put("per", "3");
		TtsResponse ttsResponse = aipSpeech.synthesis(tmp, "zh", 1, options);
		System.out.println(ttsResponse.toString());
		byte[] aa = ttsResponse.getData();
		getFile(aa, "E:/", "123.mp3");
		System.out.println(JSONObject.fromObject(ttsResponse));
		return new JSONObject();*/
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
	public JSONObject getSoundEffect(MultipartFile txt) throws UnsupportedEncodingException{
		AipSpeech aipSpeech = new AipSpeech("16683764","Ec37oetfvG54IuQeciRjDwWE", "dtAnXT5w9Hy99NElxrVzcBnmn554ZndL");
		AipNlp client = new AipNlp("16683764","Ec37oetfvG54IuQeciRjDwWE", "dtAnXT5w9Hy99NElxrVzcBnmn554ZndL");
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
		String tmp="";
		for (int i=0;i<text.length();i++)
		{
			char c=text.charAt(i);
			tmp=tmp+c;
			if (tmp.length()>=1000)
			{
				System.out.println(tmp);
				HashMap<String, Object> options = new HashMap<String, Object>();
				org.json.JSONObject res = client.sentimentClassify(tmp, options);
				System.out.println(res.toString());
				double confidence=res.getJSONArray("items").getJSONObject(0).getDouble("confidence");
				double positive=res.getJSONArray("items").getJSONObject(0).getDouble("positive_prob");
				double negative=res.getJSONArray("items").getJSONObject(0).getDouble("negative_prob");
				options.put("spd", 3+1.5*(confidence*positive-(1-confidence)*negative));
				options.put("pit", 3+1.5*(confidence*positive-(1-confidence)*negative));
				options.put("per", "3");
				TtsResponse ttsResponse = aipSpeech.synthesis(tmp, "zh", 1, options);
				System.out.println(ttsResponse.toString());
				byte[] aa = ttsResponse.getData();
				getFile(aa, "E:/", "123.mp3");
				System.out.println(JSONObject.fromObject(ttsResponse));
				tmp="";
			}
		}
		HashMap<String, Object> options = new HashMap<String, Object>();
		org.json.JSONObject res = client.sentimentClassify(tmp, options);
		System.out.println(res.toString());
		double confidence=res.getJSONArray("items").getJSONObject(0).getDouble("confidence");
		double positive=res.getJSONArray("items").getJSONObject(0).getDouble("positive_prob");
		double negative=res.getJSONArray("items").getJSONObject(0).getDouble("negative_prob");
		System.out.println(confidence);
		System.out.println(positive);
		System.out.println(negative);
		options.put("spd", 3+1.5*(confidence*positive-(1-confidence)*negative));
		options.put("pit", 3+1.5*(confidence*positive-(1-confidence)*negative));
		options.put("per", "3");
		TtsResponse ttsResponse = aipSpeech.synthesis(tmp, "zh", 1, options);
		System.out.println(ttsResponse.toString());
		byte[] aa = ttsResponse.getData();
		getFile(aa, "E:/", "123.mp3");
		System.out.println(JSONObject.fromObject(ttsResponse));
		return new JSONObject();
	}
}
