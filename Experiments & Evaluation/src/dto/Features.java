package dto;

public enum Features {
	URDUPHONE(1,"UrduPhone"),
	URDUPHONE_EDITDISTANCE(2,"UrduPhone+ED"),
	EDITDISTANCE(3,"ED"),
	URDUPHONE_EDITDISTANCE_URDUPHONEID(4,"UrduPhone+ED+UrduPhoneID"),
	URDUPHONE_EDITDISTANCE_WORDID(5,"UrduPhone+ED+WordID");
	
	private int id;
	private String name;
	
	Features(int id, String name){
		this.id =id;
		this.name = name;
	}
	
	public int getID(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
}
