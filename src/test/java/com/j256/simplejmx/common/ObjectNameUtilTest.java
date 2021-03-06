package com.j256.simplejmx.common;

import static org.junit.Assert.assertEquals;

import javax.management.ObjectName;

import org.junit.Test;

public class ObjectNameUtilTest {

	private static final String DOMAIN_NAME = "foo.com";
	private static final String OBJECT_NAME = "someObj";
	private static final String FIELD_NAME1 = "a";
	private static final String FOLDER_NAME1 = "folder1";
	private static final String FIELD_NAME2 = "b";
	private static final String FOLDER_NAME2 = "folder2";

	@Test(expected = IllegalArgumentException.class)
	public void testSelfNamingNoDescription() {
		SelfNamingNoDescription obj = new SelfNamingNoDescription();
		ObjectNameUtil.makeObjectName(obj.getClass().getAnnotation(JmxResource.class), (JmxSelfNaming) obj);
	}

	@Test
	public void testSelfNamingUseJmxResourceObjectName() {
		SelfNamingUseJmxResourceObjectName obj = new SelfNamingUseJmxResourceObjectName();
		ObjectName name =
				ObjectNameUtil.makeObjectName(obj.getClass().getAnnotation(JmxResource.class), (JmxSelfNaming) obj);
		assertEquals(DOMAIN_NAME + ":name=" + OBJECT_NAME, name.toString());
	}

	@Test
	public void testUseObjectClassForName() {
		UseObjectClassForName obj = new UseObjectClassForName();
		ObjectName name = ObjectNameUtil.makeObjectName(obj.getClass().getAnnotation(JmxResource.class), obj);
		assertEquals(DOMAIN_NAME + ":name=" + obj.getClass().getSimpleName(), name.toString());
	}

	@Test
	public void testSelfNamingUseObjectClassForName() {
		SelfNamingUseObjectClassForName obj = new SelfNamingUseObjectClassForName();
		ObjectName name =
				ObjectNameUtil.makeObjectName(obj.getClass().getAnnotation(JmxResource.class), (JmxSelfNaming) obj);
		assertEquals(DOMAIN_NAME + ":name=" + obj.getClass().getSimpleName(), name.toString());
	}

	@Test
	public void testStringFolderField() {
		StringFolderField obj = new StringFolderField();
		ObjectName name = ObjectNameUtil.makeObjectName(obj.getClass().getAnnotation(JmxResource.class), obj);
		assertEquals(DOMAIN_NAME + ":" + FIELD_NAME1 + "=" + FOLDER_NAME1 + "," + FIELD_NAME2 + "=" + FOLDER_NAME2
				+ ",name=" + OBJECT_NAME, name.toString());
	}

	@Test
	public void testNoStringFolderField() {
		NoStringFolderField obj = new NoStringFolderField();
		ObjectName name = ObjectNameUtil.makeObjectName(obj.getClass().getAnnotation(JmxResource.class), obj);
		assertEquals(DOMAIN_NAME + ":00=" + FOLDER_NAME1 + ",01=" + FOLDER_NAME2 + ",name=" + OBJECT_NAME,
				name.toString());
	}

	@Test
	public void testJmxFolderNameField() {
		JmxFolderNameField obj = new JmxFolderNameField();
		ObjectName name = ObjectNameUtil.makeObjectName(obj.getClass().getAnnotation(JmxResource.class), obj);
		assertEquals(DOMAIN_NAME + ":" + FIELD_NAME1 + "=" + FOLDER_NAME1 + "," + FIELD_NAME2 + "=" + FOLDER_NAME2
				+ ",name=" + OBJECT_NAME, name.toString());
	}

