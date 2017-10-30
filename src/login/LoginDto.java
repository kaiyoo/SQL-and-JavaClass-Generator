package login;

public class LoginDto {
	private String userId;
	private String password;
	private String host;
	private String port;
	private String serviceName;
		
	public LoginDto(String userId, String password, String host, String port, String serviceName) {
		this.userId = userId;
		this.password = password;
		this.host = host;
		this.port = port;
		this.serviceName = serviceName;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
}
