package net.evecom.rd.ie.baseline.core.db.untis;

import net.evecom.rd.ie.baseline.core.db.model.entity.TableColumn;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenCodeUtile {
	// baseBean已含有属性
	private static Set<String> butset = new HashSet<String>();
	static {
		butset.add("TID");
		butset.add("UPDATE_TIME");
		butset.add("CREATE_TIME");
	}

	public void getCode(List<TableColumn> resultlist, String tbname, String filePath) throws IOException {
		ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("");
		Configuration cfg = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
		Template t = gt.getTemplate("/bean.btl");
		t.binding("tablename", tbname);
		t.binding("name", setColName2(tbname));
		String str = tbname.substring(0, 1).toLowerCase() + tbname.substring(1);
		t.binding("name2", str);// 首字母小写
		resultlist = setAttrType(resultlist,true);// 设置java属性
		t.binding("collist", resultlist);
		str = t.render();
		System.out.println(str);
		String fileName = setColName2(tbname);
		strToFile(str, filePath + "/" + fileName + ".java");
	}

	public static String getFileName(String name) {
		name=name.toLowerCase();
		int i = name.lastIndexOf("_");
		name = name.substring(i + 1);
		return name;
	}

	/**
	 * parent_id ===>ParentId;
	 *
	 * @param name
	 * @return
	 */
	public static String setColName2(String name) {
		if (isEmpty(name)) {
			name=name.toLowerCase();
			String[] array = name.split("_");
			name = "";
			for (int i = 0; i < array.length; i++) {
				name += array[i].substring(0, 1).toUpperCase() + array[i].substring(1);
			}
			name = name.trim();
		}
		return name;
	}

	/**
	 * parent_id ===>parentId;
	 *
	 * @param name
	 * @return
	 */
	public static String setColName(String name) {
		if (isEmpty(name)) {
			name=name.toLowerCase();
			String[] array = name.split("_");
			name = "";
			for (int i = 0; i < array.length; i++) {
				if (i == 0) {
					name += array[i];
				} else {
					name += array[i].substring(0, 1).toUpperCase() + array[i].substring(1);
					;
				}
			}
			name = name.trim();
		}
		return name;
	}

	/**
	 * @param str
	 * @return false 为空 true 不为空
	 */
	public static boolean isEmpty(String str) {
		boolean bo = false;
		if (str != null && !str.trim().equals("")) {
			bo = true;
		}
		return bo;
	}

	public File strToFile(String str, String path) {
		File file = new File(path);
		FileWriter writer;
		try {
			writer = new FileWriter(file);
			writer.write(str);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("file:" + file.getAbsolutePath());
		return file;
	}

	public static List<TableColumn> setAttrType(List<TableColumn> list,boolean bo) {
		List<TableColumn> templist = new ArrayList<TableColumn>();
		// 设置数据与java类型匹配
		for (TableColumn temp : list) {
			String str = "";
			str = temp.getColumnName();
			if(bo){
				if (butset.contains(str.toUpperCase())) {
					continue;
				}
			}
			// 属性首字符大写
			str = temp.getColumnName();
			// 下划线名称进行更换
			temp.setAttrName(setColName2(str));
			temp.setAttrname(setColName(str));
			str = temp.getDataType();
			if (str.equals("int")) {
				temp.setAttrType("Long");
			} else if (str.equals("varchar")) {
				temp.setAttrType("String");
			} else if (str.equals("double")) {
				temp.setAttrType("Double");
			} else if (str.equals("datetime")) {
				temp.setAttrType("Date");
			} else if (str.equals("char")) {
				temp.setAttrType("String");
			} else if (str.equals("bigint")) {
				temp.setAttrType("Long");
			} else if (str.equals("date")) {
				temp.setAttrType("String");
			} else if (str.equals("tinyint")) {
				temp.setAttrType("int");
			} else if (str.equals("text")) {
				temp.setAttrType("String");
			} else {
				temp.setAttrType("请为数据库类型\"" + str + "\"设置对应java类型");
			}

			templist.add(temp);
		}
		return templist;

	}

}
