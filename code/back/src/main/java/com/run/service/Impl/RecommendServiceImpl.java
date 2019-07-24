package com.run.service.Impl;

import java.io.IOException;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.run.entity.Book;
import com.run.service.RecommendService;

@Service
public class RecommendServiceImpl implements RecommendService {
	@Override
	public List<Book> recommend(int uid) throws TasteException, IOException {
		Properties prop = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/resources/jdbc.properties"));
		System.out.println(new ClassPathResource("../resources/jdbc.properties"));
		String userName = prop.getProperty("jdbc.userName");
		String password = prop.getProperty("jdbc.password");
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName("localhost");
		dataSource.setPort(3306);
		dataSource.setDatabaseName("sfbook");
		dataSource.setUser(userName);
		dataSource.setPassword(password);
		
		 String file = "G:/test.csv";
	        //DataModel model = new FileDataModel(new File(file));
		 	JDBCDataModel model = new MySQLJDBCDataModel(dataSource, "history", "uid", "kind", "prc", "date");
	        UserSimilarity user = new EuclideanDistanceSimilarity(model);
	        NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(2, user, model);
	        //Recommender r = new GenericUserBasedRecommender(model, neighbor, user);
	        Recommender r = new GenericBooleanPrefUserBasedRecommender(model, neighbor, user);
	        LongPrimitiveIterator iter = model.getUserIDs();

	        while (iter.hasNext()) {
	            long uuid = iter.nextLong();
	            List<RecommendedItem> list = r.recommend(uuid, 3);
	            System.out.printf("uid:%s", uuid);
	            for (RecommendedItem ritem : list) {
	                System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
	            }
	            System.out.println();
	        }
		return null;
	}
	

}
