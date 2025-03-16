package com.demo.dboperation;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.model.annotations.AutoIncrementField;

/**
 * 
 * @author Abhi Shah
 * @version 1.0
 * 
 */
public class Dboperation {

	Connection con = null;

	private String url;

	private String username;

	private String password;

	public Dboperation(String url, String username, String password) {
		// TODO Auto-generated constructor stub.
		this.url = url;
		this.username = username;
		this.password = password;
	}

	private Connection createConnection() throws SQLException {
		return DriverManager.getConnection(this.url, this.username, this.password);
	}

	public HashMap<Class<?>, List<?>> Execute(String query, ArrayList<SQLParameter> parameters,
			Map<Class<?>, Integer> resultMappings) throws SQLException {
		HashMap<Class<?>, List<?>> resultMap = new HashMap<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = createConnection();

			ps = con.prepareStatement(query);

			// Set dynamic parameters
			if (parameters != null && !parameters.isEmpty()) {
				for (SQLParameter param : parameters) {
					param.setParameter(ps);
				}
			}

			boolean isResultSet = ps.execute();
			int count = 0;

			while (true) {
				if (isResultSet) {
					rs = ps.getResultSet();

					// Find which Class<?> this result set maps to
					for (Map.Entry<Class<?>, Integer> entry : resultMappings.entrySet()) {
						if (entry.getValue() == count) {
							List<?> mappedData = ReflectionMapper.mapResultSet(rs, entry.getKey());
							resultMap.put(entry.getKey(), mappedData);
							break;
						}
					}
				} else {
					if (ps.getUpdateCount() == -1) {
						break;
					}
				}
				count++;
				isResultSet = ps.getMoreResults();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// Close resources
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null)
				con.close();
		}

