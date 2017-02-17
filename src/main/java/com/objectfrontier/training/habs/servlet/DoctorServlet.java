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
import com.objectfrontier.training.habs.service.DoctorService;

public class DoctorServlet extends HttpServlet {

	private static DoctorService ds = new DoctorService();
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		try {
			
			String DoctorId = req.getParameter("req");
		
			if (DoctorId == null) {
				ArrayList<Doctor> al = ds.readAll();
				String readstring = JsonUtil.toJson(al);
				res.setContentType("application/json");
				PrintWriter pw = res.getWriter();
				pw.write(readstring);
				pw.close();
			} else {
			
				Doctor doctor = ds.readOne(Long.parseLong(DoctorId));
				String doctorstring = JsonUtil.toJson(doctor);
				res.setContentType("application/json");
				PrintWriter pw = res.getWriter();
				pw.write(doctorstring);
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
			Doctor dr = JsonUtil.fromJson(reqJson.toString(), Doctor.class);
			long id = ds.create(dr,dr.name1);
			res.setContentType("application/json");
			PrintWriter pw = res.getWriter();
			pw.println(id);
		}  catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	public void doPost (HttpServletRequest req, HttpServletResponse res ) {
		try {
			
			StringBuffer reqJson = new StringBuffer();
			String line = null;
			BufferedReader reader = req.getReader();
			while((line = reader.readLine()) != null)
				reqJson.append(line);
			Doctor dr = JsonUtil.fromJson(reqJson.toString(), Doctor.class);
			int rowsAffected = ds.update(dr,dr.specialization);
			res.setContentType("appliactaion/json");
			PrintWriter pw = res.getWriter();
			pw.println(dr);
			pw.println("rowsAffected" + rowsAffected);
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
			Doctor dr = JsonUtil.fromJson(reqJson.toString(), Doctor.class);
			int rowsAffected = ds.delete(dr.id);
			res.setContentType("application/json");
			PrintWriter pw = res.getWriter();
			pw.write("rowsAffected" + rowsAffected);
			pw.close();
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
}
	
