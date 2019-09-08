package com.run.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.run.entity.Book;
import com.run.entity.Collector;
import com.run.entity.Comment;
import com.run.entity.TpUser;
import com.run.entity.User;

@Repository
public interface UserDao {
	User logincheck(User user);
	User usercheck(User user);
	User registercheck(User user);
	void register(User user);
	void active(int uid);
	
	Collector askcollector(int uid); 
	void updateuser(User user);
	Collector askhistory(int uid);
	
	List<Comment> askcomment(int uid);
	
	List<Book> askworks(int uid);
	List<Book> askselfworks(int uid); 
	User askuser(int uid);
	
	User askuserallinfo(int uid);
	
	boolean checkforbidden(int uid);
	
	TpUser tpcheck(String openid);
	void tp(@Param("uid") int uid, @Param("openid") String openid);
	void updatelst(@Param("uid") int uid, @Param("lst") String lst);
	
	void updatepassword(@Param("uid") int uid, @Param("password") String password);
	User modifycheck(User user); 
	
	boolean askhide(int uid);
	
}
