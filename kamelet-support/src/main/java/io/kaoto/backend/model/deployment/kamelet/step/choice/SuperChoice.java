package io.kaoto.backend.model.deployment.kamelet.step.choice;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.kaoto.backend.KamelPopulator;
import io.kaoto.backend.api.metadata.catalog.StepCatalog;
import io.kaoto.backend.api.service.step.parser.kamelet.KameletStepParserService;
import io.kaoto.backend.model.deployment.kamelet.step.EIPStep;
import io.kaoto.backend.model.parameter.Parameter;
import io.kaoto.backend.model.step.Branch;
import io.kaoto.backend.model.step.Step;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SuperChoice extends EIPStep {

    public static final String WHEN_LABEL = "when";
    public static final String OTHERWISE_LABEL = "otherwise";
    @JsonProperty(WHEN_LABEL)
    private List<Choice> choice;

    @JsonProperty(OTHERWISE_LABEL)
    private Otherwise otherwise;

    public SuperChoice(Step step, final KamelPopulator kameletPopulator) {
        super(step);

        setChoice(new LinkedList<>());

        if (step.getBranches() != null) {
            for (Branch b : step.getBranches()) {
                if (b.getCondition() != null) {
                    Choice choice = new Choice();
                    choice.setSteps(kameletPopulator.processSteps(b));
                    choice.setSimple(b.getCondition());
                    getChoice().add(choice);
                } else {
                    var otherwise = new Otherwise();
                    otherwise.setSteps(kameletPopulator.processSteps(b));
                    setOtherwise(otherwise);
                }
            }
        }
    }

    public SuperChoice() {
        //Needed for serialization
    }


    @Override
    protected void processBranches(final Step step, final StepCatalog catalog,
                                   final KameletStepParserService kameletStepParserService) {
        step.setBranches(new LinkedList<>());

        if (getChoice() != null) {
            for (var flow : getChoice()) {
                Branch branch = createBranch(getChoiceIdentifier(flow), flow.getSteps(), kameletStepParserService);
                branch.setCondition(getChoiceCondition(flow));
                kameletStepParserService.setValueOnStepProperty(step, KameletStepParserService.SIMPLE,
                        branch.getCondition());
                step.getBranches().add(branch);
            }
        }

        if (getOtherwise() != null) {
            step.getBranches().add(createBranch(
                    KameletStepParserService.OTHERWISE, getOtherwise().getSteps(), kameletStepParserService));
        }
    }

    private String getChoiceIdentifier(final Choice flow) {
        return flow.getSimple();
    }

    private String getChoiceCondition(final Choice flow) {
        return flow.getSimple();
    }
    @Override
    public Map<String, Object> getRepresenterProperties() {
        Map<String, Object> properties = new HashMap<>();
        if (this.getChoice() != null) {
            properties.put(WHEN_LABEL, this.getChoice());
        }
        if (this.getOtherwise() != null) {
            properties.put(OTHERWISE_LABEL, this.getOtherwise());
        }

        return properties;
    }



    @Override
    protected void assignProperty(final Parameter parameter) {
        //We don't have properties
    }

    @Override
    protected void assignAttribute(final Parameter parameter) {
        //We don't have properties
    }

    public Otherwise getOtherwise() {
        return otherwise;
    }

    public void setOtherwise(
            final Otherwise otherwise) {
        this.otherwise = otherwise;
    }

    public List<Choice> getChoice() {
        return choice;
    }

    public void setChoice(
            final List<Choice> choice) {
        this.choice = choice;
    }


}
