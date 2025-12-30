package com.tanx.journal.cache;

import com.tanx.journal.Entity.ConfigPropsEntity;
import com.tanx.journal.Repository.ConfigPropsRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class AppCache {
    public enum KEYS{
        QUOTES_API_URL;
    }
    private Map<String, String> appCache;
    @Autowired
    private ConfigPropsRepository configPropsRepository;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigPropsEntity> allProps = configPropsRepository.findAll();
        for(ConfigPropsEntity props : allProps){
            appCache.put(props.getKey(), props.getValue());
        }
    }
}
