package com.objectfrontier.training.habs.test;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.habs.api.AppException;
import com.objectfrontier.training.habs.api.Hospital;
import com.objectfrontier.training.habs.service.HospitalService;

public class TestHospitalService {

	private String name;

	@Test(dataProvider = "dpCreate")
	public void testCreate(String hospital) throws Exception {

		HttpClient httpClient = new HttpClient();
		httpClient.start();

		try {
			
			ContentResponse response = httpClient.newRequest("http://localhost:8080/habs/hospital").
					method(HttpMethod.PUT).
					content(new StringContentProvider(hospital),("application/json")).
					send();
			System.out.println(response);
			System.out.println(response.getContentAsString());
			Assert.assertEquals(response.getStatus(), 200);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	@Test(dataProvider = "dpreadOne") 
	public void testreadOne(String hospital) throws Exception {
		HttpClient httpClient = new HttpClient(); 
		httpClient.start();

		try {

			ContentResponse response = httpClient.newRequest("http://localhost:8080/habs/hospital").
					method(HttpMethod.GET).
					content(new StringContentProvider(hospital),"application/json").
					send();
			System.out.println(response);
			System.out.println(response.getContentAsString());
			Assert.assertEquals(response.getStatus(), 200);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	@Test(dataProvider = "dpUpdate")
	public void testUpdate(String hospital) throws Exception {

		HttpClient httpClient = new HttpClient();
		httpClient.start();

		try {

			ContentResponse response = httpClient.newRequest("http://localhost:8080/habs/hospital").
					method(HttpMethod.POST).
					content(new StringContentProvider(hospital),"application/json").
					send();
			System.out.println(response);
			System.out.println(response.getContentAsString());
			Assert.assertEquals(response.getStatus(), 200);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}


	@Test(dataProvider = "dpDelete") 
	public void testDelete(String hospital) throws Exception {
		HttpClient httpClient = new HttpClient(); 
		httpClient.start();

		try {

			ContentResponse response = httpClient.newRequest("http://localhost:8080/habs/hospital").
					method(HttpMethod.DELETE).
					content(new StringContentProvider(hospital),"application/json").
					send();
			System.out.println(response);
			System.out.println(response.getContentAsString());
			Assert.assertEquals(response.getStatus(), 200);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	@DataProvider
	public Object[][] dpCreate() {
		return new Object[][] {
			new Object[] { "{ \"name\":\"RGCET\" }"}
		};
	}

	@DataProvider
	public Object[][] dpreadOne() {
		return new Object[][] {
			new Object[] { "{\"id\":100 }"},
			new Object[] { "{\"id\":101 }"}
		};
	}

	@DataProvider
	public Object[][] dpUpdate() {
		return new Object[][] {
			new Object[] { "{\"id\":87, \"name\":\"KGH\"}"} 
		};
	}

	@DataProvider
	public Object[][] dpDelete() {
		return new Object[][] {
			new Object[] { "{\"id\":71 }"},
			new Object[] { "{\"id\":72 }"}
		};
	}
}
//@Test(dataProvider = "dpCreate")
//	public void testCreate(Hospital hp) {
//
//		try {
//			HospitalService hs = new HospitalService();
//			long id = hs.create(hp);
//			Hospital expected = hs.readOne(id);
//			Assert.assertEquals(hp.name, expected.name, "Hospital name not equals");
//		} catch (Exception e) {
//			throw new AppException(e);
//		}
//	}

//	@Test 
//	public void testReadAll() {
//
//		HospitalService hs = new HospitalService();
//		ArrayList<Hospital> actual = hs.readAll();
//		ArrayList<Hospital> expected = hs.readAll();
//		Assert.assertEquals(actual, expected);
//	}

//	@Test(dataProvider = "dpUpdate")
//	public void testUpdate(long id,Hospital hp) {
//		try {
//			HospitalService hs = new HospitalService();
//			hs.update(id, hp);
//			Hospital expected = hs.readOne(id);
//			Assert.assertEquals(name, expected.name, "Hospital name not equals");
//		} catch (Exception e) {
//			throw new AppException(e);
//		}
//	}
//	@Test(dataProvider = "dpDelete")
//	public void testDelete(long id) { 

//	@DataProvider
//	public Object[][] dpDelete() {
//		return new Object[][] {
//			new Object[] { 10 }
//		};
//	}

//	@DataProvider 
//	public Object[][] dpUpdate() {
//		return new Object[][] {
//			new Object[] {10 ,("GH") },
//			new Object[] {15 ,("Anbu") }
//		};
//	}

//	@DataProvider 
//	public Object[][] dpCreate() {
//		return new Object[][] {
//			new Object[] { new Hospital("Anbu") }
//		};
//	}
