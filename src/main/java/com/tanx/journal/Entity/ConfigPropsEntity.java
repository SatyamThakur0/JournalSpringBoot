package com.tanx.journal.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Config_App_Properties")
public class ConfigPropsEntity {
    private String key;
    private String value;
}
