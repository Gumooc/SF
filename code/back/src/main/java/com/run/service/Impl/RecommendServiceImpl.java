package com.run.service.Impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.run.dao.BookDao;
import com.run.entity.Book;
import com.run.entity.BookImg;
import com.run.service.RecommendService;

import net.sf.json.JSONObject;

@Service
public class RecommendServiceImpl implements RecommendService {
	@Autowired
	private BookDao bookMapper;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public JSONObject recommend(int uid){
		JSONObject feedbody = new JSONObject();
		List<Book> recommendlist = new ArrayList<>();
		try {
			recommendlist = getrecommend(uid);
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Book> latestlist = getlatest();
		List<Book> hottestlist = gethottest();
		feedbody.put("recommend", recommendlist);
		feedbody.put("latest", latestlist);
		return feedbody;
	}
	
	private List<Book> getrecommend(int uid) throws TasteException{

		//Properties prop = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/resources/jdbc.properties"));
		//System.out.println(new ClassPathResource("../resources/jdbc.properties"));
		//String userName = prop.getProperty("jdbc.userName");
		//String password = prop.getProperty("jdbc.password");
		MysqlDataSource dataSource = new MysqlDataSource();
		String userName = "root";
		String password = "234567";
		dataSource.setServerName("localhost");
		dataSource.setPort(3306);
		dataSource.setDatabaseName("sfbook");
		dataSource.setUser(userName);
		dataSource.setPassword(password);
		
		//String file = "G:/test.csv";
	    //DataModel model = new FileDataModel(new File(file));
		JDBCDataModel model = new MySQLJDBCDataModel(dataSource, "history", "uid", "kind", "prc", "date");
	    UserSimilarity user = new EuclideanDistanceSimilarity(model);
	    NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(2, user, model);
	    //Recommender r = new GenericUserBasedRecommender(model, neighbor, user);
	    Recommender r = new GenericBooleanPrefUserBasedRecommender(model, neighbor, user);
	    
	    List<Book> booklist = new ArrayList<Book>();
	    
	    List<RecommendedItem> list = r.recommend(uid, 3);
		for (RecommendedItem ritem : list) {
	    	int bid = (int) ritem.getItemID();
	    	Book book = bookMapper.askbookinfo(bid);
	    	if (book == null) continue;
			Query query = new Query(Criteria.where("id").is(bid));
			BookImg result=mongoTemplate.findOne(query, BookImg.class, "bookimg");
			if (result != null) {
				book.setImg(result.getImg());
			}
			book.setNickname(bookMapper.getauthor(book.getUid()));
			booklist.add(book);
	    }
		    
		return booklist;
	}
	
	private List<Book> getlatest() {
		List<Book> booklist = bookMapper.asklatest(3);
		return booklist;
	}
	
	private List<Book> gethottest() {
		List<Book> booklist;
		return null; 
	}

}
