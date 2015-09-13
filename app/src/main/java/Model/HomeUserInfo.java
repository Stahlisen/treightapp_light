package Model;

public class HomeUserInfo {
	String currentweight;
	String goalweight;
	String goaldate;
	String timeremaining;
	
	public HomeUserInfo(String _currentweight, String _goalweight,
			String _goaldate, String _timeremaining) {
		this.currentweight = _currentweight;
		this.goalweight = _goalweight;
		this.goaldate = _goaldate;
		this.timeremaining = _timeremaining;
	}

	public String getCurrentweight() {
		return currentweight;
	}

	public void setCurrentweight(String currentweight) {
		this.currentweight = currentweight;
	}

	public String getGoalweight() {
		return goalweight;
	}

	public void setGoalweight(String goalweight) {
		this.goalweight = goalweight;
	}

	public String getGoaldate() {
		return goaldate;
	}

	public void setGoaldate(String goaldate) {
		this.goaldate = goaldate;
	}

	public String getTimeremaining() {
		return timeremaining;
	}

	public void setTimeremaining(String timeremaining) {
		this.timeremaining = timeremaining;
	}
	
	

}
