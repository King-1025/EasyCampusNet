package king.easycampusnet.ui;
import android.app.*;
import android.os.*;
import king.easycampusnet.*;
import android.widget.*;

public class AboutActivity extends BasedActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		((TextView)findViewById(R.id.aboutTextView1)).setText(R.string.info);
	}
	
}
