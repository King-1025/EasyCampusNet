package king.easycampusnet.model;
import king.easycampusnet.manager.*;

public class EPortalUser extends User
{
	private String accountType;
	public final static int ACCOUNT_TYPE_ZZULIS=0x00;
    public final static int ACCOUNT_TYPE_CMCC=0x01;
	public final static int ACCOUNT_TYPE_UNICOM=0x02;
	public final static int ACCOUNT_TYPE_OTHER=0x03;
	public final static int ACCOUNT_TYPE_NONE=0x04;
	
	public final static String KEY_ACCOUNT="acvount";
	public final static String KEY_PASSWORD="password";
	public final static String KEY_ACCOUNT_TYPE="account_type";
	
	public final static String KEY_IS_FRESH="is_fresh";
	
	public EPortalUser()
	{
	    this(0,null,null,null,-1);
	}
	
	public EPortalUser(String account,String password,int type){
		this(0,null,account,password,type);
	}
	
	public EPortalUser(int id, String name, String account, String password,int type)
	{
		super(id,name,account,password);
		account=parseType(type);
	}

	public void setAccountType(int type)
	{
		this.accountType = parseType(type);
	}

	public String getAccountType()
	{
		return accountType;
	}
	
	private String parseType(int type){
		String value="@null";
		switch(type){
			case ACCOUNT_TYPE_ZZULIS:
				value="@zzulis";
				break;
			case ACCOUNT_TYPE_CMCC:
				value="@cmcc";
				break;
			case ACCOUNT_TYPE_UNICOM:
				value="@unicom";
				break;
			case ACCOUNT_TYPE_OTHER:
				value="@other";
				break;
		}
		return value;
	}
	
	public void login(){
		  EPortalRequest request= (EPortalRequest) new RequestFactory().getRequest(Request.TYPE_EPORTAL);
		  request.setUser(this);
          LoginCenter.request(request);
	}
}
