package com.demo.myjdbc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.dboperation.CollectionUtils;
import com.demo.dboperation.DataType;
import com.demo.dboperation.Dboperation;
import com.demo.dboperation.ReflectionMapper;
import com.demo.dboperation.SQLParameter;
import com.demo.model.Course;
import com.demo.model.Enrollment;
import com.demo.model.Review;
import com.demo.model.User;
import com.demo.xml.XmlUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 */
public class App {
	/**
	 * Main method.
	 * 
	 * @param args
	 * @throws Exception
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws Exception {

		Dboperation db = new Dboperation(
				"jdbc:sqlserver://10.0.0.27:52284;databaseName=OnlineCourse;TrustServerCertificate=True;", "abhitest",
				"Abhi123@.");

		/*
		 * List<User> users = new ArrayList<User>();
		 * 
		 * User user = new User(); user.setUserName("Abhishah12345");
		 * user.setActive(false); user.setCreatedAt(LocalDateTime.now());
		 * user.setEmail("abhishah15788125712@gmail.com"); user.setFirstName("Abhi");
		 * user.setLastName("Shah"); user.setPasswordHash(
		 * "2ce7486236df49e893f49df162f91fddb9774d666f174bcbf2227720e1b23546");
		 * user.setRole("Student");
		 * 
		 * users.add(user);
		 * 
		 * user = new User(); user.setUserName("Abhishah12345"); user.setActive(false);
		 * user.setCreatedAt(LocalDateTime.now());
		 * user.setEmail("abhishah15278823512@gmail.com"); user.setFirstName("Abhi");
		 * user.setLastName("Shah"); user.setPasswordHash(
		 * "2ce7486236df49e893f49df162f91fddb9774d666f174bcbf2227720e1b23546");
		 * user.setRole("Student");
		 * 
		 * users.add(user);
		 * 
		 * user = new User(); user.setUserName("Abhishah122345"); user.setActive(false);
		 * user.setCreatedAt(LocalDateTime.now());
		 * user.setEmail("abhishah1527885272@gmail.com"); user.setFirstName("Abhi");
		 * user.setLastName("Shah"); user.setPasswordHash(
		 * "2ce7486236df49e893f49df162f91fddb9774d666f174bcbf2227720e1b23546");
		 * user.setRole("Student");
		 * 
		 * users.add(user);
		 * 
		 * 
		 * db.insertBatch(users, "Users");
		 * 
		 */

		/*
		 * 
		 * Map<Class<?>, Integer> resultMappings = new HashMap<>();
		 * resultMappings.put(User.class, 0); resultMappings.put(Course.class, 1);
		 * resultMappings.put(Enrollment.class, 2); resultMappings.put(Review.class, 3);
		 * 
		 * HashMap<Class<?>, List<?>> result = (HashMap<Class<?>, List<?>>)
		 * db.ExecuteStoredProcedure("GetList", new ArrayList<SQLParameter>(),
		 * resultMappings);
		 * 
		 * // Extract Users List<User> users = CollectionUtils.getList(result,
		 * User.class); System.out.println("Users:");
		 * users.forEach(System.out::println);
		 * 
		 * // Extract Orders List<Course> orders = CollectionUtils.getList(result,
		 * Course.class); System.out.println("\nOrders:");
		 * orders.forEach(System.out::println);
		 * 
		 * 
		 * 
		 * Enrollment enrollment = CollectionUtils.getSingle(result,Enrollment.class);
		 * System.out.println("\nenrollments:");
		 * System.out.println(enrollment.toString());
		 * 
		 * List<Review> reviews = CollectionUtils.getList(result, Review.class);
		 * System.out.println("\nReviews:"); if (reviews != null && !reviews.isEmpty())
		 * { reviews.forEach(System.out::println); } else {
		 * System.out.println("No record for Reviews found"); }
		 * 
		 * String xml = XmlUtils.convertListToXml(reviews);
		 * 
		 * System.out.println(xml);
		 * 
		 */

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM Reviews WITH(NOLOCK) WHERE rating <= ?;");
		sb.append("SELECT * FROM Users WITH(NOLOCK) WHERE Email = ?;");
		sb.append("UPDATE Reviews SET comment = ?, ReviewDate = ? WHERE reviewId = ?;");
		sb.append("Delete from Users where Username = ?;");
		// sb.append("INSERT INTO Review (reviewId, courseId, studentId, rating,
		// comment, reviewDate) VALUES (?, ?, ?, ?, ?, ?);");

		ArrayList<SQLParameter> params = new ArrayList<SQLParameter>();
		SQLParameter p = new SQLParameter(1, DataType.Integer, 1);

		params.add(p);

		p = new SQLParameter("abhishah167811@gmail.com", DataType.String, 2);

		params.add(p);

		p = new SQLParameter("Abhi Shah Test w27", DataType.String, 3);

		params.add(p);

		p = new SQLParameter(LocalDateTime.now(), DataType.LocalDateT, 4);

		params.add(p);

		p = new SQLParameter("0FA1B672-0888-40EA-98E6-ED2F4D3FF772", DataType.String, 5);

		params.add(p);
		
		
		p = new SQLParameter("Abhishah122345", DataType.String, 6);

		params.add(p);

		// Assume resultMap holds result from multi-query execution
		Map<Integer, Object> resultMap = db.executeQueryGeneric(sb.toString(), params);

		// Get List of Reviews from resultSet 0
		List<Review> reviews = ReflectionMapper.getMappedList(resultMap, 0, Review.class);

		// Get single User from resultSet 1
		User user = ReflectionMapper.getSingleMappedObject(resultMap, 1, User.class);

		// Get total rows affected by all update queries
		int totalUpdates = ReflectionMapper.getTotalUpdateCount(resultMap);

		System.out.println("Reviews: " + reviews);
		System.out.println("User: " + user);
		System.out.println("Total Rows Updated: " + totalUpdates);

	}
}
