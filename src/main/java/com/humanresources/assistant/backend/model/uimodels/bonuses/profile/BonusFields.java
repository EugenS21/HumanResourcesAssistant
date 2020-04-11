package com.humanresources.assistant.backend.model.uimodels.bonuses.profile;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BonusFields extends GenericRiseFields {

    private final String motivation;
}
