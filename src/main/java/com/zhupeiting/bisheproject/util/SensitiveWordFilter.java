package com.zhupeiting.bisheproject.util;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Component
public class SensitiveWordFilter {
	@SuppressWarnings("rawtypes")
	private Map sensitiveWordMap = null;
	public static int minMatchTYpe = 1;      //最小匹配规则
	public static int maxMatchType = 2;      //最大匹配规则

	/**
	 * 构造函数，初始化敏感词库
	 */
	public SensitiveWordFilter(){
		sensitiveWordMap = new SensitiveWordInit().initKeyWord();
	}

	/**
	 * 获取文字中的敏感词
	 */
	public Set<String> getSensitiveWord(String txt , int matchType){
		Set<String> sensitiveWordList = new HashSet<String>();
		for(int i = 0 ; i < txt.length() ; i++){
			int length = CheckSensitiveWord(txt, i, matchType);
			if(length > 0){
				sensitiveWordList.add(txt.substring(i, i+length));
				i = i + length - 1;
			}
		}
		return sensitiveWordList;
	}

	/**
	 * 检查文字中是否包含敏感字符
	 */
	@SuppressWarnings({ "rawtypes"})
	public int CheckSensitiveWord(String txt,int beginIndex,int matchType){
		boolean  flag = false;
		int matchFlag = 0;
		char word = 0;
		Map nowMap = sensitiveWordMap;
		for(int i = beginIndex; i < txt.length() ; i++){
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word);
			if(nowMap != null){
				matchFlag++;
				if("1".equals(nowMap.get("isEnd"))){
					flag = true;
					if(SensitiveWordFilter.minMatchTYpe == matchType){
						break;
					}
				}
			}
			else{
				break;
			}
		}
		if(matchFlag < 2 || !flag){
			matchFlag = 0;
		}
		return matchFlag;
	}


	/**
	 * 用替换字符串替换敏感字
	 */
	public String replaceSensitiveWord(String txt,int matchType,String replaceChar){
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt, matchType);
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}
		return resultTxt;
	}

	/**
	 * 获取替换敏感词的替换字符串
	 */
	public String getReplaceChars(String replaceChar,int length){
		String resultReplace = replaceChar;
		for(int i = 1 ; i < length ; i++){
			resultReplace += replaceChar;
		}

		return resultReplace;
	}

}
