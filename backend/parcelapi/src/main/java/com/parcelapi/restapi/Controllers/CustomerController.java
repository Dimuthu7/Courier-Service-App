package com.parcelapi.restapi.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.parcelapi.restapi.Constants.Constants;
import com.parcelapi.restapi.Dto.CreateFeedbackDTO;
import com.parcelapi.restapi.Dto.CreateOrderDTO;
import com.parcelapi.restapi.Dto.ViewOrderDTO;
import com.parcelapi.restapi.Models.ParcelOrderDetails;
import com.parcelapi.restapi.Models.ParcelOrderFeedbackDetails;
import com.parcelapi.restapi.Services.Impl.OrderSericeImpl;
import com.parcelapi.restapi.Services.Impl.UserServiceImpl;
import com.parcelapi.restapi.Utility.ApiResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "3. Customer Details Controller", description = "Handle all customer related operations")
@RestController
@RequestMapping(path = Constants.REQUEST_MAPPING.CUSTOMER)
public class CustomerController {
    
    @Autowired
    private OrderSericeImpl orderSerice;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping(value = "/createOrder")
    public ResponseEntity<ApiResponse<String>> createOrder(@Valid @RequestBody CreateOrderDTO order) {
        order.setUser(userService.GetUserDetails(order.getUserId()));
        ParcelOrderDetails saveOrderObj = orderSerice.createOrder(modelMapper.map(order, ParcelOrderDetails.class));
        return new ResponseEntity<>(new ApiResponse<String>(true, "Successfully saved the order! Order ID : " + saveOrderObj.getOrderId()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getOrderInfo/{orderId}")
    public ResponseEntity<ApiResponse<ViewOrderDTO>> getOrderById(@PathVariable("orderId") long orderId) {
        ParcelOrderDetails orderObj = orderSerice.getParcelOrderDetails(orderId);
        ViewOrderDTO viewOrder = modelMapper.map(orderObj, ViewOrderDTO.class);
        
        if(orderObj != null) {
            if(orderObj.getDeliveryDetails() != null) {
                viewOrder.setStatus(orderObj.getDeliveryDetails().getStatus());
            } else {
                viewOrder.setStatus("P");
            }
            return new ResponseEntity<>(new ApiResponse<ViewOrderDTO>(true, viewOrder), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<ViewOrderDTO>(false, null, "Cannot find the order!"), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getAllOrderInfo/{userId}")
    public ResponseEntity<ApiResponse<List<ViewOrderDTO>>> getAllOrderByUserId(@PathVariable("userId") long userId) {
        List<ViewOrderDTO> listOrders = new ArrayList<>();
        for(ParcelOrderDetails order : orderSerice.getAllOrdersByCustId(userId)) {
            ViewOrderDTO orderDto = modelMapper.map(order, ViewOrderDTO.class);
            if(order.getDeliveryDetails() != null) {
                orderDto.setStatus(order.getDeliveryDetails().getStatus());
            } else if(order.getOrderApprovalDetails() != null) {
                orderDto.setStatus(order.getOrderApprovalDetails().getApprovalStatus());
            } else {
                orderDto.setStatus("P");
            }
            listOrders.add(orderDto);
        }

        if(listOrders.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse<List<ViewOrderDTO>>(false, null, "Cannot find the order!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<List<ViewOrderDTO>>(true, listOrders), HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "deleteOrder/{orderId}")
    public ResponseEntity<ApiResponse<String>> deleteOrderById(@PathVariable("orderId") long orderId) {
        String deleteOrder = orderSerice.deleteParcelOrder(orderId);
        return new ResponseEntity<>(new ApiResponse<String>(true, deleteOrder), HttpStatus.OK);
    }

    @PostMapping(value = "/createFeedback")
    public ResponseEntity<ApiResponse<String>> createFeedback(@Valid @RequestBody CreateFeedbackDTO feedback) {
        feedback.setUser(userService.GetUserDetails(feedback.getUserId()));
        feedback.setOrder(orderSerice.getParcelOrderDetails(feedback.getOrderId()));

        ParcelOrderFeedbackDetails saveOrderFeedbackObj = orderSerice.createFeedback(modelMapper.map(feedback, ParcelOrderFeedbackDetails.class));
        return new ResponseEntity<>(new ApiResponse<String>(true, "Successfully saved the feedback! Feedback ID : " + saveOrderFeedbackObj.getFeedbackId()), HttpStatus.CREATED);
    }
}
