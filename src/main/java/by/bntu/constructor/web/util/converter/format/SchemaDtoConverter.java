package by.bntu.constructor.web.util.converter.format;

import by.bntu.constructor.web.dto.SchemaDTO;

public interface SchemaDtoConverter {


    String to(SchemaDTO schemaDTO);

    SchemaDTO from(String data);

}
