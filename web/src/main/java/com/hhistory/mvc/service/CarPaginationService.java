package com.hhistory.mvc.service;

import com.hhistory.mvc.dto.CarPaginationDTO;
import com.hhistory.mvc.dto.PaginationDTO;
import org.springframework.ui.Model;

/**
 * Created by Gonzalo Carbajosa on 7/08/17.
 */
public interface CarPaginationService {

    CarPaginationDTO paginate(Model model, CarPaginationDTO paginationDTO);
}
