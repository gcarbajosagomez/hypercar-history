package com.hhistory.mvc.cms.form;

/**
 * Adapts {@link CarInternetContentForm}s to {@link EditForm}s
 *
 * Created by Gonzalo Carbajosa on 21/05/17.
 */
public class CarInternetContentFormAdapter implements EditForm {

    private CarInternetContentForm carInternetContentForm;

    public CarInternetContentFormAdapter(CarInternetContentForm carInternetContentForm) {
        this.carInternetContentForm = carInternetContentForm;
    }

    @Override
    public Long getId() {
        return this.carInternetContentForm.getId();
    }

    public CarInternetContentForm adapt() {
        return this.carInternetContentForm;
    }
}
