package com.objectfrontier.training.habs.test;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
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
import com.objectfrontier.training.habs.api.Doctor;
import com.objectfrontier.training.habs.service.DoctorService;

public class TestDoctorService {

	@Test(dataProvider = "dpCreate")
	public void testCreate(String dr) throws Exception {

		HttpClient httpClient = new HttpClient();
		httpClient.start();

		try {
			
			ContentResponse response = httpClient.newRequest("http://localhost:8080/habs/doctor").	
					method(HttpMethod.PUT).
					content(new StringContentProvider(dr),("application/json")).
					send();
			System.out.println(response);
			System.out.println(response.getContentAsString());
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	@Test(dataProvider = "dpreadOne") 
	public void testreadOne(String doctor) throws Exception {
		HttpClient httpClient = new HttpClient(); 
		httpClient.start();

		try {

			ContentResponse response = httpClient.newRequest("http://localhost:8080/habs/doctor").
					method(HttpMethod.GET).
					content(new StringContentProvider(doctor),"application/json").
					send();
			System.out.println(response);
			System.out.println(response.getContentAsString());
			Assert.assertEquals(response.getStatus(),200);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}


	@Test 
	public void testReadAll() {

		DoctorService ds = new DoctorService();
		ArrayList<Doctor> actual = ds.readAll();
		ArrayList<Doctor> expected = ds.readAll();
		Assert.assertEquals(actual, expected);
	}

	@Test(dataProvider = "dpUpdate")
	public void testUpdate(String dr) throws Exception {

		HttpClient httpClient = new HttpClient();
		httpClient.start(); 

		try {
			
			ContentResponse response = httpClient.newRequest("http://localhost:8080/habs/doctor").
					method(HttpMethod.POST).
					content(new StringContentProvider(dr),("application/json")).
					send();
			System.out.println(response.getStatus());
			System.out.println(response.getContentAsString());
			Assert.assertEquals(response.getStatus(),200);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}


	@Test(dataProvider = "dpDelete") 
	public void testDelete(String doctor) throws Exception {
		HttpClient httpClient = new HttpClient(); 
		httpClient.start();

		try {

			ContentResponse response = httpClient.newRequest("http://localhost:8080/habs/doctor").
					method(HttpMethod.DELETE).
					content(new StringContentProvider(doctor),"application/json").
					send();
			System.out.println(response);
			System.out.println(response.getContentAsString());
			Assert.assertEquals(response.getStatus(),200);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	@DataProvider
	public Object[][] dpDelete() {
		return new Object[][] {
			new Object[] { "{\"id\":54 }"},
			new Object[] { "{\"id\":55 }"}
		};
	}
	
	@DataProvider
	public Object[][] dpUpdate() {
		return new Object[][] {
			new Object[] { "{\"id\":72,\"specialization\":\"teeth\"}"}
		};
	}
	
	@DataProvider
	public Object[][] dpreadOne() {
		return new Object[][] {
			new Object[] { "{\"id\":81 }"},
			new Object[] { "{\"id\":82 }"}
		};
	}
	
	@DataProvider 
	public Object[][] dpCreate() {
		return new Object[][] {
			new Object[] { "{ \"name\":\"Mani\",\"specialization\":\"cancer\",\"department\":\"haematological\",\"name1\":\"MGH\" }"}
		};
	}
}

//@Test(dataProvider = "dpCreate")
//	public void testCreate(Doctor dr,String name) {
//
//		try {
//			DoctorService ds = new DoctorService();
//			long id = ds.create(dr, name);
//			Doctor expected = ds.readOne(id);
//			Assert.assertEquals(dr.name, expected.name, "Doctor.name not equals");
//			Assert.assertEquals(dr.specialization, expected.specialization, "specialization name not equals");
//			Assert.assertEquals(dr.department, expected.department, "department name not equals");
//		} catch (Exception e) {
//			throw new AppException(e);
//		}
//	}

//	@Test(dataProvider = "dpDelete")
//	public void testDelete(long id) {
//
//		DoctorService ds = new DoctorService();
//		ds.delete(id);
//	}

//	@DataProvider
//	public Object[][] dpDelete() {
//		return new Object[][] {
//			new Object[] { 22 }
//		};
//	}

//
//	@Test(dataProvider = "dpUpdate")
//	public void testUpdate(long id,String specialization) {
//		try {
//			DoctorService ds = new DoctorService();
//			ds.update(id, specialization);
//			Doctor expected = ds.readOne(id);
//			Assert.assertEquals(specialization, expected.specialization, "Doctor.specialization name not equals");
//		} catch (Exception e) {
//			throw new AppException(e);
//		}
//	}
//	@DataProvider 
//	public Object[][] dpUpdate() {
//		return new Object[][] {
//			new Object[] {30 ,("lungs") },
//		};
//	}



















