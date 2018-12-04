package net.evecom.rd.ie.baseline.tools.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: Result
 * @Description: 统一返回值对象 @author： zhengc @date： 2011年5月8日
 */
public class Result<T> implements Serializable {
	/**
	 * 结果是否成功 true成功 false失败
	 */
	private boolean success = true;

	/**
	 * 编码 成功为0 失败为具体失败码
	 */
	private int code;

	/**
	 * 描述信息
	 */
	private String msg;

	/**
	 * 返回结果携带的数据
	 */
	private Map<String, Object> data;

	public static <T> Result<T> success() {
		Result<T> result = new Result<T>();
		result.setSuccess(true);
		result.setCode(0);
		return result;
	}

	public static <T> Result<T> success(String msg) {
		Result<T> result = success();
		result.setMsg(msg);
		return result;
	}

	public static <T> Result<T> success(String msg, Map<String, Object> map) {
		Result<T> result = success(msg);
		result.setData(map);
		return result;
	}

	public static <T> Result<T> success(String msg, Object object) {
		Map<String, Object> map = new HashMap<String, Object>();
		Result<T> result = success(msg);
		if (object != null) {
			map.put(object.getClass().getSimpleName(), object);
		}
		result.setData(map);
		return result;
	}

	public static <T> Result<T> failed(int code, String msg) {
		Result<T> result = new Result<T>();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}

	public static <T> Result<T> failed(String msg, Map<String, Object> map) {
		Result<T> result = failed(msg);
		result.setData(map);
		return result;
	}

	public static <T> Result<T> failed(String msg, Object object) {
		Map<String, Object> map = new HashMap<String, Object>();
		Result<T> result = failed(msg);
		map.put(object.getClass().getSimpleName(), object);
		result.setData(map);
		return result;
	}

	public static <T> Result<T> failed(String msg) {
		Result<T> result = failed();
		result.setMsg(msg);
		return result;
	}

	public static <T> Result<T> failed() {
		Result<T> result = new Result<T>();
		result.success = false;
		result.setCode(1);
		return result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
