package dto;

public enum Dataset {
	SMS_SMALL(1,"SMS_small"),
	SMS_LARGE(2,"SMS_large"),
	CFMP(3,"CFMP"),
	WEB(4,"WEB");
	
	private int id;
	private String name;
	
	Dataset(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public int getID(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
}
