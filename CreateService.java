package com.run.service;

import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public interface CreateService {
    public JSONObject createoriginbook(MultipartFile text);
    public JSONObject createsoundeffect(MultipartFile text);
    public JSONObject emotionanalyse(MultipartFile text);
    public JSONObject wordanalyse(MultipartFile text);
    public void getFile(byte[] bfile, String filePath, String fileName);
}
