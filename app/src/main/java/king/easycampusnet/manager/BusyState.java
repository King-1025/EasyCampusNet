package king.easycampusnet.manager;
import org.apache.http.cookie.*;

public class BusyState implements State
{

	@Override
	public int getType()
	{
		// TODO: Implement this method
		return State.BUSY_STATE;
	}

	@Override
	public void doAction(LoginContext context)
	{
		// TODO: Implement this method
		context.setState(this);
	}
	
}
