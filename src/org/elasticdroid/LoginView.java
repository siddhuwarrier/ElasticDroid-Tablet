package org.elasticdroid;

import org.elasticdroid.intf.callback.LoginCallbackIntf;
import org.elasticdroid.model.tables.AWSUserModel;
import org.elasticdroid.task.LoginTask;
import org.elasticdroid.tpl.GenericActivity;
import org.elasticdroid.utils.DialogConstants;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;


public class LoginView extends GenericActivity implements OnClickListener, LoginCallbackIntf {	
	private LoginTask loginTask;
	
	private AWSUserModel userTable;
	
	private static final String TAG = LoginView.class.getName();
	
	public LoginView() {
		userTable = new AWSUserModel();
	}
	
    /**
     * Called when the activity is first created.
     * 
     * @param savedInstanceState
     *            the saved instance state.
     * */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.login);
    	
    	((Button) findViewById(R.id.loginButton)).setOnClickListener(this);
        
    	Object retained = getLastNonConfigurationInstance();
        if (retained instanceof LoginTask) {
            Log.v(TAG, "Reclaiming previous background task...");

            loginTask = (LoginTask) retained;
            loginTask.setCallback(this);
        }
        
        
        //TODO MOVE TO LOGINSUCCESS
        userTable = new AWSUserModel();
        userTable.setAwsAccessKey("AKIAIFNZVN3SD3GFZ4UA");
        userTable.setAwsSecretAccessKey("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");
        userTable.setAwsUsername("ec2lab");
        ((ElasticDroidApp) getApplication()).setAwsUserModel(userTable);
        Intent dashboardIntent = new Intent();
        dashboardIntent.setClassName("org.elasticdroid", "org.elasticdroid.EC2DashboardView");
        
        startActivity(dashboardIntent);
    }
    
    @Override
    public void onSaveInstanceState(Bundle stateToSave) {
    	
    }
    @Override
    public void onRestoreInstanceState(Bundle stateToRestore) {
    	
    }
    
    @Override
    public void onResume() {
    	super.onResume(); //call base class method
    }
    
    @Override
    public Object onRetainNonConfigurationInstance() {
        Log.v(TAG, "onRetainNonConfigurationInstance");

        if (loginTask != null) {
            Log.v(TAG, "Saving instance of LoginModel");

            // set the activity in loginModel to null so that it does not call
            // the activity
            loginTask.setCallback(null);

            return loginTask;
        }
        
        return null;
    }

    /* OnClickListener interface */
	public void onClick(View v) {
		userTable.setAwsUsername((((EditText) findViewById(R.id.usernameEntry)).getText().toString()));
		userTable.setAwsAccessKey((((EditText) findViewById(R.id.akEntry)).getText().toString()));
		userTable.setAwsSecretAccessKey((((EditText) findViewById(R.id.sakEntry)).getText().toString()));
		
		if (userTable.getAwsAccessKey() == null ) {
			((EditText) findViewById(R.id.akEntry)).setError(getString(R.string.loginview_accesskey_empty_err));
		}
		if (userTable.getAwsSecretAccessKey() == null) {
			((EditText) findViewById(R.id.sakEntry)).setError(getString(R.string.loginview_secretaccesskey_empty_err));
		}
		if (userTable.getAwsUsername() == null) {
			((EditText) findViewById(R.id.usernameEntry)).setError(getString(R.string.loginview_username_empty_err));
		}		
		
		loginTask = new LoginTask(this, this, userTable);
		loginTask.execute();
		
		showDialog(DialogConstants.PROGRESS_DIALOG.ordinal()); //show progress dialog
	}
	
	/* LoginCallbackIntf interface */
	public void cleanUpAfterTaskExecution() {
		// TODO Auto-generated method stub
        Log.d(TAG, "cleanUpAfterModelExecution: Dismissing progress dialog...");
        dismissProgressDialog();
	}

	public void loginSuccess() {
		// TODO Auto-generated method stub
		Log.d(TAG, "Login success!");
		
		//display dashboard
		
		//save AWS User Data
		((ElasticDroidApp)getApplication()).setAwsUserModel(userTable);
	}

	public void awsClientException(AmazonClientException exception) {
		Log.d(TAG, "Amazon Client Exception!");
		displayAlertDialog(R.string.loginview_no_connxn_dlg, false);
	}

	public void awsServerException(AmazonServiceException exception) {
		Log.d(TAG, "Amazon Service Exception!");
		displayAlertDialog(R.string.loginview_invalid_credentials_err, false);
	}
	
	/**
	 * Overridden method to display the menu on press of the menu key
	 * 
	 * Inflates and displays dashboard menu.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.login_menu, menu);
		
		return true;
	}

	/**
	 * Overriden method to handle selection of menu item
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem selectedItem) {
		return true;
	}

}
