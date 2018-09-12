package king.easycampusnet.manager;

public interface State
{
	public final static int FREE_STATE=0x01;
	public final static int BUSY_STATE=0x02;
	public int getType();
	public void doAction(LoginContext context);
}
