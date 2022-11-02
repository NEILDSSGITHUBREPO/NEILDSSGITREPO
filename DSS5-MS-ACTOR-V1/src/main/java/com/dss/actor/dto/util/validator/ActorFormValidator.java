package com.dss.actor.dto.util.validator;

import com.dss.actor.dto.ActorForm;

import java.util.HashMap;
import java.util.Map;

/**
 * Actor form validator utility class
 */
public class ActorFormValidator {

    /**
     * Validates the ActorForm
     * @Param ActorForm
     * @Return Map<String, ValidationError>
     * */
    public static Map<String, ValidationError> validateActorForm(ActorForm actorForm){
        Map<String, ValidationError> fieldMessage = new HashMap<>();

        if(actorForm.getFirstName() == null){
            fieldMessage.put("firstName", ValidationError.UNDEFINED_FIELD);
        }

        if(actorForm.getLastName() == null){
            fieldMessage.put("lastName", ValidationError.UNDEFINED_FIELD);
        }

        if(actorForm.getGender() == null){
            fieldMessage.put("gender", ValidationError.UNDEFINED_FIELD);
        }else if(actorForm.getGender().length() > 1){
            fieldMessage.put("gender", ValidationError.FORMAT_MISMATCH);
        }

        if(actorForm.getAge() == null){
            fieldMessage.put("age", ValidationError.UNDEFINED_FIELD);
        }

        return fieldMessage;
    }
}
