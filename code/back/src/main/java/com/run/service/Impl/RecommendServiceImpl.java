package com.run.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
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
import com.run.dao.RecommendDao;
import com.run.entity.Book;
import com.run.entity.BookImg;
import com.run.service.RecommendService;

import net.sf.json.JSONObject;

@Service
public class RecommendServiceImpl implements RecommendService {
	@Autowired
	private RecommendDao recommendMapper;
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
		System.out.println(recommendlist);
		List<Book> latestlist = getlatest();
		System.out.println(latestlist);
		List<Book> hottestlist = gethottest();
		System.out.println(hottestlist);
		
		int index = 0;
		while (recommendlist.size()<3) {
			recommendlist.add(latestlist.get(index));
			index++;
		}
		feedbody.put("recommend", recommendlist);
		feedbody.put("latest", latestlist);
		feedbody.put("hottest", hottestlist);
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
		
		JDBCDataModel model = new MySQLJDBCDataModel(dataSource, "history", "uid", "kind", "prc", "date");
	    UserSimilarity user = new EuclideanDistanceSimilarity(model);
	    NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(2, user, model);
	    Recommender r = new GenericBooleanPrefUserBasedRecommender(model, neighbor, user);
	    
	    List<Book> booklist = new ArrayList<Book>();
	    
	    List<RecommendedItem> list = r.recommend(uid, 3);
	    
	    List<Book> datalist = bookMapper.askbooklist(); 
	    
	    
	    for (final RecommendedItem rItem : list) {
		    Collections.sort(datalist,new Comparator<Book>() {
		    	private int kind = (int) rItem.getItemID();
				@Override
				public int compare(Book o1, Book o2) {
					int k1 = Integer.parseInt(o1.getKind());
					int k2 = Integer.parseInt(o2.getKind());
					if ((k1&kind) > (k2&kind)) return 1;
					else  return 0;
				}
		    });
		    int index = 0;
		    while (booklist.size()<3) {
		    	Book book = datalist.get(index);
		    	Query query = new Query(Criteria.where("id").is(book.getBid()));
				BookImg result=mongoTemplate.findOne(query, BookImg.class, "bookimg");
				if (result != null) {
					book.setImg(result.getImg());
				}
				book.setNickname(bookMapper.getauthor(book.getUid()));
				booklist.add(book);
				index++;
		    }
		    break;
	    }
		    
		return booklist;
	}
	
	private List<Book> getlatest() {
		List<Book> booklist = recommendMapper.asklatest(3);
		for (Book book:booklist) {
			Query query = new Query(Criteria.where("id").is(book.getBid()));
			BookImg result=mongoTemplate.findOne(query, BookImg.class, "bookimg");
			if (result != null) {
				book.setImg(result.getImg());
			}
			book.setNickname(bookMapper.getauthor(book.getUid()));
		}
		return booklist;
	}
	
	private List<Book> gethottest() {
		List<Book> booklist = recommendMapper.askhottest(3);
		for (Book book:booklist) {
			Query query = new Query(Criteria.where("id").is(book.getBid()));
			BookImg result=mongoTemplate.findOne(query, BookImg.class, "bookimg");
			if (result != null) {
				book.setImg(result.getImg());
			}
			book.setNickname(bookMapper.getauthor(book.getUid()));
		}
		return booklist; 
	}

}
