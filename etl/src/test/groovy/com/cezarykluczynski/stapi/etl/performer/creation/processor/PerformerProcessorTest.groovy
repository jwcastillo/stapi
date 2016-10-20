package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplatePageProcessor
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.wiki.dto.Page
import com.cezarykluczynski.stapi.wiki.dto.PageHeader
import spock.lang.Specification

class PerformerProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private ActorTemplatePageProcessor actorTemplatePageProcessorMock

	private ActorTemplateProcessor actorTemplateProcessorMock

	private PerformerProcessor performerProcessor

	def setup() {
		pageHeaderProcessorMock = Mock(PageHeaderProcessor)
		actorTemplatePageProcessorMock = Mock(ActorTemplatePageProcessor)
		actorTemplateProcessorMock = Mock(ActorTemplateProcessor)
		performerProcessor = new PerformerProcessor(pageHeaderProcessorMock, actorTemplatePageProcessorMock,
				actorTemplateProcessorMock)
	}

	def "converts PageHeader to Performer"() {
		given:
		PageHeader pageHeader = PageHeader.builder().build()
		Page page = new Page()
		ActorTemplate actorTemplate = new ActorTemplate()
		Performer performer = new Performer()

		when:
		Performer outputPerformer = performerProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page
		1 * actorTemplatePageProcessorMock.process(page) >> actorTemplate
		1 * actorTemplateProcessorMock.process(actorTemplate) >> performer

		then: 'last processor output is returned'
		outputPerformer == performer
	}

}