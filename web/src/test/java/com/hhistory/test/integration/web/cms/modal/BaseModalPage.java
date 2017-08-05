package com.hhistory.test.integration.web.cms.modal;

import com.hhistory.test.integration.web.BasePage;
import org.openqa.selenium.WebDriver;

/**
 * Created by gonzalo on 7/17/16.
 */
public class BaseModalPage extends BasePage
{
    protected static final String DATA_BB_HANDLER_HTML_ATTRIBUTE = "data-bb-handler";
    protected static final String CONFIRM_HANDLER = "confirm";
    protected static final String CANCEL_HANDLER = "cancel";


    public BaseModalPage(WebDriver webDriver) {
        super(webDriver);
    }
}
