/**
 *  This file is part of ElasticDroid.
 *
 * ElasticDroid is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * ElasticDroid is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with ElasticDroid.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Authored by Siddhu Warrier on 27 May 2011
 */

package org.elasticdroid.task;

import java.security.InvalidParameterException;

import org.elasticdroid.intf.callback.GenericCallbackIntf;
import org.elasticdroid.model.LoginReturnModel;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Generic Task.
 * 
 * @author siddhuwarrier
 * 
 * @param <T>Parameter passed to doInBackground
 * @param <U>Parameter passed to the calling thread to indicate progress
 * @param <V>Return value from the model
 * @param <W>The callback type
 */
public abstract class GenericTask<T, U, V, W extends GenericCallbackIntf> extends AsyncTask<T, U, V> {

    /** The context that called this model */
    private Context context;

    /** The callback class */
    protected W callback;

    /**
     * Constructor. Saves the activity that called this. This is used to return
     * the data back to the Activity.
     * 
     * @param activity
     *            The Android UI activity that created the GenericModel.
     */
    public GenericTask(Context context, W callback) {
        this.context = context;
        if (context == null) {
            throw new InvalidParameterException("Context cannot be null");
        }
        this.callback = callback;
    }

    /**
     * Called in *UI Thread* before doInBackground executes in a separate
     * thread.
     */
    @Override
    protected void onPreExecute() {
    }

    /**
     * Set the callback object referred to by the model. This is used by the
     * activity to reset itself to null when it is being destroyed temporarily
     * (for instance whenever the screen orientation is changed), and to reset
     * it whenever the object is restored after being destroyed.
     * 
     * @param callback
     *            the GenericCallback referred to in the Model
     */
    public void setCallback(W callback) {
        this.callback = callback;
    }

    /**
     * Returns the current context
     * 
     * @return
     */
    public Context getCurrentContext() {
        return context;
    }
    
	@Override
	protected void onPostExecute(V ignore) {
		if (callback != null) {
			callback.cleanUpAfterTaskExecution();
		}
	}
}
