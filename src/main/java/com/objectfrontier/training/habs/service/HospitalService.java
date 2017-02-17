package com.objectfrontier.training.habs.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.objectfrontier.training.habs.api.AppErrorCode;
import com.objectfrontier.training.habs.api.AppException;
import com.objectfrontier.training.habs.api.Hospital;

public class HospitalService {

	private int rowsAffected;
	
	public long create(Hospital hs) {

		final String sql = new StringBuilder().
				append("INSERT INTO hospital (name) VALUES (?) ").
				toString();

		validate(hs);
		Connection con   = ConnectionService.getConnection();
		try {

			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, hs.name);
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

	public Hospital readOne(long id) { 
		String sql = "SELECT id,name FROM hospital WHERE id = ?";

		Connection con =  ConnectionService.getConnection();
		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				throw new AppException(AppErrorCode.HOSPITAL_NOT_FOUND);
			}
			Hospital hs = new Hospital();
			hs.name = rs.getString("name");
			hs.id = rs.getLong("id");
			return hs;
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public ArrayList<Hospital> readAll() {
		final String sql = new StringBuilder().
				append("SELECT id,name FROM hospital").
				toString();

		Connection con = ConnectionService.getConnection();
		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ArrayList<Hospital> al = new ArrayList<>(); 

			while(rs.next()) {
				Hospital hs = new Hospital();
				hs.name = rs.getString("name");
				hs.id 	= rs.getLong("id");
				al.add(hs);
			} 
			return al;
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public int update(long id, Hospital hp) {
		if (hp == null) { throw new AppException(AppErrorCode.HOSPITAL_NOT_FOUND); }
		final String sql = new StringBuilder().
				append("UPDATE hospital SET  name = ? WHERE id = ? ").
				toString();

		Connection con = ConnectionService.getConnection();
		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, hp.name);
			ps.setLong(2, id);
			int rowsAffected =	ps.executeUpdate();
			if (!(rowsAffected == 1)) { 
				throw new AppException(AppErrorCode.HOSPITAL_NOT_FOUND);
			}
			con.commit();
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(e);
		}
		return rowsAffected;
	}

	public int delete(long id) {

		String sql = new StringBuilder().
				append("DELETE FROM hospital WHERE id = ?").
				toString();

		Connection con = ConnectionService.getConnection();
		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			int rowsAffected = ps.executeUpdate();
			if (!(rowsAffected == 1)) 
			{ 
				throw new AppException(AppErrorCode.HOSPITAL_NOT_FOUND);
			}
			con.commit();
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(e);
		}
		return rowsAffected;
	}


	private void validate (Hospital hs) {

		AppErrorCode errorCode = null;
		if (hs.name == null) {
			errorCode = AppErrorCode.HOSPITAL_INVALID_NAME;
		}

		if (errorCode != null) {
			throw new AppException(errorCode);
		}
	}
}

