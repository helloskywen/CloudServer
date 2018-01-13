
public class RoomLocation{	
	int building_number;
	int building_level;
	int room;
	
	public RoomLocation(int building_number, int building_level, int room){
		this.building_number = building_number;
		this.building_level = building_level;
		this.room = room;
	}	
	
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime*result + (building_number == 0? 0:Integer.hashCode(building_number));
		result = prime*result + (building_level == 0? 0:Integer.hashCode(building_level));
		result = prime*result + (room == 0? 0:Integer.hashCode(room));
		return result;
	}
	
	public boolean equals(Object o){
		if(this  == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		RoomLocation sensor = (RoomLocation)o;
		
		if(this.building_number == sensor.building_number && this.building_level == sensor.building_level && this.room == sensor.room)
			return true;
		else 
			return false;		
	}
}
