package com.cezarykluczynski.stapi.etl.magazine.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class MagazineReader extends ListItemReader<PageHeader> {

	public MagazineReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of magazine list: {}", list.size());
	}

}
