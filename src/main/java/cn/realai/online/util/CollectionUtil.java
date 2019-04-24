package cn.realai.online.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合工具类
 * @author lyh
 */
public class CollectionUtil {

	/*
	 * 将大的list按指定size拆分成小的list 
	 * @param originalList
	 * @param clazz
	 * @param size
	 * @return
	 */
	public static <T> List<List<T>> segmentationList(List<T> originalList, T clazz, int size) {
		List<List<T>> returnList = new ArrayList<List<T>>();
		int originalSize = originalList.size();
		int count = originalSize / size;
		int remainder = originalSize % size;
		count = remainder > 0 ? count + 1 : count;
		for (int i = 0; i < count; i++) {
			int end = (i + 1) * size;
			if (end > originalSize) end = originalSize;
			returnList.add(originalList.subList(i * size, end));
		}
		return returnList;
	}
	
	/*public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 1023; i++) {
			list.add("123123");
		}
		List<List<String>> l = segmentationList((List)list, String.class, 10);
		System.out.println(l);
	}*/
	
}

	