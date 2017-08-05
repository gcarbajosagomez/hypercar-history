package com.hhistory.mvc.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Command class to load pictures
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureLoadCommand {

    private PictureLoadAction action;
    private Long              entityId;
}
