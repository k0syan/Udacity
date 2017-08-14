package com.example.hydrationreminder.sync;

import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;

import android.content.Context;
import android.os.AsyncTask;

public class WaterReminderFirebaseJobService extends JobService {

	private AsyncTask mBackgroundTask;

	@Override
	public boolean onStartJob(final JobParameters jobParameters) {
		mBackgroundTask = new AsyncTask() {
			@Override
			protected Object doInBackground(Object[] objects) {

				Context context = WaterReminderFirebaseJobService.this;
				ReminderTasks.executeTask(context, ReminderTasks.ACTION_CHARGING_REMINDER);

				return null;
			}

			@Override
			protected void onPostExecute(Object o) {
				jobFinished(jobParameters, false);
			}
		};

		mBackgroundTask.execute();
		return true;
	}

	@Override
	public boolean onStopJob(JobParameters jobParameters) {
		if (mBackgroundTask != null) {
			mBackgroundTask.cancel(true);
		}
		return true;
	}
}