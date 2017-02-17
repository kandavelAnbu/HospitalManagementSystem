package com.objectfrontier.training.habs.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.objectfrontier.training.habs.api.AppException;
import com.objectfrontier.training.habs.api.Hospital;
import com.objectfrontier.training.habs.api.JsonUtil;
import com.objectfrontier.training.habs.service.HospitalService;

public class HospitalServlet extends HttpServlet {
	
	public static HospitalService hs = new HospitalService(); 

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		try {
			
			String HospitalId = req.getParameter("id");
			
			if (HospitalId == null) {
				
				ArrayList<Hospital> al = hs.readAll();
				String readstring = JsonUtil.toJson(al);
				res.setContentType("application/json");
				PrintWriter pw = res.getWriter();
				pw.print(readstring);
				pw.close();
	 		} else  {
			
				Hospital hospital = hs.readOne(Long.parseLong(HospitalId));
				String hospitalstring = JsonUtil.toJson(hospital);
				res.setContentType("application/json");
				PrintWriter pw = res.getWriter();
				pw.print(hospitalstring);
				pw.close();
	 		} 
		} catch (Exception e) {
	 		throw new AppException(e);
	 	}
	}
	
	public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		try {
		
			StringBuffer reqJson = new StringBuffer();
			String line = null;
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null)
				reqJson.append(line);
			Hospital hp = JsonUtil.fromJson(reqJson.toString(), Hospital.class);
			long id = hs.create(hp);
			res.setContentType("application/json");
			PrintWriter pw = res.getWriter();
			pw.println(id);
			pw.close();
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	public void doPost (HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		try {
			
			StringBuffer reqJson = new StringBuffer();
			String line = null;
			BufferedReader reader = req.getReader();
			while((line = reader.readLine()) != null)
				reqJson.append(line);
			Hospital hp = JsonUtil.fromJson(reqJson.toString(), Hospital.class);
			int rowsAffected = hs.update(hp.id, hp);
			res.setContentType("application/json");
			PrintWriter pw = res.getWriter();
			pw.write("rowsAffected" + rowsAffected);
//			pw.write(hp.name);
			pw.close();
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
			Hospital hp = JsonUtil.fromJson(reqJson.toString(), Hospital.class);
			int rowsAffected = hs.delete(hp.id);
			res.setContentType("application/json");
			PrintWriter pw = res.getWriter();
			pw.write("rowsAffected" + rowsAffected);
			pw.close();
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
}
