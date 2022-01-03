package com.parcelapi.restapi.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.parcelapi.restapi.Constants.Constants;
import com.parcelapi.restapi.Dto.CreateApproveOrderDTO;
import com.parcelapi.restapi.Dto.ViewDiscountInfoDTO;
import com.parcelapi.restapi.Dto.ViewFeedbackDTO;
import com.parcelapi.restapi.Dto.ViewOrderDTO;
import com.parcelapi.restapi.Dto.ViewUserDetailsDTO;
import com.parcelapi.restapi.Models.ParcelOrderApprovalDetails;
import com.parcelapi.restapi.Models.ParcelOrderDetails;
import com.parcelapi.restapi.Models.ParcelOrderFeedbackDetails;
import com.parcelapi.restapi.Models.UserDetails;
import com.parcelapi.restapi.Services.UserService;
import com.parcelapi.restapi.Services.Impl.AdminServiceImpl;
import com.parcelapi.restapi.Services.Impl.OrderSericeImpl;
import com.parcelapi.restapi.Utility.ApiResponse;
import org.springframework.web.bind.annotation.PathVariable;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "4. Admin Details Controller", description = "Handle all admin related operations")
@RestController
@RequestMapping(path = Constants.REQUEST_MAPPING.ADMIN)
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private OrderSericeImpl orderSerice;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/getAllOrders/")
    public ResponseEntity<ApiResponse<List<ViewOrderDTO>>> getAllOrders() {
        List<ViewOrderDTO> listOrders = new ArrayList<>();
        for(ParcelOrderDetails order : adminService.GetAllOrders()) {
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

        if (listOrders.isEmpty()) {
            return new ResponseEntity<>( new ApiResponse<List<ViewOrderDTO>>(false, null, "Cannot find the orders!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<List<ViewOrderDTO>>(true, listOrders), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getAllUsers/")
    public ResponseEntity<ApiResponse<List<ViewUserDetailsDTO>>> getAllUsers() {
        List<ViewUserDetailsDTO> listUsers = new ArrayList<>();
        for (UserDetails user : adminService.GetAllUsers()) {
            listUsers.add(modelMapper.map(user, ViewUserDetailsDTO.class));
        }

        if (listUsers.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<List<ViewUserDetailsDTO>>(false, null, "Cannot find the users!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<List<ViewUserDetailsDTO>>(true, listUsers), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getAllFeedbacks/")
    public ResponseEntity<ApiResponse<List<ViewFeedbackDTO>>> getAllFeedbacks() {
        List<ViewFeedbackDTO> listFeedbacks = new ArrayList<>();
        for(ParcelOrderFeedbackDetails feedback : adminService.GetAllFeedbacks()) {
            listFeedbacks.add(modelMapper.map(feedback, ViewFeedbackDTO.class));
        }

        if (listFeedbacks.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<List<ViewFeedbackDTO>>(false, null, "Cannot find the feedbacks!"),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<List<ViewFeedbackDTO>>(true, listFeedbacks),
                    HttpStatus.OK);
        }
    }

    @PostMapping(value = "/approveOrder")
    public ResponseEntity<ApiResponse<String>> ApproveOrder(@Valid @RequestBody CreateApproveOrderDTO orderApprove) {
        orderApprove.setUser(userService.GetUserDetails(orderApprove.getUserId()));
        orderApprove.setOrder(orderSerice.getParcelOrderDetails(orderApprove.getOrderId()));
        ParcelOrderApprovalDetails approveOrder = adminService
                .ApproveOrder(modelMapper.map(orderApprove, ParcelOrderApprovalDetails.class),
                        orderApprove.getDriverId());

        return new ResponseEntity<>(new ApiResponse<String>(true, "Successfully approved the order. Approved ID : " + approveOrder.getApprovalId()),
                HttpStatus.CREATED);
    }

    @GetMapping(value = "/getDiscountInfo/{orderId}")
    public ResponseEntity<ApiResponse<ViewDiscountInfoDTO>> getDiscountInfoByOrderId(@PathVariable("orderId") long orderId) {
        ViewDiscountInfoDTO discountObj = adminService.getDiscountInfo(orderId);
        return new ResponseEntity<>(new ApiResponse<ViewDiscountInfoDTO>(true, discountObj), HttpStatus.OK);
    }
}
