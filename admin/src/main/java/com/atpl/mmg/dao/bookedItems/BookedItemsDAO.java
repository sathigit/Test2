package com.atpl.mmg.dao.bookedItems;

import java.util.List;

import com.atpl.mmg.domain.bookedItems.BookedItemsDomain;
import com.atpl.mmg.domain.dashboard.DashboardDomain;

public interface BookedItemsDAO {
	
	public String saveBookedItem(BookedItemsDomain bookedItemsDomain) throws Exception;

	public List<BookedItemsDomain> getBookedItem(String bookingId,int lowerBound, int upperBound) throws Exception;
	
	public DashboardDomain getBookedItemCount(String bookingId) throws Exception;

	public String updateBookedItem(BookedItemsDomain bookedItemsDomain) throws Exception;



}
