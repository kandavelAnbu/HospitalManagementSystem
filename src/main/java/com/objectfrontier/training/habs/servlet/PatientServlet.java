package com.objectfrontier.training.habs.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.objectfrontier.training.habs.api.AppException;
import com.objectfrontier.training.habs.api.Doctor;
import com.objectfrontier.training.habs.api.Hospital;
import com.objectfrontier.training.habs.api.JsonUtil;
import com.objectfrontier.training.habs.api.Patient;
import com.objectfrontier.training.habs.service.PatientService;

public class PatientServlet extends HttpServlet {

	private static PatientService ps = new PatientService();
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
	try {
		
		String PatientId = req.getParameter("id");
		
		if (PatientId == null) {
		
			ArrayList<Patient> al = ps.readAll();
			String readstring = JsonUtil.toJson(al);
			res.setContentType("application/json");
			PrintWriter pw = res.getWriter();
			pw.write(readstring);
			pw.close();
		} else {
			
			Patient patient = ps.readOne(Long.parseLong(PatientId));
			String patientstring = JsonUtil.toJson(patient);
			res.setContentType("application/json");
			PrintWriter pw = res.getWriter();
			pw.write(patientstring);
			pw.close();
		  }
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	public void doPut (HttpServletRequest req, HttpServletResponse res) {
			
			try {
				
				StringBuffer reqJson = new StringBuffer();
				String line = null;
				BufferedReader reader = req.getReader();
				while((line = reader.readLine()) != null)
					reqJson.append(line);
				Patient pt = JsonUtil.fromJson(reqJson.toString(), Patient.class);
				long id = ps.create(pt,pt.name1,pt.name2);
				res.setContentType("application/json");
				PrintWriter pw = res.getWriter();
				pw.println(id);
			}  catch (Exception e) {
				throw new AppException(e);
			}
		}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res ) {
		try {
			
			StringBuffer reqJson = new StringBuffer();
			String line = null;
			BufferedReader reader = req.getReader();
			while((line = reader.readLine()) != null){
				reqJson.append(line);
			}
				
			Patient pt = JsonUtil.fromJson(reqJson.toString(), Patient.class);
			int rowsAffected = ps.update(pt,pt.disease);
			res.setContentType("appliactaion/json");
			PrintWriter pw = res.getWriter();
			pw.println("rowsAffected" + rowsAffected);
			pw.println(pt.id);
			pw.println(pt.disease);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	public void doDelete (HttpServletRequest req, HttpServletResponse res) {
		
		try {
			
			StringBuffer reqJson = new StringBuffer();
			String line = null;
			BufferedReader reader = req.getReader();
			while((line = reader.readLine()) != null)
				reqJson.append(line);
			Patient pt = JsonUtil.fromJson(reqJson.toString(), Patient.class);
			int rowsAffected = ps.delete(pt.id);
			res.setContentType("application/json");
			PrintWriter pw = res.getWriter();
			pw.write("rowsAffected" + rowsAffected);
			pw.close();
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
}
