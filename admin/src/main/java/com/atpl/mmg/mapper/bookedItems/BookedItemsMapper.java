package com.atpl.mmg.mapper.bookedItems;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.bookedItems.BookedItemsDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.bookedItems.BookedItemsModel;

@Component
public class BookedItemsMapper extends AbstractModelMapper<BookedItemsModel, BookedItemsDomain>{

	@Override
	public Class<BookedItemsModel> entityType() {
		// TODO Auto-generated method stub
		return BookedItemsModel.class;
	}

	@Override
	public Class<BookedItemsDomain> modelType() {
		// TODO Auto-generated method stub
		return BookedItemsDomain.class;
	}

}
