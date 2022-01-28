package com.atpl.mmg.service.bookedItems;

import java.util.Map;

import com.atpl.mmg.model.bookedItems.BookedItemsModel;
import com.atpl.mmg.utils.ListDto;

public interface BookedItemsService {
	
	public String saveBookedItem(BookedItemsModel bookedItemsModel) throws Exception;

//	public List<BookedItemsModel> getBookedItem(String bookingId) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public ListDto getBookedItem(String bookingId,Map<String, String> reqParam) throws Exception;

	public String updateBookedItem(BookedItemsModel bookedItemsModel) throws Exception;


}
