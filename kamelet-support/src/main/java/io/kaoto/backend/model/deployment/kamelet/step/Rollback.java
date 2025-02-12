package io.kaoto.backend.model.deployment.kamelet.step;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.kaoto.backend.model.parameter.Parameter;
import io.kaoto.backend.model.step.Step;

import java.util.LinkedHashMap;
import java.util.Map;


public class Rollback extends EIPStep {

    public static final String MARK_ROLLBACK_ONLY_LABEL = "markRollbackOnly";
    public static final String MARK_ROLLBACK_ONLY_LABEL2 = "mark-rollback-only";

    public static final String MARK_ROLLBACK_ONLY_LAST_LABEL = "markRollbackOnlyLast";
    public static final String MARK_ROLLBACK_ONLY_LAST_LABEL2 = "mark-rollback-only-last";

    public static final String MESSAGE_LABEL = "message";

    private Boolean markRollbackOnly;

    private Boolean markRollbackOnlyLast;

    private String message;


    public Rollback() {
         //Needed for serialization
    }

    public Rollback(final @JsonProperty(MARK_ROLLBACK_ONLY_LABEL) Boolean markRollbackOnly,
                    final @JsonProperty(MARK_ROLLBACK_ONLY_LABEL2) Boolean markRollbackOnly2,
                    final @JsonProperty(MARK_ROLLBACK_ONLY_LAST_LABEL) Boolean markRollbackOnlyLast,
                    final @JsonProperty(MARK_ROLLBACK_ONLY_LAST_LABEL2) Boolean markRollbackOnlyLast2,
                    final @JsonProperty(MESSAGE_LABEL) String message) {
        setMarkRollbackOnly(markRollbackOnly != null ? markRollbackOnly : markRollbackOnly2);
        setMarkRollbackOnlyLast(markRollbackOnlyLast != null ? markRollbackOnlyLast : markRollbackOnlyLast2);
        setMessage(message);
    }
    public Rollback(Step step) {
        super(step);
    }

    @Override
    public Map<String, Object> getRepresenterProperties() {
        Map<String, Object> properties = new LinkedHashMap<>();

        if (this.getMarkRollbackOnly() != null) {
            properties.put(MARK_ROLLBACK_ONLY_LABEL2, this.getMarkRollbackOnly());
        }
        if (this.getMarkRollbackOnlyLast() != null) {
            properties.put(MARK_ROLLBACK_ONLY_LAST_LABEL2, this.getMarkRollbackOnlyLast());
        }
        if (this.getMessage() != null) {
            properties.put(MESSAGE_LABEL, this.getMessage());
        }

        return properties;
    }

    @Override
    protected void assignProperty(final Parameter parameter) {
        switch (parameter.getId()) {
            case MARK_ROLLBACK_ONLY_LABEL:
            case MARK_ROLLBACK_ONLY_LABEL2:
                parameter.setValue(this.getMarkRollbackOnly());
                break;
            case MARK_ROLLBACK_ONLY_LAST_LABEL:
            case MARK_ROLLBACK_ONLY_LAST_LABEL2:
                parameter.setValue(this.getMarkRollbackOnlyLast());
                break;
            case MESSAGE_LABEL:
                parameter.setValue(this.getMessage());
                break;
            default:
                break;
        }
    }


    @Override
    protected void assignAttribute(final Parameter parameter) {
        switch (parameter.getId()) {
            case MARK_ROLLBACK_ONLY_LABEL:
            case MARK_ROLLBACK_ONLY_LABEL2:
                this.setMarkRollbackOnly(Boolean.valueOf(String.valueOf(parameter.getValue())));
                break;
            case MARK_ROLLBACK_ONLY_LAST_LABEL:
            case MARK_ROLLBACK_ONLY_LAST_LABEL2:
                this.setMarkRollbackOnlyLast(Boolean.valueOf(String.valueOf(parameter.getValue())));
                break;
            case MESSAGE_LABEL:
                this.setMessage(String.valueOf(parameter.getValue()));
                break;
            default:
                break;
        }
    }

    public Boolean getMarkRollbackOnly() {
        return markRollbackOnly;
    }

    public void setMarkRollbackOnly(final Boolean markRollbackOnly) {
        this.markRollbackOnly = markRollbackOnly;
    }

    public Boolean getMarkRollbackOnlyLast() {
        return markRollbackOnlyLast;
    }

    public void setMarkRollbackOnlyLast(final Boolean markRollbackOnlyLast) {
        this.markRollbackOnlyLast = markRollbackOnlyLast;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