		return resultMap;
	}

	public HashMap<Class<?>, List<?>> ExecuteStoredProcedure(String procedureName, ArrayList<SQLParameter> parameters,
			Map<Class<?>, Integer> resultMappings) throws SQLException {
		HashMap<Class<?>, List<?>> resultMap = new HashMap<>();
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		try {

			con = createConnection();

			// Construct Stored Procedure Call: "{ call procedure_name(?, ?, ...) }"
			StringBuilder spCall = new StringBuilder("{ call " + procedureName + " (");
			for (int i = 0; i < parameters.size(); i++) {
				spCall.append("?");
				if (i < parameters.size() - 1) {
					spCall.append(", ");
				}
			}
			spCall.append(") }");

			cs = con.prepareCall(spCall.toString());

			// Set Parameters Dynamically
			if (parameters != null && !parameters.isEmpty()) {
				for (SQLParameter param : parameters) {
					param.setParameter(cs);
				}
			}

			boolean isResultSet = cs.execute();
			int count = 0;

			while (true) {
				if (isResultSet) {
					rs = cs.getResultSet();

					// Map ResultSet to Corresponding Class Based on Order
					for (Map.Entry<Class<?>, Integer> entry : resultMappings.entrySet()) {
						if (entry.getValue() == count) {
							List<?> mappedData = ReflectionMapper.mapResultSet(rs, entry.getKey());
							resultMap.put(entry.getKey(), mappedData);
							break;
						}
					}
				} else {
					if (cs.getUpdateCount() == -1) {
						break;
					}
				}
				count++;
				isResultSet = cs.getMoreResults();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// Close Resources
			if (rs != null)
				rs.close();
			if (cs != null)
				cs.close();
			if (con != null)
				con.close();
		}

		return resultMap;
	}

	public void insertRecord(Object obj, String tableName)
			throws SQLException, IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Connection con = null;

		List<Field> insertableFields = new ArrayList<>();

		StringBuilder columns = new StringBuilder();
		StringBuilder placeholders = new StringBuilder();

		for (Field field : fields) {
			if (field.isAnnotationPresent(AutoIncrementField.class)) {
				continue; // Skip auto-increment fields
			}

			insertableFields.add(field);
			columns.append(field.getName()).append(", ");
			placeholders.append("?, ");
		}

		// Remove trailing comma
		if (columns.length() > 0)
			columns.setLength(columns.length() - 2);
		if (placeholders.length() > 0)
			placeholders.setLength(placeholders.length() - 2);

		String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, placeholders);
		PreparedStatement ps = null;
		try {
			con = createConnection();

			con.setAutoCommit(false);

			ps = con.prepareStatement(sql);

			for (int i = 0; i < insertableFields.size(); i++) {
				Field field = insertableFields.get(i);
				field.setAccessible(true);
				Object value = field.get(obj);
				ps.setObject(i + 1, value); // JDBC index starts at 1
			}
			ps.executeUpdate();

			// perform commit.
			con.commit();
		} catch (SQLException ex) {
			con.rollback();

		} finally {
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
			}

		}

	}

	public <T> int insertBatch(List<T> objectList, String tableName) throws Exception {
		if (objectList == null || objectList.isEmpty())
			return 0;

		Connection con = null;
		PreparedStatement ps = null;
		int insertedCount = 0;

		Class<?> clazz = objectList.get(0).getClass();
		Field[] allFields = clazz.getDeclaredFields();
		List<Field> insertableFields = new ArrayList<>();

		StringBuilder columns = new StringBuilder();
		StringBuilder placeholders = new StringBuilder();

		for (Field field : allFields) {
			if (field.isAnnotationPresent(AutoIncrementField.class)) {
				continue; // Skip auto-increment fields
			}
			insertableFields.add(field);
			columns.append(field.getName()).append(", ");
			placeholders.append("?, ");
		}

		// Remove trailing commas
		if (columns.length() > 0)
			columns.setLength(columns.length() - 2);
		if (placeholders.length() > 0)
			placeholders.setLength(placeholders.length() - 2);

		String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, placeholders);

		try {
			con = this.createConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);

			for (T obj : objectList) {
				for (int i = 0; i < insertableFields.size(); i++) {
					Field field = insertableFields.get(i);
					field.setAccessible(true);
					Object value = field.get(obj);
					ps.setObject(i + 1, value);
				}
				ps.addBatch();
			}

			int[] results = ps.executeBatch();
			con.commit();
			insertedCount = results.length;
			System.out.println("Batch Insert Success. Total Records Inserted: " + insertedCount);

		} catch (SQLException ex) {
			try {
				if (con != null) {
					con.rollback();
					System.out.println("Transaction rolled back due to error.");
				}
			} catch (SQLException rollbackEx) {
				System.err.println("Rollback failed: " + rollbackEx.getMessage());
			}
			ex.printStackTrace(); // Properly print exception
		} finally {
			if (ps != null)
				ps.close();
			if (con != null)
				con.close();
		}

		return insertedCount;
	}

	public Map<Integer, Object> executeQueryGeneric(String sql, ArrayList<SQLParameter> parameters)
			throws SQLException {
		Map<Integer, Object> resultMap = new HashMap<>(); // Map of result index to data
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = createConnection();
			con.setAutoCommit(false); // Use transaction for safety

			ps = con.prepareStatement(sql);
			// Set dynamic parameters
			for (SQLParameter param : parameters) {
				param.setParameter(ps);
			}

			boolean hasResultSet = ps.execute();
			int count = 0;

			while (true) {
				if (hasResultSet) {
					ResultSet rs = ps.getResultSet();
					List<Map<String, Object>> resultList = new ArrayList<>();

					ResultSetMetaData metaData = rs.getMetaData();
					int columnCount = metaData.getColumnCount();

					while (rs.next()) {
						Map<String, Object> row = new HashMap<>();
						for (int i = 1; i <= columnCount; i++) {
							row.put(metaData.getColumnLabel(i), rs.getObject(i));
						}
						resultList.add(row);
					}
					rs.close();
					resultMap.put(count, resultList);
				} else {
					int updateCount = ps.getUpdateCount();
					if (updateCount == -1)
						break; // No more results
					resultMap.put(count, updateCount); // Update count for DML
				}
				count++;
				hasResultSet = ps.getMoreResults();
			}

			con.commit(); // Commit transaction
		} catch (SQLException e) {
			try {
				if (con != null) {
					con.rollback();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} finally {
			if (ps != null)
				ps.close();
			if (con != null)
				con.close();
		}

		return resultMap; // ResultSet or update counts
	}

}
