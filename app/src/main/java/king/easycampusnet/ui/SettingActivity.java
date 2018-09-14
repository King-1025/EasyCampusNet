package king.easycampusnet.ui;
import android.app.*;
import android.os.*;
import king.easycampusnet.*;
import android.widget.*;
import android.widget.AdapterView.*;
import android.view.*;
import java.util.*;
import king.easycampusnet.tool.*;
import king.easycampusnet.model.*;

public class SettingActivity extends BasedActivity
{

	private EditText etAccount;
	private EditText etPassword;
	private Spinner type;
	private Button reset;
	private Button ok;
	private TextView log;
	
	private int maxAppend=100;
	private int count=0;
	private boolean isflush=true;
	
	private int accountType;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		etAccount=(EditText) findViewById(R.id.settingAccount);
		etPassword=(EditText) findViewById(R.id.settingPassword);
		type=(Spinner) findViewById(R.id.settingType);
		reset=(Button) findViewById(R.id.settingButtonReset);
		ok=(Button) findViewById(R.id.settingButtonOK);
		log=(TextView) findViewById(R.id.settingLog);
		
		type.setSelection(0,true);
		
		type.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
				{
					if(!isflush)
					log("类型:"+type.getAdapter().getItem(p3)+" "+p3);
					parseType(p3);
				}

				@Override
				public void onNothingSelected(AdapterView<?> p1)
				{
					// TODO: Implement this method
				}
			});
			
		reset.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					etAccount.setText(null);
					etPassword.setText(null);
					type.setSelection(0,true);
				}
			});
		
		ok.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					update();
				}
			});
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		isflush=true;
		
		HashMap map=SaveTool.query(this,MyApplication.DATA_NAME,new String[]{
									   EPortalUser.KEY_ACCOUNT,EPortalUser.KEY_PASSWORD,EPortalUser.KEY_ACCOUNT_TYPE
								   });
		if(map!=null){
			String account=String.valueOf(map.get(EPortalUser.KEY_ACCOUNT));
			String passwd=String.valueOf(map.get(EPortalUser.KEY_PASSWORD));
			String type=String.valueOf(map.get(EPortalUser.KEY_ACCOUNT_TYPE));
			log("当前用户配置:\n"+"account:"+account+"\npasswd:"+passwd+"\ntype:"+type);
		}
	}
	
	private void update(){
		String account=String.valueOf(etAccount.getText());
		String passwd=String.valueOf(etPassword.getText());
		String type=String.valueOf(accountType);
		if(isNull(account)){
			Logger.show(this,"账户不能为空!");
			return;
		}
		if(isNull(passwd)){
			Logger.show(this,"密码不能为空!");
			return;
		}
		if(isNull(type)){
			Logger.show(this,"类型为空!");
			log("类型为空! accountType:"+accountType);
			return;
		}
		boolean isOk=SaveTool.add(this,MyApplication.DATA_NAME,new String[]{
	        EPortalUser.KEY_ACCOUNT,EPortalUser.KEY_PASSWORD,EPortalUser.KEY_ACCOUNT_TYPE,EPortalUser.KEY_IS_FRESH
		},new String[]{
			account,passwd,type,"yes"
		});
		if(isOk){
			Logger.show(this,"更新成功!");
			log("账户配置更新成功!\n"+"account:"+account+"\npasswd:"+passwd+"\ntype:"+type);
		}else{
			Logger.show(this,"更新失败!");
			log("账户配置更新失败!\n"+"account:"+account+"\npasswd:"+passwd+"\ntype:"+type);
		}
	}
	
	private void parseType(int index){
		switch(index){
			case 0:
				accountType=EPortalUser.ACCOUNT_TYPE_ZZULIS;
				break;
			case 1:
				accountType=EPortalUser.ACCOUNT_TYPE_CMCC;
				break;
			case 2:
				accountType=EPortalUser.ACCOUNT_TYPE_UNICOM;
				break;
			case 3:
				accountType=EPortalUser.ACCOUNT_TYPE_OTHER;
			    break;
			default:
			    accountType=EPortalUser.ACCOUNT_TYPE_NONE;
				Logger.show(this,"类型异常!");
				log("类型异常! index:"+index);
				break;
		}
	}
	
	private void log(String msg){
		if(isflush){
			log.setText("");
			isflush=false;
		}
		if(count<maxAppend){
			log.append(String.valueOf(msg)+"\n\n");
			count++;
		}else{
			log.setText(String.valueOf(msg)+"\n\n");
			count=0;
		}
	}
	
	private boolean isNull(String s1){
		return ActivityTool.isNull(s1);
	}
	
}
