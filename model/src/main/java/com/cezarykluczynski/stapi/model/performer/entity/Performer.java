package com.cezarykluczynski.stapi.model.performer.entity;

import com.cezarykluczynski.stapi.model.common.entity.RealWorldPerson;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Performer extends RealWorldPerson implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="performer_sequence_generator")
	@SequenceGenerator(name="performer_sequence_generator", sequenceName="performer_sequence", allocationSize = 1)
	private Long id;

	private boolean animalPerformer;

	private boolean disPerformer;

	@Column(name = "ds9_performer")
	private boolean ds9Performer;

	private boolean entPerformer;

	private boolean filmPerformer;

	private boolean standInPerformer;

	private boolean stuntPerformer;

	private boolean tasPerformer;

	private boolean tngPerformer;

	private boolean tosPerformer;

	private boolean videoGamePerformer;

	private boolean voicePerformer;

	private boolean voyPerformer;

	@ManyToMany(mappedBy = "performers")
	private Set<Episode> performances;

	@ManyToMany(mappedBy = "stuntPerformers")
	private Set<Episode> stuntPerformances;

	@ManyToMany(mappedBy = "standInPerformers")
	private Set<Episode> standInPerformances;

}
