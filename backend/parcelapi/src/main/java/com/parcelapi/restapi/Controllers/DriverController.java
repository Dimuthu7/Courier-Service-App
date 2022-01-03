package com.parcelapi.restapi.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.parcelapi.restapi.Constants.Constants;
import com.parcelapi.restapi.Dto.UpdateOrderStatusDTO;
import com.parcelapi.restapi.Dto.UpdateUserStatusDTO;
import com.parcelapi.restapi.Dto.ViewOrderDTO;
import com.parcelapi.restapi.Models.ParcelOrderDetails;
import com.parcelapi.restapi.Services.Impl.DriverServiceImpl;
import com.parcelapi.restapi.Utility.ApiResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "4. Driver Details Controller", description = "Handle all driver related operations")
@RestController
@RequestMapping(path = Constants.REQUEST_MAPPING.DRIVER)
public class DriverController {
    
    @Autowired
    private DriverServiceImpl driverService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/getAllOrder/{userId}")
    public ResponseEntity<ApiResponse<List<ViewOrderDTO>>> getOrderById(@PathVariable("userId") long userId) {
        List<ViewOrderDTO> listOrders = new ArrayList<>();
        for(ParcelOrderDetails order : driverService.GetAllOrders(userId)) {
            ViewOrderDTO viewOrder = modelMapper.map(order, ViewOrderDTO.class);
            viewOrder.setStatus(order.getDeliveryDetails().getStatus());
            listOrders.add(viewOrder);
        }

        if(listOrders.isEmpty()) {
            return new ResponseEntity<>( new ApiResponse<List<ViewOrderDTO>>(false, null, "Cannot find the orders!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<List<ViewOrderDTO>>(true, listOrders), HttpStatus.OK);
        }
    }

    @PutMapping(value = "/updateOrderStatus/")
    public ResponseEntity<ApiResponse<String>> updateOrderStatus(@Valid @RequestBody UpdateOrderStatusDTO orderStatus) {
        String updateStatus = driverService.UpdateOrderStatus(orderStatus.getOrderId(), orderStatus.getStatus());
        return new ResponseEntity<>(new ApiResponse<String>(true, updateStatus), HttpStatus.OK);
    }

    @PutMapping(value = "/updateDriverStatus/")
    public ResponseEntity<ApiResponse<String>> updateDriverStatus(@Valid @RequestBody UpdateUserStatusDTO userStatus) {
        String updateStatus = driverService.UpdateDriverStatus(userStatus.getUserId(), userStatus.getStatus());
        return new ResponseEntity<>(new ApiResponse<String>(true, updateStatus), HttpStatus.OK);
    }
}
