/*******************************************************************************
 * The file DelegateActivity.java is part of ChipsAway.
 *  
 * Copyright (c) Cloudreach Limited 2011. All rights reserved. 
 *  
 *  Authored by Siddhu Warrier and Rodolfo Cartas Ayala.
 ******************************************************************************/
package org.elasticdroid.tpl;

import org.elasticdroid.R;
import org.elasticdroid.utils.DialogConstants;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;



/**
 * Delegate class which holds code shared between GenericActivity and GenericListActivity
 * 
 * @author siddhuwarrier
 *
 * 3 Mar 2011
 */
class DelegateActivity {

	/** LOG Tag */
	private static final String TAG = "com.chipsaway.tpl.DelegateActivity";
	
	/**
	 * Build an alert dialog box.
	 * @param activity  The GenericActivity to build the dialog box for.
	 */
	void buildAlertDialogBox(final GenericActivity activity) {
		// create and initialise the alert dialog
		activity.alertDialogBox = new AlertDialog.Builder(activity).create(); // create alert 
		//dialog box
		activity.alertDialogBox.setCancelable(false);								
		activity.alertDialogBox.setButton(
				activity.getString(android.R.string.ok),
				new DialogInterface.OnClickListener() {
					// click listener on the alert box - unlock orientation when
					// clicked.
					// this is to prevent orientation changing when alert box
					// locked.
                    public void onClick(DialogInterface arg0, int arg1) {
						activity.alertDialogDisplayed = false;
						activity.alertDialogBox.dismiss(); // dismiss dialog.
						// if an error occurs that is serious enough, finish the activity
						if (activity.killActivityOnError) {
							activity.finish();
						}
					}
				});
	}
	
	/**
	 * Overloaded: Build an alert dialog box.
	 * @param activity  The GenericListActivity to build the dialog box for.
	 */
	void buildAlertDialogBox(final GenericListActivity activity) {
		// create and initialise the alert dialog
		activity.alertDialogBox = new AlertDialog.Builder(activity).create(); // create alert 
		//dialog box
		activity.alertDialogBox.setCancelable(false);								
		activity.alertDialogBox.setButton(
				activity.getString(android.R.string.ok),
				new DialogInterface.OnClickListener() {
					// click listener on the alert box - unlock orientation when
					// clicked.
					// this is to prevent orientation changing when alert box
					// locked.
                    public void onClick(DialogInterface arg0, int arg1) {
						activity.alertDialogDisplayed = false;
						activity.alertDialogBox.dismiss(); // dismiss dialog.
						// if an error occurs that is serious enough return the
						// user to the login
						// screen. THis happens due to exceptions caused by
						// programming errors and
						// exceptions caused due to invalid credentials.
						if (activity.killActivityOnError) {
							activity.finish();
						}
					}
				});
	}
	
    /**
     * Shows a dialog message
     * 
     * @param activity the GenericActivity to display the alertdialog for.
     * @param id The resource id to read the string from
     * @param _killActivityOnError kills the activity if true after the dialog returns
     */
	void displayAlertDialog(GenericActivity activity,int id, boolean killActivityOnError) {
        // show the alert dialog
        activity.alertDialogMessage = activity.getString(id);
        activity.alertDialogDisplayed = true;
        activity.killActivityOnError = killActivityOnError;

        // display the alert dialog
        activity.alertDialogBox.setMessage(activity.alertDialogMessage);
        activity.alertDialogBox.show();// show error
	}
	
    /**
     * Overloaded: Shows a dialog message
     * 
     * @param activity the GenericListActivity to display the alertdialog for.
     * @param id The resource id to read the string from
     * @param _killActivityOnError kills the activity if true after the dialog returns
     */
	void displayAlertDialog(GenericListActivity activity,int id, boolean killActivityOnError) {
        // show the alert dialog
        activity.alertDialogMessage = activity.getString(id);
        activity.alertDialogDisplayed = true;
        activity.killActivityOnError = killActivityOnError;

        // display the alert dialog
        activity.alertDialogBox.setMessage(activity.alertDialogMessage);
        activity.alertDialogBox.show();// show error
	}
	
