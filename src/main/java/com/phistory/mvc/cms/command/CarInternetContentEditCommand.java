package com.phistory.mvc.cms.command;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.phistory.mvc.cms.form.CarInternetContentForm;

/**
 * Command class to save or edit a {@link CarInternetContentForm}
 * 
 * @author gonzalo
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarInternetContentEditCommand
{
	private List<CarInternetContentForm> carInternetContentForms = new ArrayList<>();
}
