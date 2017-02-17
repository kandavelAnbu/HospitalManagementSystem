package com.objectfrontier.training.habs.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.objectfrontier.training.habs.api.AppErrorCode;
import com.objectfrontier.training.habs.api.AppException;
import com.objectfrontier.training.habs.api.Patient;

public class PatientService {
	
	private int rowsAffected;

	public long hospital(String name) {
		
		String sql = ("SELECT id FROM hospital WHERE name = ? ");
		Connection con = ConnectionService.getConnection();
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			rs.next();
			long hid = rs.getLong("id");
			return hid;
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	public long doctor(String name1) {
		
		String sql1 = ("SELECT id FROM doctor WHERE name = ? ");
		Connection con = ConnectionService.getConnection();
		try {
			
			PreparedStatement ps1 = con.prepareStatement(sql1);
			ps1.setString(1, name1);
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();
			long did = rs1.getLong("id");
			return did;
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public long create(Patient pt, String name, String name1) {

		final String sq2 = "INSERT INTO patient (name,age,gender,disease,D_id,H_id) VALUES (?,?,?,?,?,?)";

		validate(pt);
		Connection con = ConnectionService.getConnection();
		try {
			
			long H_id = hospital(name);
			long D_id = doctor(name1);
			PreparedStatement ps2 = con.prepareStatement(sq2, Statement.RETURN_GENERATED_KEYS);
			ps2.setString(1, pt.name);
			ps2.setInt(2, pt.age);
			ps2.setString(3, pt.gender);
			ps2.setString(4, pt.disease);
			ps2.setLong(5, D_id);
			ps2.setLong(6, H_id);
			
			ps2.executeUpdate();
			con.commit();
			ResultSet rs2 = ps2.getGeneratedKeys();
			rs2.next();
			long id = rs2.getLong(1);
			
			return id;
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public Patient readOne(long id) {

		final String sql = new StringBuilder().
				append("SELECT name,age,gender,disease,D_id,H_id FROM patient WHERE id = ?").
				toString();

		Connection con = ConnectionService.getConnection();
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				throw new AppException(AppErrorCode.PATIENT_NOT_FOUND);
			}
			Patient pt = new Patient();
			pt.id      = rs.getLong("id");
			pt.name    = rs.getString("name");
			pt.age	   = rs.getInt("age");
			pt.gender  = rs.getString("gender");
			pt.disease = rs.getString("disease");
			pt.did     = rs.getLong("D_id");
			pt.hid     = rs.getLong("H_id");
			return pt;
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

		public ArrayList<Patient> readAll() {
			final String sql = new StringBuilder().
					append("SELECT id,name,age,gender,disease,d_id,H_id FROM patient").
					toString();
	
			Connection con = ConnectionService.getConnection();
			try {
				
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				ArrayList<Patient> al = new ArrayList<Patient>();
				while (rs.next()) { 
					Patient pt = new Patient();
					pt.id       = rs.getLong("id");
					pt.name		= rs.getString("name");
					pt.age   	= rs.getInt("age");
					pt.gender 	= rs.getString("gender");
					pt.disease 	= rs.getString("disease");
					pt.did		= rs.getLong("D_id");
					pt.hid		= rs.getLong("H_id");
					al.add(pt);
				} 
				return al;
			} catch (AppException e) {
				throw e;
			} catch (Exception e) {
				throw new AppException(e);
			}
		}
	
	public int update (Patient pt, String disease) {
		final String sql = new StringBuilder().
				append("UPDATE patient SET disease = ? WHERE id = ?; ").
				toString();
	
		Connection con = ConnectionService.getConnection();
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, pt.disease);
			ps.setLong(2, pt.id);
			int rowsAffected = ps.executeUpdate();
			if(rowsAffected != 1) {
				throw new AppException(AppErrorCode.PATIENT_DISEASE_NOTFOUND);
			}
			con.commit();
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(e);
		}
		return rowsAffected;
	}
	
	public int delete (long id) {
	
		final String sql = new StringBuilder().
				append("DELETE FROM patient WHERE id = ?  ").
				toString();
	
		Connection con = ConnectionService.getConnection();
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 1) {
				throw new AppException(AppErrorCode.PATIENT_NOT_FOUND);
			}
			con.commit();
			return rowsAffected;
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public void validate (Patient pt) {

		AppErrorCode errorCode = null;
		if(pt.name == null)           { errorCode = AppErrorCode.PATIENT_NAME_NULL; }
		if(pt.age <= 0) 			  { errorCode = AppErrorCode.PATIENT_AGE_ZERO_OR_NEGATIVE; }
		if(pt.gender == null)     	  { errorCode = AppErrorCode.PATIENT_GENDER_NULL; }
		if(pt.disease == null)     	  { errorCode = AppErrorCode.PATIENT_DISEASE_NULL; }
		if(errorCode != null){
			throw new AppException(errorCode);
		}
	}
}