    /**
     * Shows a dialog message with a custom string
     * 
     * @param activity the GenericActivity to display the alertdialog for.
     * @param id The resource id to read the string from
     * @param _killActivityOnError kills the activity if true after the dialog returns
     */
	void displayAlertDialog(GenericActivity activity,String message, boolean killActivityOnError) {
        // show the alert dialog
        activity.alertDialogMessage = message;
        activity.alertDialogDisplayed = true;
        activity.killActivityOnError = killActivityOnError;

        // display the alert dialog
        activity.alertDialogBox.setMessage(activity.alertDialogMessage);
        activity.alertDialogBox.show();// show error
	}
	
    /**
     * Overloaded: Shows a dialog message with a custom string
     * 
     * @param activity the GenericActivity to display the alertdialog for.
     * @param id The resource id to read the string from
     * @param _killActivityOnError kills the activity if true after the dialog returns
     */
	void displayAlertDialog(GenericListActivity activity,String message, boolean 
			killActivityOnError) {
        // show the alert dialog
        activity.alertDialogMessage = message;
        activity.alertDialogDisplayed = true;
        activity.killActivityOnError = killActivityOnError;

        // display the alert dialog
        activity.alertDialogBox.setMessage(activity.alertDialogMessage);
        activity.alertDialogBox.show();// show error
	}
	
	/**
	 * Dismiss the progress dialog
	 * @param activity The GenericActivity where the progress dialog is being displayed.
	 */
	void dismissProgressDialog(GenericActivity activity) {
        // dismiss the progress dialog if displayed. Check redundant
        if (activity.progressDialogDisplayed) {
            Log.v(TAG, "Dismissing progress dialog...");
            activity.progressDialogDisplayed = false;
            activity.removeDialog(DialogConstants.PROGRESS_DIALOG.ordinal());
        }
	}
	
	/**
	 * Overloaded: Dismiss the progress dialog
	 * @param activity The GenericListActivity where the progress dialog is being displayed.
	 */
	void dismissProgressDialog(GenericListActivity activity) {
        // dismiss the progress dialog if displayed. Check redundant
        if (activity.progressDialogDisplayed) {
            Log.v(TAG, "Dismissing progress dialog...");
            activity.progressDialogDisplayed = false;
            activity.removeDialog(DialogConstants.PROGRESS_DIALOG.ordinal());
        }
	}
	
	/**
	 * Prepare dialog
	 * @param activity The GenericActivity in which the dialog is being displayed.
	 * @param id The dialog ID
	 */
	void onPrepareDialog(GenericActivity activity, int id) {
		if (id == DialogConstants.PROGRESS_DIALOG.ordinal()) {
			activity.progressDialogDisplayed = true;
			Log.d(TAG, "Dialog prepped.progress dialog displayed=" + 
					activity.progressDialogDisplayed);
		}
	}
	
	/**
	 * Overloaded: Prepare dialog
	 * @param activity The GenericListActivity in which the dialog is being displayed.
	 * @param id The dialog ID
	 */
	void onPrepareDialog(GenericListActivity activity, int id) {
		if (id == DialogConstants.PROGRESS_DIALOG.ordinal()) {
			activity.progressDialogDisplayed = true;
			Log.d(TAG, "Dialog prepped.progress dialog displayed=" + 
					activity.progressDialogDisplayed);
		}
	}
	
	/**
	 * Create and return a progress dialog.
	 * 
	 * @param activity The Activity that requires a dialog created.
	 * @param id The id of the dialog
	 * @return ProgressDialog
	 * @throws IllegalArgumentException if the dialog ID is not a progress dialog.
	 */
	Dialog createProgressDialog(Activity activity, int id) throws IllegalArgumentException {
		if (id == DialogConstants.PROGRESS_DIALOG.ordinal()) {
			ProgressDialog dialog = new ProgressDialog(activity);
			dialog.setMessage(activity.getString(R.string.progressdialog_message));
			dialog.setCancelable(true);
			//dialog.setOnCancelListener(this);
			return dialog;
		}
		// if some other sort of dialog...
		throw new IllegalArgumentException("Can create only progress dialogs.");
	}
	
