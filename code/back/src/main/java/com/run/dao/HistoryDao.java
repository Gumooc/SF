package com.run.dao;

import com.run.entity.UserbookItem;

public interface HistoryDao {
	void insert(UserbookItem ubi);
	void clear(int uid);
}
