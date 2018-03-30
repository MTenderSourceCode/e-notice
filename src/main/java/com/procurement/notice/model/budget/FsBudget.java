package com.procurement.notice.model.budget;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.procurement.notice.model.ocds.EuropeanUnionFunding;
import com.procurement.notice.model.ocds.OrganizationReference;
import com.procurement.notice.model.ocds.Period;
import com.procurement.notice.model.ocds.Value;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "description",
        "period",
        "amount",
        "europeanUnionFunding",
        "isEuropeanUnionFunded",
        "budgetBreakdown",
        "verified",
        "sourceEntity"
})
public class FsBudget {

    @JsonProperty("id")
    private String id;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("period")
    private final Period period;

    @JsonProperty("amount")
    private final Value amount;

    @JsonProperty("europeanUnionFunding")
    private final EuropeanUnionFunding europeanUnionFunding;

    @JsonProperty("isEuropeanUnionFunded")
    private final Boolean isEuropeanUnionFunded;

    @JsonProperty("verified")
    private Boolean verified;

    @JsonProperty("sourceEntity")
    private OrganizationReference sourceEntity;

    @JsonCreator
    public FsBudget(@JsonProperty("id") final String id,
                    @JsonProperty("description") final String description,
                    @JsonProperty("period") final Period period,
                    @JsonProperty("amount") final Value amount,
                    @JsonProperty("europeanUnionFunding") final EuropeanUnionFunding europeanUnionFunding,
                    @JsonProperty("isEuropeanUnionFunded") final Boolean isEuropeanUnionFunded,
                    @JsonProperty("verified") final Boolean verified,
                    @JsonProperty("sourceEntity") final OrganizationReference sourceEntity) {
        this.id = id;
        this.description = description;
        this.period = period;
        this.amount = amount;
        this.europeanUnionFunding = europeanUnionFunding;
        this.isEuropeanUnionFunded = isEuropeanUnionFunded;
        this.verified = verified == null ? false : verified;
        this.sourceEntity = sourceEntity;
    }
}
