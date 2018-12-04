package net.evecom.rd.ie.baseline.utils.report.csv;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CSVUtils
 * @Description: CSV操作(导出和导入)
 * @author： zhengc
 * @date： 2017-6-19
 */
public class CSVUtils {

	/**
	 * 导出
	 * @param outputStream 输出数据
	 * @param dataList 数据
	 * @param flag 换行标志
	 * @return
	 */
	public static boolean exportCsv(ServletOutputStream outputStream,
                                    List<String> dataList, int flag) {
		boolean isSucess = false;
		StringBuffer bw=new StringBuffer();;
		try {
			if (dataList != null && !dataList.isEmpty()) {
				for (int i = 0; i < dataList.size(); i++) {
					String data=dataList.get(i);
					bw.append("\""+data+"\"").append(",");
					if(i%flag==(flag-1)){
						bw.append("\r\n");
					}
				}
			}
			outputStream.write(bw.toString().getBytes("GBK"));// 写入输出流
			outputStream.flush();// 缓存流
			isSucess = true;
		} catch (Exception e) {
			isSucess = false;
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
					outputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return isSucess;
	}

	/**
	 * 导入
	 *
	 * @param file
	 *            csv文件(路径+文件)
	 * @return
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static List<String> importCsv(File file)
			throws FileNotFoundException, UnsupportedEncodingException {
		List<String> dataList = new ArrayList<String>();

		BufferedReader br = null;
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, "gbk");
		try {
			br = new BufferedReader(isr);
			String line = "";
			while ((line = br.readLine()) != null) {
				dataList.add(line);
			}
		} catch (Exception e) {
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return dataList;
	}
}
