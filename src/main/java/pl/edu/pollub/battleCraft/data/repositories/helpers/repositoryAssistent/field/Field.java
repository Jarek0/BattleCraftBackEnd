package pl.edu.pollub.battleCraft.data.repositories.helpers.repositoryAssistent.field;


import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.SimpleProjection;

import java.util.function.Function;

public class Field {
    public String name;
    public String value;
    public Function<String,Projection> operation;
    public Field(String name, String value, Function<String,Projection> operation){
        this.name = name;
        this.value = value;
        this.operation = operation;
    }

    public Field(String name, String value){
        this.name = name;
        this.value = value;
        this.operation = Projections::property;
    }
}
