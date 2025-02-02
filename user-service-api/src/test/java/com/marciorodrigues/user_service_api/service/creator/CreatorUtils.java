package com.marciorodrigues.user_service_api.service.creator;

import lombok.experimental.UtilityClass;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@UtilityClass
public class CreatorUtils {

    private static final PodamFactory podamFactory = new PodamFactoryImpl();

    public static <T> T generatedMock(final Class<T> clazz){
        return podamFactory.manufacturePojo(clazz);
    }
}
