package main;

public class MainVo {
	private String tableName;

	public MainVo(){};
	
	public MainVo(String tableName){
		this.tableName = tableName;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
