package com.j256.simplejmx.server;

import com.j256.simplejmx.common.JmxAttributeField;
import com.j256.simplejmx.common.JmxAttributeMethod;
import com.j256.simplejmx.common.JmxOperation;
import com.j256.simplejmx.common.JmxResource;

/**
 * Here's a little example program that was written to show off the basic features of SimpleJmx.
 * 
 * <p>
 * <b>NOTE:</b> This is posted on the http://256.com/sources/simplejmx/ website.
 * </p>
 * 
 * @author graywatson
 */
public class ExampleTestProgram {

	private static final int JMX_PORT = 8000;

	public static void main(String[] args) throws Exception {
		new ExampleTestProgram().doMain(args);
	}

	private void doMain(String[] args) throws Exception {

		// create the object we will be exposing with JMX
		RuntimeCounter counter = new RuntimeCounter();

		// create a new JMX server listening on a port
		JmxServer jmxServer = new JmxServer(JMX_PORT);
		try {
			// start our server
			jmxServer.start();

			// register our object
			jmxServer.register(counter);
			// we can register other objects here
			// jmxServer.register(someOtherObject);

			// do your other code here...
			// we just sleep forever to let the server do its stuff
			System.out.println("Sleeping for a while to let the server do its stuff");
			Thread.sleep(1000000000);

		} finally {
			// unregister is not necessary if we are stopping the server
			jmxServer.unregister(counter);
			// stop our server
			jmxServer.stop();
		}
	}

	/**
	 * Here is our little bean that we are exposing via JMX. It can be in another class. It's just an inner class here
	 * for convenience.
	 */
	@JmxResource(description = "Runtime counter", domainName = "j256.simplejmx", beanName = "RuntimeCounter")
	public static class RuntimeCounter {

		// start our timer
		private long startMillis = System.currentTimeMillis();

		// we can annotate fields directly to be published in JMX, isReadible defaults to true
		@JmxAttributeField(description = "Show runtime in seconds", isWritable = true)
		private boolean showSeconds;

		// we can annotate getter methods
		@JmxAttributeMethod(description = "Run time in seconds or milliseconds")
		public long getRunTime() {
			// show how long we are running
			long diffMillis = System.currentTimeMillis() - startMillis;
			if (showSeconds) {
				// as seconds
				return diffMillis / 1000;
			} else {
				// or as milliseconds
				return diffMillis;
			}
		}

		/*
		 * NOTE: there is no setRunTime(...) so it won't be writable.
		 */

		// this is an operation that shows up in the operations tab in jconsole.
		@JmxOperation(description = "Reset our start time to the current millis")
		public String resetStartTime() {
			startMillis = System.currentTimeMillis();
			return "Timer has been reset to current millis";
		}
	}
}
