package com.firesend.other;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import com.firesend.*;

public class AboutFireSend extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) 
		{
			case android.R.id.home:
				Intent upIntent = getParentActivityIntent();
				if (shouldUpRecreateTask(upIntent)) {
					TaskStackBuilder.create(this)
						.addNextIntentWithParentStack(upIntent)
						.startActivities();
				} else {
					upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					navigateUpTo(upIntent);
				}
				break;
		}
		return true;
	}
}
