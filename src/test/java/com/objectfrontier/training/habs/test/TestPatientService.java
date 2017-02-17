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
import com.objectfrontier.training.habs.api.Patient;
import com.objectfrontier.training.habs.service.PatientService;

public class TestPatientService {

	@Test(dataProvider = "dpCreate")
	public void testCreate(String pt) throws Exception {

		HttpClient httpClient = new HttpClient();
		httpClient.start();

		try {
			
			ContentResponse response = httpClient.newRequest("http://localhost:8080/habs/patient").	
					method(HttpMethod.PUT).
					content(new StringContentProvider(pt),("application/json")).
					send();
			System.out.println(response);
			System.out.println(response.getContentAsString());
			Assert.assertEquals(response.getStatus(), 200);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	@Test(dataProvider = "dpreadOne") 
	public void testreadOne(String patient) throws Exception {
		HttpClient httpClient = new HttpClient(); 
		httpClient.start();

		try {

			ContentResponse response = httpClient.newRequest("http://localhost:8080/habs/patient").
					method(HttpMethod.DELETE).
					content(new StringContentProvider(patient),"application/json").
					send();
			System.out.println(response);
			System.out.println(response.getContentAsString());
			Assert.assertEquals(response.getStatus(), 200);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	@Test(dataProvider = "dpUpdate")
	public void testUpdate(String pt) throws Exception {

		HttpClient httpClient = new HttpClient();
		httpClient.start(); 

		try {
			
			ContentResponse response = httpClient.newRequest("http://localhost:8080/habs/patient").
					method(HttpMethod.POST).
					content(new StringContentProvider(pt),("application/json")).
					send();
			System.out.println(response);
			System.out.println(response.getContentAsString());
			Assert.assertEquals(response.getStatus(), 200);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}


	@Test(dataProvider = "dpDelete") 
	public void testDelete(String patient) throws Exception {
		HttpClient httpClient = new HttpClient(); 
		httpClient.start();

		try {

			ContentResponse response = httpClient.newRequest("http://localhost:8080/habs/patient").
					method(HttpMethod.DELETE).
					content(new StringContentProvider(patient),"application/json").
					send();
			System.out.println(response);
			System.out.println(response.getContentAsString());
			Assert.assertEquals(response.getStatus(), 200);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	@DataProvider
	public Object[][] dpDelete() {
		return new Object[][] {
			new Object[] { "{\"id\":15 }"},
			new Object[] { "{\"id\":16 }"}
		};
	}
	
	@DataProvider
	public Object[][] dpUpdate() {
		return new Object[][] {
			new Object[] { "{\"id\":34,\"disease\":\"eyes\"}" }
		};
	}

	@DataProvider
	public Object[][] dpreadOne() {
		return new Object[][] {
			new Object[] { "{\"id\":42 }"},
			new Object[] { "{\"id\":43 }"}
		};
	}
	
	@DataProvider 
	public Object[][] dpCreate() {
		return new Object[][] {
			new Object[] { "{ \"name\":\"karthi\",\"age\": 33,\"gender\":\"male\",\"disease\":\"Brain\",\"name1\":\"MGH\",\"name2\":\"Mani\" }"}
		};
	}
}

//@Test(dataProvider = "dpCreate")
//public void testCreate(Patient pt,String name, String name1) {
//
//		PatientService ps = new PatientService();
//		long id = ps.create(pt, name, name1);
//		Patient expected = ps.readOne(id);
////		Assert.assertEquals(ps.name, expected.name, "Patient.name not equals");
////		Assert.assertEquals(ps.age, expected.age, "Patient.age not equals");
////		Assert.assertEquals(ps.gender, expected.gender, "Patient.gender name not equals");
////		Assert.assertEquals(ps.disease, expected.disease, "Patient.disease name not equals");
//}
//
//@Test 
//public void testReadAll() {
//
//	PatientService pt = new PatientService();
//	ArrayList<Patient> actual = pt.readAll();
//	ArrayList<Patient> expected = pt.readAll();
//	Assert.assertEquals(actual, expected);
//}
//
//
//@Test(dataProvider = "dpUpdate")
//public void testUpdate(long id,String disease) {
//	try {
//		PatientService ps = new PatientService();
//		pt.update(id, disease);
//		Patient expected = pt.readOne(id);
//		Assert.assertEquals(disease, expected.disease, "Patient.disease name not equals");
//	} catch (Exception e) {
//		throw new AppException(e);
//	}
//}

//@Test(dataProvider = "dpDelete")
//public void testDelete(long id) {
//
//	PatientService pt = new PatientService();
//	pt.delete(id);
//}
//
//@DataProvider
//public Object[][] dpDelete() {
//	return new Object[][] {
//		new Object[] { 22 }
//	};
//}
//
//@DataProvider 
//public Object[][] dpUpdate() {
//	return new Object[][] {
//		new Object[] {12 ,("lungs") },
//	};
//}
//
////@DataProvider 
////public Object[][] dpCreate() {
////	return new Object[][] {
////		new Object[] { new Patient("madhan",23,"male","heart"),"Norway","vinoth" }
////	};
////}
