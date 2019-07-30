package com.run.tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class demo {
    public static void main(String[] args) {
        String path = "E:\\ontology-master\\ontology.json";
        System.out.println(path);
        String s = ReadUtils.readJsonFile(path);
        JSONArray res=JSONArray.fromObject(s);
        //System.out.println(res);
        int cnt=0;
        for (int i=0;i<res.size();i++){
            JSONObject tmp=res.getJSONObject(i);
            JSONArray eg=tmp.getJSONArray("positive_examples");
            String label=tmp.getString("name");
            if (eg.size()>0){
                for (int j=0;j<eg.size();j++){
                    cnt=cnt+1;
                    String list=eg.getString(j);
                    System.out.println(label+" "+list.toString());
                }
            }
        }
        System.out.println(cnt);
    }
}