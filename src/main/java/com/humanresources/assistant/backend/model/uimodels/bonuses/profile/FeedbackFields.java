package com.humanresources.assistant.backend.model.uimodels.bonuses.profile;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FeedbackFields extends GenericRiseFields {

    private final String feedBack;

}
