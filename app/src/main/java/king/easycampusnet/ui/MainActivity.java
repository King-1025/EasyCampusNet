package king.easycampusnet.ui;


import android.app.*;
import android.os.*;
import king.easycampusnet.*;
import android.widget.*;
import android.widget.ExpandableListView.*;
import android.view.*;
import king.easycampusnet.model.*;
import android.content.*;

public class MainActivity extends Activity 
{
	private Button test;
	private TextView log;
	private int maxAppend=100;
	private int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		test=(Button)findViewById(R.id.mainButton1);
		log=(TextView)findViewById(R.id.mainTextView1);
		log.setText("请点击认证按钮");
		test.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					log.setText("");
					EPortalUser user=new EPortalUser();
					user.setAccount("541513260118");
					user.setPassword("102510");
					user.setAccountType(EPortalUser.ACCOUNT_TYPE_OTHER);
					user.login();
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
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		getMenuInflater().inflate(R.menu.top, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
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
}
