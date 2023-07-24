package com.yellow.ordermanageryellow.controller;

import com.yellow.ordermanageryellow.Service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Graph")

public class GraphController {
@Autowired
    private GraphService graphService;

        @GetMapping("/topEmploeey")
    public void topEmploeey(){  graphService.topEmployee()  ;}





}
