package com.run.dao;

import com.run.entity.UserbookItem;

public interface CollectorDao {

	void insert(UserbookItem ubi);
	void delete(UserbookItem ubi);
}
