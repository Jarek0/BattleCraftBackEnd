package pl.edu.pollub.battleCraft.serviceLayer.services.helpers.implementations;

import org.springframework.stereotype.Component;
import pl.edu.pollub.battleCraft.serviceLayer.exceptions.CheckedExceptions.PageOfEntities.OperaionOnDataBaseFailedException;
import pl.edu.pollub.battleCraft.serviceLayer.services.helpers.interfaces.UniqueNamesValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UniqueNamesValidatorImpl implements UniqueNamesValidator{

    public void validateUniqueNamesElementsToDelete(List<String> validUniqueNames, String... uniqueNamesToValidate){
        List<String> notValidUniqueNames =
                this.validateUniqueNamesBeforeOperationOnDatabase(validUniqueNames,uniqueNamesToValidate);

        if(notValidUniqueNames.size()>0)
            throw new OperaionOnDataBaseFailedException(
                    new StringBuilder("Elements ").append(String.join(", ", notValidUniqueNames))
                            .append(" are not deleted because if you want delete element you must ban it firstly").toString());
    }

    public void validateUniqueNamesElementsToAccept(List<String> validUniqueNames, String... uniqueNamesToValidate){
        List<String> notValidUniqueNames =
                this.validateUniqueNamesBeforeOperationOnDatabase(validUniqueNames,uniqueNamesToValidate);
        if(notValidUniqueNames.size()>0)
            throw new OperaionOnDataBaseFailedException(
                    new StringBuilder("Elements ").append(String.join(", ", notValidUniqueNames))
                            .append(" are not accepted because you can accept only new elements and not banned").toString());
    }

    public void validateUniqueNamesElementsToReject(List<String> validUniqueNames, String... uniqueNamesToValidate){
        List<String> notValidUniqueNames =
                this.validateUniqueNamesBeforeOperationOnDatabase(validUniqueNames,uniqueNamesToValidate);
        if(notValidUniqueNames.size()>0)
            throw new OperaionOnDataBaseFailedException(
                    new StringBuilder("Elements ").append(String.join(", ", notValidUniqueNames))
                            .append(" are not deleted because if you want delete element you must ban it firstly").toString());
    }

    public void validateUniqueNamesElementsToAdvance(List<String> validUniqueNames, String... uniqueNamesToValidate){
        List<String> notValidUniqueNames =
                this.validateUniqueNamesBeforeOperationOnDatabase(validUniqueNames,uniqueNamesToValidate);
        if(notValidUniqueNames.size()>0)
            throw new OperaionOnDataBaseFailedException(
                    new StringBuilder("Users ").append(String.join(", ", notValidUniqueNames))
                            .append(" are not advance to Organizer because if you want advance user to Organizer he must by a Accepted").toString());
    }

    public void validateUniqueNamesElementsToDegrade(List<String> validUniqueNames, String... uniqueNamesToValidate){
        List<String> notValidUniqueNames =
                this.validateUniqueNamesBeforeOperationOnDatabase(validUniqueNames,uniqueNamesToValidate);
        if(notValidUniqueNames.size()>0)
            throw new OperaionOnDataBaseFailedException(
                    new StringBuilder("Users ").append(String.join(", ", notValidUniqueNames))
                            .append(" are not degrade to Accepted because if you want degrade user to Accepted he must by a Organizer").toString());
    }

    private List<String> validateUniqueNamesBeforeOperationOnDatabase(List<String> validUniqueNames, String... uniqueNamesToValidate){
        List<String> notValidUniqueNames = new ArrayList<String>(Arrays.asList(uniqueNamesToValidate));
        notValidUniqueNames.removeAll(validUniqueNames);
        return notValidUniqueNames;
    }
}