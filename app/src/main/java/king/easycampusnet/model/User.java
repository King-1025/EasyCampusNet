package king.easycampusnet.model;
import king.easycampusnet.manager.*;

public class User
{
	protected int ID;
	protected String name;
	protected String account;
	protected String password;

	public User(int id, String name, String account, String password)
	{
		this.ID = id;
		this.name = name;
		this.account = account;
		this.password = password;
	}
	
	public void setID(int iD)
	{
		ID = iD;
	}

	public int getID()
	{
		return ID;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getAccount()
	{
		return account;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	@Override
	public boolean equals(Object o)
	{
		// TODO: Implement this method
		if(o!=null){
			if(o instanceof User){
				if(((User)o).getID()==ID)return true;
			}
		}
		return false;
	}

}
