package king.easycampusnet.ui;

import android.app.*;
import android.os.*;
import king.easycampusnet.*;
import android.widget.*;
import android.widget.ExpandableListView.*;
import android.view.*;
import king.easycampusnet.model.*;
import android.content.*;
import java.util.*;
import king.easycampusnet.tool.*;
import king.easycampusnet.manager.*;

public class MainActivity extends BasedActivity 
{
	private Button test;
	private TextView log;
	private int maxAppend=100;
	private int count=0;
	private boolean isflush=true;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		test=(Button)findViewById(R.id.mainButton1);
		log=(TextView)findViewById(R.id.mainTextView1);
		
		test.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if(isflush){
						log.setText("");
						isflush=false;
					}
				    login();
				}
			});
			
			MyApplication.setHandler(new Handler(){
				public void handleMessage(Message msg){
					if(count<maxAppend){
						log.append(String.valueOf(msg.obj)+"\n\n");
						count++;
					}else{
						log.setText(String.valueOf(msg.obj)+"\n\n");
						count=0;
					}
				}
			});
			
    }

	@Override
	protected void onResume()
	{
		super.onResume();
		isflush=true;
		log.setText(getString(R.string.help));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.top, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()){
			case R.id.about:
				skip(AboutActivity.class,false);
				break;
			case R.id.setting:
				skip(SettingActivity.class,false);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void skip(Class<?> cls,boolean isFinish){
		startActivity(new Intent(this,cls));
		if(isFinish)finish();
	}
	
	private void login(){
		HashMap map=SaveTool.query(this,MyApplication.DATA_NAME,new String[]{
			EPortalUser.KEY_ACCOUNT,EPortalUser.KEY_PASSWORD,EPortalUser.KEY_ACCOUNT_TYPE
		});
		if(map!=null){
			String account=String.valueOf(map.get(EPortalUser.KEY_ACCOUNT));
			String passwd=String.valueOf(map.get(EPortalUser.KEY_PASSWORD));
			String type=String.valueOf(map.get(EPortalUser.KEY_ACCOUNT_TYPE));
			MessageCenter.send("account:"+account+"\npasswd:"+passwd+"\ntype:"+type);
			if(!isNull(account)&&!isNull(passwd)&&!isNull(type)){
				try{
					int accountType=Integer.valueOf(type);
					EPortalUser user=new EPortalUser();
					user.setAccount(account);
					user.setPassword(passwd);
					user.setAccountType(accountType);
					user.login();
				}catch(Exception e){
		            MessageCenter.send(e.toString());
					MessageCenter.send("数据解析失败!");
				}
			}else{
				MessageCenter.send("获取账户失败!");
			}
		}else{
			MessageCenter.send("请求数据异常!");
		}
	}
	
	private boolean isNull(String s1){
		return ActivityTool.isNull(s1);
	}
	
}
