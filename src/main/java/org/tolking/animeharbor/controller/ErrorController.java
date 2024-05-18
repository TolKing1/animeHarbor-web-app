package org.tolking.animeharbor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.tolking.animeharbor.constant.ControllerConstant.ERROR_URL;

@Controller
@RequestMapping(ERROR_URL)
public class ErrorController {

    @GetMapping("/maxUploadSizeExceeded")
    public String maxUploadSizeExceeded() {
        return "error/maxUploadSizeExceeded";
    }

    @GetMapping("/disabled")
    public String account() {
        return "error/accountIsDisabled";
    }


}
