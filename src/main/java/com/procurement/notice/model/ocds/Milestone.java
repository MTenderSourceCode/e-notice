package com.procurement.notice.model.ocds;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.procurement.notice.databinding.LocalDateTimeDeserializer;
import com.procurement.notice.databinding.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title",
        "type",
        "description",
        "code",
        "dueDate",
        "dateMet",
        "dateModified",
        "status",
        "documents",
        "relatedLots",
        "relatedParties",
        "additionalInformation"
})
public class Milestone {
    @JsonProperty("id")
    @JsonPropertyDescription("A local identifier for this milestone, unique within this block. This field is used to " +
            "keep track of multiple revisions of a milestone through the compilation from release to record mechanism.")
    private String id;

    @JsonProperty("title")
    @JsonPropertyDescription("Milestone title")
    private final String title;

    @JsonProperty("type")
    @JsonPropertyDescription("The type of milestone, drawn from an extended [milestoneType codelist](http://standard" +
            ".open-contracting.org/latest/en/schema/codelists/#milestone-type).")
    private final MilestoneType type;

    @JsonProperty("description")
    @JsonPropertyDescription("A description of the milestone.")
    private final String description;

    @JsonProperty("code")
    @JsonPropertyDescription("Milestone codes can be used to track specific events that take place for a particular " +
            "kind of contracting process. For example, a code of 'approvalLetter' could be used to allow applications" +
            " to " +
            "understand this milestone represents the date an approvalLetter is due or signed. Milestone codes is an " +
            "open" +
            " codelist, and codes should be agreed among data producers and the applications using that data.")
    private final String code;

    @JsonProperty("dueDate")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonPropertyDescription("The date the milestone is due.")
    private final LocalDateTime dueDate;

    @JsonProperty("dateMet")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonPropertyDescription("The date on which the milestone was met.")
    private final LocalDateTime dateMet;

    @JsonProperty("dateModified")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonPropertyDescription("The date the milestone was last reviewed or modified and the status was altered or " +
            "confirmed to still be correct.")
    private final LocalDateTime dateModified;

    @JsonProperty("status")
    @JsonPropertyDescription("The status that was realized on the date provided in dateModified, drawn from the " +
            "[milestoneStatus codelist](http://standard.open-contracting" +
            ".org/latest/en/schema/codelists/#milestone-status).")
    private final Status status;

    @JsonProperty("documents")
    @JsonDeserialize(as = LinkedHashSet.class)
    @JsonPropertyDescription("List of documents associated with this milestone (Deprecated in 1.1).")
    private final Set<Document> documents;

    @JsonProperty("relatedLots")
    @JsonPropertyDescription("If this milestone relates to a particular lot, provide the identifier(s) of the related" +
            " lot(s) here.")
    private final List<String> relatedLots;

    @JsonProperty("relatedParties")
    @JsonPropertyDescription("Parties that have a relationship with the milestone.")
    private final List<OrganizationReference> relatedParties;

    @JsonProperty("additionalInformation")
    @JsonPropertyDescription("Additional information about the milestone")
    private final String additionalInformation;

    public Milestone(@JsonProperty("id") final String id,
                     @JsonProperty("title") final String title,
                     @JsonProperty("type") final MilestoneType type,
                     @JsonProperty("description") final String description,
                     @JsonProperty("code") final String code,
                     @JsonProperty("dueDate") @JsonDeserialize(using = LocalDateTimeDeserializer.class) final
                     LocalDateTime dueDate,
                     @JsonProperty("dateMet") @JsonDeserialize(using = LocalDateTimeDeserializer.class) final
                     LocalDateTime dateMet,
                     @JsonProperty("dateModified") @JsonDeserialize(using = LocalDateTimeDeserializer.class) final
                     LocalDateTime dateModified,
                     @JsonProperty("status") final Status status,
                     @JsonProperty("documents") final LinkedHashSet<Document> documents,
                     @JsonProperty("relatedLots") final List<String> relatedLots,
                     @JsonProperty("relatedParties") final List<OrganizationReference> relatedParties,
                     @JsonProperty("additionalInformation") final String additionalInformation) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.code = code;
        this.dueDate = dueDate;
        this.dateMet = dateMet;
        this.dateModified = dateModified;
        this.status = status;
        this.documents = documents;
        this.relatedLots = relatedLots;
        this.relatedParties = relatedParties;
        this.additionalInformation = additionalInformation;
    }

    public enum Status {
        SCHEDULED("scheduled"),
        MET("met"),
        NOT_MET("notMet"),
        PARTIALLY_MET("partiallyMet");

        private final static Map<String, Status> CONSTANTS = new HashMap<>();

        static {
            for (final Status c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        private Status(final String value) {
            this.value = value;
        }

        @JsonCreator
        public static Status fromValue(final String value) {
            final Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            }
            return constant;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

    }

    public enum MilestoneType {
        PRE_PROCUREMENT("preProcurement"),
        APPROVAL("approval"),
        ENGAGEMENT("engagement"),
        ASSESSMENT("assessment"),
        DELIVERY("delivery"),
        REPORTING("reporting"),
        FINANCING("financing");

        private final static Map<String, MilestoneType> CONSTANTS = new HashMap<>();

        static {
            for (final MilestoneType c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        private MilestoneType(final String value) {
            this.value = value;
        }

        @JsonCreator
        public static MilestoneType fromValue(final String value) {
            final MilestoneType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            }
            return constant;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

    }
}