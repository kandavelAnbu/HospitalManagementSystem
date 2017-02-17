package com.objectfrontier.training.habs.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.objectfrontier.training.habs.api.AppErrorCode;
import com.objectfrontier.training.habs.api.AppException;
import com.objectfrontier.training.habs.api.Doctor;

public class DoctorService {

	private int rowsAffected;
	public long create(Doctor dr,String hospitalname) {

		String sql1 = "select id from hospital where name = ? ";
		final String sql = "INSERT INTO doctor (name,specialization,department,H_id) VALUES (?,?,?,?)";
		validate(dr);
		Connection con = ConnectionService.getConnection();
		try {

			PreparedStatement ps1 = con.prepareStatement(sql1);
			ps1.setString(1, hospitalname);
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();
			long hid = rs1.getLong("id");

			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, dr.name);
			ps.setString(2, dr.specialization);
			ps.setString(3, dr.department);
			ps.setLong(4, hid);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			long id = rs.getLong(1);
			con.commit();
			return id;
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public Doctor readOne(long id) {

		final String sql = new StringBuilder().
				append("SELECT name,specialization,department,H_id FROM doctor WHERE id = ?").
				toString();

		Connection con = ConnectionService.getConnection();
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				throw new AppException(AppErrorCode.DOCTOR_NOT_FOUND);
			}
			Doctor dr = new Doctor();
			dr.id 			  = rs.getLong("id"); 
			dr.name           = rs.getString("name");
			dr.specialization = rs.getString("specialization");
			dr.department     = rs.getString("department");
			dr.hid 			  = rs.getLong("h_id");
			return dr;
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public ArrayList<Doctor> readAll() {
		final String sql = new StringBuilder().
				append("SELECT id,name,specialization,department,H_id FROM doctor").
				toString();

		Connection con = ConnectionService.getConnection();
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ArrayList<Doctor> al = new ArrayList<Doctor>();
			while (rs.next()) {
				Doctor dr = new Doctor();
				dr.id 				= rs.getLong(1);
				dr.name 			= rs.getString("name");
				dr.specialization 	= rs.getString("specialization");
				dr.department 		= rs.getString("department");
				dr.hid				= rs.getLong("H_id");
				al.add(dr);
			} 
			return al;
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public int update ( Doctor dr,String specialization) {
		final String sql = new StringBuilder().
				append("UPDATE doctor SET specialization = ? WHERE id = ? ").
				toString();

		Connection con = ConnectionService.getConnection();
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dr.specialization);
			ps.setLong(2, dr.id);
			int rowsAffected = ps.executeUpdate();
			if(rowsAffected != 1) {
				throw new AppException(AppErrorCode.DOCTOR_SPECIALIZATON_NOTFOUND);
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
				append("DELETE FROM `doctor` WHERE  `id`= ? ").
				toString();

		Connection con = ConnectionService.getConnection();
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 1) {
				throw new AppException(AppErrorCode.DOCTOR_NOT_FOUND);
			}
			con.commit();
			return rowsAffected;
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public void validate (Doctor dr) {

		AppErrorCode errorCode = null;
		if(dr.name == null)           { errorCode = AppErrorCode.DOCTOR_NAME_NULL; }
		if(dr.specialization == null) { errorCode = AppErrorCode.DOCTOR_SPECIALIZATON_NULL; }
		if(dr.department == null)     { errorCode = AppErrorCode.DOCTOR_DEPARTMENT_NULL; }
		if(errorCode != null){
			throw new AppException(errorCode);
		}
	}
}
