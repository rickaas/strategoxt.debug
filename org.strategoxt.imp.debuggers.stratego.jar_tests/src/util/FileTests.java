package util;

import java.io.IOException;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;

import org.junit.Assert;
import org.junit.Test;

public class FileTests {

	@Test
	public void testJarFile() {
		// loading StrTestConstants.STRATEGOSUGAR_JAR
		// throws a java.util.zip.ZipException: error in opening zip file
		JarFile jar = null;
		try {
			jar = new JarFile(StrTestConstants.STRATEGOSUGAR_JAR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String name = jar.getName();
		System.out.println("NAME: " + name);
	}
	
	@Test
	public void testZipFile1() {
		try {
			System.out.println("N:"+StrTestConstants.STRATEGOSUGAR_JAR);
			ZipFile zip = new ZipFile(StrTestConstants.STRATEGOSUGAR_JAR);
			System.out.println(zip.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testZipFile2() {
		try {
			ZipFile zip = new ZipFile(StrTestConstants.DSLDI_JAVA_JAR);
			System.out.println(zip.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	
	@Test
	public void testZipFile5() {
		try {
			ZipFile zip = new ZipFile("lib/stratego/stratego_sugar.jar");
			System.out.println(zip.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}

}
