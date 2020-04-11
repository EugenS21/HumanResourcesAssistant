package com.humanresources.assistant.backend.model.uimodels.bonuses.profile;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class GeneralInformationFields extends GenericRiseFields {

    private String phone;
    private String email;
    private String address;
}
