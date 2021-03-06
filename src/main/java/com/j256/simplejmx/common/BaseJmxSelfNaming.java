package com.j256.simplejmx.common;

/**
 * Base class which has default implementations of all of the {@link JmxSelfNaming} methods.
 * 
 * @author graywatson
 */
public class BaseJmxSelfNaming implements JmxSelfNaming {

	public String getJmxDomainName() {
		return null;
	}

	public String getJmxNameOfObject() {
		return null;
	}

	public JmxFolderName[] getJmxFolderNames() {
		return null;
	}
}
