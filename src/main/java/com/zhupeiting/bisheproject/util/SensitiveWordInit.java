package com.zhupeiting.bisheproject.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
@Component
public class SensitiveWordInit {
	private String ENCODING = "UTF-8";
	@SuppressWarnings("rawtypes")
	public HashMap sensitiveWordMap;

	/**
	 * 初始化敏感词库
	 * */
	@SuppressWarnings("rawtypes")
	public Map initKeyWord(){
		try {
			//读取敏感词库
			Set<String> keyWordSet = readSensitiveWordFile();
			//将敏感词库加入到HashMap中
			addSensitiveWordToHashMap(keyWordSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sensitiveWordMap;
	}

	/**
	 * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：
	 * 中 = {
	 *      isEnd = 0
	 *      国 = {
	 *      	 isEnd = 1
	 *           人 = {isEnd = 0
	 *                民 = {isEnd = 1}
	 *                }
	 *           男  = {
	 *           	   isEnd = 0
	 *           		人 = {
	 *           			 isEnd = 1
	 *           			}
	 *           	}
	 *           }
	 *      }
	 *  五 = {
	 *      isEnd = 0
	 *      星 = {
	 *      	isEnd = 0
	 *      	红 = {
	 *              isEnd = 0
	 *              旗 = {
	 *                   isEnd = 1
	 *                  }
	 *              }
	 *      	}
	 *      }
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
		sensitiveWordMap = new HashMap(keyWordSet.size());
		String key = null;
		Map nowMap = null;
		Map<String, String> newWorMap = null;
		Iterator<String> iterator = keyWordSet.iterator();
		while(iterator.hasNext()){
			key = iterator.next();
			nowMap = sensitiveWordMap;
			for(int i = 0 ; i < key.length() ; i++){
				char keyChar = key.charAt(i);
				Object wordMap = nowMap.get(keyChar);
				if(wordMap != null){
					nowMap = (Map) wordMap;
				}
				else{
					newWorMap = new HashMap<String,String>();
					newWorMap.put("isEnd", "0");
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}

				if(i == key.length() - 1){
					nowMap.put("isEnd", "1");
				}
			}
		}
	}

	/**
	 * 读取敏感词库中的内容
	 */
	@SuppressWarnings("resource")
	private Set<String> readSensitiveWordFile() throws Exception{
		Set<String> set = null;
		File file = new File("src/main/resources/static/sensitiveWord/baidu_filter.txt");
		InputStreamReader read = new InputStreamReader(new FileInputStream(file),ENCODING);
		try {
			if(file.isFile() && file.exists()){
				set = new HashSet<String>();
				BufferedReader bufferedReader = new BufferedReader(read);
				String txt = null;
				while((txt = bufferedReader.readLine()) != null){
					set.add(txt);
				}
			}
			else{
				throw new Exception("敏感词库文件不存在");
			}
		} catch (Exception e) {
			throw e;
		}finally{
			read.close();
		}
		return set;
	}
}