	@Test
	public void testNoJmxFolderNameField() {
		NoJmxFolderNameField obj = new NoJmxFolderNameField();
		ObjectName name = ObjectNameUtil.makeObjectName(obj.getClass().getAnnotation(JmxResource.class), obj);
		assertEquals(DOMAIN_NAME + ":00=" + FOLDER_NAME1 + ",01=" + FOLDER_NAME2 + ",name=" + OBJECT_NAME,
				name.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidObjectName() {
		InvalidObjectName obj = new InvalidObjectName();
		ObjectNameUtil.makeObjectName(obj.getClass().getAnnotation(JmxResource.class), obj);
	}

	@Test
	public void testLargeNumberOfAutoGeneratedFields() {
		LargeNumberOfAutoGeneratedFields obj = new LargeNumberOfAutoGeneratedFields();
		ObjectName name = ObjectNameUtil.makeObjectName(obj.getClass().getAnnotation(JmxResource.class), obj);
		assertEquals(DOMAIN_NAME + ":00=a,01=b,02=c,03=d,04=e,05=f,06=g,07=h,08=i,09=j,10=k,name=" + OBJECT_NAME,
				name.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNoDomainInfo() {
		ObjectNameUtil.makeObjectName(null, new NoDomainInfo());
	}

	@Test
	public void testNoObjectNameInfo() {
		NoObjectNameInfo obj = new NoObjectNameInfo();
		ObjectName name = ObjectNameUtil.makeObjectName(null, obj);
		assertEquals(obj.getClass().getSimpleName(), name.getKeyProperty("name"));
	}

	@Test
	public void testFolderNamesFromAnnoationWithSelfNaming() {
		FoldersFromAnnotationWithSelfNaming obj = new FoldersFromAnnotationWithSelfNaming();
		ObjectName name = ObjectNameUtil.makeObjectName(obj);
		assertEquals(obj.getJmxNameOfObject(), name.getKeyProperty("name"));
		assertEquals(FOLDER_NAME1, name.getKeyProperty("00"));
	}

	@Test
	public void testFolderNamesFromSelfNameingWithAnnoation() {
		FoldersFromSelfNaming obj = new FoldersFromSelfNaming();
		ObjectName name = ObjectNameUtil.makeObjectName(obj);
		assertEquals(obj.getJmxNameOfObject(), name.getKeyProperty("name"));
		assertEquals(FOLDER_NAME2, name.getKeyProperty("00"));
	}

	/* ================================================================== */

	@JmxResource
	protected static class SelfNamingNoDescription implements JmxSelfNaming {
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

	@JmxResource(beanName = OBJECT_NAME)
	protected static class SelfNamingUseJmxResourceObjectName implements JmxSelfNaming {
		public String getJmxDomainName() {
			return DOMAIN_NAME;
		}
		public String getJmxNameOfObject() {
			return null;
		}
		public JmxFolderName[] getJmxFolderNames() {
			return null;
		}
	}

	@JmxResource(domainName = DOMAIN_NAME)
	protected static class UseObjectClassForName {
	}

	@JmxResource(domainName = DOMAIN_NAME)
	protected static class SelfNamingUseObjectClassForName implements JmxSelfNaming {
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

	@JmxResource(domainName = DOMAIN_NAME, beanName = OBJECT_NAME, folderNames = { FIELD_NAME1 + "=" + FOLDER_NAME1,
			FIELD_NAME2 + "=" + FOLDER_NAME2 })
	protected static class StringFolderField {
	}

	@JmxResource(domainName = DOMAIN_NAME, beanName = OBJECT_NAME, folderNames = { FOLDER_NAME1, FOLDER_NAME2 })
	protected static class NoStringFolderField {
	}

	@JmxResource(domainName = DOMAIN_NAME, beanName = OBJECT_NAME)
	protected static class JmxFolderNameField implements JmxSelfNaming {
		public String getJmxDomainName() {
			return null;
		}
		public String getJmxNameOfObject() {
			return null;
		}
		public JmxFolderName[] getJmxFolderNames() {
			return new JmxFolderName[] { new JmxFolderName(FIELD_NAME1, FOLDER_NAME1),
					new JmxFolderName(FIELD_NAME2, FOLDER_NAME2) };
		}
	}

	@JmxResource(domainName = DOMAIN_NAME, beanName = OBJECT_NAME)
	protected static class NoJmxFolderNameField implements JmxSelfNaming {
		public String getJmxDomainName() {
			return null;
		}
		public String getJmxNameOfObject() {
			return null;
		}
		public JmxFolderName[] getJmxFolderNames() {
			return new JmxFolderName[] { new JmxFolderName(FOLDER_NAME1), new JmxFolderName(FOLDER_NAME2) };
		}
	}

	@JmxResource(domainName = DOMAIN_NAME, beanName = OBJECT_NAME, folderNames = { FOLDER_NAME1 })
	protected static class FoldersFromAnnotationWithSelfNaming implements JmxSelfNaming {
		public String getJmxDomainName() {
			return null;
		}
		public String getJmxNameOfObject() {
			return "FoldersFromAnnotationWithSelfNaming";
		}
		public JmxFolderName[] getJmxFolderNames() {
			return null;
		}
	}

	@JmxResource(domainName = DOMAIN_NAME, beanName = OBJECT_NAME, folderNames = { FOLDER_NAME1 })
	protected static class FoldersFromSelfNaming implements JmxSelfNaming {
		public String getJmxDomainName() {
			return null;
		}
		public String getJmxNameOfObject() {
			return "FoldersFromSelfNaming";
		}
		// NOTE: this overrides the folders in the @JmxResource
		public JmxFolderName[] getJmxFolderNames() {
			return new JmxFolderName[] { new JmxFolderName(FOLDER_NAME2) };
		}
	}

	@JmxResource(domainName = ":::")
	protected static class InvalidObjectName {
	}

	@JmxResource(domainName = DOMAIN_NAME, beanName = OBJECT_NAME, folderNames = { "a", "b", "c", "d", "e", "f", "g",
			"h", "i", "j", "k" })
	protected static class LargeNumberOfAutoGeneratedFields {
	}

	protected static class NoDomainInfo implements JmxSelfNaming {
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

	protected static class NoObjectNameInfo implements JmxSelfNaming {
		public String getJmxDomainName() {
			return "NoObjectNameInfo";
		}
		public String getJmxNameOfObject() {
			return null;
		}
		public JmxFolderName[] getJmxFolderNames() {
			return null;
		}
	}
}
