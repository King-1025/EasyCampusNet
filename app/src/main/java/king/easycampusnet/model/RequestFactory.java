package king.easycampusnet.model;

public class RequestFactory
{
	public Request getRequest(int type){
		Request request=null;
		switch(type){
			case Request.TYPE_EPORTAL:
				request=new EPortalRequest();
				break;
		}
		return request;
	}
}
