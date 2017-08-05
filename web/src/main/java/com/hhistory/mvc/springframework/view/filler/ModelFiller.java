package com.hhistory.mvc.springframework.view.filler;

import org.springframework.ui.Model;

/**
 * Fills a Spring Framework {@link Model} with information
 *
 * @author gonzalo
 */
public interface ModelFiller {

    /**
     * Fill the model with information
     *
     * @param model
     */
    Model fillModel(Model model);
}
