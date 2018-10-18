package net.evecom.utils.database.redis;

/**
 * @ClassName: JedisConfig
 * @Description: redis配置对象
 * @author： zhengc
 * @date： 2015年3月10日
 */
public class JedisConfig {
    private int MaxActive;
    private int MaxIdle;
    private int MaxWait;
    private Boolean TestOnBorrow;
    private String ip;
    private int post;
    private String password;

    public int getMaxActive() {
        return MaxActive;
    }

    public void setMaxActive(int maxActive) {
        MaxActive = maxActive;
    }

    public int getMaxIdle() {
        return MaxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        MaxIdle = maxIdle;
    }

    public int getMaxWait() {
        return MaxWait;
    }

    public void setMaxWait(int maxWait) {
        MaxWait = maxWait;
    }

    public Boolean getTestOnBorrow() {
        return TestOnBorrow;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        TestOnBorrow = testOnBorrow;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "JedisConfig [MaxActive=" + MaxActive + ", MaxIdle=" + MaxIdle + ", MaxWait=" + MaxWait
				+ ", TestOnBorrow=" + TestOnBorrow + ", ip=" + ip + ", post=" + post + ", password=" + password + "]";
	}


}
