package com.example.hydrationreminder.sync;


public class ReminderUtilities {
	// TODO (15) Create three constants and one variable:
	//  - REMINDER_INTERVAL_SECONDS should be an integer constant storing the number of seconds in 15 minutes
	//  - SYNC_FLEXTIME_SECONDS should also be an integer constant storing the number of seconds in 15 minutes
	//  - REMINDER_JOB_TAG should be a String constant, storing something like "hydration_reminder_tag"
	//  - sInitialized should be a private static boolean variable which will store whether the job
	//    has been activated or not

	// TODO (16) Create a synchronized, public static method called scheduleChargingReminder that takes
	// in a context. This method will use FirebaseJobDispatcher to schedule a job that repeats roughly
	// every REMINDER_INTERVAL_SECONDS when the phone is charging. It will trigger WaterReminderFirebaseJobService
	// Checkout https://github.com/firebase/firebase-jobdispatcher-android for an example
	// TODO (17) If the job has already been initialized, return
	// TODO (18) Create a new GooglePlayDriver
	// TODO (19) Create a new FirebaseJobDispatcher with the driver
	// TODO (20) Use FirebaseJobDispatcher's newJobBuilder method to build a job which:
	// - has WaterReminderFirebaseJobService as it's service
	// - has the tag REMINDER_JOB_TAG
	// - only triggers if the device is charging
	// - has the lifetime of the job as forever
	// - has the job recur
	// - occurs every 15 minutes with a window of 15 minutes. You can do this using a
	//   setTrigger, passing in a Trigger.executionWindow
	// - replaces the current job if it's already running
	// Finally, you should build the job.
	// TODO (21) Use dispatcher's schedule method to schedule the job
	// TODO (22) Set sInitialized to true to mark that we're done setting up the job
}