package com.cezarykluczynski.stapi.etl.template.characterbox.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.characterbox.dto.CharacterboxTemplate
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualTemplateWithCharacterboxTemplateEnrichingProcessor
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateName
import spock.lang.Specification

class CharacterboxIndividualTemplateEnrichingProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'

	private PageApi pageApiMock

	private CharacterboxTemplateProcessor characterboxTemplateProcessorMock

	private IndividualTemplateWithCharacterboxTemplateEnrichingProcessor individualTemplateWithCharacterboxTemplateEnrichingProcessorMock

	private CharacterboxIndividualTemplateEnrichingProcessor characterboxIndividualTemplateEnrichingProcessor

	def setup() {
		pageApiMock = Mock(PageApi)
		characterboxTemplateProcessorMock = Mock(CharacterboxTemplateProcessor)
		individualTemplateWithCharacterboxTemplateEnrichingProcessorMock = Mock(IndividualTemplateWithCharacterboxTemplateEnrichingProcessor)
		characterboxIndividualTemplateEnrichingProcessor = new CharacterboxIndividualTemplateEnrichingProcessor(pageApiMock,
				characterboxTemplateProcessorMock, individualTemplateWithCharacterboxTemplateEnrichingProcessorMock)
	}

	def "adds template part with page title when no template parts are present"() {
		given:
		Template template = new Template()
		IndividualTemplate individualTemplate = new IndividualTemplate(page: new PageEntity(
				title: TITLE
		))

		when:
		characterboxIndividualTemplateEnrichingProcessor.enrich(EnrichablePair.of(template, individualTemplate))

		then:
		template.parts.size() == 1
		template.parts[0].value == TITLE

	}

	def "does not interact with other dependencies when PageApi returns null"() {
		given:
		Template template = new Template(title: TemplateName.MBETA)
		IndividualTemplate individualTemplate = new IndividualTemplate(page: new PageEntity(
				title: TITLE
		))

		when:
		characterboxIndividualTemplateEnrichingProcessor.enrich(EnrichablePair.of(template, individualTemplate))

		then:
		1 * pageApiMock.getPage(TITLE, CharacterboxIndividualTemplateEnrichingProcessor.SOURCE) >> null
		0 * _
	}

	def "when template and page is found, and CharacterboxTemplateProcessor returns null, IndividualTemplateWithCharacterboxTemplateEnrichingProcessor is not called"() {
		given:
		Template template = new Template(title: TemplateName.MBETA)
		IndividualTemplate individualTemplate = new IndividualTemplate(page: new PageEntity(
				title: TITLE
		))
		Page page = new Page()

		when:
		characterboxIndividualTemplateEnrichingProcessor.enrich(EnrichablePair.of(template, individualTemplate))

		then:
		1 * pageApiMock.getPage(TITLE, CharacterboxIndividualTemplateEnrichingProcessor.SOURCE) >> page
		1 * characterboxTemplateProcessorMock.process(page) >> null
	}

	def "when template and page is found, and CharacterboxTemplateProcessor returns template, IndividualTemplateWithCharacterboxTemplateEnrichingProcessor is called"() {
		given:
		Template template = new Template(title: TemplateName.MBETA)
		IndividualTemplate individualTemplate = new IndividualTemplate(page: new PageEntity(
				title: TITLE
		))
		Page page = new Page()
		CharacterboxTemplate characterboxTemplate = new CharacterboxTemplate()

		when:
		characterboxIndividualTemplateEnrichingProcessor.enrich(EnrichablePair.of(template, individualTemplate))

		then:
		1 * pageApiMock.getPage(TITLE, CharacterboxIndividualTemplateEnrichingProcessor.SOURCE) >> page
		1 * characterboxTemplateProcessorMock.process(page) >> characterboxTemplate
		1 * individualTemplateWithCharacterboxTemplateEnrichingProcessorMock.enrich(_) >> { EnrichablePair<CharacterboxTemplate, IndividualTemplate> enrichablePair ->
			assert enrichablePair.input == characterboxTemplate
			assert enrichablePair.output == individualTemplate
		}
	}

}