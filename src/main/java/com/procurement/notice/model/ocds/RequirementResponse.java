package com.procurement.notice.model.ocds;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title",
        "description",
        "value",
        "period",
        "requirement",
        "relatedTenderer"
})
public class RequirementResponse {
    @JsonProperty("id")
    @JsonPropertyDescription("The identifier for this requirement response. It must be unique and cannot change " +
            "within the Open Contracting Process it is part of (defined by a single ocid). See the [identifier " +
            "guidance]" +
            "(http://standard.open-contracting.org/latest/en/schema/identifiers/) for further details.")
    private final String id;

    @JsonProperty("title")
    @JsonPropertyDescription("Requirement response title")
    private final String title;

    @JsonProperty("description")
    @JsonPropertyDescription("Requirement response description")
    private final String description;

    @JsonProperty("value")
    @JsonPropertyDescription("Requirement response value. The value must be of the type defined in the requirement" +
            ".dataType field")
    private final String value;

    @JsonProperty("period")
    private final Period period;

    @JsonProperty("requirement")
    @JsonPropertyDescription("Used to cross reference a requirement")
    private final RequirementReference requirement;

    @JsonProperty("relatedTenderer")
    @JsonPropertyDescription("The id and name of the party being referenced. Used to cross-reference to the parties " +
            "section")
    private final OrganizationReference relatedTenderer;

    @JsonCreator
    public RequirementResponse(@JsonProperty("id") final String id,
                               @JsonProperty("title") final String title,
                               @JsonProperty("description") final String description,
                               @JsonProperty("value") final String value,
                               @JsonProperty("period") final Period period,
                               @JsonProperty("requirement") final RequirementReference requirement,
                               @JsonProperty("relatedTenderer") final OrganizationReference relatedTenderer) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.value = value;
        this.period = period;
        this.requirement = requirement;
        this.relatedTenderer = relatedTenderer;
    }
}