	/**
	 * Restore instance state
	 * @param activity The GenericActivity to restore to.
	 * @param stateToRestore The contents of the state to restore to.
	 */
	void restoreInstanceState(GenericActivity activity, Bundle stateToRestore) {
        //restore alertDialogDisplayed data
		activity.alertDialogDisplayed = stateToRestore.getBoolean("alertDialogDisplayed");
		activity.alertDialogMessage = stateToRestore.getString("alertDialogMessage");
		activity.killActivityOnError = stateToRestore.getBoolean("killActivityOnError");
		activity.progressDialogDisplayed = stateToRestore.getBoolean("progressDialogDisplayed");
        
		// restore alert dialog box if any.
		if (activity.alertDialogDisplayed) {
			activity.alertDialogBox.setMessage(activity.alertDialogMessage);
			activity.alertDialogBox.show();
		}	
	}
	
	/**
	 * Overloaded: Restore instance state
	 * @param activity The GenericListActivity to restore to.
	 * @param stateToRestore The contents of the state to restore to.
	 */
	void restoreInstanceState(GenericListActivity activity, Bundle stateToRestore) {
        //restore alertDialogDisplayed data
		activity.alertDialogDisplayed = stateToRestore.getBoolean("alertDialogDisplayed");
		activity.alertDialogMessage = stateToRestore.getString("alertDialogMessage");
		activity.killActivityOnError = stateToRestore.getBoolean("killActivityOnError");
		activity.progressDialogDisplayed = stateToRestore.getBoolean("progressDialogDisplayed");
        
		// restore alert dialog box if any.
		if (activity.alertDialogDisplayed) {
			activity.alertDialogBox.setMessage(activity.alertDialogMessage);
			activity.alertDialogBox.show();
		}	
	}

	/**
	 * Save instance state.
	 * @param activity The GenericActivity to save from.
	 * @param saveState The Bundle to save to.
	 */
	void saveInstanceState(GenericActivity activity, Bundle saveState) {
		saveState.putBoolean("alertDialogDisplayed", activity.alertDialogDisplayed);
		saveState.putBoolean("killActivityOnError", activity.killActivityOnError);
		saveState.putString("alertDialogMessage", activity.alertDialogMessage);
		saveState.putBoolean("progressDialogDisplayed", activity.progressDialogDisplayed);
	}
	
	/**
	 * Overloaded: Save instance state.
	 * @param activity The GenericListActivity to save from.
	 * @param saveState The Bundle to save to.
	 */
	void saveInstanceState(GenericListActivity activity, Bundle saveState) {
		saveState.putBoolean("alertDialogDisplayed", activity.alertDialogDisplayed);
		saveState.putBoolean("killActivityOnError", activity.killActivityOnError);
		saveState.putString("alertDialogMessage", activity.alertDialogMessage);
		saveState.putBoolean("progressDialogDisplayed", activity.progressDialogDisplayed);
	}
	
	/**
	 * Return if alert Dialog box in activity is displayed. Delegated for future-proofing
	 * @param activity The GenericActivity to check.
	 * @return {@link activity.alertDialogDisplayed}
	 */ 
	boolean isAlertDialogDisplayed(GenericActivity activity) {
		return activity.alertDialogDisplayed;
	}
	
	/**
	 * Overloaded: Return if alert Dialog box in activity is displayed. Delegated for future-proofing
	 * @param activity The GenericListAactivity to check.
	 * @return {@link activity.alertDialogDisplayed}
	 */ 
	boolean isAlertDialogDisplayed(GenericListActivity activity) {
		return activity.alertDialogDisplayed;
	}
}
