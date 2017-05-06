package com.phistory.mvc.dto;

import com.phistory.data.model.GenericEntity;
import com.phistory.data.model.car.Car;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO class that transports pagination data
 *
 * @author Gonzalo
 */
@Data
@NoArgsConstructor
public class PaginationDTO {

    public static final Integer ITEMS_PER_PAGE_DEFAULT_VALUE = 8;

    private static final Integer PAG_NUM_DEFAULT_VALUE = 1;

    private Integer pagNum       = PAG_NUM_DEFAULT_VALUE;
    private Integer itemsPerPage = ITEMS_PER_PAGE_DEFAULT_VALUE;
    private Integer             firstResult;
    private List<GenericEntity> items;

    public PaginationDTO(Integer pagNum, Integer itemsPerPage) {
        this.pagNum = pagNum;
        this.itemsPerPage = itemsPerPage;
    }

    public void setPn(Integer pagNum) {
        this.pagNum = pagNum;
    }

    public void setCpp(Integer carsPerPage) {
        this.itemsPerPage = carsPerPage;
    }

    public void setMpp(Integer manufacturersPerPage) {
        this.itemsPerPage = manufacturersPerPage;
    }

    /**
     * Calculate the index (0 based) of the first result based on the number of the current page and items per page
     */
    public int getFirstResult() {
        if (this.itemsPerPage != null && this.pagNum > 1) {
            int firstResult = (this.pagNum - 1) * this.itemsPerPage;
            if (firstResult > 0) {
                //it's 0 based
                return firstResult--;
            }
        }
        return 0;
    }

    /**
     * Calculate the index (0 based) of the last result based on the number of the current page and items per page
     */
    public int getLastResult() {
        if (this.itemsPerPage != null) {
            return this.getFirstResult() + itemsPerPage;
        }
        return 0;
    }
}