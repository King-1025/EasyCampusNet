package king.easycampusnet.ui;

import android.app.*;
import android.os.*;
import king.easycampusnet.*;
import android.widget.*;
import android.view.*;
import king.easycampusnet.model.*;
import android.content.*;
import java.util.*;
import king.easycampusnet.tool.*;
import king.easycampusnet.manager.*;
import king.easycampusnet.tool.other.*;
import android.text.*;
import android.view.View.*;
import android.widget.RadioGroup.*;

public class MainActivity extends BasedActivity 
{
	private Button test;
	private TextView log;
	private int maxAppend=100;
	private int count=0;
	private boolean isflush=true;
	private ScrollView sl;
	private AlertDialog alertDialog;
    private int theme;
	private RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		test=(Button)findViewById(R.id.mainButton1);
		log=(TextView)findViewById(R.id.mainTextView1);
		sl=(ScrollView) findViewById(R.id.mainScrollView1);
		log.setTextIsSelectable(true);
		test.setOnClickListener(new View.OnClickListener(){

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
					if(log.getMeasuredHeight() != sl.getScrollY() + log.getHeight()){
						this.postDelayed(new Runnable(){

								@Override
								public void run()
								{
									// TODO: Implement this method
									sl.fullScroll(ScrollView.FOCUS_DOWN);//滚动到底部
								}
							},500);
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
			case R.id.theme:
				changeTheme();
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
	
	private void changeTheme(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		View view=LayoutInflater.from(this).inflate(R.layout.theme,null);
		Button cancel=(Button) view.findViewById(R.id.themeButtonCancel);
		Button ok=(Button) view.findViewById(R.id.themeButtonOK);
		rg=(RadioGroup) view.findViewById(R.id.themeRadioGroup1);
		new king.easycampusnet.tool.other.RadioGroupUtils(rg).supportNest();
		String[] str=getResources().getStringArray(R.array.theme);
		int []color={R.color.black,R.color.snow,R.color.blueviolet,R.color.pink,R.color.brown,R.color.purple};
		int length=str.length<color.length ? str.length:color.length;
		for(int i=0;i<length;i++){
			LinearLayout line=(LinearLayout) LayoutInflater.from(this).inflate(R.layout.line,null);
			line.setBottom(20);
			RadioButton rb=(RadioButton) line.getChildAt(0);
			rb.setId(i);
			rb.setText(str[i]);
			TextView tv=(TextView) line.getChildAt(2);
			tv.setBackgroundColor(getResources().getColor(color[i]));
			rg.addView(line);
			//MessageCenter.send(i+" str:"+str[i]+"\n");
		}
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup p1, int p2)
				{
					  int id = rg.getCheckedRadioButtonId();
				      switch(id){
						  case 0:
							  theme=R.style.dark;
							  break;
						  case 1:
							  theme=R.style.light;
							  break;
						  default:
						      theme=ThemeTool.THEME_NONE;
							  break;
					  }
			          //MessageCenter.send("id:"+id+" theme:"+theme+"\n");
				}
			});
		cancel.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					alertDialog.dismiss();
				}
			});
		ok.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
				    if(theme==ThemeTool.THEME_NONE){
						Logger.show(MainActivity.this,"敬请期待!");
						return;
					}
					if(MyApplication.setTheme(theme)){
						MessageCenter.send("主题设置成功!");
						skip(MainActivity.class,true);
					}else{
						MessageCenter.send("主题设置失败!");
					}
					alertDialog.dismiss();
				}
			});
		builder.setView(view);
		alertDialog=builder.create();
		alertDialog.show();
	}
}
