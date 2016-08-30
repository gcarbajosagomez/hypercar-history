package com.phistory.mvc.propertyEditor;

import com.phistory.mvc.command.PictureLoadAction;

import java.beans.PropertyEditorSupport;

/**
 * Created by gonzalo on 8/30/16.
 */
public class PictureLoadActionPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String pictureLoadAction) throws IllegalArgumentException {
        super.setValue(PictureLoadAction.map(pictureLoadAction));
    }
}
