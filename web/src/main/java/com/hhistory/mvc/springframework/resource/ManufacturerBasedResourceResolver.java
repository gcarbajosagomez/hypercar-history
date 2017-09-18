package com.hhistory.mvc.springframework.resource;

import com.hhistory.mvc.controller.BaseControllerData;
import com.hhistory.mvc.manufacturer.Manufacturer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

import static com.hhistory.mvc.controller.BaseControllerData.STATIC_RESOURCES_URI;

/**
 * Created by Gonzalo Carbajosa on 17/09/17.
 */
public class ManufacturerBasedResourceResolver extends PathResourceResolver {

    @Override
    public Resource resolveResource(HttpServletRequest request,
                                    String requestPath,
                                    List<? extends Resource> locations,
                                    ResourceResolverChain chain) {

        Manufacturer manufacturer = (Manufacturer) request.getAttribute(BaseControllerData.MANUFACTURER_DATA);

        return super.resolveResource(request,
                                     requestPath,
                                     Collections.singletonList(new ClassPathResource(STATIC_RESOURCES_URI +
                                                                                     manufacturer.getName() + "/")),
                                     chain);
    }
}
