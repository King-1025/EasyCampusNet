package king.easycampusnet.manager;

public class FreeState implements State
{

	@Override
	public int getType()
	{
		// TODO: Implement this method
		return State.FREE_STATE;
	}

	@Override
	public void doAction(LoginContext context)
	{
		// TODO: Implement this method
		context.setState(this);
	}
	
}
