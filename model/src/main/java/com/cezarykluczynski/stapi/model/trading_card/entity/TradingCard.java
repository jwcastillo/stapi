package com.cezarykluczynski.stapi.model.trading_card.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.model.video_release.repository.VideoReleaseRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"tradingCardSet"})
@EqualsAndHashCode(exclude = {"tradingCardSet"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = VideoReleaseRepository.class, singularName = "trading card set",
		pluralName = "trading card sets")
public class TradingCard {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trading_card_sequence_generator")
	@SequenceGenerator(name = "trading_card_sequence_generator", sequenceName = "trading_card_sequence", allocationSize = 1)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "trading_card_set_id")
	private TradingCardSet tradingCardSet;

	@Column(name = "u_id")
	private String uid;

	private String name;

	@Column(name = "card_number")
	private String number;

}
