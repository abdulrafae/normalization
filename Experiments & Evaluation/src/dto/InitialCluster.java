package dto;

public enum InitialCluster {
	URDUPHONE(1),
	RANDOM_URDUPHONESIZE(1),
	RANDOM_SINGLE(2);
	
	private int id;

	InitialCluster(int id){
		this.id =id;
	}
	
	public int getID(){
		return this.id;
	}
}
