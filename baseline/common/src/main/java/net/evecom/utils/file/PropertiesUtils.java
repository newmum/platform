package net.evecom.utils.file;

import java.io.*;
import java.util.*;

/**
 * @ClassName: PropertiesUtils
 * @Description 全局配置操作组件
 * @author zhengc
 * @date 2018-5-21
 *
 */
public class PropertiesUtils {
	private Properties properties = new Properties();

	public PropertiesUtils(InputStream inputStream) {
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PropertiesUtils(String filePath) {
		try {
			ArrayList<File> fileList = new ArrayList<File>();
			fileList = getFiles(fileList, filePath);
			for (File file : fileList) {
				if (file.getName().lastIndexOf(".properties") > -1) {
					InputStreamReader inputStream = new InputStreamReader(new FileInputStream(file), "UTF-8");
					properties.load(inputStream);
				} else {
					System.out.println(file.getAbsolutePath());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<File> getFiles(ArrayList<File> fileList, String path) throws Exception {
		// path = Global.class.getClassLoader().getResource(path).getFile();
		// System.out.println("path==" + path);
		// 目标集合fileList
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File fileIndex : files) {
				// 如果这个文件是目录，则进行递归搜索
				if (fileIndex.isDirectory()) {
					getFiles(fileList, fileIndex.getPath());
				} else {
					// 如果文件是普通文件，则将文件句柄放入集合中
					fileList.add(fileIndex);
				}
			}
		} else {
			fileList.add(file);
		}
		return fileList;
	}

	public String getKey(String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			value = "";
		}
		return value.trim();
	}

	/**
	 * 得到所有的配置信息
	 *
	 * @return
	 */
	public Map<?, ?> getAll() {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<?> enu = properties.propertyNames();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			String value = properties.getProperty(key);
			// System.out.println("key:" + key + ";value:" + value);
			map.put(key, value);
		}
		return map;
	}

	public void updateKey(String key, String value) {
		properties.setProperty(key, value);
	}

	public void removeKey(String key) {
		properties.remove(key);
	}

	public boolean containsKey(String key) {
		return properties.containsKey(key);
	}

	public boolean containsValue(String value) {
		return properties.containsValue(value);
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}
