package king.easycampusnet.model;
import java.util.*;

public interface Request
{
	public final static int TYPE_EPORTAL=0x01;
    public String redirectURL;
	
	public String getUrl();

	public HashMap<String,Object> getPayload()
}
