package king.easycampusnet.manager;

public class LoginContext
{
	private State state;
	
	public LoginContext(){
		state = null;
	}

	public void setState(State state){
		this.state = state;     
	}

	public State getState(){
		return state;
	}
}
